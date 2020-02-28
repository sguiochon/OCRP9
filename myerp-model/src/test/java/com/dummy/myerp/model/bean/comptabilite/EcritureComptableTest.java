package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class EcritureComptableTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testConstructionBuGettersAndSetters() {
		// Arrange
		Date testDate = new Date();

		// Act
		EcritureComptable ecritureComptable = new EcritureComptable();
		ecritureComptable.setReference("Référence");
		ecritureComptable.setDate(testDate);
		ecritureComptable.setId(1010);
		ecritureComptable.setLibelle("Libellé");
		ecritureComptable.setJournal(new JournalComptable("Code journal", "Libellé journal"));

		// Assert
		assertEquals("Test of 'reference' getter and setter", "Référence", ecritureComptable.getReference());
		assertEquals("Test of 'date' getter and setter", testDate, ecritureComptable.getDate());
		assertEquals("Test of 'id' getter and setter", Integer.valueOf(1010), ecritureComptable.getId());
		assertEquals("Test of 'libelle' getter and setter", "Libellé", ecritureComptable.getLibelle());
		assertEquals("Test of 'journal' getter and setter", "JournalComptable{code='Code journal', libelle='Libellé journal'}", ecritureComptable.getJournal().toString());
	}

	@Test
	public void testIsEquilibree() throws FunctionalException {
		EcritureComptable vEcriture;
		vEcriture = new EcritureComptable();

		vEcriture.setLibelle("Equilibrée");
		vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
		vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", null));
		vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "302"));
		vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", null));
		Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
	}

	@Test
	public void isNotEquilibree() throws FunctionalException {
		EcritureComptable vEcriture;
		vEcriture = new EcritureComptable();

		vEcriture.setLibelle("Non équilibrée");
		vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
		vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", null));
		vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
		vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", null));
		Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
	}

	private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) throws FunctionalException {
		BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
		BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
		String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO).subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();

		LigneEcritureComptable ecritureComptable = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero), vLibelle, vDebit, vCredit);
		return ecritureComptable;
	}

}
