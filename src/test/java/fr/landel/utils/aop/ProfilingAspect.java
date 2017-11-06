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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import fr.landel.utils.aop.exception.AOPException;

/**
 * Profile aspect
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 */
@Aspect
public class ProfilingAspect extends AbstractAspectExtends {

    /**
     * point cut for all methods of the service layer of the application
     */
    @Pointcut("execution(* " + OBSERVABLE + ".*(..))")
    public void trace() {
        // Point cut, no code here
    }

    /**
     * Profile trace service
     * 
     * @param call
     *            The call join point
     * @return The proceeded object
     * @throws AOPException
     *             The exception thrown during proceed
     */
    @Around("trace()")
    public final Object profileService(final ProceedingJoinPoint call) throws AOPException {
        return super.profile(call);
    }
}
