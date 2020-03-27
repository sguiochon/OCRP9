package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SequenceEcritureComptableTest {

	@Test
	public void testConstructor1() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable();
		sequence.setAnnee(2020);
		sequence.setDerniereValeur(10000);

		// Assert
		assertEquals("Test of 'annee' setter and getter", Integer.valueOf(2020), sequence.getAnnee());
		assertEquals("Test of 'derniereValeur' setter and getter", Integer.valueOf(10000), sequence.getDerniereValeur());
		assertEquals("Test of empty constructor", "SequenceEcritureComptable{journal_code=null, annee=2020, derniereValeur=10000}", sequence.toString());
	}

	@Test
	public void testConstructor2() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable(2030, 5000);

		// Assert
		assertEquals("Test of 'annee' setter and getter", Integer.valueOf(2030), sequence.getAnnee());
		assertEquals("Test of 'derniereValeur' setter and getter", Integer.valueOf(5000), sequence.getDerniereValeur());
		assertEquals("Test of empty constructor", "SequenceEcritureComptable{journal_code=null, annee=2030, derniereValeur=5000}", sequence.toString());
	}
}
