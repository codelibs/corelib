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
package org.codelibs.core.lang;

import static org.codelibs.core.collection.ArrayUtil.asArray;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.codelibs.core.exception.ClIllegalArgumentException;
import org.codelibs.core.exception.IllegalAccessRuntimeException;

/**
 * Utility class for {@link Field} operations.
 *
 * @author higa
 */
public abstract class FieldUtil {

    /**
     * Do not instantiate.
     */
    protected FieldUtil() {
    }

    /**
     * Returns the value of a {@code static} field represented by the given {@link Field}.
     *
     * @param <T> the type of the field
     * @param field the field (must not be {@literal null})
     * @return the value represented by the {@code static} field
     * @throws IllegalAccessRuntimeException if the field cannot be accessed
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(final Field field) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        return (T) get(field, null);
    }

    /**
     * Returns the value of the field represented by the given {@link Field} for the specified object.
     *
     * @param <T> the type of the field
     * @param field the field (must not be {@literal null})
     * @param target the object from which to extract the field value; {@literal null} if the field is static
     * @return the value represented by the field in the object
     * @throws IllegalAccessRuntimeException if the field cannot be accessed
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(final Field field, final Object target) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            return (T) field.get(target);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), ex);
        }
    }

    /**
     * Returns the value of a {@literal static} {@link Field} as an int.
     *
     * @param field the field (must not be {@literal null})
     * @return the field value
     * @throws IllegalAccessRuntimeException {@link IllegalAccessException} if an error occurs
     * @see #getInt(Field, Object)
     */
    public static int getInt(final Field field) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        return getInt(field, null);
    }

    /**
     * Returns the value of a {@link Field} as an int.
     *
     * @param field the field (must not be {@literal null})
     * @param target the target object; {@literal null} if the field is static
     * @return the field value
     * @throws IllegalAccessRuntimeException {@link IllegalAccessException} if an error occurs
     * @see Field#getInt(Object)
     */
    public static int getInt(final Field field, final Object target) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            return field.getInt(target);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), ex);
        }
    }

    /**
     * Returns the value of a {@literal static} {@link Field} as a {@link String}.
     *
     * @param field the field (must not be {@literal null})
     * @return the field value
     * @throws IllegalAccessRuntimeException {@link IllegalAccessException} if an error occurs
     * @see #getString(Field, Object)
     */
    public static String getString(final Field field) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        return getString(field, null);
    }

    /**
     * Returns the value of a {@link Field} as a {@link String}.
     *
     * @param field the field (must not be {@literal null})
     * @param target the target object; {@literal null} if the field is static
     * @return the field value
     * @throws IllegalAccessRuntimeException {@link IllegalAccessException} if an error occurs
     * @see Field#get(Object)
     */
    public static String getString(final Field field, final Object target) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            return (String) field.get(target);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), ex);
        }
    }

    /**
     * Sets the value of the given {@link Field} representing a {@code static} field to the specified new value.
     *
     * @param field the field (must not be {@literal null})
     * @param value the new value for the {@literal static} field
     * @throws IllegalAccessRuntimeException if the field cannot be accessed
     * @see Field#set(Object, Object)
     */
    public static void set(final Field field, final Object value) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        set(field, null, value);
    }

    /**
     * Sets the value of the given {@link Field} representing the specified object argument's field to the specified new value.
     *
     * @param field the field (must not be {@literal null})
     * @param target the object whose field is to be modified; {@literal null} if the field is static
     * @param value the new value for the field of the {@code target} object
     * @throws IllegalAccessRuntimeException if the field cannot be accessed
     * @see Field#set(Object, Object)
     */
    public static void set(final Field field, final Object target, final Object value) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            field.set(target, value);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), e);
        } catch (final IllegalArgumentException e) {
            final Class<?> clazz = field.getDeclaringClass();
            final Class<?> fieldClass = field.getType();
            final Class<?> valueClass = value == null ? null : value.getClass();
            final Class<?> targetClass = target == null ? field.getDeclaringClass() : target.getClass();
            throw new ClIllegalArgumentException("field", "ECL0094",
                    asArray(clazz.getName(), clazz.getClassLoader(), fieldClass.getName(), fieldClass.getClassLoader(), field.getName(),
                            valueClass == null ? null : valueClass.getName(), valueClass == null ? null : valueClass.getClassLoader(),
                            value, targetClass == null ? null : targetClass.getName(),
                            targetClass == null ? null : targetClass.getClassLoader()),
                    e);
        }
    }

    /**
     * Checks if the given field is an instance field.
     *
     * @param field the field (must not be {@literal null})
     * @return {@literal true} if it is an instance field
     */
    public static boolean isInstanceField(final Field field) {
        assertArgumentNotNull("field", field);

        return !Modifier.isStatic(field.getModifiers());
    }

    /**
     * Checks if the given field is a public field.
     *
     * @param field the field (must not be {@literal null})
     * @return {@literal true} if it is a public field
     */
    public static boolean isPublicField(final Field field) {
        assertArgumentNotNull("field", field);

        return Modifier.isPublic(field.getModifiers());
    }

    /**
     * Checks if the given field is a final field.
     *
     * @param field the field (must not be {@literal null})
     * @return {@literal true} if it is a final field
     */
    public static boolean isFinalField(final Field field) {
        assertArgumentNotNull("field", field);

        return Modifier.isFinal(field.getModifiers());
    }

    /**
     * Returns the element type of a parameterized collection field.
     *
     * @param field a field of a parameterized collection type (must not be {@literal null})
     * @return the element type of the collection
     */
    public static Class<?> getElementTypeOfCollection(final Field field) {
        assertArgumentNotNull("field", field);

        final Type type = field.getGenericType();
        return GenericsUtil.getRawClass(GenericsUtil.getElementTypeOfCollection(type));
    }

    /**
     * Returns the key type of a parameterized map field.
     *
     * @param field a field of a parameterized map type (must not be {@literal null})
     * @return the key type of the map
     */
    public static Class<?> getKeyTypeOfMap(final Field field) {
        assertArgumentNotNull("field", field);

        final Type type = field.getGenericType();
        return GenericsUtil.getRawClass(GenericsUtil.getKeyTypeOfMap(type));
    }

    /**
     * Returns the value type of a parameterized map field.
     *
     * @param field a field of a parameterized map type (must not be {@literal null})
     * @return the value type of the map
     */
    public static Class<?> getValueTypeOfMap(final Field field) {
        assertArgumentNotNull("field", field);

        final Type type = field.getGenericType();
        return GenericsUtil.getRawClass(GenericsUtil.getValueTypeOfMap(type));
    }

}
