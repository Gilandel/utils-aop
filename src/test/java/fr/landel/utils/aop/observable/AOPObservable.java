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
package fr.landel.utils.aop.observable;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.landel.utils.commons.DateUtils;

/**
 * AOP observable for tests
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 */
public class AOPObservable {

    private static final long TIMEOUT = 6 * DateUtils.MILLIS_PER_SECOND;
    private static final Logger LOGGER = LoggerFactory.getLogger(AOPObservable.class);

    /**
     * Test method (logging)
     */
    public void test() {
        // Observable method, no parameter
    }

    /**
     * Test method with object parameter (logging)
     * 
     * @param object
     *            Object
     */
    public void test(final Object object) {
        // Observable method, object parameter
    }

    /**
     * Test method with parameters (logging)
     * 
     * @param p1
     *            String
     * @param p2
     *            Character
     * @param p3
     *            Number
     * @param p4
     *            Boolean
     * @param p5
     *            EnumTest
     * @param p6
     *            Date
     */
    public void test(final String p1, final Character p2, final Number p3, final Boolean p4, final EnumTest p5, final Date p6) {
        // Observable method, boxed simple type
    }

    /**
     * Test method with primitive parameters (logging)
     * 
     * @param p1
     *            Double
     * @param p2
     *            Float
     * @param p3
     *            Long
     * @param p4
     *            Integer
     * @param p5
     *            Short
     */
    public void test(final double p1, final float p2, final long p3, final int p4, final short p5) {
        // Observable method, unboxed simple type
    }

    /**
     * Test method with primitive parameters (logging)
     * 
     * @param p1
     *            Character
     * @param p2
     *            Boolean
     * @param p3
     *            Byte
     */
    public void test(final char p1, final boolean p2, final byte p3) {
        // Observable method, unboxed simple type
    }

    /**
     * Test method with complex parameters (logging)
     * 
     * @param p1
     *            String array
     * @param p2
     *            String array
     * @param p3
     *            String array
     * @param p4
     *            List of string
     * @param p5
     *            List of string
     * @param p6
     *            Iterator of string
     * @param p7
     *            Map
     * @param p8
     *            Map
     * @param p9
     *            Map
     */
    public void test(final String[] p1, final String[] p2, final String[] p3, final List<String> p4, final List<String> p5,
            final Iterator<String> p6, final Map<String, String> p7, final Map<String, String> p8, final Map<String, String> p9) {
        // Observable method, array, list and map
    }

    /**
     * Test method that throws an exception
     */
    public void testThrowable() {
        throw new UnsupportedOperationException();
    }

    /**
     * Test sleep method (reach profiling timeout)
     * 
     * @throws InterruptedException
     *             If sleep failed
     */
    public void testSleep() throws InterruptedException {
        LOGGER.info("START AOPObservable#testSleep: " + System.currentTimeMillis());
        Thread.sleep(TIMEOUT);
        LOGGER.info("STOP AOPObservable#testSleep: " + System.currentTimeMillis());
    }
}
