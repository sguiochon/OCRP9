package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "com.sun.*", "javax.xml.*", "org.xml.sax.*", "org.w3c.*", "javax.script.*", "org.slf4j.*"})
@PrepareForTest({LoggerFactory.class, AbstractDbConsumer.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComptabiliteDaoImplTest {

	@Mock
	Logger loggerMock;

	@Before
	public void setUp(){
		PowerMockito.mockStatic(LoggerFactory.class);
		loggerMock = PowerMockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(loggerMock);
	}

	@Test
	public void testBConfigurationOfDataSourcesWhenNoDataSourceProvided() {
		// Arrange
		EnumMap<DataSourcesEnum, DataSource> configDataSourceMap = new EnumMap<>(DataSourcesEnum.class);
		configDataSourceMap.put(DataSourcesEnum.MYERP, null);

		// Act
		AbstractDbConsumer.configure(configDataSourceMap);

		// Assert
		verify(loggerMock, times(1)).error(anyString(), (Object) any());
	}

	@Test
	public void testAConfigurationOfDataSourcesWhenValidDataSourceProvided() {
		// Arrange
		EnumMap<DataSourcesEnum, DataSource> configDataSourceMap = new EnumMap<>(DataSourcesEnum.class);
		configDataSourceMap.put(DataSourcesEnum.MYERP, buildFakeDataSource());

		// Act
		AbstractDbConsumer.configure(configDataSourceMap);

		// Assert
		verify(loggerMock, times(0)).error(anyString(), (Object) any());
	}

	private DataSource buildFakeDataSource() {
		return new  DataSource() {
			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				return null;
			}

			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				return false;
			}

			@Override
			public Connection getConnection() throws SQLException {
				return null;
			}

			@Override
			public Connection getConnection(String username, String password) throws SQLException {
				return null;
			}

			@Override
			public PrintWriter getLogWriter() throws SQLException {
				return null;
			}

			@Override
			public void setLogWriter(PrintWriter out) throws SQLException {

			}

			@Override
			public int getLoginTimeout() throws SQLException {
				return 0;
			}

			@Override
			public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
				return null;
			}

			@Override
			public void setLoginTimeout(int seconds) throws SQLException {

			}
		};
	}
}
