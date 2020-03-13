package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(PowerMockRunner.class)
public class JournalComptableRMTest {

	@Mock
	ResultSet resultSetMock;

	@Test
	public void testFieldSettingIsValid() throws SQLException {
		// Arrange
		doReturn("AZ-456-FG").when(resultSetMock).getString(eq("code"));
		doReturn("Label").when(resultSetMock).getString(eq("libelle"));
		JournalComptableRM journalComptableRM = new JournalComptableRM();

		// Act
		JournalComptable journalComptable = journalComptableRM.mapRow(resultSetMock, 0);

		// Assert
		assertEquals("Row mapping - setting of field 'code'", "AZ-456-FG", journalComptable.getCode());
		assertEquals("Row mapping - setting of field 'libelle'", "Label", journalComptable.getLibelle());
	}
}
