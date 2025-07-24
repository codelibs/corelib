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

import static org.codelibs.core.lang.StringUtil.isEmpty;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.codelibs.core.misc.Base64Util;

/**
 * Utility class for conversions related to {@link String}.
 *
 * @author higa
 */
public abstract class StringConversionUtil {

    /**
     * Do not instantiate.
     */
    private StringConversionUtil() {
    }

    /** WAVE DASH */
    public static final char WAVE_DASH = '\u301C';

    /** FULLWIDTH TILDE */
    public static final char FULLWIDTH_TILDE = '\uFF5E';

    /** DOUBLE VERTICAL LINE */
    public static final char DOUBLE_VERTICAL_LINE = '\u2016';

    /** PARALLEL TO */
    public static final char PARALLEL_TO = '\u2225';

    /** MINUS SIGN */
    public static final char MINUS_SIGN = '\u2212';

    /** FULLWIDTH HYPHEN-MINUS */
    public static final char FULLWIDTH_HYPHEN_MINUS = '\uFF0D';

    /** CENT SIGN */
    public static final char CENT_SIGN = '\u00A2';

    /** FULLWIDTH CENT SIGN */
    public static final char FULLWIDTH_CENT_SIGN = '\uFFE0';

    /** POUND SIGN */
    public static final char POUND_SIGN = '\u00A3';

    /** FULLWIDTH POUND SIGN */
    public static final char FULLWIDTH_POUND_SIGN = '\uFFE1';

    /** NOT SIGN */
    public static final char NOT_SIGN = '\u00AC';

    /** FULLWIDTH NOT SIGN */
    public static final char FULLWIDTH_NOT_SIGN = '\uFFE2';

    /**
     * Converts the given object to a string.
     *
     * @param value
     *            The object to convert
     * @return The converted {@literal String}
     */
    public static String toString(final Object value) {
        return toString(value, null);
    }

    /**
     * Converts the given object to a string.
     *
     * @param value
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal String}
     */
    public static String toString(final Object value, final String pattern) {
        return switch (value) {
        case null -> null;
        case String s -> s;
        case java.util.Date d -> toString(d, pattern);
        case Number n -> toString(n, pattern);
        case byte[] bytes -> Base64Util.encode(bytes);
        default -> value.toString();
        };
    }

    /**
     * Converts the given object to a string.
     *
     * @param value
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal String}
     */
    public static String toString(final Number value, final String pattern) {
        if (value != null) {
            if (pattern != null) {
                return new DecimalFormat(pattern).format(value);
            }
            return value.toString();
        }
        return null;
    }

    /**
     * Converts the given object to a string.
     *
     * @param value
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal String}
     */
    public static String toString(final java.util.Date value, final String pattern) {
        if (value != null) {
            if (pattern != null) {
                return new SimpleDateFormat(pattern).format(value);
            }
            return value.toString();
        }
        return null;
    }

    /**
     * Fixes a string created with Windows-specific mapping rules.
     *
     * @param source
     *            String created with Windows-specific mapping rules
     * @return The fixed string
     */
    public static String fromWindowsMapping(final String source) {
        if (isEmpty(source)) {
            return source;
        }
        final char[] array = source.toCharArray();
        for (int i = 0; i < array.length; ++i) {
            array[i] = switch (array[i]) {
            case WAVE_DASH -> FULLWIDTH_TILDE;
            case DOUBLE_VERTICAL_LINE -> PARALLEL_TO;
            case MINUS_SIGN -> FULLWIDTH_HYPHEN_MINUS;
            case CENT_SIGN -> FULLWIDTH_CENT_SIGN;
            case POUND_SIGN -> FULLWIDTH_POUND_SIGN;
            case NOT_SIGN -> FULLWIDTH_NOT_SIGN;
            default -> array[i];
            };
        }
        return new String(array);
    }

    /**
     * Fixes a string to match Windows-specific mapping rules.
     *
     * @param source
     *            The string
     * @return The string fixed to match Windows-specific mapping rules
     */
    public static String toWindowsMapping(final String source) {
        if (isEmpty(source)) {
            return source;
        }
        final char[] array = source.toCharArray();
        for (int i = 0; i < array.length; ++i) {
            array[i] = switch (array[i]) {
            case FULLWIDTH_TILDE -> WAVE_DASH;
            case PARALLEL_TO -> DOUBLE_VERTICAL_LINE;
            case FULLWIDTH_HYPHEN_MINUS -> MINUS_SIGN;
            case FULLWIDTH_CENT_SIGN -> CENT_SIGN;
            case FULLWIDTH_POUND_SIGN -> POUND_SIGN;
            case FULLWIDTH_NOT_SIGN -> NOT_SIGN;
            default -> array[i];
            };
        }
        return new String(array);
    }

}
