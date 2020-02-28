package com.dummy.myerp.consumer;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("com/dummy/myerp/consumer/applicationContext.xml");

		DaoProxy daoProxy = (DaoProxy) context.getBean("DaoProxy");

		List<CompteComptable> compteComptableList = daoProxy.getComptabiliteDao().getListCompteComptable();

		for (CompteComptable compteComptable : compteComptableList) {
			System.out.println("Libelle: " + compteComptable.getLibelle());

		}

		// Termine le contexte Spring
		context.close();
	}
}
