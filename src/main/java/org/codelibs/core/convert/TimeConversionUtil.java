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
import static java.text.DateFormat.getTimeInstance;
import static org.codelibs.core.lang.StringUtil.isEmpty;
import static org.codelibs.core.lang.StringUtil.isNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.codelibs.core.collection.MultiIterator;
import org.codelibs.core.exception.ClUnsupportedOperationException;
import org.codelibs.core.exception.ParseRuntimeException;
import org.codelibs.core.misc.LocaleUtil;

/**
 * Utility for converting time-representing objects to {@link Date}, {@link Calendar}, or {@link Time}.
 * <p>
 * For objects representing only dates, see {@link DateConversionUtil}. For objects representing both date and time,
 * refer to {@link TimestampConversionUtil}.
 * </p>
 * <p>
 * If the source object is an instance of {@link Date}, {@link Calendar}, or {@link Time},
 * the conversion uses their millisecond value to create the target object.
 * For other types, the conversion uses the string representation of the source object.
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
 * <td rowspan="4">Standard {@link DateFormat} formats</td>
 * <td>{@link DateFormat#SHORT} format</td>
 * <td>{@literal H:mm}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#MEDIUM} format</td>
 * <td>{@literal H:mm:ss}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#LONG} format</td>
 * <td>{@literal H:mm:ss z}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#FULL} format</td>
 * <td>{@literal H'時'mm'分'ss'秒' z}</td>
 * </tr>
 * <tr>
 * <td rowspan="4">Plain formats</td>
 * <td>{@link DateFormat#SHORT} format without delimiters</td>
 * <td>{@literal HHmm}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#MEDIUM} format without delimiters</td>
 * <td>{@literal HHmmss}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#LONG} format without delimiters</td>
 * <td>{@literal HHmmss z}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#FULL} format without delimiters</td>
 * <td>{@literal HHmmss z}</td>
 * </tr>
 * <tr>
 * <td>Other</td>
 * <td>{@link Time#valueOf(String) JDBC escape syntax} format</td>
 * <td>{@literal HH:mm:ss}</td>
 * </tr>
 * </table>
 *
 * @author koichik
 * @see DateConversionUtil
 * @see TimestampConversionUtil
 */
public abstract class TimeConversionUtil {

    /**
     * Do not instantiate.
     */
    private TimeConversionUtil() {
    }

