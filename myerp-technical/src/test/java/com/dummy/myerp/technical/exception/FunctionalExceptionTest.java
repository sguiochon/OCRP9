package com.dummy.myerp.technical.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FunctionalExceptionTest {

	@Test
	public void testConstrutor1() {
		// Act
		FunctionalException functionalException = new FunctionalException("message");

		// Assert
		assertNull("Test 'cause' is not set", functionalException.getCause());
		assertEquals("Test of field 'localizedMessage'","message", functionalException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "message", functionalException.getMessage());
	}

	@Test
	public void testConstrutor2() {
		// Arrange
		Throwable throwable = new Throwable("message");
		// Act
		FunctionalException functionalException = new FunctionalException(throwable);

		// Assert
		assertEquals("Test of field 'cause'", throwable , functionalException.getCause());
		assertEquals("Test of field 'localizedMessage'","java.lang.Throwable: message", functionalException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "java.lang.Throwable: message", functionalException.getMessage());
	}

	@Test
	public void testConstrutor3() {
		// Arrange
		String message = "message";
		Throwable throwable = new Throwable("message of throwable");

		// Act
		FunctionalException functionalException = new FunctionalException(message, throwable);

		// Assert
		assertEquals("Test of field 'cause'", throwable , functionalException.getCause());
		assertEquals("Test of field 'localizedMessage'","message", functionalException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "message", functionalException.getMessage());
	}
}
