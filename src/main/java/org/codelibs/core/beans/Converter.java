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

/**
 * This interface performs conversion between strings and objects.
 *
 * @author higa
 */
public interface Converter {

    /**
     * Returns the value as a string.
     *
     * @param value
     *            the value
     * @return the value as a string
     */
    String getAsString(Object value);

    /**
     * Returns the value as an object.
     *
     * @param value
     *            the value
     * @return the value as an object
     */
    Object getAsObject(String value);

    /**
     * Returns {@literal true} if this converter can handle the specified type.
     *
     * @param clazz
     *            the type. Must not be {@literal null}
     * @return {@literal true} if this converter can handle the specified type
     */
    boolean isTarget(Class<?> clazz);

}
