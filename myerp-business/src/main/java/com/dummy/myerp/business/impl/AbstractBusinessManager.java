package com.dummy.myerp.business.impl;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * <p>Classe mère des Managers</p>
 */
public abstract class AbstractBusinessManager {


	private static BusinessProxy businessProxy;

	private static DaoProxy daoProxy;

	private static TransactionManager transactionManager;


	/**
	 * Méthode de configuration de la classe
	 *
	 * @param pBusinessProxy      -
	 * @param pDaoProxy           -
	 * @param pTransactionManager -
	 */
	public static void configure(BusinessProxy pBusinessProxy,
	                             DaoProxy pDaoProxy,
	                             TransactionManager pTransactionManager) {
		businessProxy = pBusinessProxy;
		daoProxy = pDaoProxy;
		transactionManager = pTransactionManager;
	}


	protected BusinessProxy getBusinessProxy() {
		return businessProxy;
	}


	protected DaoProxy getDaoProxy() {
		return daoProxy;
	}


	protected TransactionManager getTransactionManager() {
		return transactionManager;
	}


	/**
	 * Renvoie un {@link Validator} de contraintes
	 *
	 * @return Validator
	 */
	protected Validator getConstraintValidator() {
		Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
		ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
		Validator vValidator = vFactory.getValidator();
		return vValidator;
	}
}
