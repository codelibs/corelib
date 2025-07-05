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
 * Utility class for conversions related to {@link Short}.
 *
 * @author higa
 */
public abstract class ShortConversionUtil {

    /**
     * Do not instantiate.
     */
    private ShortConversionUtil() {
    }

    /**
     * Converts to {@link Short}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link Short}
     */
    public static Short toShort(final Object o) {
        return toShort(o, null);
    }

    /**
     * Converts to {@link Short}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link Short}
     */
    public static Short toShort(final Object o, final String pattern) {
        if (o == null) {
            return null;
        } else if (o instanceof Short) {
            return (Short) o;
        } else if (o instanceof Number) {
            return ((Number) o).shortValue();
        } else if (o instanceof String) {
            return toShort((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Short.valueOf(new SimpleDateFormat(pattern).format(o));
            }
            return (short) ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? (short) 1 : (short) 0;
        } else {
            return toShort(o.toString());
        }
    }

    private static Short toShort(final String s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Short.valueOf(DecimalFormatUtil.normalize(s));
    }

    /**
     * Converts to {@literal short}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@literal short}
     */
    public static short toPrimitiveShort(final Object o) {
        return toPrimitiveShort(o, null);
    }

    /**
     * Converts to {@literal short}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal short}
     */
    public static short toPrimitiveShort(final Object o, final String pattern) {
        if (o == null) {
            return 0;
        } else if (o instanceof Number) {
            return ((Number) o).shortValue();
        } else if (o instanceof String) {
            return toPrimitiveShort((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Short.parseShort(new SimpleDateFormat(pattern).format(o));
            }
            return (short) ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? (short) 1 : (short) 0;
        } else {
            return toPrimitiveShort(o.toString());
        }
    }

    private static short toPrimitiveShort(final String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Short.parseShort(DecimalFormatUtil.normalize(s));
    }

}
