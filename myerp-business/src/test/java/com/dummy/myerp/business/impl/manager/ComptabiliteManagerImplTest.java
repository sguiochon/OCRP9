package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Date;

public class ComptabiliteManagerImplTest {

	@Rule
	public ExpectedException raisedException = ExpectedException.none();

	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

	private EcritureComptable ecritureComptable;

	@Before
	public void setUp() {
		ecritureComptable = new EcritureComptable();
	}

	@Test
	public void testCheckEcritureComptableUnitWhenInvalidFields() throws FunctionalException {
		// Arrange
		raisedException.expect(FunctionalException.class);
		raisedException.expectMessage("Au moins 2 lignes d'ecriture sont requises");
		raisedException.expectMessage("Le champ 'reference' ne respecte pas le format attendu");
		raisedException.expectMessage("Le libelle doit contenir entre 1 et 200 caractères");
		raisedException.expectMessage("La date d'écriture est obligatoire");
		raisedException.expectMessage("Un journal doit être associé à l'ecriture");

		ecritureComptable.setJournal(null);
		ecritureComptable.setDate(null);
		ecritureComptable.setLibelle("");
		ecritureComptable.setReference("AC-20/00010");
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		// Act
		manager.checkEcritureComptableUnit(ecritureComptable);
		// Assert
		// Exception raised with expected message
	}

	@Test
	public void checkEcritureComptableUnitWhenInconsistantReferenceCase1() throws FunctionalException {
		// Arrange
		raisedException.expect(FunctionalException.class);
		raisedException.expectMessage("Incohérence entre la date de l'écriture comptable et l'année indiquée dans se référence");

		ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("Libelle");
		ecritureComptable.setReference("AC-2019/00010");
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));

		// Act
		manager.checkEcritureComptableUnit(ecritureComptable);
		// Assert
		// Exception raised with expected message
	}

	@Test
	public void checkEcritureComptableUnitWhenInconsistantReferenceCase2() throws FunctionalException {
		// Arrange
		raisedException.expect(FunctionalException.class);
		raisedException.expectMessage("Incohérence entre la code journal de l'écriture comptable et le code journal indiqué dans se référence");

		ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("Libelle");
		ecritureComptable.setReference("BAC-2020/00010");
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));

		// Act
		manager.checkEcritureComptableUnit(ecritureComptable);

		// Assert
		// Exception raised with expected message
	}

	@Test
	public void checkEcritureComptableUnitWhenRG2Violation() throws Exception {
		// Arrange
		raisedException.expect(FunctionalException.class);
		raisedException.expectMessage("L'écriture comptable n'est pas équilibrée. Violation de la règle RG_Compta_2");

		ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("Libelle");
		ecritureComptable.setReference("AC-2020/00010");
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(1234)));
		// Act

		manager.checkEcritureComptableUnit(ecritureComptable);
		// Assert
		// Exception raised with expected message
	}


	@Test
	public void checkEcritureComptableUnitWhenNoViolation() throws FunctionalException {
		// Arrange
		ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("Libelle");
		ecritureComptable.setReference("AC-2020/00010");
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));

		// Act
		manager.checkEcritureComptableUnit(ecritureComptable);
		// Assert
		// No Exception raised
	}






	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG3() throws Exception {
		EcritureComptable ecritureComptable = new EcritureComptable();
		ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("Libelle");
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123.00), null));
		manager.checkEcritureComptableUnit(ecritureComptable);
	}

}
