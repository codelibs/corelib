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

import java.text.SimpleDateFormat;

import org.codelibs.core.lang.StringUtil;
import org.codelibs.core.text.DecimalFormatUtil;

/**
 * Utility class for conversions related to {@link Integer}.
 *
 * @author higa
 */
public abstract class IntegerConversionUtil {

    /**
     * Do not instantiate.
     */
    private IntegerConversionUtil() {
    }

    /**
     * Converts to {@link Integer}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link Integer}
     */
    public static Integer toInteger(final Object o) {
        return toInteger(o, null);
    }

    /**
     * Converts to {@link Integer}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link Integer}
     */
    public static Integer toInteger(final Object o, final String pattern) {
        if (o == null) {
            return null;
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Number) {
            return ((Number) o).intValue();
        } else if (o instanceof String) {
            return toInteger((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Integer.valueOf(new SimpleDateFormat(pattern).format(o));
            }
            return (int) ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        } else {
            return toInteger(o.toString());
        }
    }

    private static Integer toInteger(final String s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Integer.valueOf(DecimalFormatUtil.normalize(s));
    }

    /**
     * Converts to {@literal int}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@literal int}
     */
    public static int toPrimitiveInt(final Object o) {
        return toPrimitiveInt(o, null);
    }

    /**
     * Converts to {@literal int}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal int}
     */
    public static int toPrimitiveInt(final Object o, final String pattern) {
        if (o == null) {
            return 0;
        } else if (o instanceof Number) {
            return ((Number) o).intValue();
        } else if (o instanceof String) {
            return toPrimitiveInt((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Integer.parseInt(new SimpleDateFormat(pattern).format(o));
            }
            return (int) ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        } else {
            return toPrimitiveInt(o.toString());
        }
    }

    private static int toPrimitiveInt(final String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Integer.parseInt(DecimalFormatUtil.normalize(s));
    }

}
