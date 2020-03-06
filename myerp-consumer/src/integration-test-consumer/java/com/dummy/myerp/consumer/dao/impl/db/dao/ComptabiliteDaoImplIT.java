package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContextIT.xml")
public class ComptabiliteDaoImplIT {

	@Rule
	public ExpectedException raisedExcpetion = ExpectedException.none();

	@Autowired
	ComptabiliteDaoImpl comptabiliteDaoImpl;

	@Test
	//@Sql({"classpath:create_schema.sql", "classpath:create_tables.sql", "classpath:insert_data.sql"})
	public void testListCompteComptable(){
		// Act
		List<CompteComptable> compteComptableList = comptabiliteDaoImpl.getListCompteComptable();

		// Assert
		Assert.assertEquals("Vérification du nombre de comptes", 7, compteComptableList.size());
	}

	@Test
	public void testListJournalComptable(){
		// Act
		List<JournalComptable> journalComptableList = comptabiliteDaoImpl.getListJournalComptable();

		// Assert
		Assert.assertEquals("Vérification du nombre de journaux", 4, journalComptableList.size());
	}

	@Test
	public void testListEcritureComptable(){
		// Act
		List<EcritureComptable> ecritureComptableList = comptabiliteDaoImpl.getListEcritureComptable();
		// Assert
		Assert.assertNotNull("Vérification de la lecture des ecritures comptables", ecritureComptableList);
		Assert.assertEquals("Vérification de la lecture des ecritures comptables", 5, ecritureComptableList.size());
	}

	@Test
	@Sql(value={"classpath:delete-test-ecriturecomptable.sql", "classpath:create-test-ecriturecomptable.sql"})
	@Sql(value="classpath:delete-test-ecriturecomptable.sql", executionPhase = AFTER_TEST_METHOD)
	public void testGetEcritureComptableByReferenceWhenExists() throws NotFoundException {
		// Act
		final EcritureComptable test_reference = comptabiliteDaoImpl.getEcritureComptableByRef("test reference");

		// Assert
		Assert.assertEquals("Lecture d'une écriture comptable (id)",Integer.valueOf(-1000), test_reference.getId());
	}

	@Test
	@Sql(value={"classpath:delete-test-ecriturecomptable.sql", "classpath:create-test-ecriturecomptable.sql"})
	@Sql(value="classpath:delete-test-ecriturecomptable.sql", executionPhase = AFTER_TEST_METHOD)
	public void testGetEcritureComptableByIdWhenExists() throws NotFoundException {
		// Act
		final EcritureComptable test_reference = comptabiliteDaoImpl.getEcritureComptable(Integer.valueOf(-1000));

		// Assert
		Assert.assertEquals("Lecture d'une écriture comptable (id)",Integer.valueOf(-1000), test_reference.getId());
	}

	@Test
	@Sql(value="classpath:delete-test-ecriturecomptable.sql")
	public void testGetEcritureComptableByReferenceWhenNotExists() throws NotFoundException {
		// Act
		raisedExcpetion.expect(NotFoundException.class);
		raisedExcpetion.expectMessage("EcritureComptable non trouvée");
		final EcritureComptable test_reference = comptabiliteDaoImpl.getEcritureComptableByRef("test reference");

		// Assert
		// An exception is raised
	}

	@Test
	@Sql(value="classpath:delete-test-ecriturecomptable.sql")
	public void testGetEcritureComptableByIdWhenNotExists() throws NotFoundException {
		// Act
		raisedExcpetion.expect(NotFoundException.class);
		raisedExcpetion.expectMessage("EcritureComptable non trouvée");
		final EcritureComptable test_reference = comptabiliteDaoImpl.getEcritureComptable(Integer.valueOf(-1000));

		// Assert
		// An exception is raised
	}

	@Test
	@Sql(value="classpath:delete-test-ecriturecomptable.sql")
	@Sql(value = "classpath:delete-test-ecriturecomptable.sql", executionPhase = AFTER_TEST_METHOD)
	public void testInsertEcriture() throws NotFoundException{
		// Arrange
		EcritureComptable ecritureComptable = new EcritureComptable();
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("libellé...");
		ecritureComptable.setReference("test reference");
		JournalComptable journalComptable = comptabiliteDaoImpl.getListJournalComptable().get(0);
		ecritureComptable.setJournal(journalComptable);

		// Act
		comptabiliteDaoImpl.insertEcritureComptable(ecritureComptable);
		EcritureComptable createdEcritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("test reference");

		// Assert
		Assert.assertEquals("Vérification de la sauvegarde d'une écriture comptable", "test reference", createdEcritureComptable.getReference());
		Assert.assertEquals("Vérification de la sauvegarde d'une écriture comptable", "libellé...", createdEcritureComptable.getLibelle());
	}

	@Test
	@Sql(value={"classpath:delete-test-ligneecriturecomptable.sql", "classpath:delete-test-ecriturecomptable.sql", "classpath:create-test-ecriturecomptable.sql"})
	@Sql(value = {"classpath:delete-test-ligneecriturecomptable.sql"}, executionPhase = AFTER_TEST_METHOD)
	public void testUpdateEcriture() throws NotFoundException, FunctionalException {
		// Arrange
		EcritureComptable ecritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("test reference");
		CompteComptable compteComptable = comptabiliteDaoImpl.getListCompteComptable().get(0);
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable, "test credit", BigDecimal.valueOf(100.50), BigDecimal.ZERO ));
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable, "test credit", BigDecimal.ZERO, BigDecimal.valueOf(100.50)));

		// Act
		comptabiliteDaoImpl.updateEcritureComptable(ecritureComptable);
		ecritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("test reference");

		// Assert
		Assert.assertEquals("", 2, ecritureComptable.getListLigneEcriture().size());
	}


}
