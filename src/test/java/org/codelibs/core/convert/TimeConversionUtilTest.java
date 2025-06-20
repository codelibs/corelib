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

import static org.codelibs.core.convert.TimeConversionUtil.toCalendar;
import static org.codelibs.core.convert.TimeConversionUtil.toDate;
import static org.codelibs.core.convert.TimeConversionUtil.toPlainPattern;
import static org.codelibs.core.convert.TimeConversionUtil.toSqlTime;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.sql.Time;
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
public class TimeConversionUtilTest {

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
        final Date date = toDate("11:49");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(date), is("11:49:00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_MediumStyle() throws Exception {
        final Date date = toDate("11:49:10");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(date), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_LongStyle() throws Exception {
        final Date date = toDate("11:49:10 JST");
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(date), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_FullStyle() throws Exception {
        final Date date = toDate("11時49分10秒 JST");
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(date), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_PlainFormat() throws Exception {
        final Date date = toDate("114910");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(date), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_JdbcEscapeFormat() throws Exception {
        final Date date = toDate("11:49:10");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(date), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_SpecificLocale() throws Exception {
        final Date date = toDate("11:49:10 AM", "hh:mm:ss a", Locale.US);
        assertThat(new SimpleDateFormat("HH:mm:ss").format(date), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToDate_SpecificPattern() throws Exception {
        final Date date = toDate("10::49::11", "ss::mm::HH");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(date), is("11:49:10"));
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
        final Calendar calendar = toCalendar("11:49");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()), is("11:49:00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_MediumStyle() throws Exception {
        final Calendar calendar = toCalendar("11:49:10");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_LongStyle() throws Exception {
        final Calendar calendar = toCalendar("11:49:10 JST");
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(calendar.getTime()), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_FullStyle() throws Exception {
        final Calendar calendar = toCalendar("11時49分10秒 JST");
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(calendar.getTime()), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_PlainFormat() throws Exception {
        final Calendar calendar = toCalendar("114910");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_JdbcEscapeFormat() throws Exception {
        final Calendar calendar = toCalendar("11:49:10");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_SpecificLocale() throws Exception {
        final Calendar calendar = toCalendar("11:49:10", Locale.US);
        assertThat(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToCalendar_SpecificPattern() throws Exception {
        final Calendar calendar = toCalendar("10::49::11", "ss::mm::HH");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToSqlTime_Null() throws Exception {
        assertThat(toSqlTime(null), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_EmptyString() throws Exception {
        assertThat(toSqlTime(""), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_ShortStyle() throws Exception {
        final Time time = toSqlTime("11:49");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(time), is("11:49:00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_MediumStyle() throws Exception {
        final Time time = toSqlTime("11:49:10");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(time), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_LongStyle() throws Exception {
        final Time time = toSqlTime("11:49:10 JST");
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(time), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_FullStyle() throws Exception {
        final Time time = toSqlTime("11時49分10秒 JST");
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("JST"));
        assertThat(df.format(time), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_PlainFormat() throws Exception {
        final Time time = toSqlTime("114910");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(time), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_JdbcEscapeFormat() throws Exception {
        final Time time = toSqlTime("11:49:10");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(time), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_SpecificLocale() throws Exception {
        final Time time = toSqlTime("11:49:10", Locale.US);
        assertThat(new SimpleDateFormat("HH:mm:ss").format(time), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToTime_SpecificPattern() throws Exception {
        final Time time = toSqlTime("10::49::11", "ss::mm::HH");
        assertThat(new SimpleDateFormat("HH:mm:ss").format(time), is("11:49:10"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToPlainPattern() throws Exception {
        assertThat(toPlainPattern("H:m:s"), is("HHmmss"));
    }

}
