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
package org.codelibs.core.convert;

/**
 * Utility class for conversions related to {@link Boolean}.
 *
 * @author higa
 */
public abstract class BooleanConversionUtil {

    /**
     * Converts to {@link Boolean}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link Boolean}
     */
    public static Boolean toBoolean(final Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof Number) {
            final int num = ((Number) o).intValue();
            return num != 0;
        } else if (o instanceof String) {
            final String s = (String) o;
            if ("true".equalsIgnoreCase(s)) {
                return Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(s)) {
                return Boolean.FALSE;
            } else if (s.equals("0")) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Converts to {@literal boolean}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@literal boolean}
     */
    public static boolean toPrimitiveBoolean(final Object o) {
        final Boolean b = toBoolean(o);
        if (b != null) {
            return b;
        }
        return false;
    }

}
