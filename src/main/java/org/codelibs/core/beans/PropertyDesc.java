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
package org.codelibs.core.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Interface for handling properties.
 *
 * @author higa
 */
public interface PropertyDesc {

    /**
     * Returns the property name.
     *
     * @return the property name
     */
    String getPropertyName();

    /**
     * Returns the property type.
     *
     * @param <T>
     *            the property type
     * @return the property type
     */
    <T> Class<T> getPropertyType();

    /**
     * Returns the getter method.
     *
     * @return the getter method
     */
    Method getReadMethod();

    /**
     * Returns whether the property has a getter method.
     *
     * @return whether the property has a getter method
     */
    boolean hasReadMethod();

    /**
     * Returns the setter method.
     *
     * @return the setter method
     */
    Method getWriteMethod();

    /**
     * Returns whether the property has a setter method.
     *
     * @return whether the property has a setter method
     */
    boolean hasWriteMethod();

    /**
     * Returns whether the property value can be retrieved.
     *
     * @return whether the property value can be retrieved
     */
    boolean isReadable();

    /**
     * Returns whether the property value can be set.
     *
     * @return whether the property value can be set
     */
    boolean isWritable();

    /**
     * Returns the public field recognized as a property.
     *
     * @return the public field recognized as a property
     */
    Field getField();

    /**
     * Returns the property value.
     *
     * @param <T>
     *            the property type
     * @param target
     *            the target object. Must not be {@literal null}
     * @return the property value
     */
    <T> T getValue(Object target);

    /**
     * Sets the value of the property.
     *
     * @param target
     *            the target object. Must not be {@literal null}
     * @param value
     *            the value to set to the property
     */
    void setValue(Object target, Object value);

    /**
     * Converts the value to the appropriate type if necessary based on the property type.
     *
     * @param <T>
     *            the converted type
     * @param value
     *            the value to be converted
     * @return the converted value
     */
    <T> T convertIfNeed(Object value);

    /**
     * Returns the {@link BeanDesc}.
     *
     * @return the {@link BeanDesc}
     */
    BeanDesc getBeanDesc();

    /**
     * Returns {@literal true} if this property is parameterized.
     *
     * @return {@literal true} if this property is parameterized
     */
    boolean isParameterized();

    /**
     * Returns the information if this property is parameterized.
     *
     * @return the information if this property is parameterized, otherwise {@literal null}
     */
    ParameterizedClassDesc getParameterizedClassDesc();

    /**
     * Returns the element type if this property is a parameterized {@link Collection}.
     *
     * @return the element type if this property is a parameterized {@link Collection}, otherwise {@literal null}
     */
    Class<?> getElementClassOfCollection();

    /**
     * Returns the key type if this property is a parameterized {@link Map}.
     *
     * @return the key type if this property is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getKeyClassOfMap();

    /**
     * Returns the value type if this property is a parameterized {@link Map}.
     *
     * @return the value type if this property is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getValueClassOfMap();

}
