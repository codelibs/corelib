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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class for conversions related to {@link Calendar}.
 *
 * @author higa
 */
public abstract class CalendarConversionUtil {

    /**
     * Do not instantiate.
     */
    private CalendarConversionUtil() {
    }

    /**
     * Converts to {@link Calendar}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link Calendar}
     */
    public static Calendar toCalendar(final Object o) {
        return toCalendar(o, null);
    }

    /**
     * Converts to {@link Calendar}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link Calendar}
     */
    public static Calendar toCalendar(final Object o, final String pattern) {
        if (o instanceof Calendar) {
            return (Calendar) o;
        }
        final java.util.Date date = DateConversionUtil.toDate(o, pattern);
        if (date != null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
        return null;
    }

    /**
     * Converts to a {@link Calendar} with the local {@link TimeZone} and {@link Locale}.
     *
     * @param calendar
     *            {@link Calendar}
     * @return The converted {@link Calendar}
     */
    public static Calendar localize(final Calendar calendar) {
        assertArgumentNotNull("calendar", calendar);
        final Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeInMillis(calendar.getTimeInMillis());
        return localCalendar;
    }

}
