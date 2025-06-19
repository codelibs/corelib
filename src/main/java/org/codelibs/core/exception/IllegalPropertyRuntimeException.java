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

/**
 * Exception thrown when setting a property value fails.
 *
 * @author higa
 *
 */
public class IllegalPropertyRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 3584516316082904020L;

    private final Class<?> targetClass;

    private final String propertyName;

    /**
     * Creates a {@link IllegalPropertyRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param propertyName
     *            Property name
     * @param cause
     *            The cause of the exception
     */
    public IllegalPropertyRuntimeException(final Class<?> targetClass, final String propertyName, final Throwable cause) {
        super("ECL0059", asArray(targetClass.getName(), propertyName, cause), cause);
        this.targetClass = targetClass;
        this.propertyName = propertyName;
    }

    /**
     * Returns the target class.
     *
     * @return Target class
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns the property name.
     *
     * @return Property name
     */
    public String getPropertyName() {
        return propertyName;
    }

}
