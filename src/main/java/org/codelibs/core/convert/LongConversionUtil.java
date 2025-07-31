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
    protected LongConversionUtil() {
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
        return switch (o) {
        case null -> null;
        case Long l -> l;
        case Number n -> n.longValue();
        case String s -> toLong(s);
        case java.util.Date d -> pattern != null ? Long.valueOf(new SimpleDateFormat(pattern).format(d)) : d.getTime();
        case Boolean b -> b ? 1L : 0L;
        default -> toLong(o.toString());
        };
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
        return switch (o) {
        case null -> 0L;
        case Number n -> n.longValue();
        case String s -> toPrimitiveLong(s);
        case java.util.Date d -> pattern != null ? Long.parseLong(new SimpleDateFormat(pattern).format(d)) : d.getTime();
        case Boolean b -> b ? 1L : 0L;
        default -> toPrimitiveLong(o.toString());
        };
    }

    private static long toPrimitiveLong(final String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Long.parseLong(DecimalFormatUtil.normalize(s));
    }

}
