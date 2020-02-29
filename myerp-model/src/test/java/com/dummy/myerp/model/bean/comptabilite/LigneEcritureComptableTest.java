package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class LigneEcritureComptableTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testEmptyConstructor() {
		// Act
		LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
		ligneEcritureComptable.setCompteComptable(new CompteComptable(20, "Compte Comptable 20"));
		ligneEcritureComptable.setCredit(BigDecimal.valueOf(14.5));
		ligneEcritureComptable.setDebit(BigDecimal.valueOf(-345.67));
		ligneEcritureComptable.setLibelle("Libellé");

		// Assert
		assertEquals("Test of 'compteComptable' getter and setter", Integer.valueOf(20), ligneEcritureComptable.getCompteComptable().getNumero());
		assertEquals("Test of 'credit' getter and setter", BigDecimal.valueOf(14.5), ligneEcritureComptable.getCredit());
		assertEquals("Test of 'debit' getter and setter", BigDecimal.valueOf(-345.67), ligneEcritureComptable.getDebit());
		assertEquals("Test of 'credit' getter and setter", "Libellé", ligneEcritureComptable.getLibelle());
	}

	@Test
	public void testConstructionWhenSuccessful() throws FunctionalException {
		// Act
		LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
				new CompteComptable(20, "Compte Comptable 20"),
				"Libellé",
				BigDecimal.ZERO,
				BigDecimal.valueOf(14.5)
		);

		// Assert
		assertEquals("Test of 'compteComptable' getter and setter", Integer.valueOf(20), ligneEcritureComptable.getCompteComptable().getNumero());
		assertEquals("Test of 'credit' getter and setter", BigDecimal.valueOf(14.5), ligneEcritureComptable.getCredit());
		assertEquals("Test of 'debit' getter and setter", BigDecimal.ZERO, ligneEcritureComptable.getDebit());
		assertEquals("Test of 'credit' getter and setter", "Libellé", ligneEcritureComptable.getLibelle());
	}

	@Test
	public void testConstructionWhenFailsBecauseNoCreditNorDebit1() throws FunctionalException {
		// Arrange
		exceptionRule.expect(FunctionalException.class);
		exceptionRule.expectMessage("[RG_Compta_3]");

		// Act
		LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
				new CompteComptable(20, "Compte Comptable 20"),
				"Libellé",
				BigDecimal.ZERO,
				BigDecimal.ZERO
		);

		// Assert
		// Exception is raised and catched by Junit and exception message as expected...
	}

	@Test
	public void testConstructionWhenFailsBecauseNoCreditNorDebit2() throws FunctionalException {
		// Arrange
		exceptionRule.expect(FunctionalException.class);
		exceptionRule.expectMessage("[RG_Compta_3]");

		// Act
		LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
				new CompteComptable(20, "Compte Comptable 20"),
				"Libellé",
				null,
				null
		);

		// Assert
		// Exception is raised and catched by Junit and exception message as expected...
	}

	@Test
	public void testConstructionWhenFailsBecauseNoCreditNorDebit3() throws FunctionalException {
		// Arrange
		exceptionRule.expect(FunctionalException.class);
		exceptionRule.expectMessage("[RG_Compta_3]");

		// Act
		LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
				new CompteComptable(20, "Compte Comptable 20"),
				"Libellé",
				BigDecimal.ZERO,
				null
		);

		// Assert
		// Exception is raised and catched by Junit and exception message as expected...
	}

	@Test
	public void testConstructionWhenFailsBecauseNoCreditNorDebit4() throws FunctionalException {
		// Arrange
		exceptionRule.expect(FunctionalException.class);
		exceptionRule.expectMessage("[RG_Compta_3]");

		// Act
		LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
				new CompteComptable(20, "Compte Comptable 20"),
				"Libellé",
				null,
				BigDecimal.ZERO
		);

		// Assert
		// Exception is raised and catched by Junit and exception message as expected...
	}

	@Test
	public void testConstructionWhenFailsBecauseBothCreditAndDebit() throws FunctionalException {
		// Arrange
		exceptionRule.expect(FunctionalException.class);
		exceptionRule.expectMessage("[RG_Compta_3]");

		// Act
		LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(
				new CompteComptable(20, "Compte Comptable 20"),
				"Libellé",
				BigDecimal.valueOf(45.3),
				BigDecimal.valueOf(56.5)
		);

		// Assert
		// Exception is raised and catched by Junit and exception message as expected...
	}


}
