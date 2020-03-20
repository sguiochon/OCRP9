package com.dummy.myerp.consumer.dao.impl.cache;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

import java.util.List;


/**
 * Cache DAO de {@link CompteComptable}
 */
public class CompteComptableDaoCache {

	private List<CompteComptable> listCompteComptable;

	/**
	 * Retourne un CompteComptable par son numero en le recherchant dans un cache pour améliorer les performances.
	 *
	 * @param pNumero the numero
	 * @return {@link CompteComptable} ou {@code null}
	 */
	public CompteComptable getByNumero(Integer pNumero) {
		if (listCompteComptable == null) {
			listCompteComptable = ConsumerHelper.getDaoProxy().getComptabiliteDao().getListCompteComptable();
		}
		return CompteComptable.getByNumero(listCompteComptable, pNumero);
	}
}
