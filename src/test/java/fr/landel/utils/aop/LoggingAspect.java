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
