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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.landel.utils.aop.observable.AOPObservable;
import fr.landel.utils.aop.observable.EnumTest;
import fr.landel.utils.io.EncodingUtils;

/**
 * Check logging aspect methods.
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 */
public class LoggingAspectTest extends AbstractAspectTest<LoggingAspect> {

    private static final String EXPECTED_TEXT = LoggingAspect.class.getSimpleName() + " class " + AOPObservable.class.getCanonicalName()
            + ".test";

    /**
     * 
     * Constructor
     *
     */
    public LoggingAspectTest() {
        super(LoggingAspect.class);
    }

    /**
     * Check AOP in log mode (JoinPoint)
     */
    @Test
    public void logTestInfo() {
        // set logger temporary to info level
        final Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);
        final Level level = logger.getLevel();
        logger.setLevel(Level.INFO);

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();

        assertNotNull(aspect.getLogger());

        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        proxy.test();

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertTrue(outputLog.isEmpty());
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTestInfo()\n" + e);
        } finally {
            // reset logger level
            logger.setLevel(level);
        }
    }

    /**
     * Check AOP in log mode (JoinPoint)
     */
    @Test
    public void logTest() {
        final String expectedLog = EXPECTED_TEXT + "()";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();

        assertNotNull(aspect.getLogger());

        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        proxy.test();

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTest()\n" + e);
        }
    }

    /**
     * Check AOP in log mode with 3 primitive parameters (JoinPoint)
     */
    @Test
    public void logTest3Primitive() {
        final String expectedLog = EXPECTED_TEXT + "(p, true, 33)";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        final byte p3 = (byte) 33;
        proxy.test('p', true, p3);

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTest3Primitive()\n" + e);
        }
    }

    /**
     * Check AOP in log mode with 3 complex parameters (JoinPoint)
     */
    @Test
    public void logTest3Complex() {
        final String expectedLog = EXPECTED_TEXT + "((String[])[p1], (ArrayList)[p2], (HashMap)[key=p3])";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        String[] p1 = new String[1];
        p1[0] = "p1";
        List<String> p2 = new ArrayList<String>();
        p2.add("p2");
        Map<String, String> p3 = new HashMap<>();
        p3.put("key", "p3");
        proxy.test(p1, p2, p3);

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTest3Complex()\n" + e);
        }
    }

    /**
     * Check AOP in log mode with 5 primitive parameters (JoinPoint)
     */
    @Test
    public void logTest5Primitive() {
        final String expectedLog = EXPECTED_TEXT + "(2.1, 3.2, 4, 5, 6)";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        final double p1 = 2.1d;
        final float p2 = 3.2f;
        final long p3 = 4L;
        final int p4 = 5;
        final short p5 = (short) 6;
        proxy.test(p1, p2, p3, p4, p5);

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTest5Primitive()\n" + e);
        }
    }

    /**
     * Check AOP in log mode with 6 simple parameters (JoinPoint)
     */
    @Test
    public void logTest6SimpleDateTime() {
        final String expectedLog = EXPECTED_TEXT + "(\"p1\", p, 3, true, EnumTest.KEY, Date{2016/01/01 16:29:55.916})";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        final String p1 = "p1";
        final Character p2 = 'p';
        final Integer p3 = new Integer(3);
        final Boolean p4 = Boolean.TRUE;
        final EnumTest p5 = EnumTest.KEY;

        Calendar p6 = Calendar.getInstance();
        final int year = 2015;
        final int month = 12;
        final int day = 1;
        final int hour = 16;
        final int minute = 29;
        final int second = 55;
        final int millisecond = 916;
        p6.set(year, month, day, hour, minute, second);
        p6.set(Calendar.MILLISECOND, millisecond);

        proxy.test(p1, p2, p3, p4, p5, p6.getTime());

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTest6Simple()\n" + e);
        }
    }

    /**
     * Check AOP in log mode with 6 simple parameters (JoinPoint)
     */
    @Test
    public void logTest6SimpleDate() {
        final String expectedLog = EXPECTED_TEXT + "(\"p1\", p, 3, true, EnumTest.KEY, Date{2016/01/01})";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        final String p1 = "p1";
        final Character p2 = 'p';
        final Integer p3 = new Integer(3);
        final Boolean p4 = Boolean.TRUE;
        final EnumTest p5 = EnumTest.KEY;

        Calendar p6 = Calendar.getInstance();
        final int year = 2015;
        final int month = 12;
        final int day = 1;
        final int hour = 0;
        final int minute = 0;
        final int second = 0;
        final int millisecond = 0;
        p6.set(year, month, day, hour, minute, second);
        p6.set(Calendar.MILLISECOND, millisecond);

        proxy.test(p1, p2, p3, p4, p5, p6.getTime());

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTest6Simple()\n" + e);
        }
    }

    /**
     * Check AOP
     */
    @Test
    public void logTestNull() {
        final String expectedLog = EXPECTED_TEXT + "(null)";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();

        assertNotNull(aspect.getLogger());

        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        proxy.test(null);

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTestNull()\n" + e);
        }
    }

    /**
     * Check AOP
     */
    @Test
    public void logTestObject() {
        final String expectedLog = EXPECTED_TEXT + "((Color){java.awt.Color[r=0,g=0,b=0]})";

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();

        assertNotNull(aspect.getLogger());

        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        proxy.test(Color.BLACK);

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            assertEquals(expectedLog, outputLog);
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTestNull()\n" + e);
        }
    }
}
