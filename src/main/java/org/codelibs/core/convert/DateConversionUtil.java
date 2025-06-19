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

import static java.text.DateFormat.FULL;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;
import static java.text.DateFormat.getDateInstance;
import static org.codelibs.core.collection.MultiIterator.iterable;
import static org.codelibs.core.lang.StringUtil.isEmpty;
import static org.codelibs.core.lang.StringUtil.isNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.codelibs.core.exception.ClUnsupportedOperationException;
import org.codelibs.core.exception.ParseRuntimeException;
import org.codelibs.core.misc.LocaleUtil;

/**
 * Utility for converting objects that represent only dates to {@link Date}, {@link Calendar}, or {@link java.sql.Date}.
 * <p>
 * For objects that represent only time, see {@link TimeConversionUtil}. For objects that represent both date and time, see {@link TimestampConversionUtil}.
 * </p>
 * <p>
 * If the source object is a {@link Date}, {@link Calendar}, or {@link java.sql.Date}, the converted object is created using the millisecond value of the source.
 * For other types, the converted object is created from the string representation of the source object.
 * </p>
 * <p>
 * If no pattern is specified, the pattern used for conversion depends on the locale as follows:
 * </p>
 * <table border="1">
 * <caption>Conversion Patterns</caption>
 * <tr>
 * <th>Category</th>
 * <th>Pattern</th>
 * <th>Example for {@link Locale#JAPANESE}</th>
 * </tr>
 * <tr>
 * <td rowspan="4">Standard formats of {@link DateFormat}</td>
 * <td>{@link DateFormat#SHORT} format</td>
 * <td>{@literal yy/MM/dd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#MEDIUM} format</td>
 * <td>{@literal yyyy/MM/dd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#LONG} format</td>
 * <td>{@literal yyyy/MM/dd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#FULL} format</td>
 * <td>{@literal yyyy'nen'M'gatsu'd'nichi'}</td>
 * </tr>
 * <tr>
 * <td rowspan="4">Plain formats</td>
 * <td>{@link DateFormat#SHORT} format without delimiters</td>
 * <td>{@literal yyMMdd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#MEDIUM} format without delimiters</td>
 * <td>{@literal yyyyMMdd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#LONG} format without delimiters</td>
 * <td>{@literal yyyyMMdd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#FULL} format without delimiters</td>
 * <td>{@literal yyyyMMdd}</td>
 * </tr>
 * <tr>
 * <td>Other</td>
 * <td>{@link java.sql.Date#valueOf(String) Jdbc escape syntax} format</td>
 * <td>{@literal yyyy-MM-dd}</td>
 * </tr>
 * </table>
 *
 * @author higa
 * @see TimeConversionUtil
 * @see TimestampConversionUtil
 */
public abstract class DateConversionUtil {

    /** Array of styles held by {@link DateFormat}. */
    protected static final int[] STYLES = new int[] { SHORT, MEDIUM, LONG, FULL };

    /**
     * Returns the pattern string for {@link DateFormat#SHORT} style in the default locale.
     *
     * @return the pattern string for {@link DateFormat#SHORT} style
     */
    public static String getShortPattern() {
        return getShortPattern(LocaleUtil.getDefault());
    }

