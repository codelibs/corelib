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

import static org.codelibs.core.convert.TemporalConversionUtil.toInstant;
import static org.codelibs.core.convert.TemporalConversionUtil.toLocalDate;
import static org.codelibs.core.convert.TemporalConversionUtil.toLocalDateTime;
import static org.codelibs.core.convert.TemporalConversionUtil.toLocalTime;
import static org.codelibs.core.convert.TemporalConversionUtil.toOffsetDateTime;
import static org.codelibs.core.convert.TemporalConversionUtil.toZonedDateTime;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import org.codelibs.core.exception.NullArgumentException;
import org.codelibs.core.exception.ParseRuntimeException;
import org.junit.Test;

/**
 * @author shinsuke
 */
public class TemporalConversionUtilTest {

    /** Fixed reference instant used across the tests: 2010-09-07T06:05:04Z. */
    private static final Instant INSTANT = Instant.parse("2010-09-07T06:05:04Z");

    /** Epoch milliseconds of {@link #INSTANT}. */
    private static final long MILLIS = INSTANT.toEpochMilli();

    /** Fixed time zone used to keep zone-dependent assertions environment independent. */
    private static final ZoneId UTC = ZoneOffset.UTC;

    // -----------------------------------------------------------------
    // toLocalDate
    // -----------------------------------------------------------------

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDate_Null() throws Exception {
        assertThat(toLocalDate(null), is(nullValue()));
        assertThat(toLocalDate(null, UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDate_EmptyString() throws Exception {
        assertThat(toLocalDate("", UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDate_Passthrough() throws Exception {
        final LocalDate src = LocalDate.of(2010, 9, 7);
        assertThat(toLocalDate(src, UTC), is(src));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDate_Date() throws Exception {
        assertThat(toLocalDate(new Date(MILLIS), UTC), is(LocalDate.of(2010, 9, 7)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDate_EpochMillis() throws Exception {
        assertThat(toLocalDate(MILLIS, UTC), is(LocalDate.of(2010, 9, 7)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDate_String() throws Exception {
        assertThat(toLocalDate("2010-09-07", UTC), is(LocalDate.of(2010, 9, 7)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDate_DefaultZone() throws Exception {
        assertThat(toLocalDate(new Date(MILLIS)), is(INSTANT.atZone(ZoneId.systemDefault()).toLocalDate()));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ParseRuntimeException.class)
    public void testToLocalDate_InvalidString() throws Exception {
        toLocalDate("invalid", UTC);
    }

    /**
     * @throws Exception
     */
    @Test(expected = NullArgumentException.class)
    public void testToLocalDate_NullZone() throws Exception {
        toLocalDate(new Date(MILLIS), null);
    }

    // -----------------------------------------------------------------
    // toLocalDateTime
    // -----------------------------------------------------------------

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDateTime_Null() throws Exception {
        assertThat(toLocalDateTime(null), is(nullValue()));
        assertThat(toLocalDateTime(null, UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDateTime_EmptyString() throws Exception {
        assertThat(toLocalDateTime("", UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDateTime_Passthrough() throws Exception {
        final LocalDateTime src = LocalDateTime.of(2010, 9, 7, 6, 5, 4);
        assertThat(toLocalDateTime(src, UTC), is(src));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDateTime_Date() throws Exception {
        assertThat(toLocalDateTime(new Date(MILLIS), UTC), is(LocalDateTime.of(2010, 9, 7, 6, 5, 4)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDateTime_EpochMillis() throws Exception {
        assertThat(toLocalDateTime(MILLIS, UTC), is(LocalDateTime.of(2010, 9, 7, 6, 5, 4)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDateTime_String() throws Exception {
        assertThat(toLocalDateTime("2010-09-07T06:05:04", UTC), is(LocalDateTime.of(2010, 9, 7, 6, 5, 4)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalDateTime_DefaultZone() throws Exception {
        assertThat(toLocalDateTime(new Date(MILLIS)), is(LocalDateTime.ofInstant(INSTANT, ZoneId.systemDefault())));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ParseRuntimeException.class)
    public void testToLocalDateTime_InvalidString() throws Exception {
        toLocalDateTime("invalid", UTC);
    }

    // -----------------------------------------------------------------
    // toLocalTime
    // -----------------------------------------------------------------

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalTime_Null() throws Exception {
        assertThat(toLocalTime(null), is(nullValue()));
        assertThat(toLocalTime(null, UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalTime_EmptyString() throws Exception {
        assertThat(toLocalTime("", UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalTime_Passthrough() throws Exception {
        final LocalTime src = LocalTime.of(6, 5, 4);
        assertThat(toLocalTime(src, UTC), is(src));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalTime_Date() throws Exception {
        assertThat(toLocalTime(new Date(MILLIS), UTC), is(LocalTime.of(6, 5, 4)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalTime_EpochMillis() throws Exception {
        assertThat(toLocalTime(MILLIS, UTC), is(LocalTime.of(6, 5, 4)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalTime_String() throws Exception {
        assertThat(toLocalTime("06:05:04", UTC), is(LocalTime.of(6, 5, 4)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToLocalTime_DefaultZone() throws Exception {
        assertThat(toLocalTime(new Date(MILLIS)), is(INSTANT.atZone(ZoneId.systemDefault()).toLocalTime()));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ParseRuntimeException.class)
    public void testToLocalTime_InvalidString() throws Exception {
        toLocalTime("invalid", UTC);
    }

    // -----------------------------------------------------------------
    // toInstant
    // -----------------------------------------------------------------

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_Null() throws Exception {
        assertThat(toInstant(null), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_EmptyString() throws Exception {
        assertThat(toInstant(""), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_Passthrough() throws Exception {
        assertThat(toInstant(INSTANT), is(INSTANT));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_Date() throws Exception {
        assertThat(toInstant(new Date(MILLIS)), is(INSTANT));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_Calendar() throws Exception {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(MILLIS);
        assertThat(toInstant(calendar), is(INSTANT));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_Timestamp_PreservesNanos() throws Exception {
        final Instant withNanos = INSTANT.plusNanos(123456789);
        final Timestamp timestamp = Timestamp.from(withNanos);
        assertThat(toInstant(timestamp), is(withNanos));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_EpochMillis() throws Exception {
        assertThat(toInstant(MILLIS), is(INSTANT));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToInstant_String() throws Exception {
        assertThat(toInstant("2010-09-07T06:05:04Z"), is(INSTANT));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ParseRuntimeException.class)
    public void testToInstant_InvalidString() throws Exception {
        toInstant("invalid");
    }

    // -----------------------------------------------------------------
    // toOffsetDateTime
    // -----------------------------------------------------------------

    /**
     * @throws Exception
     */
    @Test
    public void testToOffsetDateTime_Null() throws Exception {
        assertThat(toOffsetDateTime(null), is(nullValue()));
        assertThat(toOffsetDateTime(null, UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToOffsetDateTime_EmptyString() throws Exception {
        assertThat(toOffsetDateTime("", UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToOffsetDateTime_Passthrough() throws Exception {
        final OffsetDateTime src = OffsetDateTime.of(2010, 9, 7, 6, 5, 4, 0, ZoneOffset.UTC);
        assertThat(toOffsetDateTime(src, UTC), is(src));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToOffsetDateTime_Date() throws Exception {
        assertThat(toOffsetDateTime(new Date(MILLIS), UTC), is(OffsetDateTime.of(2010, 9, 7, 6, 5, 4, 0, ZoneOffset.UTC)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToOffsetDateTime_EpochMillis() throws Exception {
        assertThat(toOffsetDateTime(MILLIS, UTC), is(OffsetDateTime.of(2010, 9, 7, 6, 5, 4, 0, ZoneOffset.UTC)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToOffsetDateTime_String() throws Exception {
        assertThat(toOffsetDateTime("2010-09-07T06:05:04+00:00", UTC), is(OffsetDateTime.of(2010, 9, 7, 6, 5, 4, 0, ZoneOffset.UTC)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToOffsetDateTime_DefaultZone() throws Exception {
        assertThat(toOffsetDateTime(new Date(MILLIS)), is(OffsetDateTime.ofInstant(INSTANT, ZoneId.systemDefault())));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ParseRuntimeException.class)
    public void testToOffsetDateTime_InvalidString() throws Exception {
        toOffsetDateTime("invalid", UTC);
    }

    // -----------------------------------------------------------------
    // toZonedDateTime
    // -----------------------------------------------------------------

    /**
     * @throws Exception
     */
    @Test
    public void testToZonedDateTime_Null() throws Exception {
        assertThat(toZonedDateTime(null), is(nullValue()));
        assertThat(toZonedDateTime(null, UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToZonedDateTime_EmptyString() throws Exception {
        assertThat(toZonedDateTime("", UTC), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToZonedDateTime_Passthrough() throws Exception {
        final ZonedDateTime src = ZonedDateTime.of(2010, 9, 7, 6, 5, 4, 0, UTC);
        assertThat(toZonedDateTime(src, UTC), is(src));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToZonedDateTime_Date() throws Exception {
        assertThat(toZonedDateTime(new Date(MILLIS), UTC), is(ZonedDateTime.ofInstant(INSTANT, UTC)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToZonedDateTime_EpochMillis() throws Exception {
        assertThat(toZonedDateTime(MILLIS, UTC), is(ZonedDateTime.ofInstant(INSTANT, UTC)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToZonedDateTime_String() throws Exception {
        assertThat(toZonedDateTime("2010-09-07T06:05:04Z", UTC), is(ZonedDateTime.parse("2010-09-07T06:05:04Z")));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToZonedDateTime_DefaultZone() throws Exception {
        assertThat(toZonedDateTime(new Date(MILLIS)), is(ZonedDateTime.ofInstant(INSTANT, ZoneId.systemDefault())));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ParseRuntimeException.class)
    public void testToZonedDateTime_InvalidString() throws Exception {
        toZonedDateTime("invalid", UTC);
    }

}
