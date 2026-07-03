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
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import org.codelibs.core.exception.ParseRuntimeException;

/**
 * Utility for converting objects to {@code java.time} types such as {@link LocalDate}, {@link LocalDateTime}, {@link LocalTime},
 * {@link Instant}, {@link OffsetDateTime}, and {@link ZonedDateTime}.
 * <p>
 * This complements {@link DateConversionUtil}, {@link TimeConversionUtil}, and {@link TimestampConversionUtil}, which convert to the legacy
 * {@link Date}, {@link Calendar}, and {@link java.sql.Timestamp} types.
 * </p>
 * <p>
 * The following source object types are supported:
 * </p>
 * <ul>
 * <li>The target {@code java.time} type itself (returned as-is).</li>
 * <li>{@link Instant}, treated as an absolute point on the time line.</li>
 * <li>{@link Date} (including {@link java.sql.Timestamp}, {@link java.sql.Date}, and {@link java.sql.Time}), converted from its millisecond
 * value. {@link java.sql.Timestamp} additionally preserves nanosecond precision.</li>
 * <li>{@link Calendar}, converted from its instant.</li>
 * <li>{@link Number} (for example {@link Long}), interpreted as epoch milliseconds.</li>
 * <li>{@link String} in ISO-8601 format, parsed with the {@code parse} method of the target type (for example {@link LocalDate#parse}).</li>
 * </ul>
 * <p>
 * A {@literal null} source object is always converted to {@literal null}. Any other unsupported source object is converted from its
 * {@link Object#toString()} representation, and a {@link ParseRuntimeException} is thrown when it cannot be parsed.
 * </p>
 * <p>
 * Converting from an absolute point on the time line (a {@link Date}, {@link Calendar}, {@link Instant}, or epoch milliseconds) to a
 * zone-dependent type requires a {@link ZoneId}. The overloads that do not take a {@link ZoneId} use {@link ZoneId#systemDefault()}. The
 * zone is not used when the source is a {@link String}, because ISO-8601 strings already carry their own zone or offset information (or none
 * at all).
 * </p>
 *
 * @author shinsuke
 * @see DateConversionUtil
 * @see TimeConversionUtil
 * @see TimestampConversionUtil
 */
public abstract class TemporalConversionUtil {

    /**
     * Do not instantiate.
     */
    protected TemporalConversionUtil() {
    }

