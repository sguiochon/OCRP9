package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceEcritureRM implements RowMapper<SequenceEcritureComptable> {
	@Override
	public SequenceEcritureComptable mapRow(ResultSet resultSet, int i) throws SQLException {
		SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
		sequenceEcritureComptable.setJournalCode(resultSet.getString("journal_code"));
		sequenceEcritureComptable.setDerniereValeur(resultSet.getInt("derniere_valeur"));
		sequenceEcritureComptable.setAnnee(resultSet.getInt("annee"));
		return sequenceEcritureComptable;
	}
}
