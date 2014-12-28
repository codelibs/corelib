/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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

import org.codelibs.core.exception.ClNoSuchElementException;
import org.codelibs.core.exception.ClUnsupportedOperationException;
import org.codelibs.core.exception.ParseRuntimeException;
import org.codelibs.core.misc.LocaleUtil;

/**
 * 日付だけを表現するオブジェクトから{@link Date}、{@link Calendar}、{@link java.sql.Date}
 * への変換ユーティリティです。
 * <p>
 * 時刻だけを表現するオブジェクトを変換する場合は {@link TimeConversionUtil}を、 日付と時刻を表現するオブジェクトを変換する場合は
 * {@link TimestampConversionUtil}を 参照してください。
 * </p>
 * <p>
 * 変換元のオブジェクトが{@link Date}、{@link Calendar}、{@link java.sql.Date}の場合は、
 * それらの持つミリ秒単位の値を使って変換後のオブジェクトを作成します。
 * その他の型の場合は変換元オブジェクトの文字列表現から変換後のオブジェクトを作成します。
 * </p>
 * <p>
 * パターンを指定されなかった場合、変換に使用するパターンはロケールに依存して次のようになります。
 * </p>
 * <table border="1">
 * <caption></caption>
 * <tr>
 * <th>カテゴリ</th>
 * <th>パターン</th>
 * <th>{@link Locale#JAPANESE}の例</th>
 * </tr>
 * <tr>
 * <td rowspan="4">{@link DateFormat}の標準形式</td>
 * <td>{@link DateFormat#SHORT}の形式</td>
 * <td>{@literal yy/MM/dd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#MEDIUM}の形式</td>
 * <td>{@literal yyyy/MM/dd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#LONG}の形式</td>
 * <td>{@literal yyyy/MM/dd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#FULL}の形式</td>
 * <td>{@literal yyyy'年'M'月'd'日'}</td>
 * </tr>
 * <tr>
 * <td rowspan="4">プレーン形式</td>
 * <td>{@link DateFormat#SHORT}の区切り文字を除去した形式</td>
 * <td>{@literal yyMMdd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#MEDIUM}の区切り文字を除去した形式</td>
 * <td>{@literal yyyyMMdd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#LONG}の区切り文字を除去した形式</td>
 * <td>{@literal yyyyMMdd}</td>
 * </tr>
 * <tr>
 * <td>{@link DateFormat#FULL}の区切り文字を除去した形式</td>
 * <td>{@literal yyyyMMdd}</td>
 * </tr>
 * <tr>
 * <td>その他</td>
 * <td>{@link java.sql.Date#valueOf(String) Jdbcエスケープ構文}形式</td>
 * <td>{@literal yyyy-MM-dd}</td>
 * </tr>
 * </table>
 *
 * @author higa
 * @see TimeConversionUtil
 * @see TimestampConversionUtil
 */
public abstract class DateConversionUtil {

    /** {@link DateFormat}が持つスタイルの配列 */
    protected static final int[] STYLES = new int[] { SHORT, MEDIUM, LONG, FULL };

    /**
     * デフォルロケールで{@link DateFormat#SHORT}スタイルのパターン文字列を返します。
     *
     * @return {@link DateFormat#SHORT}スタイルのパターン文字列
     */
    public static String getShortPattern() {
        return getShortPattern(LocaleUtil.getDefault());
    }

