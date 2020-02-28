package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JournalComptableTest {

	@Test
	public void testConstructor1() {
		// Act
		JournalComptable journalComptable = new JournalComptable();
		journalComptable.setCode("ABC");
		journalComptable.setLibelle("Libellé ABC");

		// Assert
		assertEquals("Test of code setter and getter", "ABC", journalComptable.getCode());
		assertEquals("Test of libelle setter and getter", "Libellé ABC", journalComptable.getLibelle());
		assertEquals("Test of empty constructor", "JournalComptable{code='ABC', libelle='Libellé ABC'}", journalComptable.toString());
	}

	@Test
	public void testConstructor2() {
		// Act
		JournalComptable journalComptable = new JournalComptable("DEF", "Libellé DEF");

		// Assert
		assertEquals("Test of code setter and getter", "DEF", journalComptable.getCode());
		assertEquals("Test of libelle setter and getter", "Libellé DEF", journalComptable.getLibelle());
		assertEquals("Test of empty constructor", "JournalComptable{code='DEF', libelle='Libellé DEF'}", journalComptable.toString());
	}

	@Test
	public void testFindByCodeWhenFound() {
		// Arrange
		List<JournalComptable> journalComptableList = new ArrayList<>();
		JournalComptable journalComptable = new JournalComptable("ABF", "journal numéro ABF");
		journalComptableList.add(journalComptable);

		journalComptable = new JournalComptable("FEH45", "journal numéro FEH45");
		journalComptableList.add(journalComptable);

		journalComptable = new JournalComptable("PDGH", "journal numéro PDGH");
		journalComptableList.add(journalComptable);

		// Act
		JournalComptable foundJournalComptable = JournalComptable.getByCode(journalComptableList, "PDGH");

		// Assert
		assertNotNull("Test of findByCode when found", foundJournalComptable);
		assertEquals("Test of findByCode when found", "JournalComptable{code='PDGH', libelle='journal numéro PDGH'}", foundJournalComptable.toString());
	}

	@Test
	public void testFindByCodeWhenNotFound() {
		// Arrange
		List<JournalComptable> journalComptableList = new ArrayList<>();
		JournalComptable journalComptable = new JournalComptable("ABF", "journal numéro ABF");
		journalComptableList.add(journalComptable);

		journalComptable = new JournalComptable("FEH45", "journal numéro FEH45");
		journalComptableList.add(journalComptable);

		journalComptable = new JournalComptable("PDGH", "journal numéro PDGH");
		journalComptableList.add(journalComptable);

		// Act
		JournalComptable foundJournalComptable = JournalComptable.getByCode(journalComptableList, "AB");

		// Assert
		assertNull("Test of findByCode when not found", foundJournalComptable);
	}

}
