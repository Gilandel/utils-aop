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
package fr.landel.utils.aop;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;

/**
 * Abstract aspect test (initialize the test appender).
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 * @param <A>
 *            The type of the class to log
 */
public abstract class AbstractAspectTest<A extends AbstractAspect> {

    /**
     * The output logging stream
     */
    protected ByteArrayOutputStream stream;

    /**
     * The appender
     */
    protected OutputStreamAppender<ILoggingEvent> appender;

    private Logger logger;

    private Class<A> clazz;

    /**
     * 
     * Constructor
     *
     * @param clazz
     *            The class to log
     */
    public AbstractAspectTest(final Class<A> clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the logger
     */
    public final Logger getLogger() {
        return this.logger;
    }

    /**
     * Init the appender
     */
    @Before
    public void init() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        this.logger = loggerContext.getLogger(this.clazz);

        // Destination stream
        this.stream = new ByteArrayOutputStream();

        // Encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%logger{0} %m");
        encoder.setImmediateFlush(true);
        encoder.start();

        // Appender
        this.appender = new OutputStreamAppender<>();
        this.appender.setName("OutputStream Appender " + this.clazz.getSimpleName());
        this.appender.setContext(loggerContext);
        this.appender.setEncoder(encoder);
        this.appender.setOutputStream(this.stream);
        this.appender.start();

        this.logger.addAppender(this.appender);
    }

    /**
     * Release the appender
     */
    @After
    public void dispose() {
        this.appender.stop();
    }
}
