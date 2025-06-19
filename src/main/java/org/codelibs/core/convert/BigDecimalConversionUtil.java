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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.codelibs.core.lang.StringUtil;

/**
 * Utility class for conversions related to {@link BigDecimal}.
 *
 * @author higa
 */
public abstract class BigDecimalConversionUtil {

    /**
     * Converts to {@link BigDecimal}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(final Object o) {
        return toBigDecimal(o, null);
    }

    /**
     * Converts to {@link BigDecimal}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(final Object o, final String pattern) {
        if (o == null) {
            return null;
        } else if (o instanceof BigDecimal) {
            return (BigDecimal) o;
        } else if (o instanceof java.util.Date) {
            if (pattern != null) {
                return new BigDecimal(new SimpleDateFormat(pattern).format(o));
            }
            return new BigDecimal(Long.toString(((java.util.Date) o).getTime()));
        } else if (o instanceof String) {
            final String s = (String) o;
            if (StringUtil.isEmpty(s)) {
                return null;
            }
            return normalize(new BigDecimal(s));
        } else {
            return normalize(new BigDecimal(o.toString()));
        }
    }

    /**
     * Converts a {@link BigDecimal} to a string.
     *
     * @param dec
     *            The {@link BigDecimal} to convert
     * @return The converted string
     */
    public static String toString(final BigDecimal dec) {
        return dec.toPlainString();
    }

    /**
     * Normalizes a {@link BigDecimal}.
     *
     * @param dec
     *            The {@link BigDecimal} to normalize
     * @return The normalized data
     */
    private static BigDecimal normalize(final BigDecimal dec) {
        return new BigDecimal(dec.toPlainString());
    }

}
