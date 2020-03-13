package com.dummy.myerp.consumer.dao.impl.cache;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class JournalComptableDaoCacheTest {

	@Mock
	DaoProxy daoProxy;

	@Mock
	ComptabiliteDao comptabiliteDao;

	JournalComptableDaoCache journalComptableDaoCache;

	@Test
	public void testGetByNumeroFirstCallRetrievesListThenUsesIt() {
		// Arrange
		PowerMockito.mockStatic(ConsumerHelper.class);
		when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxy);
		when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
		journalComptableDaoCache = Mockito.spy(new JournalComptableDaoCache());
		// Act
		journalComptableDaoCache.getByCode("code");
		journalComptableDaoCache.getByCode("code");
		// Assert
		Mockito.verify(comptabiliteDao, times(1)).getListJournalComptable();
	}
}
