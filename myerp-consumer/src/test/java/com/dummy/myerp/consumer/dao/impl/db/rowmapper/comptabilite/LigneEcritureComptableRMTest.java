package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class LigneEcritureComptableRMTest {

	@Mock
	ResultSet resultSetMock;

	@Mock
	DaoProxy daoProxyMock;

	@Mock
	ComptabiliteDao comptabiliteDaoMock;

	@Mock
	CompteComptableDaoCache compteComptableDaoCacheMock;

	@InjectMocks
	LigneEcritureComptableRM ligneEcritureComptableRM;

	@Test
	public void testFieldSettingIsValid() throws SQLException {
		// Arrange
		PowerMockito.mockStatic(ConsumerHelper.class);

		CompteComptable simulatedCompteComptable = new CompteComptable();
		when(compteComptableDaoCacheMock.getByNumero(isNull())).thenReturn(simulatedCompteComptable);

		BigDecimal simulatedCredit = BigDecimal.valueOf(546);
		doReturn(simulatedCredit).when(resultSetMock).getBigDecimal(eq("credit"));

		BigDecimal simulatedDebit = BigDecimal.valueOf(-456);
		doReturn(simulatedDebit).when(resultSetMock).getBigDecimal(eq("debit"));

		doReturn("simulated label").when(resultSetMock).getString(eq("libelle"));

		when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxyMock);
		when(daoProxyMock.getComptabiliteDao()).thenReturn(comptabiliteDaoMock);

		// Act
		LigneEcritureComptable ligneEcritureComptable = ligneEcritureComptableRM.mapRow(resultSetMock, 0);

		// Assert
		assertEquals("Row mapping - setting of field 'credit'", simulatedCredit, ligneEcritureComptable.getCredit());
		assertEquals("Row mapping - setting of field 'debit'", simulatedDebit, ligneEcritureComptable.getDebit());
		assertEquals("Row mapping - setting of field 'libelle'", "simulated label", ligneEcritureComptable.getLibelle());
		assertEquals("Row mapping - setting of field 'compteComptable'", simulatedCompteComptable, ligneEcritureComptable.getCompteComptable());
	}
}
