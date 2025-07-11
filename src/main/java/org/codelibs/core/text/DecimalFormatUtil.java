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
package org.codelibs.core.text;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.codelibs.core.misc.LocaleUtil;

/**
 * Utility class for {@link DecimalFormat}.
 *
 * @author higa
 */
public abstract class DecimalFormatUtil {

    /**
     * Do not instantiate.
     */
    private DecimalFormatUtil() {
    }

    /**
     * Normalizes the string representation of a number.
     *
     * @param s
     *            A string representing a number
     * @return The normalized string
     * @see #normalize(String, Locale)
     */
    public static String normalize(final String s) {
        return normalize(s, LocaleUtil.getDefault());
    }

    /**
     * Normalizes the string representation of a number by removing grouping separators
     * and representing the decimal point with '.'.
     *
     * @param s
     *            A string representing a number
     * @param locale
     *            The locale. Must not be {@literal null}.
     * @return The normalized string
     */
    public static String normalize(final String s, final Locale locale) {
        assertArgumentNotNull("locale", locale);

        if (s == null) {
            return null;
        }
        final DecimalFormatSymbols symbols = DecimalFormatSymbolsUtil.getDecimalFormatSymbols(locale);
        final char decimalSep = symbols.getDecimalSeparator();
        final char groupingSep = symbols.getGroupingSeparator();
        final StringBuilder buf = new StringBuilder(20);
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == groupingSep) {
                continue;
            } else if (c == decimalSep) {
                c = '.';
            }
            buf.append(c);
        }
        return buf.toString();
    }

}
