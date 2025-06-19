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
 * Exception thrown when a property cannot be found.
 *
 * @author higa
 *
 */
public class PropertyNotFoundRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -5177019197796206774L;

    private final Class<?> targetClass;

    private final String propertyName;

    /**
     * Returns a {@link PropertyNotFoundRuntimeException}.
     *
     * @param targetClass the target class
     * @param propertyName the property name
     */
    public PropertyNotFoundRuntimeException(final Class<?> targetClass, final String propertyName) {
        super("ECL0065", asArray(targetClass.getName(), propertyName));
        this.targetClass = targetClass;
        this.propertyName = propertyName;
    }

    /**
     * Returns the target class.
     *
     * @return the target class
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
        return propertyName;
    }

}
