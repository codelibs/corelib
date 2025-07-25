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
 * Utility class for conversions related to {@link Byte}.
 *
 * @author higa
 */
public abstract class ByteConversionUtil {

    /**
     * Do not instantiate.
     */
    private ByteConversionUtil() {
    }

    /**
     * Converts to {@link Byte}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link Byte}
     */
    public static Byte toByte(final Object o) {
        return toByte(o, null);
    }

    /**
     * Converts to {@link Byte}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link Byte}
     */
    public static Byte toByte(final Object o, final String pattern) {
        if (o == null) {
            return null;
        } else if (o instanceof Byte) {
            return (Byte) o;
        } else if (o instanceof Number) {
            return ((Number) o).byteValue();
        } else if (o instanceof String) {
            return toByte((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Byte.valueOf(new SimpleDateFormat(pattern).format(o));
            }
            return (byte) ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? (byte) 1 : (byte) 0;
        } else {
            return toByte(o.toString());
        }
    }

    private static Byte toByte(final String s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Byte.valueOf(DecimalFormatUtil.normalize(s));
    }

    /**
     * Converts to {@literal byte}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@literal byte}
     */
    public static byte toPrimitiveByte(final Object o) {
        return toPrimitiveByte(o, null);
    }

    /**
     * Converts to {@literal byte}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal byte}
     */
    public static byte toPrimitiveByte(final Object o, final String pattern) {
        if (o == null) {
            return 0;
        } else if (o instanceof Number) {
            return ((Number) o).byteValue();
        } else if (o instanceof String) {
            return toPrimitiveByte((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Byte.parseByte(new SimpleDateFormat(pattern).format(o));
            }
            return (byte) ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? (byte) 1 : (byte) 0;
        } else {
            return toPrimitiveByte(o.toString());
        }
    }

    private static byte toPrimitiveByte(final String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Byte.parseByte(DecimalFormatUtil.normalize(s));
    }

}
