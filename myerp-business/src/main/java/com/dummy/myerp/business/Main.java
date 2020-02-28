package com.dummy.myerp.business;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("com/dummy/myerp/business/applicationContext.xml");

		BusinessProxy businessProxy = (BusinessProxy) context.getBean("BusinessProxy");

		List<CompteComptable> compteComptables = businessProxy.getComptabiliteManager().getListCompteComptable();
		System.out.println("Codes comptable:");
		for (CompteComptable compteComptable : compteComptables) {
			System.out.println(compteComptable.toString());
		}

		List<JournalComptable> journalComptableList = businessProxy.getComptabiliteManager().getListJournalComptable();
		System.out.println("Journaux comptables:");
		for (JournalComptable journalComptable : journalComptableList) {
			System.out.println(journalComptable.toString());
		}

		List<EcritureComptable> ecritureComptableList = businessProxy.getComptabiliteManager().getListEcritureComptable();
		System.out.println("Ecritures comptables: ");
		for (EcritureComptable ecritureComptable : ecritureComptableList) {
			System.out.println(ecritureComptable);
		}

		// Termine le contexte Spring
		context.close();

	}
}
