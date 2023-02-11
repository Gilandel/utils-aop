/*
 * #%L
 * utils-aop
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package fr.landel.utils.aop.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Locale;

import org.junit.jupiter.api.Test;

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
