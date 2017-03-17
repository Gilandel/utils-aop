/*
 * #%L
 * utils-aop
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.aop.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import org.junit.Test;

/**
 * Check AOP exception
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 */
public class AOPExceptionTest {

    /**
     * Test method for {@link AOPException#AOPException()} .
     */
    @Test
    public void testAOPException() {
        AOPException exception = new AOPException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * Test method for {@link AOPException#AOPException(String, Object...)} .
     */
    @Test
    public void testAOPExceptionString() {
        String expectedMessage = "message";
        AOPException exception = new AOPException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());

        expectedMessage = "message exception";
        String message = "message %s";
        exception = new AOPException(message, "exception");
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * Test method for
     * {@link AOPException#AOPException(Locale, String, Object...)} .
     */
    @Test
    public void testAOPExceptionLocale() {
        String expectedMessage = "message";
        AOPException exception = new AOPException(Locale.FRANCE, expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());

        expectedMessage = "message 3,14";
        String message = "message %.2f";
        exception = new AOPException(Locale.FRANCE, message, Math.PI);
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * Test method for {@link AOPException#AOPException(java.lang.Throwable)} .
     */
    @Test
    public void testAOPExceptionThrowable() {
        final Throwable expectedCause = new IllegalArgumentException("illegal");
        AOPException exception = new AOPException(expectedCause);
        assertEquals(AOPException.class.getSimpleName(), exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }

    /**
     * Test method for
     * {@link AOPException#AOPException(java.lang.String, java.lang.Throwable)}
     * .
     */
    @Test
    public void testAOPExceptionStringThrowable() {
        final String expectedMessage = "message";
        final Throwable expectedCause = new IllegalArgumentException("illegal");
        AOPException exception = new AOPException(expectedMessage, expectedCause);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }

    /**
     * Test method for
     * {@link AOPException#AOPException(Throwable, String, Object...)} .
     */
    @Test
    public void testAOPExceptionThrowableString() {
        String expectedMessage = "message";
        Throwable expectedCause = new IllegalArgumentException("illegal");
        AOPException exception = new AOPException(expectedCause, expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());

        expectedMessage = "message exception";
        final String message = "message %s";
        expectedCause = new IllegalArgumentException("illegal");
        exception = new AOPException(expectedCause, message, "exception");
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }

    /**
     * Test method for
     * {@link AOPException#AOPException(Throwable, java.util.Locale, String, Object...)}
     * .
     */
    @Test
    public void testAOPExceptionThrowableLocale() {
        String expectedMessage = "message";
        Throwable expectedCause = new IllegalArgumentException("illegal");
        AOPException exception = new AOPException(expectedCause, Locale.FRANCE, expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());

        expectedMessage = "message 3,14";
        String message = "message %.2f";
        expectedCause = new IllegalArgumentException("illegal");
        exception = new AOPException(expectedCause, Locale.FRANCE, message, Math.PI);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }
}
