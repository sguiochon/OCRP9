package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.db.DataSourcesEnum;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.EnumMap;
import java.util.logging.Logger;

import static org.powermock.configuration.ConfigurationType.PowerMock;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*","com.sun.*",  "javax.xml.*", "org.xml.sax.*", "org.w3c.*" }) // If not set, JAXB unmarshalling will not work!
public class ComptabiliteDaoImplTest {

	@Mock
	Logger mockLogger;

	@Spy
	ComptabiliteDaoImpl comptabiliteDaoImpl;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		String someName = "Some Name";
//		ReflectionTestUtils.setField(comptabiliteDaoImpl, // inject into this object
//				"LOGGER", // assign to this field
//				mockLogger); // object to be injected
	}

	@Test
	@Ignore
	public void testConfigurationOfDataSourcesWhenValidDataSourceProvided() {
		// Arrange
		EnumMap<DataSourcesEnum, DataSource> configDataSourceMap = new EnumMap<>(DataSourcesEnum.class);
		configDataSourceMap.put(DataSourcesEnum.MYERP, buildFakeDataSource());


		// Act
		comptabiliteDaoImpl.configure(configDataSourceMap);

		// Assert
		//ComptabiliteDaoImpl.getInstance().g

	}

	private DataSource buildFakeDataSource() {
		return new DataSource() {
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
			public void setLoginTimeout(int seconds) throws SQLException {

			}

			@Override
			public Logger getParentLogger() throws SQLFeatureNotSupportedException {
				return null;
			}
		};
	}

}
