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
import java.util.TreeMap;

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
        // @formatter:off
        final String expectedLog = EXPECTED_TEXT
                + "((String[])[p1],"
                + " (String[])[p2_0, p2_1, p2_2, p2_3, p2_4, p2_5, p2_6, p2_7, p2_8, p2_9…],"
                + " (String[])[],"
                + " (ArrayList)[p4],"
                + " (ArrayList)[],"
                + " (Itr)[p6_0, p6_1, p6_2, p6_3, p6_4, p6_5, p6_6, p6_7, p6_8, p6_9…],"
                + " (HashMap)[key=p7],"
                + " (TreeMap)[key01=1, key02=2, key03=3, key04=4, key05=5, key06=6, key07=7, key08=8, key09=9, key10=10…],"
                + " (HashMap)[])";
        // @formatter:on

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        String[] p1 = new String[1];
        p1[0] = "p1";
        String[] p2 = new String[11];
        p2[0] = "p2_0";
        p2[1] = "p2_1";
        p2[2] = "p2_2";
        p2[3] = "p2_3";
        p2[4] = "p2_4";
        p2[5] = "p2_5";
        p2[6] = "p2_6";
        p2[7] = "p2_7";
        p2[8] = "p2_8";
        p2[9] = "p2_9";
        p2[10] = "p2_10";
        String[] p3 = new String[0];
        List<String> p4 = new ArrayList<>();
        p4.add("p4");
        List<String> p5 = new ArrayList<>();
        List<String> p6 = new ArrayList<>();
        p6.add("p6_0");
        p6.add("p6_1");
        p6.add("p6_2");
        p6.add("p6_3");
        p6.add("p6_4");
        p6.add("p6_5");
        p6.add("p6_6");
        p6.add("p6_7");
        p6.add("p6_8");
        p6.add("p6_9");
        p6.add("p6_10");
        Map<String, String> p7 = new HashMap<>();
        p7.put("key", "p7");
        Map<String, String> p8 = new TreeMap<>();
        p8.put("key01", "1");
        p8.put("key02", "2");
        p8.put("key03", "3");
        p8.put("key04", "4");
        p8.put("key05", "5");
        p8.put("key06", "6");
        p8.put("key07", "7");
        p8.put("key08", "8");
        p8.put("key09", "9");
        p8.put("key10", "10");
        p8.put("key11", "11");
        Map<String, String> p9 = new HashMap<>();
        proxy.test(p1, p2, p3, p4, p5, p6.iterator(), p7, p8, p9);

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
        final Integer p3 = 3;
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
        final Integer p3 = 3;
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

    /**
     * Check AOP with throwable exception (JoinPoint)
     */
    @Test(expected = UnsupportedOperationException.class)
    public void logTestThrowable() {
        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        LoggingAspect aspect = new LoggingAspect();

        assertNotNull(aspect.getLogger());

        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        proxy.testThrowable();
    }
}
