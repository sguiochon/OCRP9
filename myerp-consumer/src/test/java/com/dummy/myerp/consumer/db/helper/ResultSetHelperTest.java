package com.dummy.myerp.consumer.db.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ResultSetHelperTest {

	@Mock
	ResultSet resultSetMock;

	@Test
	public void testGetDateWhenNull() throws SQLException {
		//Arrange
		when(resultSetMock.getDate(anyString())).thenReturn(null);
		// Act
		Date date = ResultSetHelper.getDate(resultSetMock, "dummycolumn name");
		// Assert
		Assert.assertNull("Date conversion - returns null", date);
	}

	@Test
	public void testGetDateWhenNotNull() throws SQLException, ParseException {
		// Arrange
		java.sql.Date simulatedDate = new java.sql.Date(System.currentTimeMillis());
		simulatedDate.setTime(System.currentTimeMillis());
		doReturn(simulatedDate).when(resultSetMock).getDate(anyString());
		Calendar calendar = GregorianCalendar.getInstance();

		// Act
		Date date = ResultSetHelper.getDate(resultSetMock, "dummy column name");

		// Assert
		Assert.assertNotNull("Date conversion - date not null", date);
		calendar.setTime(date);
		Assert.assertEquals("Date conversion - no hours", 0, calendar.get(Calendar.HOUR));
		Assert.assertEquals("Date conversion - no minutes", 0, calendar.get(Calendar.MINUTE));
		Assert.assertEquals("Date conversion - no seconds", 0, calendar.get(Calendar.SECOND));
	}

	@Test
	public void testGetIntegerWhenNull() throws SQLException {
		// Arrange
		doReturn(true).when(resultSetMock).wasNull();
		doReturn(1000).when(resultSetMock).getInt(anyString());

		// Act
		Integer integer = ResultSetHelper.getInteger(resultSetMock, "dummy column name");

		// Assert
		Assert.assertNull("Integer conversion - returns null", integer);
	}

	@Test
	public void testGetIntegerWhenNotNull() throws SQLException {
		// Arrange
		doReturn(false).when(resultSetMock).wasNull();
		doReturn(1000).when(resultSetMock).getInt(anyString());

		// Act
		Integer integer = ResultSetHelper.getInteger(resultSetMock, "dummy column name");

		// Assert
		Assert.assertEquals("Integer conversion", Integer.valueOf(1000), integer);
	}

	@Test
	public void testGetLongWhenNull() throws SQLException {
		// Arrange
		doReturn(true).when(resultSetMock).wasNull();
		doReturn(1000L).when(resultSetMock).getLong(anyString());

		// Act
		Long longValue = ResultSetHelper.getLong(resultSetMock, "dummy column name");

		// Assert
		Assert.assertNull("Long conversion - returns null", longValue);
	}

	@Test
	public void testGetLongWhenNotNull() throws SQLException {
		// Arrange
		doReturn(false).when(resultSetMock).wasNull();
		doReturn(1000L).when(resultSetMock).getLong(anyString());

		// Act
		Long longValue = ResultSetHelper.getLong(resultSetMock, "dummy column name");

		// Assert
		Assert.assertEquals("Long conversion", Long.valueOf(1000), longValue);
	}

}
