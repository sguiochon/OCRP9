package com.dummy.myerp.business.impl;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

/**
 * <p>Implémentation du Proxy d'accès à la couche Business.</p>
 */
public class BusinessProxyImpl implements BusinessProxy {

	private static final BusinessProxyImpl INSTANCE = new BusinessProxyImpl();

	private static DaoProxy daoProxy;

	private ComptabiliteManager comptabiliteManager = new ComptabiliteManagerImpl();

	protected BusinessProxyImpl() {
		super();
	}

	/**
	 * Renvoie l'instance unique de la classe (design pattern Singleton).
	 *
	 * @return {@link BusinessProxyImpl}
	 */
	protected static BusinessProxyImpl getInstance() {
		if (daoProxy == null) {
			throw new UnsatisfiedLinkError("La classe BusinessProxyImpl n'a pas été initialisée.");
		}
		return BusinessProxyImpl.INSTANCE;
	}

	/**
	 * Renvoie l'instance unique de la classe (design pattern Singleton).
	 *
	 * @param pDaoProxy           -
	 * @param pTransactionManager -
	 * @return {@link BusinessProxyImpl}
	 */
	public static BusinessProxyImpl getInstance(DaoProxy pDaoProxy,
	                                            TransactionManager pTransactionManager) {
		daoProxy = pDaoProxy;
		AbstractBusinessManager.configure(BusinessProxyImpl.INSTANCE, pDaoProxy, pTransactionManager);
		return BusinessProxyImpl.INSTANCE;
	}

	@Override
	public ComptabiliteManager getComptabiliteManager() {
		return comptabiliteManager;
	}
}
