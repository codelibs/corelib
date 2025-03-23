/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
package org.codelibs.core.beans;

import java.lang.reflect.ParameterizedType;

/**
 * Interface for handling parameterized classes.
 *
 * @author koichik
 */
public interface ParameterizedClassDesc {

    /**
     * Returns <code>true</code> if the class represented by this instance is parameterized.
     *
     * @return <code>true</code> if the class represented by this instance is parameterized
     */
    boolean isParameterizedClass();

    /**
     * Returns the raw class.
     *
     * @param <T>
     *            the type of the raw class
     * @return the raw class
     * @see ParameterizedType#getRawType()
     */
    <T> Class<T> getRawClass();

    /**
     * Returns an array of {@link ParameterizedClassDesc} representing the type arguments.
     * <p>
     * If the class represented by this instance is not a parameterized class, returns {@literal null}.
     * </p>
     *
     * @return an array of {@link ParameterizedClassDesc} representing the type arguments
     * @see ParameterizedType#getActualTypeArguments()
     */
    ParameterizedClassDesc[] getArguments();

}
