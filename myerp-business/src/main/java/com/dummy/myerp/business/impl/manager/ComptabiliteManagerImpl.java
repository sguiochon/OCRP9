package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.technical.exception.TechnicalException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

	@Override
	public List<CompteComptable> getListCompteComptable() {
		return getDaoProxy().getComptabiliteDao().getListCompteComptable();
	}

	@Override
	public List<JournalComptable> getListJournalComptable() {
		return getDaoProxy().getComptabiliteDao().getListJournalComptable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EcritureComptable> getListEcritureComptable() {
		return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
	}

	/**
	 * 1.  Remonter depuis la persitance la dernière valeur de la séquence du journal pour l'année de l'écriture
	 * (table sequence_ecriture_comptable)
	 *   S'il n'y a aucun enregistrement pour le journal pour l'année concernée : Utiliser le numéro 1.
	 *  Sinon : Utiliser la dernière valeur + 1
	 * 2.  Mettre à jour la référence de l'écriture avec la référence calculée (RG_Compta_5)
	 * 3.  Enregistrer (insert/update) la valeur de la séquence en persitance (table sequence_ecriture_comptable)
	 *
	 * {@inheritDoc}
	 */
	// TODO à tester
	@Override
	public synchronized void addReference(EcritureComptable ecritureComptable) {
		TransactionStatus transactionStatus = null;
		try {
			transactionStatus = getTransactionManager().beginTransactionMyERP();

			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(ecritureComptable.getDate());
			int yearEcriture = calendar.get(Calendar.YEAR);

			String journalCode = ecritureComptable.getJournal().getCode();
			Integer lastValue = getDaoProxy().getComptabiliteDao().getDerniereValeurSequenceEcriture(journalCode, yearEcriture);
			Integer newValue;
			if (lastValue == null) {
				newValue = 1;
				ecritureComptable.setReference(buildReference(journalCode, yearEcriture, newValue));
				SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, yearEcriture, newValue);
				getDaoProxy().getComptabiliteDao().insertSequenceEcriture(sequenceEcritureComptable);
			} else {
				newValue = ++lastValue;
				ecritureComptable.setReference(buildReference(journalCode, yearEcriture, newValue));
				SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, yearEcriture, newValue);
				getDaoProxy().getComptabiliteDao().updateSequenceEcriture(sequenceEcritureComptable);
			}
			getDaoProxy().getComptabiliteDao().updateEcritureComptable(ecritureComptable);
			getTransactionManager().commitMyERP(transactionStatus);
		} catch (TechnicalException e) {
			getTransactionManager().rollbackMyERP(transactionStatus);
		}

	}

	private String buildReference(String journalCode, Integer annee, Integer compteur) throws TechnicalException {
		if (compteur > 99999)
			throw new TechnicalException("Depassement de capacite de numerotation des ecritures comptables.");
		return journalCode + "-" + annee + "/" + String.format("%05d", compteur);
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO à tester
	@Override
	public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		this.checkEcritureComptableUnit(pEcritureComptable);
		this.checkEcritureComptableContext(pEcritureComptable);
	}


	/**
	 * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
	 * c'est à dire indépendemment du contexte (unicité de la référence, exercice comptable non cloturé...)
	 *
	 * @param ecritureComptable -
	 * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
	 */
	// TODO tests à compléter
	protected void checkEcritureComptableUnit(EcritureComptable ecritureComptable) throws FunctionalException {
		// ===== Vérification des contraintes unitaires sur les attributs de l'écriture
		Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(ecritureComptable);
		if (!vViolations.isEmpty()) {
			throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
					new ConstraintViolationException("L'écriture comptable ne respecte pas une/des contrainte(s) unitaire(s) sur les attributs de l'ecriture comptable: ", vViolations));
		}

		// ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
		if (!ecritureComptable.isEquilibree()) {
			throw new FunctionalException("L'écriture comptable n'est pas équilibrée. Violation de la règle RG_Compta_2");
		}

		// ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
		int vNbrCredit = 0;
		int vNbrDebit = 0;
		for (LigneEcritureComptable vLigneEcritureComptable : ecritureComptable.getListLigneEcriture()) {
			if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(), BigDecimal.ZERO)) != 0) {
				vNbrCredit++;
			}
			if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(), BigDecimal.ZERO)) != 0) {
				vNbrDebit++;
			}
		}
		// On teste le nombre de lignes car si l'écriture à une seule ligne
		//      avec un montant au débit et un montant au crédit ce n'est pas valable
		if (ecritureComptable.getListLigneEcriture().size() < 2 || vNbrCredit < 1 || vNbrDebit < 1) {
			throw new FunctionalException("L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit. Violation de la règle RG_Compta_3");
		}

		// TODO ===== RG_Compta_5 : contenu de la référence
		Instant ecritureComptableInstant = Instant.ofEpochMilli(ecritureComptable.getDate().getTime());
		Integer ecritureComptableYear = LocalDateTime.ofInstant(ecritureComptableInstant, ZoneId.of("UTC+1")).toLocalDate().getYear();;
		if (!ecritureComptableYear.equals(readYearFromEcritureComptable(ecritureComptable))){
			throw new FunctionalException("Incohérence entre la date de l'écriture comptable et l'année indiquée dans se référence");
		}

		String ecritureComptableJournalCode = ecritureComptable.getJournal().getCode();
		if (!ecritureComptableJournalCode.equals(readJournalCodeFromEcritureComptable(ecritureComptable))){
			throw new FunctionalException("Incohérence entre la code journal de l'écriture comptable et le code journal indiqué dans se référence");
		}
	}


	/**
	 * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
	 * (unicité de la référence, année comptable non cloturé...)
	 *
	 * @param pEcritureComptable -
	 * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
	 */
	protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
		// ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
		if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
			try {
				// Recherche d'une écriture ayant la même référence
				EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(pEcritureComptable.getReference());

				// Si l'écriture à vérifier est une nouvelle écriture (id == null),
				// ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
				// c'est qu'il y a déjà une autre écriture avec la même référence
				if (pEcritureComptable.getId() == null || !pEcritureComptable.getId().equals(vECRef.getId())) {
					throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
				}
			} catch (NotFoundException vEx) {
				// Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		this.checkEcritureComptable(pEcritureComptable);
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEcritureComptable(Integer pId) {
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}
	}

	private Integer readYearFromEcritureComptable(EcritureComptable ecritureComptable){
		if (ecritureComptable!=null){
			int dashPosition = ecritureComptable.getReference().indexOf("-");
			return Integer.valueOf(ecritureComptable.getReference().substring(++dashPosition, dashPosition+4));
		}
		else {
			return null;
		}
	}

	private String readJournalCodeFromEcritureComptable(EcritureComptable ecritureComptable){
		if (ecritureComptable!=null){
			int dashPosition = ecritureComptable.getReference().indexOf("-");
			return ecritureComptable.getReference().substring(0, dashPosition);
		}
		else {
			return null;
		}
	}

	private String readNumeroFromEcritureComptable(EcritureComptable ecritureComptable){
		if (ecritureComptable!=null){
			int slashPosition = ecritureComptable.getReference().indexOf("/");
			return ecritureComptable.getReference().substring(++slashPosition, slashPosition+5);
		}
		else {
			return null;
		}
	}
}
