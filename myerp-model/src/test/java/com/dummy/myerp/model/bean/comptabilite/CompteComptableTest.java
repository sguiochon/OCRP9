package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CompteComptableTest {

	@Test
	public void testConstructor1(){
		// Act
		CompteComptable cc1 = new CompteComptable();
		cc1.setLibelle("libellé 1");
		cc1.setNumero(1);

		//Assert
		assertEquals("Test of the Libelle setter", "libellé 1", cc1.getLibelle());
		assertEquals("Test of the numero setter", Integer.valueOf(1), cc1.getNumero());
		assertEquals("Test of empty constructor", "CompteComptable{numero=1, libelle='libellé 1'}", cc1.toString());
	}

	@Test
	public void testConstructor2(){
		// Act
		CompteComptable cc1 = new CompteComptable( 2);
		cc1.setLibelle("libellé 2");

		//Assert
		assertEquals("Test of constructor with 1 param", "CompteComptable{numero=2, libelle='libellé 2'}", cc1.toString());
	}

	@Test
	public void testConstructor3(){
		// Act
		CompteComptable cc1 = new CompteComptable( 324, "libellé 324");

		//Assert
		assertEquals("Test of constructor with 2 params", "CompteComptable{numero=324, libelle='libellé 324'}", cc1.toString());
	}

	@Test
	public void testGetByNumberWhenFound(){
		// Arrange
		List<CompteComptable> compteComptableList = new ArrayList<>();
		CompteComptable compteComptable = new CompteComptable(1, "compte numéro 1");
		compteComptableList.add(compteComptable);

		compteComptable = new CompteComptable(100, "compte numéro 100");
		compteComptableList.add(compteComptable);

		compteComptable = new CompteComptable(2000, "compte numéro 2000");
		compteComptableList.add(compteComptable);

		// Act
		CompteComptable foundCompteComptable = CompteComptable.getByNumero(compteComptableList, Integer.valueOf(100));

		// Assert
		assertEquals("Test of getByNumber when found", "CompteComptable{numero=100, libelle='compte numéro 100'}", foundCompteComptable.toString());
	}

	@Test
	public void testGetByNumberWhenNotFound(){
		// Arrange
		List<CompteComptable> compteComptableList = new ArrayList<>();
		CompteComptable compteComptable = new CompteComptable(1, "compte numéro 1");
		compteComptableList.add(compteComptable);

		compteComptable = new CompteComptable(100, "compte numéro 100");
		compteComptableList.add(compteComptable);

		compteComptable = new CompteComptable(2000, "compte numéro 2000");
		compteComptableList.add(compteComptable);

		// Act
		CompteComptable foundCompteComptable = CompteComptable.getByNumero(compteComptableList, Integer.valueOf(500));

		// Assert
		assertNull("Test of getByNumber when not found", foundCompteComptable);
	}

}
