package com.dummy.myerp.consumer;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

public abstract class ConsumerHelper {

	private static DaoProxy daoProxy;

	private ConsumerHelper() {
	}

	public static void configure(DaoProxy pDaoProxy) {
		daoProxy = pDaoProxy;
	}

	public static DaoProxy getDaoProxy() {
		return daoProxy;
	}
}
