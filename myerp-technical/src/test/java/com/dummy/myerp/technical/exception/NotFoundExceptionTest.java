package com.dummy.myerp.technical.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NotFoundExceptionTest {

	@Test
	public void testConstrutor1() {
		// Act
		NotFoundException notFoundException = new NotFoundException("message");

		// Assert
		assertNull("Test 'cause' is not set", notFoundException.getCause());
		assertEquals("Test of field 'localizedMessage'","message", notFoundException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "message", notFoundException.getMessage());
	}

	@Test
	public void testConstrutor2() {
		// Arrange
		Throwable throwable = new Throwable("message");
		// Act
		NotFoundException notFoundException = new NotFoundException(throwable);

		// Assert
		assertEquals("Test of field 'cause'", throwable , notFoundException.getCause());
		assertEquals("Test of field 'localizedMessage'","java.lang.Throwable: message", notFoundException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "java.lang.Throwable: message", notFoundException.getMessage());
	}

	@Test
	public void testConstrutor3() {
		// Arrange
		String message = "message";
		Throwable throwable = new Throwable("message of throwable");

		// Act
		NotFoundException notFoundException = new NotFoundException(message, throwable);

		// Assert
		assertEquals("Test of field 'cause'", throwable , notFoundException.getCause());
		assertEquals("Test of field 'localizedMessage'","message", notFoundException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "message", notFoundException.getMessage());
	}

	@Test
	public void testEmptyConstrutor() {
		// Act
		NotFoundException notFoundException = new NotFoundException();

		// Assert
		assertNull("Test 'cause' is not set", notFoundException.getCause());
		assertNull("Test of field 'localizedMessage'", notFoundException.getLocalizedMessage());
		assertNull("Test of field 'message'",  notFoundException.getMessage());
	}
}
