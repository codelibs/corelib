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
package org.codelibs.core.exception;

import static org.codelibs.core.collection.ArrayUtil.asArray;

import org.codelibs.core.beans.Converter;

/**
 * Exception thrown when an error occurs in a {@link Converter}.
 *
 * @author higa
 */
public class ConverterRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 1L;

    private final String propertyName;

    private final Object value;

    /**
     * Constructs an instance.
     *
     * @param propertyName
     *            Property name
     * @param value
     *            Value
     * @param cause
     *            Cause
     */
    public ConverterRuntimeException(final String propertyName, final Object value, final Throwable cause) {
        super("ECL0097", asArray(propertyName, value, cause), cause);
        this.propertyName = propertyName;
        this.value = value;
    }

    /**
     * Returns the property name.
     *
     * @return Property name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Returns the value.
     *
     * @return Value
     */
    public Object getValue() {
        return value;
    }

}