    /**
     * Converts the given object to a {@link LocalDate} using the system default time zone.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link LocalDate}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static LocalDate toLocalDate(final Object src) {
        return toLocalDate(src, ZoneId.systemDefault());
    }

    /**
     * Converts the given object to a {@link LocalDate}.
     *
     * @param src
     *            The source object to convert.
     * @param zoneId
     *            The time zone used when the source is an absolute point on the time line. Must not be {@literal null}.
     * @return The converted {@link LocalDate}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static LocalDate toLocalDate(final Object src, final ZoneId zoneId) {
        assertArgumentNotNull("zoneId", zoneId);
        if (src == null) {
            return null;
        }
        if (src instanceof LocalDate) {
            return (LocalDate) src;
        }
        final Instant instant = toEpochInstant(src);
        if (instant != null) {
            return instant.atZone(zoneId).toLocalDate();
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        try {
            return LocalDate.parse(str);
        } catch (final DateTimeParseException e) {
            throw new ParseRuntimeException(str);
        }
    }

    /**
     * Converts the given object to a {@link LocalDateTime} using the system default time zone.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link LocalDateTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static LocalDateTime toLocalDateTime(final Object src) {
        return toLocalDateTime(src, ZoneId.systemDefault());
    }

    /**
     * Converts the given object to a {@link LocalDateTime}.
     *
     * @param src
     *            The source object to convert.
     * @param zoneId
     *            The time zone used when the source is an absolute point on the time line. Must not be {@literal null}.
     * @return The converted {@link LocalDateTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static LocalDateTime toLocalDateTime(final Object src, final ZoneId zoneId) {
        assertArgumentNotNull("zoneId", zoneId);
        if (src == null) {
            return null;
        }
        if (src instanceof LocalDateTime) {
            return (LocalDateTime) src;
        }
        final Instant instant = toEpochInstant(src);
        if (instant != null) {
            return LocalDateTime.ofInstant(instant, zoneId);
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        try {
            return LocalDateTime.parse(str);
        } catch (final DateTimeParseException e) {
            throw new ParseRuntimeException(str);
        }
    }

    /**
     * Converts the given object to a {@link LocalTime} using the system default time zone.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link LocalTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static LocalTime toLocalTime(final Object src) {
        return toLocalTime(src, ZoneId.systemDefault());
    }

    /**
     * Converts the given object to a {@link LocalTime}.
     *
     * @param src
     *            The source object to convert.
     * @param zoneId
     *            The time zone used when the source is an absolute point on the time line. Must not be {@literal null}.
     * @return The converted {@link LocalTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static LocalTime toLocalTime(final Object src, final ZoneId zoneId) {
        assertArgumentNotNull("zoneId", zoneId);
        if (src == null) {
            return null;
        }
        if (src instanceof LocalTime) {
            return (LocalTime) src;
        }
        final Instant instant = toEpochInstant(src);
        if (instant != null) {
            return instant.atZone(zoneId).toLocalTime();
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        try {
            return LocalTime.parse(str);
        } catch (final DateTimeParseException e) {
            throw new ParseRuntimeException(str);
        }
    }

    /**
     * Converts the given object to an {@link Instant}.
     * <p>
     * An {@link Instant} is an absolute point on the time line, so no time zone is required.
     * </p>
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link Instant}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static Instant toInstant(final Object src) {
        if (src == null) {
            return null;
        }
        final Instant instant = toEpochInstant(src);
        if (instant != null) {
            return instant;
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        try {
            return Instant.parse(str);
        } catch (final DateTimeParseException e) {
            throw new ParseRuntimeException(str);
        }
    }

    /**
     * Converts the given object to an {@link OffsetDateTime} using the system default time zone.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link OffsetDateTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static OffsetDateTime toOffsetDateTime(final Object src) {
        return toOffsetDateTime(src, ZoneId.systemDefault());
    }

    /**
     * Converts the given object to an {@link OffsetDateTime}.
     *
     * @param src
     *            The source object to convert.
     * @param zoneId
     *            The time zone used when the source is an absolute point on the time line. Must not be {@literal null}.
     * @return The converted {@link OffsetDateTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static OffsetDateTime toOffsetDateTime(final Object src, final ZoneId zoneId) {
        assertArgumentNotNull("zoneId", zoneId);
        if (src == null) {
            return null;
        }
        if (src instanceof OffsetDateTime) {
            return (OffsetDateTime) src;
        }
        final Instant instant = toEpochInstant(src);
        if (instant != null) {
            return OffsetDateTime.ofInstant(instant, zoneId);
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        try {
            return OffsetDateTime.parse(str);
        } catch (final DateTimeParseException e) {
            throw new ParseRuntimeException(str);
        }
    }

    /**
     * Converts the given object to a {@link ZonedDateTime} using the system default time zone.
     *
     * @param src
     *            The source object to convert.
     * @return The converted {@link ZonedDateTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static ZonedDateTime toZonedDateTime(final Object src) {
        return toZonedDateTime(src, ZoneId.systemDefault());
    }

    /**
     * Converts the given object to a {@link ZonedDateTime}.
     *
     * @param src
     *            The source object to convert.
     * @param zoneId
     *            The time zone used when the source is an absolute point on the time line. Must not be {@literal null}.
     * @return The converted {@link ZonedDateTime}, or {@literal null} if the source is {@literal null} or an empty string.
     */
    public static ZonedDateTime toZonedDateTime(final Object src, final ZoneId zoneId) {
        assertArgumentNotNull("zoneId", zoneId);
        if (src == null) {
            return null;
        }
        if (src instanceof ZonedDateTime) {
            return (ZonedDateTime) src;
        }
        final Instant instant = toEpochInstant(src);
        if (instant != null) {
            return ZonedDateTime.ofInstant(instant, zoneId);
        }
        final String str = src.toString();
        if (isEmpty(str)) {
            return null;
        }
        try {
            return ZonedDateTime.parse(str);
        } catch (final DateTimeParseException e) {
            throw new ParseRuntimeException(str);
        }
    }

    /**
     * Returns the {@link Instant} for a source object that represents an absolute point on the time line.
     * <p>
     * Handles {@link Instant}, {@link Timestamp} (preserving nanosecond precision), {@link Date} (via its millisecond value, which is safe
     * for {@link java.sql.Date} and {@link java.sql.Time} whose {@code toInstant} is unsupported), {@link Calendar}, and {@link Number}
     * (interpreted as epoch milliseconds). Returns {@literal null} for any other type, including {@link String}, so the caller can fall
     * back to string parsing.
     * </p>
     *
     * @param src
     *            The source object. Must not be {@literal null}.
     * @return The corresponding {@link Instant}, or {@literal null} if the source does not represent an absolute point on the time line.
     */
    protected static Instant toEpochInstant(final Object src) {
        if (src instanceof Instant) {
            return (Instant) src;
        }
        if (src instanceof Timestamp) {
            return ((Timestamp) src).toInstant();
        }
        if (src instanceof Date) {
            return Instant.ofEpochMilli(((Date) src).getTime());
        }
        if (src instanceof Calendar) {
            return ((Calendar) src).toInstant();
        }
        if (src instanceof Number) {
            return Instant.ofEpochMilli(((Number) src).longValue());
        }
        return null;
    }

}