    /** Array of styles held by {@link DateFormat} */
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
     * @param locale
     *            Locale. Must not be {@literal null}.
     * @return the pattern string for {@link DateFormat#SHORT} style
     */
    public static String getShortPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getTimeInstance(SHORT, locale)).toPattern();
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
     * @param locale
     *            Locale. Must not be {@literal null}.
     * @return the pattern string for {@link DateFormat#MEDIUM} style
     */
    public static String getMediumPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getTimeInstance(MEDIUM, locale)).toPattern();
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
     * @param locale
     *            Locale. Must not be {@literal null}.
     * @return the pattern string for {@link DateFormat#LONG} style
     */
    public static String getLongPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getTimeInstance(LONG, locale)).toPattern();
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
     * @param locale
     *            Locale. Must not be {@literal null}.
     * @return the pattern string for {@link DateFormat#FULL} style
     */
    public static String getFullPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getTimeInstance(FULL, locale)).toPattern();
    }

    /**
     * Converts the given object to a {@link Date}.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link Date}.
     */
    public static Date toDate(final Object src) {
        return toDate(src, null, LocaleUtil.getDefault());
    }

    /**
     * Converts the given object to a {@link Date}.
     *
     * @param src
     *            The source object to convert.
     * @param pattern
     *            The pattern string.
     * @return The converted {@link Date}.
     */
    public static Date toDate(final Object src, final String pattern) {
        return toDate(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * Converts the given object to a {@link Date}.
     *
     * @param src
     *            The source object to convert.
     * @param locale
     *            Locale. Must not be {@literal null}.
     * @return The converted {@link Date}.
     */
    public static Date toDate(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toDate(src, null, locale);
    }

    /**
     * Converts the given object to a {@link Date}.
     *
     * @param src
     *            The source object to convert.
     * @param pattern
     *            The pattern string.
     * @param locale
     *            Locale.
     * @return The converted {@link Date}.
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
        final Time time = toSqlTimeJdbcEscape(str);
        if (time != null) {
            return new Date(time.getTime());
        }
        throw new ParseRuntimeException(str);
    }

    /**
     * Converts the given object to a {@link Calendar}.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link Calendar}.
     */
    public static Calendar toCalendar(final Object src) {
        return toCalendar(src, null, LocaleUtil.getDefault());
    }

    /**
     * Converts the given object to a {@link Calendar}.
     *
     * @param src
     *            The source object to convert.
     * @param pattern
     *            The pattern string.
     * @return The converted {@link Calendar}.
     */
    public static Calendar toCalendar(final Object src, final String pattern) {
        return toCalendar(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * Converts the given object to a {@link Calendar}.
     *
     * @param src
     *            The source object to convert.
     * @param locale
     *            Locale. Must not be {@literal null}.
     * @return The converted {@link Calendar}.
     */
    public static Calendar toCalendar(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toCalendar(src, null, locale);
    }

    /**
     * Converts the given object to a {@link Calendar}.
     *
     * @param src
     *            The source object to convert.
     * @param pattern
     *            The pattern string.
     * @param locale
     *            Locale.
     * @return The converted {@link Calendar}.
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
        final Time time = toSqlTimeJdbcEscape(str);
        if (time != null) {
            return toCalendar(time, locale);
        }
        throw new ParseRuntimeException(str);
    }

    /**
     * Converts the given object to a {@link Time}.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link Time}.
     */
    public static Time toSqlTime(final Object src) {
        return toSqlTime(src, null, LocaleUtil.getDefault());
    }

    /**
     * Converts the given object to a {@link Time}.
     *
     * @param src
     *            The source object to convert.
     * @param pattern
     *            The pattern string.
     * @return The converted {@link Time}.
     */
    public static Time toSqlTime(final Object src, final String pattern) {
        return toSqlTime(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * Converts the given object to a {@link Time}.
     *
     * @param src
     *            The source object to convert.
     * @param locale
     *            Locale. Must not be {@literal null}.
     * @return The converted {@link Time}.
     */
    public static Time toSqlTime(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toSqlTime(src, null, locale);
    }

    /**
     * Converts the given object to a {@link Time}.
     *
     * @param src
     *            The source object to convert.
     * @param pattern
     *            The pattern string.
     * @param locale
     *            Locale.
     * @return The converted {@link Time}.
     */
    protected static Time toSqlTime(final Object src, final String pattern, final Locale locale) {
        if (src == null) {
            return null;
        }
        if (src.getClass() == Time.class) {
            return (Time) src;
        }
        if (src instanceof Date) {
            return new Time(((Date) src).getTime());
        }
        if (src instanceof Calendar) {
            return new Time(((Calendar) src).getTimeInMillis());
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        if (isNotEmpty(pattern)) {
            final SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
            final Date date = toDate(str, format);
            if (date != null) {
                return new Time(date.getTime());
            }
        }
        final Date date = toDate(str, locale);
        if (date != null) {
            return new Time(date.getTime());
        }
        final Time time = toSqlTimeJdbcEscape(str);
        if (time != null) {
            return time;
        }
        throw new ParseRuntimeException(str);
    }

    /**
     * Converts a string to a {@link Date}.
     *
     * @param str
     *            The string to convert.
     * @param locale
     *            The locale.
     * @return The converted {@link Date}.
     */
    @SuppressWarnings("unchecked")
    protected static Date toDate(final String str, final Locale locale) {
        for (final DateFormat format : MultiIterator.iterable(new DateFormatIterator(locale), new PlainDateFormatIterator(str, locale))) {
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
     * @param str
     *            The string to convert.
     * @param format
     *            {@link DateFormat}
     * @return The converted {@link Date}
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
     * @param date
     *            {@link Date}
     * @param locale
     *            Locale
     * @return The converted {@link Calendar}
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
     * Converts a string to a {@link Time}.
     *
     * @param str
     *            The string to convert.
     * @return The converted {@link Time}.
     */
    protected static Time toSqlTimeJdbcEscape(final String str) {
        try {
            return Time.valueOf(str);
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * Converts a pattern string to a plain pattern string without delimiters.
     *
     * @param pattern
     *            The pattern string.
     * @return The plain pattern string without delimiters.
     */
    protected static String toPlainPattern(final String pattern) {
        final StringBuilder buf = new StringBuilder(pattern.length());
        for (int i = 0; i < pattern.length(); ++i) {
            final char ch = pattern.charAt(i);
            if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)) {
                buf.append(ch);
            }
        }
        if (buf.indexOf("HH") == -1) {
            final int pos = buf.indexOf("H");
            if (pos != -1) {
                buf.replace(pos, pos + 1, "HH");
            }
        }
        if (buf.indexOf("mm") == -1) {
            final int pos = buf.indexOf("m");
            if (pos != -1) {
                buf.replace(pos, pos + 1, "mm");
            }
        }
        if (buf.indexOf("ss") == -1) {
            final int pos = buf.indexOf("s");
            if (pos != -1) {
                buf.replace(pos, pos + 1, "ss");
            }
        }
        return new String(buf);
    }

    /**
     * An {@link Iterator} that iterates over {@link DateFormat} instances corresponding to the styles supported by the locale.
     *
     * @author koichik
     */
    protected static class DateFormatIterator implements Iterator<DateFormat> {

        /** Locale */
        protected final Locale locale;

        /** Index indicating the current style */
        protected int index;

        /**
         * Constructs an instance.
         *
         * @param locale
         *            Locale
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
            return DateFormat.getTimeInstance(style, locale);
        }

        @Override
        public void remove() {
            throw new ClUnsupportedOperationException("remove");
        }

    }

    /**
     * An {@link Iterator} that iterates over {@link DateFormat} instances corresponding to the styles supported by the locale.
     *
     * @author koichik
     */
    protected static class PlainDateFormatIterator implements Iterator<DateFormat> {

        /** Source string to convert */
        protected final String src;

        /** Locale */
        protected final Locale locale;

        /** Index indicating the current style */
        protected int index;

        /**
         * Constructs an instance.
         *
         * @param src
         *            The string to convert.
         * @param locale
         *            Locale.
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
            final DateFormat format = DateFormat.getTimeInstance(style, locale);
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
