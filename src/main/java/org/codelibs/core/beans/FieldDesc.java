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

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Interface for handling fields.
 *
 * @author koichik
 */
public interface FieldDesc {

    /**
     * Returns the {@link BeanDesc} of the class that owns this field.
     *
     * @return {@link BeanDesc}
     */
    BeanDesc getBeanDesc();

    /**
     * Returns the field.
     *
     * @return the field
     */
    Field getField();

    /**
     * Returns the field name.
     *
     * @return the field name
     */
    String getFieldName();

    /**
     * Returns the type of the field.
     *
     * @param <T>
     *            the type of the field
     * @return the type of the field
     */
    <T> Class<T> getFieldType();

    /**
     * Returns {@literal true} if the field is {@literal public}.
     *
     * @return {@literal true} if the field is {@literal public}
     */
    boolean isPublic();

    /**
     * Returns {@literal true} if the field is {@literal static}.
     *
     * @return {@literal true} if the field is {@literal static}
     */
    boolean isStatic();

    /**
     * Returns {@literal true} if the field is {@literal final}.
     *
     * @return {@literal true} if the field is {@literal final}
     */
    boolean isFinal();

    /**
     * Returns {@literal true} if the field is parameterized.
     *
     * @return {@literal true} if the field is parameterized
     */
    boolean isParameterized();

    /**
     * Returns the {@link ParameterizedClassDesc} that represents the type of the field.
     *
     * @return the {@link ParameterizedClassDesc} that represents the type of the field
     */
    ParameterizedClassDesc getParameterizedClassDesc();

    /**
     * Returns the element type if this field is a parameterized {@link Collection}.
     *
     * @return the element type if this field is a parameterized {@link Collection}, otherwise {@literal null}
     */
    Class<?> getElementClassOfCollection();

    /**
     * Returns the key type if this field is a parameterized {@link Map}.
     *
     * @return the key type if this field is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getKeyClassOfMap();

    /**
     * Returns the value type if this field is a parameterized {@link Map}.
     *
     * @return the value type if this field is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getValueClassOfMap();

    /**
     * Returns the value of the {@link Field}.
     *
     * @param <T>
     *            the type of the field
     * @param target
     *            the target object. Must not be {@literal null}
     * @return the value of the {@link Field}
     */
    <T> T getFieldValue(Object target);

    /**
     * Returns the value of the static {@link Field}.
     *
     * @param <T>
     *            the type of the field
     * @return the value of the {@link Field}
     */
    <T> T getStaticFieldValue();

    /**
     * Sets the value of the {@link Field}.
     *
     * @param target
     *            the target object. Must not be {@literal null}
     * @param value
     *            the value of the {@link Field}
     */
    void setFieldValue(Object target, Object value);

    /**
     * Sets the value of the static {@link Field}.
     *
     * @param value
     *            the value of the {@link Field}
     */
    void setStaticFieldValue(Object value);

}
