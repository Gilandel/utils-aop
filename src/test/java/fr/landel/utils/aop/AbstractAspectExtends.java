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

/**
 * Aspect implementation for tests
 *
 * @since Dec 2, 2015
 * @author Gilles
 *
 */
public abstract class AbstractAspectExtends extends AbstractAspect {

    /**
     * Observable
     */
    protected static final String OBSERVABLE = "fr.landel.utils.aop.observable.AOPObservable";
}
