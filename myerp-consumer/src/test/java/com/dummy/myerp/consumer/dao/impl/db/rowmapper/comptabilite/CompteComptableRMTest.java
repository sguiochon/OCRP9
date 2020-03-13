package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(PowerMockRunner.class)
public class CompteComptableRMTest {

	@Mock
	ResultSet resultSetMock;

	@Test
	public void testFieldSettingIsValid() throws SQLException {
		// Arrange
		doReturn(Integer.valueOf(456)).when(resultSetMock).getInt(anyString());
		doReturn("Label").when(resultSetMock).getString(anyString());
		CompteComptableRM compteComptableRM = new CompteComptableRM();

		// Act
		CompteComptable compteComptable = compteComptableRM.mapRow(resultSetMock, 0);

		// Assert
		assertEquals("Row mapping - setting of field 'numero'", Integer.valueOf(456), compteComptable.getNumero());
		assertEquals("Row mapping - setting of field 'libelle'", "Label", compteComptable.getLibelle());
	}
}
