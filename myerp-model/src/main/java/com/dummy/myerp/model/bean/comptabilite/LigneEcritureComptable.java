package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.model.validation.constraint.MontantComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


/**
 * Bean représentant une Ligne d'écriture comptable.
 */
public class LigneEcritureComptable {

	@NotNull
	private CompteComptable compteComptable;

	@Size(max = 200)
	private String libelle;

	@MontantComptable
	private BigDecimal debit;

	@MontantComptable
	private BigDecimal credit;

	public LigneEcritureComptable() {
	}

	/**
	 * Instantiates a new Ligne ecriture comptable.
	 *
	 * @param pCompteComptable the Compte Comptable
	 * @param pLibelle         the libelle
	 * @param pDebit           the debit
	 * @param pCredit          the credit
	 */
	public LigneEcritureComptable(CompteComptable pCompteComptable, String pLibelle, BigDecimal pDebit, BigDecimal pCredit) throws FunctionalException {
		compteComptable = pCompteComptable;
		libelle = pLibelle;
		if ((pDebit==null || pDebit.equals(BigDecimal.ZERO)) && (pCredit==null || pCredit.equals(BigDecimal.ZERO)))
			throw new FunctionalException("[RG_Compta_3] Une ligne d'ecriture DOIT avoir un Crédit ou un Débit");

		if ((pDebit!=null && !BigDecimal.ZERO.equals(pDebit)) && (pCredit!=null && !BigDecimal.ZERO.equals(pCredit)))
			throw new FunctionalException("[RG_Compta_3] Une ligne d'ecriture DOIT avoir un Crédit ou un Débit");

		debit = pDebit;
		credit = pCredit;
	}

	public CompteComptable getCompteComptable() {
		return compteComptable;
	}

	public void setCompteComptable(CompteComptable pCompteComptable) {
		compteComptable = pCompteComptable;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String pLibelle) {
		libelle = pLibelle;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal pDebit) {
		debit = pDebit;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal pCredit) {
		credit = pCredit;
	}

	@Override
	public String toString() {
		final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
		final String vSEP = ", ";
		vStB.append("{")
				.append("compteComptable=").append(compteComptable)
				.append(vSEP).append("libelle='").append(libelle).append('\'')
				.append(vSEP).append("debit=").append(debit)
				.append(vSEP).append("credit=").append(credit)
				.append("}");
		return vStB.toString();
	}
}
