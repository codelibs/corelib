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
 * Utility class for conversions related to {@link Double}.
 *
 * @author higa
 */
public abstract class DoubleConversionUtil {

    /**
     * Do not instantiate.
     */
    protected DoubleConversionUtil() {
    }

    /**
     * Converts to {@link Double}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link Double}
     */
    public static Double toDouble(final Object o) {
        return toDouble(o, null);
    }

    /**
     * Converts to {@link Double}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link Double}
     */
    public static Double toDouble(final Object o, final String pattern) {
        if (o == null) {
            return null;
        } else if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof Number) {
            return Double.valueOf(((Number) o).doubleValue());
        } else if (o instanceof String) {
            return toDouble((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Double.valueOf(new SimpleDateFormat(pattern).format(o));
            }
            return Double.valueOf(((java.util.Date) o).getTime());
        } else {
            return toDouble(o.toString());
        }
    }

    private static Double toDouble(final String s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Double.valueOf(DecimalFormatUtil.normalize(s));
    }

    /**
     * Converts to {@literal double}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@literal double}
     */
    public static double toPrimitiveDouble(final Object o) {
        return toPrimitiveDouble(o, null);
    }

    /**
     * Converts to {@literal double}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal double}
     */
    public static double toPrimitiveDouble(final Object o, final String pattern) {
        if (o == null) {
            return 0;
        } else if (o instanceof Number) {
            return ((Number) o).doubleValue();
        } else if (o instanceof String) {
            return toPrimitiveDouble((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Double.parseDouble(new SimpleDateFormat(pattern).format(o));
            }
            return ((java.util.Date) o).getTime();
        } else {
            return toPrimitiveDouble(o.toString());
        }
    }

    private static double toPrimitiveDouble(final String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Double.parseDouble(DecimalFormatUtil.normalize(s));
    }

}
