package com.dummy.myerp.model.bean.comptabilite;


/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

	private Integer annee;
	/**
	 * La dernière valeur utilisée
	 */
	private Integer derniereValeur;

	public SequenceEcritureComptable() {
	}

	/**
	 * Constructeur
	 *
	 * @param pAnnee          -
	 * @param pDerniereValeur -
	 */
	public SequenceEcritureComptable(Integer pAnnee, Integer pDerniereValeur) {
		annee = pAnnee;
		derniereValeur = pDerniereValeur;
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
				.append("annee=").append(annee)
				.append(vSEP).append("derniereValeur=").append(derniereValeur)
				.append("}");
		return vStB.toString();
	}
}
