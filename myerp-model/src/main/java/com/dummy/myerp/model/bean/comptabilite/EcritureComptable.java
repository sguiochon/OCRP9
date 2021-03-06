package com.dummy.myerp.model.bean.comptabilite;


import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Bean représentant une Écriture Comptable
 */
public class EcritureComptable {

	@Valid
	@Size(min = 2, message = "Au moins 2 lignes d'ecriture sont requises")
	private final List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();
	private Integer id;
	@NotNull(message = "Un journal doit être associé à l'ecriture")
	private JournalComptable journal;
	@Pattern(regexp = "[a-zA-Z0-9]{1,5}-\\d{4}/\\d{5}", message = "Le champ 'reference' ne respecte pas le format attendu. {javax.validation.constraints.Pattern.message}")
	private String reference;
	@NotNull(message = "La date d'écriture est obligatoire")
	private Date date;
	@NotNull
	@Size(min = 1, max = 200, message = "Le libelle doit contenir entre 1 et 200 caractères")
	private String libelle;

	public Integer getId() {
		return id;
	}

	public void setId(Integer pId) {
		id = pId;
	}

	public JournalComptable getJournal() {
		return journal;
	}

	public void setJournal(JournalComptable pJournal) {
		journal = pJournal;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String pReference) {
		reference = pReference;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date pDate) {
		date = pDate;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String pLibelle) {
		libelle = pLibelle;
	}

	public List<LigneEcritureComptable> getListLigneEcriture() {
		return listLigneEcriture;
	}

	/**
	 * Calcule et renvoie le total des montants au débit des lignes d'écriture
	 *
	 * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
	 */
	public BigDecimal getTotalDebit() {
		BigDecimal totalDebit = BigDecimal.ZERO;
		for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
			if (vLigneEcritureComptable.getDebit() != null) {
				totalDebit = totalDebit.add(vLigneEcritureComptable.getDebit());
			}
		}
		return totalDebit;
	}

	/**
	 * Calcul et renvoie le total des montants au crédit des lignes d'écriture
	 *
	 * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au crédit
	 */
	public BigDecimal getTotalCredit() {
		BigDecimal totalCredit = BigDecimal.ZERO;
		for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
			if (vLigneEcritureComptable.getCredit() != null) {
				totalCredit = totalCredit.add(vLigneEcritureComptable.getCredit());
			}
		}
		return totalCredit;
	}

	/**
	 * Renvoie si l'écriture est équilibrée (TotalDebit = TotalCrédit)
	 *
	 * @return boolean
	 */
	public boolean isEquilibree() {
		return getTotalDebit().compareTo(getTotalCredit()) == 0;
	}

	@Override
	public String toString() {
		final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
		final String vSEP = ", ";
		vStB.append("{")
				.append("id=").append(id)
				.append(vSEP).append("journal=").append(journal)
				.append(vSEP).append("reference='").append(reference).append('\'')
				.append(vSEP).append("date=").append(date)
				.append(vSEP).append("libelle='").append(libelle).append('\'')
				.append(vSEP).append("totalDebit=").append(this.getTotalDebit().toPlainString())
				.append(vSEP).append("totalCredit=").append(this.getTotalCredit().toPlainString())
				.append(vSEP).append("listLigneEcriture=[\n")
				.append(StringUtils.join(listLigneEcriture, "\n")).append("\n]")
				.append("}");
		return vStB.toString();
	}
}
