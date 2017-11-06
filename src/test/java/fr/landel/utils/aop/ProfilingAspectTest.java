/*
 * #%L
 * utils-aop
 * %%
 * Copyright (C) 2016 - 2017 Gilles Landel
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.landel.utils.aop.observable.AOPObservable;
import fr.landel.utils.io.EncodingUtils;

/**
 * Check profiling aspect methods.
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 */
public class ProfilingAspectTest extends AbstractAspectTest<ProfilingAspect> {

    private static final int MAX_TIMEOUT = 5000;

    /**
     * 
     * Constructor
     *
     */
    public ProfilingAspectTest() {
        super(ProfilingAspect.class);
    }

    /**
     * Check AOP in profile mode (ProceedingJoinPoint)
     * 
     * @throws InterruptedException
     *             If sleep failed
     */
    @Test
    public void profileTestInfo() throws InterruptedException {
        // set logger temporary to info level
        final Logger logger = (Logger) LoggerFactory.getLogger(ProfilingAspect.class);
        final Level level = logger.getLevel();
        logger.setLevel(Level.INFO);

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        ProfilingAspect aspect = new ProfilingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        proxy.testSleep();

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
     * Check AOP in profile mode (ProceedingJoinPoint)
     * 
     * @throws InterruptedException
     *             If sleep failed
     */
    @Test
    public void profileTest() throws InterruptedException {
        final String expectedLog = ProfilingAspect.class.getSimpleName() + " class " + AOPObservable.class.getCanonicalName()
                + ".testSleep()";
        final Pattern pattern = Pattern.compile(", running time: (\\d+) ms$");

        AOPObservable target = new AOPObservable();

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        ProfilingAspect aspect = new ProfilingAspect();
        factory.addAspect(aspect);

        AOPObservable proxy = factory.getProxy();

        this.stream.reset();

        proxy.testSleep();

        try {
            String outputLog = this.stream.toString(EncodingUtils.ENCODING_UTF_8);

            if (outputLog.length() > expectedLog.length()) {
                String firstPart = outputLog.substring(0, expectedLog.length());

                assertEquals(expectedLog, firstPart);

                String secondPart = outputLog.substring(expectedLog.length());

                Matcher matcher = pattern.matcher(secondPart);
                assertTrue(matcher.matches());
                assertTrue(MAX_TIMEOUT < Integer.parseInt(matcher.group(1)));
            } else {
                fail("Output stream is too short: " + outputLog);
            }
        } catch (IOException e) {
            fail("Errors occurred in AspectTest#logTest()\n" + e);
        }
    }
}