    /**
     * 指定されたロケールで{@link DateFormat#SHORT}スタイルのパターン文字列を返します。
     *
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return {@link DateFormat#SHORT}スタイルのパターン文字列
     */
    public static String getShortPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(SHORT, locale)).toPattern();
    }

    /**
     * デフォルロケールで{@link DateFormat#MEDIUM}スタイルのパターン文字列を返します。
     *
     * @return {@link DateFormat#MEDIUM}スタイルのパターン文字列
     */
    public static String getMediumPattern() {
        return getMediumPattern(LocaleUtil.getDefault());
    }

    /**
     * 指定されたロケールで{@link DateFormat#MEDIUM}スタイルのパターン文字列を返します。
     *
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return {@link DateFormat#MEDIUM}スタイルのパターン文字列
     */
    public static String getMediumPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(MEDIUM, locale)).toPattern();
    }

    /**
     * デフォルロケールで{@link DateFormat#LONG}スタイルのパターン文字列を返します。
     *
     * @return {@link DateFormat#LONG}スタイルのパターン文字列
     */
    public static String getLongPattern() {
        return getLongPattern(LocaleUtil.getDefault());
    }

    /**
     * 指定されたロケールで{@link DateFormat#LONG}スタイルのパターン文字列を返します。
     *
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return {@link DateFormat#LONG}スタイルのパターン文字列
     */
    public static String getLongPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(LONG, locale)).toPattern();
    }

    /**
     * デフォルロケールで{@link DateFormat#FULL}スタイルのパターン文字列を返します。
     *
     * @return {@link DateFormat#FULL}スタイルのパターン文字列
     */
    public static String getFullPattern() {
        return getFullPattern(LocaleUtil.getDefault());
    }

    /**
     * 指定されたロケールで{@link DateFormat#FULL}スタイルのパターン文字列を返します。
     *
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return {@link DateFormat#FULL}スタイルのパターン文字列
     */
    public static String getFullPattern(final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return ((SimpleDateFormat) getDateInstance(FULL, locale)).toPattern();
    }

    /**
     * オブジェクトを{@link Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @return 変換された{@link Date}
     */
    public static Date toDate(final Object src) {
        return toDate(src, null, LocaleUtil.getDefault());
    }

    /**
     * オブジェクトを{@link Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param pattern
     *            パターン文字列
     * @return 変換された{@link Date}
     */
    public static Date toDate(final Object src, final String pattern) {
        return toDate(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * オブジェクトを{@link Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return 変換された{@link Date}
     */
    public static Date toDate(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toDate(src, null, locale);
    }

    /**
     * オブジェクトを{@link Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param pattern
     *            パターン文字列
     * @param locale
     *            ロケール
     * @return 変換された{@link Date}
     */
    protected static Date toDate(final Object src, final String pattern,
            final Locale locale) {
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
            final SimpleDateFormat format = new SimpleDateFormat(pattern,
                    locale);
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
     * オブジェクトを{@link Calendar}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @return 変換された{@link Date}
     */
    public static Calendar toCalendar(final Object src) {
        return toCalendar(src, null, LocaleUtil.getDefault());
    }

    /**
     * オブジェクトを{@link Calendar}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param pattern
     *            パターン文字列
     * @return 変換された{@link Date}
     */
    public static Calendar toCalendar(final Object src, final String pattern) {
        return toCalendar(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * オブジェクトを{@link Calendar}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return 変換された{@link Date}
     */
    public static Calendar toCalendar(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toCalendar(src, null, locale);
    }

    /**
     * オブジェクトを{@link Calendar}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param pattern
     *            パターン文字列
     * @param locale
     *            ロケール
     * @return 変換された{@link Date}
     */
    protected static Calendar toCalendar(final Object src,
            final String pattern, final Locale locale) {
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
            final SimpleDateFormat format = new SimpleDateFormat(pattern,
                    locale);
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
     * オブジェクトを{@link java.sql.Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @return 変換された{@link java.sql.Date}
     */
    public static java.sql.Date toSqlDate(final Object src) {
        return toSqlDate(src, null, LocaleUtil.getDefault());
    }

    /**
     * オブジェクトを{@link java.sql.Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param pattern
     *            パターン文字列
     * @return 変換された{@link java.sql.Date}
     */
    public static java.sql.Date toSqlDate(final Object src, final String pattern) {
        return toSqlDate(src, pattern, LocaleUtil.getDefault());
    }

    /**
     * オブジェクトを{@link java.sql.Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return 変換された{@link java.sql.Date}
     */
    public static java.sql.Date toSqlDate(final Object src, final Locale locale) {
        assertArgumentNotNull("locale", locale);
        return toSqlDate(src, null, locale);
    }

    /**
     * オブジェクトを{@link java.sql.Date}に変換します。
     *
     * @param src
     *            変換元のオブジェクト
     * @param pattern
     *            パターン文字列
     * @param locale
     *            ロケール
     * @return 変換された{@link java.sql.Date}
     */
    protected static java.sql.Date toSqlDate(final Object src,
            final String pattern, final Locale locale) {
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
            final SimpleDateFormat format = new SimpleDateFormat(pattern,
                    locale);
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
     * 文字列を{@link Date}に変換します。
     *
     * @param str
     *            文字列
     * @param locale
     *            ロケール
     * @return 変換された{@link Date}
     */
    @SuppressWarnings("unchecked")
    protected static Date toDate(final String str, final Locale locale) {
        for (final DateFormat format : iterable(new DateFormatIterator(locale),
                new PlainDateFormatIterator(str, locale))) {
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
     * 文字列を{@link Date}に変換します。
     *
     * @param str
     *            文字列
     * @param format
     *            {@link DateFormat}
     * @return 変換された{@link Date}
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
     * {@link Date}を{@link Calendar}に変換します。
     *
     * @param date
     *            {@link Date}
     * @param locale
     *            ロケール
     * @return 変換された{@link Calendar}
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
     * 文字列を{@link java.sql.Date}に変換します。
     *
     * @param str
     *            文字列
     * @return 変換された{@link java.sql.Date}
     */
    protected static java.sql.Date toSqlDateJdbcEscape(final String str) {
        try {
            return java.sql.Date.valueOf(str);
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * パターン文字列を区切り文字を含まないプレーンなパターン文字列に変換して返します。
     *
     * @param pattern
     *            パターン文字列
     * @return 区切り文字を含まないプレーンなパターン文字列
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
     * ロケールが持つスタイルに対応する{@link DateFormat}を反復する{@link Iterator}です。
     *
     * @author koichik
     */
    protected static class DateFormatIterator implements Iterator<DateFormat> {

        /** ロケール */
        protected final Locale locale;

        /** 現在のスタイルを示すインデックス */
        protected int index;

        /**
         * インスタンスを構築します。
         *
         * @param locale
         *            ロケール
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
                throw new ClNoSuchElementException();
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
     * ロケールが持つスタイルに対応する{@link DateFormat}を反復する{@link Iterator}です。
     *
     * @author koichik
     */
    protected static class PlainDateFormatIterator implements
            Iterator<DateFormat> {

        /** 変換元の文字列 */
        protected final String src;

        /** ロケール */
        protected final Locale locale;

        /** 現在のスタイルを示すインデックス */
        protected int index;

        /**
         * インスタンスを構築します。
         *
         * @param src
         *            変換後の文字列
         * @param locale
         *            ロケール
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
                throw new ClNoSuchElementException();
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
