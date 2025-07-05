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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Utility class for modifier operations.
 *
 * @author shot
 */
public abstract class ModifierUtil {

    /**
     * Do not instantiate.
     */
    private ModifierUtil() {
    }

    /**
     * Checks if the specified method is public.
     *
     * @param method
     *            the method to check. Must not be null.
     * @return true if public, false otherwise
     */
    public static boolean isPublic(final Method method) {
        assertArgumentNotNull("method", method);

        return isPublic(method.getModifiers());
    }

    /**
     * Checks if the specified field is public.
     *
     * @param field
     *            the field to check. Must not be null.
     * @return true if public, false otherwise
     */
    public static boolean isPublic(final Field field) {
        assertArgumentNotNull("field", field);

        return isPublic(field.getModifiers());
    }

    /**
     * Checks if the specified field is public, static, and final.
     *
     * @param field
     *            the field to check. Must not be null.
     * @return true if public, static, and final, false otherwise
     */
    public static boolean isPublicStaticFinalField(final Field field) {
        assertArgumentNotNull("field", field);

        return isPublicStaticFinal(field.getModifiers());
    }

    /**
     * Checks if the specified modifier is public, static, and final.
     *
     * @param modifier
     *            the modifier to check
     * @return true if public, static, and final, false otherwise
     */
    public static boolean isPublicStaticFinal(final int modifier) {
        return isPublic(modifier) && isStatic(modifier) && isFinal(modifier);
    }

    /**
     * Checks if the specified modifier is public.
     *
     * @param modifier
     *            the modifier to check
     * @return true if public, false otherwise
     */
    public static boolean isPublic(final int modifier) {
        return Modifier.isPublic(modifier);
    }

    /**
     * Checks if the specified class is abstract.
     *
     * @param clazz
     *            the class to check. Must not be null.
     * @return true if abstract, false otherwise
     */
    public static boolean isAbstract(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return isAbstract(clazz.getModifiers());
    }

    /**
     * Checks if the specified modifier is abstract.
     *
     * @param modifier
     *            the modifier to check
     * @return true if abstract, false otherwise
     */
    public static boolean isAbstract(final int modifier) {
        return Modifier.isAbstract(modifier);
    }

    /**
     * Checks if the specified modifier is static.
     *
     * @param modifier
     *            the modifier to check
     * @return true if static, false otherwise
     */
    public static boolean isStatic(final int modifier) {
        return Modifier.isStatic(modifier);
    }

    /**
     * Checks if the specified modifier is final.
     *
     * @param modifier
     *            the modifier to check
     * @return true if final, false otherwise
     */
    public static boolean isFinal(final int modifier) {
        return Modifier.isFinal(modifier);
    }

    /**
     * Checks if the specified method is final.
     *
     * @param method
     *            the method to check
     * @return true if final, false otherwise
     */
    public static boolean isFinal(final Method method) {
        return isFinal(method.getModifiers());
    }

    /**
     * Checks if the specified field is transient.
     *
     * @param field
     *            the field to check
     * @return true if transient, false otherwise
     * @see #isTransient(int)
     */
    public static boolean isTransient(final Field field) {
        return isTransient(field.getModifiers());
    }

    /**
     * Checks if the specified modifier is transient.
     *
     * @param modifier
     *            the modifier to check
     * @return true if transient, false otherwise
     */
    public static boolean isTransient(final int modifier) {
        return Modifier.isTransient(modifier);
    }

    /**
     * Checks if the specified field is an instance field.
     *
     * @param field
     *            the field to check. Must not be null.
     * @return true if instance field, false otherwise
     */
    public static boolean isInstanceField(final Field field) {
        assertArgumentNotNull("field", field);

        final int m = field.getModifiers();
        return !isStatic(m) && !isFinal(m);
    }

}
