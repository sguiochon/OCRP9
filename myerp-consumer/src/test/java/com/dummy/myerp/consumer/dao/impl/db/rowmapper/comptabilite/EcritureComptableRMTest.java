package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class EcritureComptableRMTest {

	@Mock
	ResultSet resultSetMock;

	@Mock
	DaoProxy daoProxyMock;

	@Mock
	ComptabiliteDao comptabiliteDaoMock;

	@Mock
	JournalComptableDaoCache journalComptableDaoCacheMock;

	@InjectMocks
	EcritureComptableRM ecritureComptableRM;

	@Test
	public void testFieldSettingIsValid() throws SQLException {
		// Arrange
		PowerMockito.mockStatic(ConsumerHelper.class);

		doReturn(573).when(resultSetMock).getInt(anyString());
		doReturn("simulated reference").when(resultSetMock).getString(eq("reference"));
		Date simulatedDate = Date.valueOf("2015-03-31");
		doReturn(simulatedDate).when(resultSetMock).getDate(eq("date"));
		doReturn("simulated label").when(resultSetMock).getString(eq("libelle"));

		JournalComptable simulatedJournal = new JournalComptable();
		when(journalComptableDaoCacheMock.getByCode(isNull())).thenReturn(simulatedJournal);

		when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxyMock);
		when(daoProxyMock.getComptabiliteDao()).thenReturn(comptabiliteDaoMock);

		// Act
		EcritureComptable ecritureComptable = ecritureComptableRM.mapRow(resultSetMock, 0);

		// Assert
		assertEquals("Row mapping - setting of field 'id'", Integer.valueOf(573), ecritureComptable.getId());
		assertEquals("Row mapping - setting of field 'journal'", simulatedJournal, ecritureComptable.getJournal());
		assertEquals("Row mapping - setting of field 'label'", "simulated label", ecritureComptable.getLibelle());
		assertEquals("Row mapping - setting of field 'reference'", "simulated reference", ecritureComptable.getReference());
		assertEquals("Row mapping - setting of field 'date'", simulatedDate, ecritureComptable.getDate());
	}
}