    /**
     * Returns the pattern string for {@link DateFormat#SHORT} style in the specified locale.
     *
     * @param locale the locale (must not be {@literal null})
     * @return the pattern string for {@link DateFormat#SHORT} style
     */
    public static String getShortPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(SHORT, locale)).toPattern();
    }

    /**
     * Returns the pattern string for {@link DateFormat#MEDIUM} style in the default locale.
     *
     * @return the pattern string for {@link DateFormat#MEDIUM} style
     */
    public static String getMediumPattern() {
        return getMediumPattern(LocaleUtil.getDefault());
    }

    /**
     * Returns the pattern string for {@link DateFormat#MEDIUM} style in the specified locale.
     *
     * @param locale the locale (must not be {@literal null})
     * @return the pattern string for {@link DateFormat#MEDIUM} style
     */
    public static String getMediumPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(MEDIUM, locale)).toPattern();
    }

    /**
     * Returns the pattern string for {@link DateFormat#LONG} style in the default locale.
     *
     * @return the pattern string for {@link DateFormat#LONG} style
     */
    public static String getLongPattern() {
        return getLongPattern(LocaleUtil.getDefault());
    }

    /**
     * Returns the pattern string for {@link DateFormat#LONG} style in the specified locale.
     *
     * @param locale the locale (must not be {@literal null})
     * @return the pattern string for {@link DateFormat#LONG} style
     */
    public static String getLongPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(LONG, locale)).toPattern();
    }

    /**
     * Returns the pattern string for {@link DateFormat#FULL} style in the default locale.
     *
     * @return the pattern string for {@link DateFormat#FULL} style
     */
    public static String getFullPattern() {
        return getFullPattern(LocaleUtil.getDefault());
    }

    /**
     * Returns the pattern string for {@link DateFormat#FULL} style in the specified locale.
     *
     * @param locale the locale (must not be {@literal null})
     * @return the pattern string for {@link DateFormat#FULL} style
     */
    public static String getFullPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(FULL, locale)).toPattern();
    }

    /**
     * Converts an object to {@link Date}.
     *
     * @param src the source object
     * @return the converted {@link Date}
     */
    public static Date toDate(final Object src) {
        return toDate(src, null, LocaleUtil.getDefault());
    }

    /**
     * Converts an object to {@link Date}.
     *
     * @param src the source object
     * @param pattern the pattern string
     * @return the converted {@link Date}
     */
    public static Date toDate(final Object src, final String pattern) {
        return toDate(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * Converts an object to {@link Date}.
     *
     * @param src the source object
     * @param locale the locale (must not be {@literal null})
     * @return the converted {@link Date}
     */
    public static Date toDate(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toDate(src, null, locale);
    }

    /**
     * Converts an object to {@link Date}.
     *
     * @param src the source object
     * @param pattern the pattern string
     * @param locale the locale
     * @return the converted {@link Date}
     */
    protected static Date toDate(final Object src, final String pattern, final Locale locale) {
        if (src == null) {
            return null;
        }
        if (src.getClass() == Date.class) {
            return (Date) src;
        }
        if (src instanceof Date) {
            return new Date(((Date) src).getTime());
        }
        if (src instanceof Calendar) {
            return new Date(((Calendar) src).getTimeInMillis());
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        if (isNotEmpty(pattern)) {
            final SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
            final Date date = toDate(str, format);
            if (date != null) {
                return date;
            }
        }
        final Date date = toDate(str, locale);
        if (date != null) {
            return date;
        }
        final java.sql.Date sqlDate = toSqlDateJdbcEscape(str);
        if (sqlDate != null) {
            return new java.sql.Date(sqlDate.getTime());
        }
        throw new ParseRuntimeException(str);
    }

    /**
     * Converts an object to {@link Calendar}.
     *
     * @param src the source object
     * @return the converted {@link Date}
     */
    public static Calendar toCalendar(final Object src) {
        return toCalendar(src, null, LocaleUtil.getDefault());
    }

    /**
     * Converts an object to {@link Calendar}.
     *
     * @param src the source object
     * @param pattern the pattern string
     * @return the converted {@link Date}
     */
    public static Calendar toCalendar(final Object src, final String pattern) {
        return toCalendar(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * Converts an object to {@link Calendar}.
     *
     * @param src the source object
     * @param locale the locale (must not be {@literal null})
     * @return the converted {@link Date}
     */
    public static Calendar toCalendar(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toCalendar(src, null, locale);
    }

    /**
     * Converts an object to {@link Calendar}.
     *
     * @param src the source object
     * @param pattern the pattern string
     * @param locale the locale
     * @return the converted {@link Date}
     */
    protected static Calendar toCalendar(final Object src, final String pattern, final Locale locale) {
        if (src == null) {
            return null;
        }
        if (src instanceof Calendar) {
            return (Calendar) src;
        }
        if (src instanceof Date) {
            return toCalendar((Date) src, locale);
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        if (isNotEmpty(pattern)) {
            final SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
            final Date date = toDate(str, format);
            if (date != null) {
                return toCalendar(date, locale);
            }
        }
        final Date date = toDate(str, locale);
        if (date != null) {
            return toCalendar(date, locale);
        }
        final java.sql.Date sqlDate = toSqlDateJdbcEscape(str);
        if (sqlDate != null) {
            return toCalendar(sqlDate, locale);
        }
        throw new ParseRuntimeException(str);
    }

    /**
     * Converts an object to {@link java.sql.Date}.
     *
     * @param src the source object
     * @return the converted {@link java.sql.Date}
     */
    public static java.sql.Date toSqlDate(final Object src) {
        return toSqlDate(src, null, LocaleUtil.getDefault());
    }

    /**
     * Converts an object to {@link java.sql.Date}.
     *
     * @param src the source object
     * @param pattern the pattern string
     * @return the converted {@link java.sql.Date}
     */
    public static java.sql.Date toSqlDate(final Object src, final String pattern) {
        return toSqlDate(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * Converts an object to {@link java.sql.Date}.
     *
     * @param src the source object
     * @param locale the locale (must not be {@literal null})
     * @return the converted {@link java.sql.Date}
     */
    public static java.sql.Date toSqlDate(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toSqlDate(src, null, locale);
    }

    /**
     * Converts an object to {@link java.sql.Date}.
     *
     * @param src the source object
     * @param pattern the pattern string
     * @param locale the locale
     * @return the converted {@link java.sql.Date}
     */
    protected static java.sql.Date toSqlDate(final Object src, final String pattern, final Locale locale) {
        if (src == null) {
            return null;
        }
        if (src instanceof java.sql.Date) {
            return (java.sql.Date) src;
        }
        if (src instanceof Date) {
            return new java.sql.Date(((Date) src).getTime());
        }
        if (src instanceof Calendar) {
            return new java.sql.Date(((Calendar) src).getTimeInMillis());
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        if (isNotEmpty(pattern)) {
            final SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
            final Date date = toDate(str, format);
            if (date != null) {
                return new java.sql.Date(date.getTime());
            }
        }
        final Date date = toDate(str, locale);
        if (date != null) {
            return new java.sql.Date(date.getTime());
        }
        final java.sql.Date sqlDate = toSqlDateJdbcEscape(str);
        if (sqlDate != null) {
            return sqlDate;
        }
        throw new ParseRuntimeException(str);
    }

    /**
     * Converts a string to a {@link Date}.
     *
     * @param str the string
     * @param locale the locale
     * @return the converted {@link Date}
     */
    @SuppressWarnings("unchecked")
    protected static Date toDate(final String str, final Locale locale) {
        for (final DateFormat format : iterable(new DateFormatIterator(locale), new PlainDateFormatIterator(str, locale))) {
            if (format == null) {
                continue;
            }
            final Date date = toDate(str, format);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

    /**
     * Converts a string to a {@link Date}.
     *
     * @param str the string
     * @param format the {@link DateFormat}
     * @return the converted {@link Date}
     */
    protected static Date toDate(final String str, final DateFormat format) {
        final ParsePosition pos = new ParsePosition(0);
        final Date date = format.parse(str, pos);
        if (date == null) {
            return null;
        }
        final int index = pos.getIndex();
        if (index == 0) {
            return null;
        }
        if (index < str.length()) {
            return null;
        }
        return date;
    }

    /**
     * Converts a {@link Date} to a {@link Calendar}.
     *
     * @param date the {@link Date}
     * @param locale the locale
     * @return the converted {@link Calendar}
     */
    protected static Calendar toCalendar(final Date date, final Locale locale) {
        final Calendar calendar;
        if (locale == null) {
            calendar = Calendar.getInstance();
        } else {
            calendar = Calendar.getInstance(locale);
        }
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Converts a string to a {@link java.sql.Date}.
     *
     * @param str the string
     * @return the converted {@link java.sql.Date}
     */
    protected static java.sql.Date toSqlDateJdbcEscape(final String str) {
        try {
            return java.sql.Date.valueOf(str);
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * Converts a pattern string to a plain pattern string without delimiters.
     *
     * @param pattern the pattern string
     * @return the plain pattern string without delimiters
     */
    protected static String toPlainPattern(final String pattern) {
        final StringBuilder buf = new StringBuilder(pattern.length());
        for (int i = 0; i < pattern.length(); ++i) {
            final char ch = pattern.charAt(i);
            if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)) {
                buf.append(ch);
            }
        }
        if (buf.indexOf("yy") == -1) {
            final int pos = buf.indexOf("y");
            if (pos != -1) {
                buf.replace(pos, pos + 1, "yy");
            }
        }
        if (buf.indexOf("MM") == -1) {
            final int pos = buf.indexOf("M");
            if (pos != -1) {
                buf.replace(pos, pos + 1, "MM");
            }
        }
        if (buf.indexOf("dd") == -1) {
            final int pos = buf.indexOf("d");
            if (pos != -1) {
                buf.replace(pos, pos + 1, "dd");
            }
        }
        return new String(buf);
    }

    /**
     * {@link Iterator} that iterates over {@link DateFormat}s corresponding to the styles held by the locale.
     *
     * @author koichik
     */
    protected static class DateFormatIterator implements Iterator<DateFormat> {

        /** The locale. */
        protected final Locale locale;

        /** The index indicating the current style. */
        protected int index;

        /**
         * Constructs an instance.
         *
         * @param locale the locale
         */
        public DateFormatIterator(final Locale locale) {
            this.locale = locale;
        }

        @Override
        public boolean hasNext() {
            return index < STYLES.length;
        }

        @Override
        public DateFormat next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final int style = STYLES[index++];
            return DateFormat.getDateInstance(style, locale);
        }

        @Override
        public void remove() {
            throw new ClUnsupportedOperationException("remove");
        }

    }

    /**
     * {@link Iterator} that iterates over {@link DateFormat}s corresponding to the styles held by the locale.
     *
     * @author koichik
     */
    protected static class PlainDateFormatIterator implements Iterator<DateFormat> {

        /** The source string to convert. */
        protected final String src;

        /** The locale. */
        protected final Locale locale;

        /** The index indicating the current style. */
        protected int index;

        /**
         * Constructs an instance.
         *
         * @param src the string after conversion
         * @param locale the locale
         */
        public PlainDateFormatIterator(final String src, final Locale locale) {
            this.src = src;
            this.locale = locale;
        }

        @Override
        public boolean hasNext() {
            return index < STYLES.length;
        }

        @Override
        public DateFormat next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final int style = STYLES[index++];
            final DateFormat format = DateFormat.getDateInstance(style, locale);
            if (format instanceof SimpleDateFormat) {
                final SimpleDateFormat simpleFormat = (SimpleDateFormat) format;
                final String pattern = toPlainPattern(simpleFormat.toPattern());
                if (pattern.length() == src.length()) {
                    return new SimpleDateFormat(pattern);
                }
            }
            return null;
        }

        @Override
        public void remove() {
            throw new ClUnsupportedOperationException("remove");
        }

    }

}
