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

import static org.codelibs.core.convert.TimestampConversionUtil.toCalendar;
import static org.codelibs.core.convert.TimestampConversionUtil.toDate;
import static org.codelibs.core.convert.TimestampConversionUtil.toPlainPattern;
import static org.codelibs.core.convert.TimestampConversionUtil.toSqlTimestamp;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.codelibs.core.misc.LocaleUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author higa
 */
public class TimestampConversionUtilTest {

    @Before
    public void setUp() throws Exception {
        LocaleUtil.setDefault(() -> Locale.JAPANESE);
    }

    @After
    public void tearDown() throws Exception {
        LocaleUtil.setDefault(null);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_Null() throws Exception {
        assertThat(toDate(null), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_EmptyString() throws Exception {
        assertThat(toDate(""), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_ShortStyle() throws Exception {
        final Date date = toDate("10/9/7 11:49");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date),
                is("2010/09/07 11:49:00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_MediumStyle() throws Exception {
        final Date date = toDate("2010/9/7 11:49:10");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_LongStyle() throws Exception {
        final Date date = toDate("2010/09/07 11:49:10 JST");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(date), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_FullStyle() throws Exception {
        final Date date = toDate("2010年9月7日 11時49分10秒 JST");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(date), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_PlainFormat() throws Exception {
        final Date date = toDate("20100907 114910");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_JdbcEscapeFormat() throws Exception {
        final Date date = toDate("2010-09-07 11:49:10.123");
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(date),
                is("2010/09/07 11:49:10.123"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_SpecificLocale() throws Exception {
        final Date date = toDate("SEP 7, 2010 11:49:10 AM", Locale.US);
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_SpecificPattern() throws Exception {
        final Date date = toDate("07/09/10 10:49:11", "dd/MM/yy ss:mm:HH");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_Null() throws Exception {
        assertThat(toCalendar(null), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_EmptyString() throws Exception {
        assertThat(toCalendar(""), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_ShortStyle() throws Exception {
        final Calendar calendar = toCalendar("10/9/7 11:49");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar
                .getTime()), is("2010/09/07 11:49:00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_MediumStyle() throws Exception {
        final Calendar calendar = toCalendar("2010/9/7 11:49:10");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar
                .getTime()), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_LongStyle() throws Exception {
        final Calendar calendar = toCalendar("2010/09/07 11:49:10 JST");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(calendar.getTime()), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_FullStyle() throws Exception {
        final Calendar calendar = toCalendar("2010年9月7日 11時49分10秒 JST");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(calendar.getTime()), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_PlainFormat() throws Exception {
        final Calendar calendar = toCalendar("20100907 114910");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar
                .getTime()), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_JdbcEscapeFormat() throws Exception {
        final Calendar calendar = toCalendar("2010-09-07 11:49:10.123");
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(calendar
                        .getTime()), is("2010/09/07 11:49:10.123"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_SpecificLocale() throws Exception {
        final Calendar calendar = toCalendar("SEP 7, 2010 11:49:10 AM",
                Locale.US);
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar
                .getTime()), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_SpecificPattern() throws Exception {
        final Calendar calendar = toCalendar("07/09/10 10:49:11",
                "dd/MM/yy ss:mm:HH");
        assertThat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar
                .getTime()), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToSqlTimestamp_Null() throws Exception {
        assertThat(toSqlTimestamp(null), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_EmptyString() throws Exception {
        assertThat(toSqlTimestamp(""), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_ShortStyle() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("10/9/7 11:49");
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp),
                is("2010/09/07 11:49:00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_MediumStyle() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("2010/9/7 11:49:10");
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_LongStyle() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("2010/09/07 11:49:10 JST");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(timestamp), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_FullStyle() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("2010年9月7日 11時49分10秒 JST");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(timestamp), is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_PlainFormat() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("20100907 114910");
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_JdbcEscapeFormat() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("2010-09-07 11:49:10.123");
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
                        .format(timestamp),
                is("2010/09/07 11:49:10.123"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_SpecificLocale() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("SEP 7, 2010 11:49:10 AM",
                Locale.US);
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTimestamp_SpecificPattern() throws Exception {
        final Timestamp timestamp = toSqlTimestamp("07/09/10 10:49:11",
                "dd/MM/yy ss:mm:HH");
        assertThat(
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp),
                is("2010/09/07 11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToPlainPattern() throws Exception {
        assertThat(toPlainPattern("y/M/d H:m:s"), is("yyMMdd HHmmss"));
    }

}
