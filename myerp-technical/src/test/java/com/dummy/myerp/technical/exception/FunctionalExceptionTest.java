package com.dummy.myerp.technical.exception;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

	@Test
	public void testConstrutorWithConstraintViolationException() {
		// Arrange
		String message = "message";
		Set<ConstraintViolation<String>> violations = new HashSet<>();
		violations.add(new ConstraintViolation<String>() {
			@Override
			public String getMessage() {
				return "violation detail";
			}

			@Override
			public String getMessageTemplate() {
				return null;
			}

			@Override
			public String getRootBean() {
				return null;
			}

			@Override
			public Class<String> getRootBeanClass() {
				return null;
			}

			@Override
			public Object getLeafBean() {
				return null;
			}

			@Override
			public Object[] getExecutableParameters() {
				return new Object[0];
			}

			@Override
			public Object getExecutableReturnValue() {
				return null;
			}

			@Override
			public Path getPropertyPath() {
				return null;
			}

			@Override
			public Object getInvalidValue() {
				return null;
			}

			@Override
			public ConstraintDescriptor<?> getConstraintDescriptor() {
				return null;
			}

			@Override
			public <U> U unwrap(Class<U> aClass) {
				return null;
			}
		});
		ConstraintViolationException exception = new ConstraintViolationException("detail of constraint violation", violations);

		// Act
		FunctionalException functionalException = new FunctionalException(message, exception);

		// Assert
		assertEquals("Test of field 'cause'", exception , functionalException.getCause());
		assertEquals("Test of field 'localizedMessage'","message(detail of constraint violation[violation detail])", functionalException.getLocalizedMessage());
		assertEquals("Test of field 'message'", "message(detail of constraint violation[violation detail])", functionalException.getMessage());
	}
}
