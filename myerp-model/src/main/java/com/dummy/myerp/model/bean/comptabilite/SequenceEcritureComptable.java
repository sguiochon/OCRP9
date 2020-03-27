package com.dummy.myerp.model.bean.comptabilite;


/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

	private String journalCode;
	private Integer annee;
	private Integer derniereValeur;

	public SequenceEcritureComptable() {
	}

	public SequenceEcritureComptable(Integer annee, Integer derniereValeur) {
		this.annee = annee;
		this.derniereValeur = derniereValeur;
	}

	public SequenceEcritureComptable(String journalCode, Integer annee, Integer derniereValeur) {
		this.journalCode = journalCode;
		this.annee = annee;
		this.derniereValeur = derniereValeur;
	}

	public String getJournalCode() {
		return journalCode;
	}

	public void setJournalCode(String journalCode) {
		this.journalCode = journalCode;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer pAnnee) {
		annee = pAnnee;
	}

	public Integer getDerniereValeur() {
		return derniereValeur;
	}

	public void setDerniereValeur(Integer pDerniereValeur) {
		derniereValeur = pDerniereValeur;
	}

	@Override
	public String toString() {
		final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
		final String vSEP = ", ";
		vStB.append("{")
				.append("journal_code=").append(journalCode)
				.append(vSEP)
				.append("annee=").append(annee)
				.append(vSEP).append("derniereValeur=").append(derniereValeur)
				.append("}");
		return vStB.toString();
	}
}
