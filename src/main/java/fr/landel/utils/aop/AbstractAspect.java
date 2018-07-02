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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import fr.landel.utils.aop.exception.AOPException;
import fr.landel.utils.commons.ArrayUtils;
import fr.landel.utils.commons.DateUtils;
import fr.landel.utils.commons.EnumChar;

/**
 * Abstract aspect.
 *
 * @since Nov 27, 2015
 * @author Gilles
 *
 */
public abstract class AbstractAspect {

    /**
     * Timeout in seconds
     */
    private static final int MAX_TIMEOUT = 5;

    /**
     * Max logs
     */
    private static final int MAX_MULTIPLES_LOG = 10;

    /**
     * Logger
     */
    private final Logger logger;

    /**
     * Date time format
     */
    private DateFormat dtf;

    /**
     * Date format
     */
    private DateFormat df;

    /**
     * Constructor.
     *
     */
    public AbstractAspect() {
        this.logger = LoggerFactory.getLogger(this.getClass());

        this.dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        this.df = new SimpleDateFormat("yyyy/MM/dd");
    }

    /**
     * Get the logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Get the signature.
     * 
     * @param joinPoint
     *            join point
     * @return the signature
     */
    protected String getSignature(final JoinPoint joinPoint) {
        final StringBuilder signBuilder = new StringBuilder();

        signBuilder.append(joinPoint.getTarget().getClass());
        signBuilder.append(EnumChar.DOT);
        signBuilder.append(joinPoint.getSignature().getName());
        signBuilder.append(EnumChar.PARENTHESIS_LEFT);
        if (ArrayUtils.isNotEmpty(joinPoint.getArgs())) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                final Object object = joinPoint.getArgs()[i];
                if (object == null) {
                    signBuilder.append("null");
                } else {
                    this.appendObject(signBuilder, object);
                }
                if (i < joinPoint.getArgs().length - 1) {
                    signBuilder.append(EnumChar.COMMA).append(EnumChar.SPACE);
                }
            }
        }
        signBuilder.append(EnumChar.PARENTHESIS_RIGHT);

        return signBuilder.toString();
    }

    /**
     * Append an object.
     * 
     * @param logEntry
     *            log entry
     * @param object
     *            object
     */
    protected void appendObject(final StringBuilder logEntry, final Object object) {
        final Class<?> objClass = object.getClass();

        if (!this.checkSimpleType(logEntry, object, objClass)) {
            if (objClass.isArray()) {
                this.appendArray(logEntry, object, objClass);
            } else if (Iterable.class.isAssignableFrom(objClass) || Iterator.class.isAssignableFrom(objClass)) {
                this.appendIterable(logEntry, object, objClass);
            } else if (Map.class.isAssignableFrom(objClass)) {
                this.appendMap(logEntry, object, objClass);
            } else {
                logEntry.append(EnumChar.PARENTHESIS_LEFT).append(objClass.getSimpleName()).append(EnumChar.PARENTHESIS_RIGHT)
                        .append(EnumChar.BRACE_LEFT).append(object).append(EnumChar.BRACE_RIGHT);
            }
        }
    }

    /**
     * Check if simple type.
     * 
     * @param logEntry
     *            log entry
     * @param object
     *            object
     * @param objClass
     *            object classs
     * @return <code>true</code> if simple type, <code>false</code> otherwise
     */
    protected boolean checkSimpleType(final StringBuilder logEntry, final Object object, final Class<?> objClass) {
        boolean done = false;

        if (String.class.equals(objClass)) {
            logEntry.append(EnumChar.QUOTE).append(object).append(EnumChar.QUOTE);
            done = true;
        } else if (Character.class.isAssignableFrom(objClass)) {
            logEntry.append(object);
            done = true;
        } else if (Number.class.isAssignableFrom(objClass)) {
            logEntry.append(object);
            done = true;
        } else if (Boolean.class.isAssignableFrom(objClass)) {
            logEntry.append(object);
            done = true;
        } else if (Enum.class.isAssignableFrom(objClass)) {
            logEntry.append(objClass.getSimpleName()).append(EnumChar.DOT).append(object);
            done = true;
        } else if (Date.class.isAssignableFrom(objClass)) {
            logEntry.append(objClass.getSimpleName()).append(EnumChar.BRACE_LEFT).append(this.formatDate((Date) object))
                    .append(EnumChar.BRACE_RIGHT);
            done = true;
        }

        return done;
    }

    /**
     * Format the date.
     * 
     * @param date
     *            date
     * @return the formatted date
     */
    protected String formatDate(final Date date) {
        final Calendar calendar = DateUtils.getCalendar(date);

        final int time = calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND)
                + calendar.get(Calendar.MILLISECOND);
        if (time > 0) {
            return this.dtf.format(date);
        }
        return this.df.format(date);
    }

    /**
     * Append an array.
     * 
     * @param logEntry
     *            log entry
     * @param object
     *            object
     * @param objClass
     *            object class
     */
    protected void appendArray(final StringBuilder logEntry, final Object object, final Class<?> objClass) {
        int loop = 0;
        final Object[] objects = (Object[]) object;

        logEntry.append(EnumChar.PARENTHESIS_LEFT).append(objClass.getSimpleName()).append(EnumChar.PARENTHESIS_RIGHT);

        logEntry.append(EnumChar.BRACKET_LEFT);
        for (loop = 0; loop < objects.length && loop < MAX_MULTIPLES_LOG; loop++) {
            logEntry.append(objects[loop]).append(EnumChar.COMMA).append(EnumChar.SPACE);
        }

        if (objects.length > 0) {
            final String replacement;
            if (loop >= MAX_MULTIPLES_LOG) {
                replacement = EnumChar.ELLIPSIS.toString();
            } else {
                replacement = "";
            }
            logEntry.replace(logEntry.length() - 2, logEntry.length(), replacement);
        }
        logEntry.append(EnumChar.BRACKET_RIGHT);
    }

    /**
     * Append an iterator.
     * 
     * @param logEntry
     *            log entry
     * @param object
     *            object
     * @param objClass
     *            object class
     */
    protected void appendIterable(final StringBuilder logEntry, final Object object, final Class<?> objClass) {
        int loop = 0;
        final Iterator<?> iterator;
        if (Iterable.class.isAssignableFrom(objClass)) {
            iterator = ((Iterable<?>) object).iterator();
        } else {
            iterator = (Iterator<?>) object;
        }

        logEntry.append(EnumChar.PARENTHESIS_LEFT).append(objClass.getSimpleName()).append(EnumChar.PARENTHESIS_RIGHT);

        logEntry.append(EnumChar.BRACKET_LEFT);
        if (iterator.hasNext()) {
            while (iterator.hasNext() && loop < MAX_MULTIPLES_LOG) {
                Object obj = iterator.next();
                logEntry.append(obj).append(EnumChar.COMMA).append(EnumChar.SPACE);
                loop++;
            }

            final String replacement;
            if (!iterator.hasNext()) {
                replacement = "";
            } else {
                replacement = EnumChar.ELLIPSIS.toString();
            }
            logEntry.replace(logEntry.length() - 2, logEntry.length(), replacement);
        }

        logEntry.append(EnumChar.BRACKET_RIGHT);
    }

    /**
     * Append a map.
     * 
     * @param logEntry
     *            log entry
     * @param object
     *            object
     * @param objClass
     *            object class
     */
    protected void appendMap(final StringBuilder logEntry, final Object object, final Class<?> objClass) {
        int loop = 0;
        final Map<?, ?> map = (Map<?, ?>) object;

        logEntry.append(EnumChar.PARENTHESIS_LEFT).append(objClass.getSimpleName()).append(EnumChar.PARENTHESIS_RIGHT);

        logEntry.append(EnumChar.BRACKET_LEFT);
        String replacement = "";
        for (final Entry<?, ?> entry : map.entrySet()) {
            logEntry.append(entry.getKey()).append(EnumChar.EQUALS).append(entry.getValue()).append(EnumChar.COMMA).append(EnumChar.SPACE);

            if (++loop >= MAX_MULTIPLES_LOG) {
                replacement = EnumChar.ELLIPSIS.toString();
                break;
            }
        }

        if (!map.isEmpty()) {
            logEntry.replace(logEntry.length() - 2, logEntry.length(), replacement);
        }
        logEntry.append(EnumChar.BRACKET_RIGHT);
    }

    /**
     * Logs join point.
     * 
     * @param joinPoint
     *            The joint point to log
     */
    protected void log(final JoinPoint joinPoint) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getSignature(joinPoint));
        }
    }

    /**
     * Profiles call join point.
     * 
     * @param call
     *            The call joint point to log
     * @return The proceeded object
     * @throws AOPException
     *             The exception thrown during proceed
     */
    protected Object profile(final ProceedingJoinPoint call) throws AOPException {
        if (this.logger.isDebugEnabled()) {
            final StopWatch clock = new StopWatch("Profiling for '" + call.getSignature().getDeclaringType().getCanonicalName() + "'");
            try {
                clock.start(call.toShortString());
                return call.proceed();
            } catch (final Throwable t) {
                throw new AOPException("Error occurred during profiling " + call.getSignature().toString(), t);
            } finally {
                clock.stop();

                if (clock.getTotalTimeSeconds() > MAX_TIMEOUT) {
                    this.logger.debug(this.getSignature(call) + ", running time: " + clock.getTotalTimeMillis() + " ms");
                }
            }
        } else {
            try {
                return call.proceed();
            } catch (final Throwable t) {
                throw new AOPException("Error occurred during profiling " + call.getSignature().toString(), t);
            }
        }
    }
}
