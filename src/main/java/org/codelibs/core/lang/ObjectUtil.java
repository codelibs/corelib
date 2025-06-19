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

/**
 * Utility class for object operations.
 *
 * @author wyukawa
 */
public abstract class ObjectUtil {

    /**
     * Returns true if the two objects are equal, or both are null.
     *
     * @param o1 the first object
     * @param o2 the second object
     * @return true if equal or both null, false otherwise
     */
    public static boolean equals(final Object object1, final Object object2) {
        if (object1 == object2) {
            return true;
        }
        if (object1 == null || object2 == null) {
            return false;
        }
        return object1.equals(object2);
    }

    /**
     * Returns the hash code of the specified object, or 0 if the object is null.
     *
     * @param obj the object
     * @return the hash code or 0
     */
    public static int hashCode(final Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    /**
     * Returns the string representation of the specified object, or null if the object is null.
     *
     * @param obj the object
     * @return the string representation or null
     */
    public static String toString(final Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * Returns the object, or the defaultValue if the object is <code>null</code>.
     * <p>
     * Usage example:
     * </p>
     *
     * <pre>
     * ObjectUtil.defaultValue(null, "NULL")  = "NULL"
     * ObjectUtil.defaultValue(null, 1)    = 1
     * ObjectUtil.defaultValue(Boolean.TRUE, true) = Boolean.TRUE
     * ObjectUtil.defaultValue(null, null) = null
     * </pre>
     *
     * @param <T> the type of the object
     * @param t the object (can be <code>null</code>)
     * @param defaultValue the object to return if t is <code>null</code> (can be <code>null</code>)
     * @return t if not <code>null</code>, otherwise defaultValue
     */
    public static <T> T defaultValue(final T t, final T defaultValue) {
        return t == null ? defaultValue : t;
    }

    /**
     * Returns true if the two objects are not equal.
     *
     * @param o1 the first object
     * @param o2 the second object
     * @return true if not equal, false otherwise
     */
    public static boolean notEquals(final Object o1, final Object o2) {
        return !equals(o1, o2);
    }

}
