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

import java.lang.reflect.Field;

/**
 * Exception thrown when accessing a non-{@literal static} {@link Field} without specifying an object.
 *
 * @author koichik
 */
public class FieldNotStaticRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -7791347225750660981L;

    /**
     * The target class.
     */
    private final Class<?> targetClass;

    /**
     * The name of the field.
     */
    private final String fieldName;

    /**
     * Creates a {@link FieldNotStaticRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param fieldName
     *            Field name
     */
    public FieldNotStaticRuntimeException(final Class<?> targetClass, final String fieldName) {
        super("ECL0099", asArray(targetClass.getName(), fieldName));
        this.targetClass = targetClass;
        this.fieldName = fieldName;
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
     * Returns the field name.
     *
     * @return Field name
     */
    public String getFieldName() {
        return fieldName;
    }

}
