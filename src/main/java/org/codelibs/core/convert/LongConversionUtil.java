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
 * Utility class for conversions to {@link Long}.
 *
 * @author higa
 */
public abstract class LongConversionUtil {

    /**
     * Do not instantiate.
     */
    private LongConversionUtil() {
    }

    /**
     * Converts the given object to a {@link Long}.
     *
     * @param o
     *            the object to convert
     * @return the converted {@link Long}
     */
    public static Long toLong(final Object o) {
        return toLong(o, null);
    }

    /**
     * Converts the given object to a {@link Long}.
     *
     * @param o
     *            the object to convert
     * @param pattern
     *            the pattern string
     * @return the converted {@link Long}
     */
    public static Long toLong(final Object o, final String pattern) {
        if (o == null) {
            return null;
        } else if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof Number) {
            return ((Number) o).longValue();
        } else if (o instanceof String) {
            return toLong((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Long.valueOf(new SimpleDateFormat(pattern).format(o));
            }
            return ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? (long) 1 : (long) 0;
        } else {
            return toLong(o.toString());
        }
    }

    private static Long toLong(final String s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Long.valueOf(DecimalFormatUtil.normalize(s));
    }

    /**
     * Converts the given object to a {@literal long}.
     *
     * @param o
     *            the object to convert
     * @return the converted {@literal long}
     */
    public static long toPrimitiveLong(final Object o) {
        return toPrimitiveLong(o, null);
    }

    /**
     * Converts the given object to a {@literal long}.
     *
     * @param o
     *            the object to convert
     * @param pattern
     *            the pattern string
     * @return the converted {@literal long}
     */
    public static long toPrimitiveLong(final Object o, final String pattern) {
        if (o == null) {
            return 0;
        } else if (o instanceof Number) {
            return ((Number) o).longValue();
        } else if (o instanceof String) {
            return toPrimitiveLong((String) o);
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return Long.parseLong(new SimpleDateFormat(pattern).format(o));
            }
            return ((java.util.Date) o).getTime();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        } else {
            return toPrimitiveLong(o.toString());
        }
    }

    private static long toPrimitiveLong(final String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Long.parseLong(DecimalFormatUtil.normalize(s));
    }

}
