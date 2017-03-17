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

import java.util.Locale;

import fr.landel.utils.commons.exception.AbstractException;

/**
 * The OAP exception.
 *
 * @since Nov 27, 2015
 * @author Gilles
 *
 */
public class AOPException extends AbstractException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5088326000492567608L;

    /**
     * Constructor.
     * 
     */
    public AOPException() {
        super();
    }

    /**
     * Constructor with message.
     * 
     * @param message
     *            the message
     * @param arguments
     *            the message arguments
     */
    public AOPException(final String message, final Object... arguments) {
        super(message, arguments);
    }

    /**
     * Constructor with message.
     * 
     * @param locale
     *            the message locale
     * @param message
     *            the message
     * @param arguments
     *            the message arguments
     */
    public AOPException(final Locale locale, final String message, final Object... arguments) {
        super(locale, message, arguments);
    }

    /**
     * Constructor with exception.
     * 
     * @param exception
     *            the cause exception
     */
    public AOPException(final Throwable exception) {
        super(AOPException.class, exception);
    }

    /**
     * Constructor with message and exception.
     * 
     * @param message
     *            the message
     * @param exception
     *            the cause exception
     */
    public AOPException(final String message, final Throwable exception) {
        super(message, exception);
    }

    /**
     * Constructor with message and exception.
     * 
     * @param exception
     *            the cause exception
     * @param message
     *            the message
     * @param arguments
     *            the message arguments
     */
    public AOPException(final Throwable exception, final String message, final Object... arguments) {
        super(exception, message, arguments);
    }

    /**
     * Constructor with message and exception.
     * 
     * @param exception
     *            the cause exception
     * @param locale
     *            the message locale
     * @param message
     *            the message
     * @param arguments
     *            the message arguments
     */
    public AOPException(final Throwable exception, final Locale locale, final String message, final Object... arguments) {
        super(exception, locale, message, arguments);
    }
}
