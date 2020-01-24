package com.dummy.myerp.business.impl;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <p>Classe de gestion des Transactions de persistance</p>
 */
public class TransactionManager {

    // ==================== Attributs Static ====================
    /** PlatformTransactionManager pour le DataSource MyERP */
    private static PlatformTransactionManager transactionManager;


    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final TransactionManager INSTANCE = new TransactionManager();
    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link TransactionManager}
     */
    public static TransactionManager getInstance() {
        return TransactionManager.INSTANCE;
    }
    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @param transactionManager -
     * @return {@link TransactionManager}
     */
    public static TransactionManager getInstance(PlatformTransactionManager transactionManager) {
        TransactionManager.transactionManager = transactionManager;
        return TransactionManager.INSTANCE;
    }
    /**
     * Constructeur.
     */
    protected TransactionManager() {
        super();
    }


    // ==================== Méthodes ====================
    /**
     * Démarre une transaction sur le DataSource MyERP
     *
     * @return TransactionStatus à passer aux méthodes :
     *      <ul>
     *          <li>{@link #commitMyERP(TransactionStatus)}</li>
     *              <li>{@link #rollbackMyERP(TransactionStatus)}</li>
     *      </ul>
     */
    public TransactionStatus beginTransactionMyERP() {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("Transaction_txManagerMyERP");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionManager.getTransaction(defaultTransactionDefinition);
    }

    /**
     * Commit la transaction sur le DataSource MyERP
     *
     * @param pTStatus retrouné par la méthode {@link #beginTransactionMyERP()}
     */
    public void commitMyERP(TransactionStatus pTStatus) {
        if (pTStatus != null) {
            transactionManager.commit(pTStatus);
        }
    }

    /**
     * Rollback la transaction sur le DataSource MyERP
     *
     * @param pTStatus retrouné par la méthode {@link #beginTransactionMyERP()}
     */
    public void rollbackMyERP(TransactionStatus pTStatus) {
        if (pTStatus != null) {
            transactionManager.rollback(pTStatus);
        }
    }
}
