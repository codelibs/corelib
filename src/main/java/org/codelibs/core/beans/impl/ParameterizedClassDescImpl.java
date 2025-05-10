/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.core.beans.impl;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import org.codelibs.core.beans.ParameterizedClassDesc;

/**
 * Implementation class of {@link ParameterizedClassDesc}.
 *
 * @author koichik
 */
public class ParameterizedClassDescImpl implements ParameterizedClassDesc {

    /** The raw class */
    protected Class<?> rawClass;

    /** An array of {@link ParameterizedClassDesc} representing type arguments */
    protected ParameterizedClassDesc[] arguments;

    /**
     * Constructs an instance.
     *
     * @param rawClass
     *            The raw class. Must not be {@literal null}.
     */
    public ParameterizedClassDescImpl(final Class<?> rawClass) {
        assertArgumentNotNull("rawClass", rawClass);

        this.rawClass = rawClass;
    }

    /**
     * Constructs an instance.
     *
     * @param rawClass
     *            The raw class. Must not be {@literal null}.
     * @param arguments
     *            An array of {@link ParameterizedClassDesc} representing type arguments
     */
    public ParameterizedClassDescImpl(final Class<?> rawClass, final ParameterizedClassDesc[] arguments) {
        assertArgumentNotNull("rawClass", rawClass);

        this.rawClass = rawClass;
        this.arguments = arguments;
    }

    @Override
    public boolean isParameterizedClass() {
        return arguments != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<T> getRawClass() {
        return (Class<T>) rawClass;
    }

    /**
     * Sets the raw class.
     *
     * @param rawClass
     *            The raw class. Must not be {@literal null}.
     */
    public void setRawClass(final Class<?> rawClass) {
        assertArgumentNotNull("rawClass", rawClass);

        this.rawClass = rawClass;
    }

    @Override
    public ParameterizedClassDesc[] getArguments() {
        return arguments;
    }

    /**
     * Sets an array of {@link ParameterizedClassDesc} representing type arguments.
     *
     * @param arguments
     *            An array of {@link ParameterizedClassDesc} representing type arguments
     */
    public void setArguments(final ParameterizedClassDesc[] arguments) {
        this.arguments = arguments;
    }

}
