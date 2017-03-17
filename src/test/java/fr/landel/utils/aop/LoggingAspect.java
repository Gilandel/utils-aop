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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Log aspect
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 */
@Aspect
public class LoggingAspect extends AbstractAspectExtends {

    /**
     * Point cut to monitor controller
     */
    @Pointcut("execution(* " + OBSERVABLE + ".*(..))")
    public void trace() {
        // Point cut, no code here
    }

    /**
     * Log traced methods before their executions
     * 
     * @param joinPoint
     *            The join point
     */
    @Before("trace()")
    public final void logController(final JoinPoint joinPoint) {
        super.log(joinPoint);
    }
}
