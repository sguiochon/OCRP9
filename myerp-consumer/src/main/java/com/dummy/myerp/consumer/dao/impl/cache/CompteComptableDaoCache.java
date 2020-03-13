package com.dummy.myerp.consumer.dao.impl.cache;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

import java.util.List;


/**
 * Cache DAO de {@link CompteComptable}
 */
public class CompteComptableDaoCache {

	private List<CompteComptable> listCompteComptable;

	public CompteComptableDaoCache() {
	}

	/**
	 * Gets by numero.
	 *
	 * @param pNumero the numero
	 * @return {@link CompteComptable} ou {@code null}
	 */
	public CompteComptable getByNumero(Integer pNumero) {
		if (listCompteComptable == null) {
			listCompteComptable = ConsumerHelper.getDaoProxy().getComptabiliteDao().getListCompteComptable();
		}
		CompteComptable vRetour = CompteComptable.getByNumero(listCompteComptable, pNumero);
		return vRetour;
	}
}
