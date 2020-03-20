package com.dummy.myerp.consumer.dao.impl.cache;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

import java.util.List;


/**
 * Cache DAO de {@link JournalComptable}
 */
public class JournalComptableDaoCache {

	private List<JournalComptable> listJournalComptable;

	/**
	 * Retourne un JournalComptable par son code en le recherchant dans un cache pour améliorer les performances.
	 *
	 * @param pCode le code du {@link JournalComptable}
	 * @return {@link JournalComptable} ou {@code null}
	 */
	public JournalComptable getByCode(String pCode) {
		if (listJournalComptable == null) {
			listJournalComptable = ConsumerHelper.getDaoProxy().getComptabiliteDao().getListJournalComptable();
		}
		return JournalComptable.getByCode(listJournalComptable, pCode);
	}
}
