package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TechnicalExceptionTest {

	@Test
	public void testConstrutor1() {
		// Act
		TechnicalException technicalException = new TechnicalException("message");

		// Assert
		assertNull("Test 'cause' is not set", technicalException.getCause());
		assertEquals("Test of field 'localizedMessage'","message", technicalException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "message", technicalException.getMessage());
	}

	@Test
	public void testConstrutor2() {
		// Arrange
		Throwable throwable = new Throwable("message");
		// Act
		TechnicalException technicalException = new TechnicalException(throwable);

		// Assert
		assertEquals("Test of field 'cause'", throwable , technicalException.getCause());
		assertEquals("Test of field 'localizedMessage'","java.lang.Throwable: message", technicalException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "java.lang.Throwable: message", technicalException.getMessage());
	}

	@Test
	public void testConstrutor3() {
		// Arrange
		String message = "message";
		Throwable throwable = new Throwable("message of throwable");

		// Act
		TechnicalException technicalException = new TechnicalException(message, throwable);

		// Assert
		assertEquals("Test of field 'cause'", throwable , technicalException.getCause());
		assertEquals("Test of field 'localizedMessage'","message", technicalException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "message", technicalException.getMessage());
	}
}
