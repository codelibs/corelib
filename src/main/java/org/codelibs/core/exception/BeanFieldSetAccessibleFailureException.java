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

import java.lang.reflect.Field;

/**
 * Signals that a field could not be made accessible.
 */
public class BeanFieldSetAccessibleFailureException extends ClRuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * The target class.
     */
    protected final Class<?> targetClass;

    /**
     * The target field.
     */
    protected final transient Field targetField;

    /**
     * Creates a new {@link BeanFieldSetAccessibleFailureException} with the specified cause.
     *
     * @param componentClass
     *            the component class
     * @param targetField
     *            the target field
     * @param cause
     *            the cause
     */
    public BeanFieldSetAccessibleFailureException(final Class<?> componentClass, final Field targetField, final Throwable cause) {
        super("ECL0115", new Object[] { targetField }, cause);
        this.targetClass = componentClass;
        this.targetField = targetField;
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
     * Returns the target field.
     *
     * @return the target field
     */
    public Field getTargetField() {
        return targetField;
    }
}
