package com.dummy.myerp.business.impl;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <p>Classe de gestion des Transactions de persistance</p>
 */
public class TransactionManager {

	private static final TransactionManager INSTANCE = new TransactionManager();
	private static PlatformTransactionManager platformTransactionManager;

	protected TransactionManager() {
		super();
	}

	public static TransactionManager getInstance() {
		return TransactionManager.INSTANCE;
	}

	public static TransactionManager getInstance(PlatformTransactionManager transactionManager) {
		TransactionManager.platformTransactionManager = transactionManager;
		return TransactionManager.INSTANCE;
	}

	/**
	 * Démarre une transaction sur le DataSource MyERP
	 *
	 * @return TransactionStatus à passer aux méthodes :
	 * <ul>
	 *     <li>{@link #commitMyERP(TransactionStatus)}</li>
	 *         <li>{@link #rollbackMyERP(TransactionStatus)}</li>
	 * </ul>
	 */
	public TransactionStatus beginTransactionMyERP() {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("Transaction_txManagerMyERP");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		return platformTransactionManager.getTransaction(defaultTransactionDefinition);
	}

	/**
	 * Commit la transaction sur le DataSource MyERP
	 *
	 * @param pTStatus retrouné par la méthode {@link #beginTransactionMyERP()}
	 */
	public void commitMyERP(TransactionStatus pTStatus) {
		if (pTStatus != null) {
			platformTransactionManager.commit(pTStatus);
		}
	}

	/**
	 * Rollback la transaction sur le DataSource MyERP
	 *
	 * @param pTStatus retrouné par la méthode {@link #beginTransactionMyERP()}
	 */
	public void rollbackMyERP(TransactionStatus pTStatus) {
		if (pTStatus != null) {
			platformTransactionManager.rollback(pTStatus);
		}
	}
}
