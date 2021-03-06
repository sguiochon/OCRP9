package com.dummy.myerp.consumer.db.helper;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;


public abstract class ResultSetHelper {

	private ResultSetHelper(){}

	/**
	 * Renvoie la valeur de la colonne pColName dans un <code>Integer</code>.
	 * Si la colonne vaut <code>null</code>, la méthode renvoie <code>null</code>
	 *
	 * @param pRS      : Le ResultSet à intéroger
	 * @param pColName : Le nom de la colonne dans le retour de la requête SQL
	 * @return <code>Integer</code> ou <code>null</code>
	 * @throws SQLException sur erreur SQL
	 */
	public static Integer getInteger(ResultSet pRS, String pColName) throws SQLException {
		int vInt = pRS.getInt(pColName);
		if (!pRS.wasNull()) {
			return vInt;
		} else {
			return null;
		}
	}

	/**
	 * Renvoie la valeur de la colonne pColName dans un <code>Long</code>.
	 * Si la colonne vaut <code>null</code>, la méthode renvoie <code>null</code>
	 *
	 * @param pRS      : Le ResultSet à intéroger
	 * @param pColName : Le nom de la colonne dans le retour de la requête SQL
	 * @return <code>Long</code> ou <code>null</code>
	 * @throws SQLException sur erreur SQL
	 */
	public static Long getLong(ResultSet pRS, String pColName) throws SQLException {
		Long vLong = pRS.getLong(pColName);
		if (!pRS.wasNull()) {
			return vLong;
		} else {
			return null;
		}
	}

	/**
	 * Renvoie la valeur de la colonne pColName dans un {@link Date} en faisant un truncate de l'heure.
	 * Si la colonne vaut <code>null</code>, la méthode renvoie <code>null</code>.
	 *
	 * @param pRS      : Le ResultSet à intéroger
	 * @param pColName : Le nom de la colonne dans le retour de la requête SQL
	 * @return {@link Date} ou <code>null</code>
	 * @throws SQLException sur erreur SQL
	 */
	public static Date getDate(ResultSet pRS, String pColName) throws SQLException {
		Date vDate = pRS.getDate(pColName);
		if (vDate != null) {
			return DateUtils.truncate(vDate, Calendar.DATE);
		} else {
			return null;
		}
	}
}
