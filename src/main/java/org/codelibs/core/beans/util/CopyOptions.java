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
package org.codelibs.core.beans.util;

import static org.codelibs.core.collection.ArrayUtil.isEmpty;
import static org.codelibs.core.collection.CollectionsUtil.newArrayList;
import static org.codelibs.core.collection.CollectionsUtil.newHashMap;
import static org.codelibs.core.lang.ClassIterator.iterable;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.codelibs.core.beans.Converter;
import org.codelibs.core.beans.converter.DateConverter;
import org.codelibs.core.beans.converter.NumberConverter;
import org.codelibs.core.beans.converter.SqlDateConverter;
import org.codelibs.core.beans.converter.TimeConverter;
import org.codelibs.core.beans.converter.TimestampConverter;
import org.codelibs.core.convert.DateConversionUtil;
import org.codelibs.core.convert.TimeConversionUtil;
import org.codelibs.core.convert.TimestampConversionUtil;
import org.codelibs.core.exception.ConverterRuntimeException;

/**
 * Options to specify when copying JavaBeans or {@link Map} using {@link BeanUtil}.
 *
 * @author higa
 */
public class CopyOptions {

    /**
     * Default converter for dates.
     */
    protected static final Converter DEFAULT_DATE_CONVERTER = new DateConverter(DateConversionUtil.getMediumPattern());

    /**
     * Default converter for time.
     */
    protected static final Converter DEFAULT_TIME_CONVERTER = new DateConverter(TimeConversionUtil.getMediumPattern());

    /**
     * Default converter for timestamps.
     */
    protected static final Converter DEFAULT_TIMESTAMP_CONVERTER = new DateConverter(TimestampConversionUtil.getMediumPattern());

    /**
     * Array of property names to include in the operation.
     */
    protected final List<String> includePropertyNames = newArrayList();

    /**
     * Array of property names to exclude from the operation.
     */
    protected final List<String> excludePropertyNames = newArrayList();

    /**
     * Whether to exclude properties with null values from the operation.
     */
    protected boolean excludesNull = false;

    /**
     * Whether to exclude strings that consist only of whitespace from the operation.
     */
    protected boolean excludesWhitespace = false;

    /**
     * The prefix.
     */
    protected String prefix;

    /**
     * Delimiter for JavaBeans.
     */
    protected char beanDelimiter = '$';

    /**
     * Delimiter for Map.
     */
    protected char mapDelimiter = '.';

    /**
     * Converter associated with specific properties.
     */
    protected final Map<String, Converter> converterMap = newHashMap();

    /**
     * Converter not associated with specific properties.
     */
    protected final List<Converter> converters = newArrayList();

    /**
     * Adds property names to include in the operation.
     *
     * @param propertyNames
     *            Array of property names. Must not be {@literal null} or an empty array.
     * @return This instance itself
     */
    public CopyOptions include(final CharSequence... propertyNames) {
        assertArgumentNotEmpty("propertyNames", propertyNames);

        includePropertyNames.addAll(toStringList(propertyNames));
        return this;
    }

    /**
     * Adds property names to exclude from the operation.
     *
     * @param propertyNames
     *            Array of property names. Must not be {@literal null} or an empty array.
     * @return This instance itself
     */
    public CopyOptions exclude(final CharSequence... propertyNames) {
        assertArgumentNotEmpty("propertyNames", propertyNames);

        excludePropertyNames.addAll(toStringList(propertyNames));
        return this;
    }

    /**
     * Excludes properties with {@literal null} values from the operation.
     *
     * @return This instance itself
     */
    public CopyOptions excludeNull() {
        excludesNull = true;
        return this;
    }

    /**
     * Excludes properties with only whitespace from the operation.
     *
     * @return This instance itself
     */
    public CopyOptions excludeWhitespace() {
        excludesWhitespace = true;
        return this;
    }

    /**
     * Specifies the prefix.
     * <p>
     * When a prefix is specified, only properties whose names start with the prefix will be included in the copy operation.
     * Additionally, the prefix will be removed from the source property name to form the destination property name.
     * </p>
     *
     * @param prefix
     *            The prefix. Must not be {@literal null} or an empty string.
     * @return This instance itself
     */
    public CopyOptions prefix(final CharSequence prefix) {
        assertArgumentNotEmpty("propertyNames", prefix);

        this.prefix = prefix.toString();
        return this;
    }

    /**
     * Sets the delimiter for JavaBeans.
     * <p>
     * When copying between JavaBeans and {@link Map}, you can change the delimiter used in property names.
     * For example, if you specify an underscore as the delimiter for JavaBeans and a period as the delimiter for {@link Map},
     * the property names for the source and destination will be as follows:
     * </p>
     * <table border="1">
     * <caption>Copied JavaBeans Properties</caption>
     * <tr>
     * <th>JavaBeans Property Name</th>
     * <th>{@literal Map} Property Name</th>
     * </tr>
     * <tr>
     * <td>{@literal foo}</td>
     * <td>{@literal foo}</td>
     * </tr>
     * <tr>
     * <td>{@literal foo_bar}</td>
     * <td>{@literal foo.bar}</td>
     * </tr>
     * <tr>
     * <td>{@literal foo_bar_baz}</td>
     * <td>{@literal foo.bar.baz}</td>
     * </tr>
     * </table>
     *
     * @param beanDelimiter
     *            The delimiter for JavaBeans
     * @return This instance itself
     */
    public CopyOptions beanDelimiter(final char beanDelimiter) {
        this.beanDelimiter = beanDelimiter;
        return this;
    }

    /**
     * Sets the delimiter for {@link Map}.
     * <p>
     * When copying between JavaBeans and {@link Map}, you can change the delimiter used in property names.
     * For example, if you specify an underscore as the delimiter for JavaBeans and a period as the delimiter for {@link Map},
     * the property names for the source and destination will be as follows:
     * </p>
     * <table border="1">
     * <caption>Copied JavaBeans Properties</caption>
     * <tr>
     * <th>JavaBeans Property Name</th>
     * <th>{@literal Map} Property Name</th>
     * </tr>
     * <tr>
     * <td>{@literal foo}</td>
     * <td>{@literal foo}</td>
     * </tr>
     * <tr>
     * <td>{@literal foo_bar}</td>
     * <td>{@literal foo.bar}</td>
     * </tr>
     * <tr>
     * <td>{@literal foo_bar_baz}</td>
     * <td>{@literal foo.bar.baz}</td>
     * </tr>
     * </table>
     *
     * @param mapDelimiter
     *            The delimiter for {@link Map}
     * @return This instance itself
     */
    public CopyOptions mapDelimiter(final char mapDelimiter) {
        this.mapDelimiter = mapDelimiter;
        return this;
    }

    /**
     * Sets a converter.
     *
     * @param converter
     *            The converter. Must not be {@literal null}.
     * @param propertyNames
     *            The property names to which this converter will be applied. Each element must not be {@literal null} or an empty string.
     * @return This instance itself
     */
    public CopyOptions converter(final Converter converter, final CharSequence... propertyNames) {
        assertArgumentNotNull("converter", converter);

        if (isEmpty(propertyNames)) {
            converters.add(converter);
        } else {
            for (final CharSequence name : propertyNames) {
                assertArgumentNotEmpty("element of propertyNames", name);
                converterMap.put(name.toString(), converter);
            }
        }
        return this;
    }

    /**
     * Sets a converter for dates.
     *
     * @param pattern
     *            The date pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            The property names to which this converter will be applied. Each element must not be {@literal null} or an empty string.
     * @return This instance itself
     * @see DateConverter
     */
    public CopyOptions dateConverter(final String pattern, final CharSequence... propertyNames) {
        assertArgumentNotEmpty("pattern", pattern);

        return converter(new DateConverter(pattern), propertyNames);
    }

    /**
     * Sets a converter for SQL dates.
     *
     * @param pattern
     *            The date pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            The property names to which this converter will be applied. Each element must not be {@literal null} or an empty string.
     * @return This instance itself
     * @see SqlDateConverter
     */
    public CopyOptions sqlDateConverter(final String pattern, final CharSequence... propertyNames) {
        assertArgumentNotEmpty("pattern", pattern);

        return converter(new SqlDateConverter(pattern), propertyNames);
    }

    /**
     * Sets a converter for time.
     *
     * @param pattern
     *            The time pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            The property names to which this converter will be applied. Each element must not be {@literal null} or an empty string.
     * @return This instance itself
     * @see TimeConverter
     */
    public CopyOptions timeConverter(final String pattern, final CharSequence... propertyNames) {
        assertArgumentNotEmpty("pattern", pattern);

        return converter(new TimeConverter(pattern), propertyNames);
    }

    /**
     * Sets a converter for date and time.
     *
     * @param pattern
     *            The date and time pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            The property names to which this converter will be applied. Each element must not be {@literal null} or an empty string.
     * @return This instance itself
     * @see TimestampConverter
     */
    public CopyOptions timestampConverter(final String pattern, final CharSequence... propertyNames) {
        assertArgumentNotEmpty("pattern", pattern);

        return converter(new TimestampConverter(pattern), propertyNames);
    }

    /**
     * Sets a converter for numbers.
     *
     * @param pattern
     *            The number pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            The property names to which this converter will be applied. Each element must not be {@literal null} or an empty string.
     * @return This instance itself
     * @see NumberConverter
     */
    public CopyOptions numberConverter(final String pattern, final CharSequence... propertyNames) {
        assertArgumentNotEmpty("pattern", pattern);

        return converter(new NumberConverter(pattern), propertyNames);
    }

    /**
     * Converts an array of {@literal CharSequence} to a {@literal List} of {@literal String}.
     *
     * @param array
     *            An array of {@literal CharSequence}
     * @return A {@literal List} of {@literal String}
     */
    protected static List<String> toStringList(final CharSequence[] array) {
        final List<String> list = newArrayList(array.length);
        for (final CharSequence element : array) {
            list.add(element.toString());
        }
        return list;
    }

    /**
     * Returns whether the property is a target property.
     *
     * @param name
     *            The property name
     * @return Whether the property is a target property
     */
    protected boolean isTargetProperty(final String name) {
        if (prefix != null && !name.startsWith(prefix)) {
            return false;
        }
        if (!includePropertyNames.isEmpty()) {
            for (final String includeName : includePropertyNames) {
                if (includeName.equals(name)) {
                    for (final String excludeName : excludePropertyNames) {
                        if (excludeName.equals(name)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        if (!excludePropertyNames.isEmpty()) {
            for (final String excludeName : excludePropertyNames) {
                if (excludeName.equals(name)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    /**
     * Returns {@literal true} if the value is a target for copying.
     *
     * @param value
     *            The source value to be copied
     * @return {@literal true} if the value is a target for copying
     */
    protected boolean isTargetValue(final Object value) {
        if (value == null) {
            return !excludesNull;
        }
        if (value instanceof String && excludesWhitespace && ((String) value).trim().isEmpty()) {
            return !excludesWhitespace;
        }
        return true;
    }

    /**
     * Converts the source property name to the destination property name for use in a {@literal Map}.
     *
     * @param srcPropertyName
     *            The source property name
     * @return The destination property name
     */
    protected String toMapDestPropertyName(final String srcPropertyName) {
        return trimPrefix(srcPropertyName.replace(beanDelimiter, mapDelimiter));
    }

    /**
     * Converts the source property name to the destination property name for use in a Bean.
     *
     * @param srcPropertyName
     *            The source property name
     * @return The destination property name
     */
    protected String toBeanDestPropertyName(final String srcPropertyName) {
        return trimPrefix(srcPropertyName.replace(mapDelimiter, beanDelimiter));
    }

    /**
     * Trims the prefix.
     *
     * @param propertyName
     *            The property name
     * @return The result after trimming
     */
    protected String trimPrefix(final String propertyName) {
        if (prefix == null) {
            return propertyName;
        }
        return propertyName.substring(prefix.length());
    }

    /**
     * Converts a value.
     *
     * @param value
     *            The value
     * @param destPropertyName
     *            The destination property name
     * @param destPropertyClass
     *            The destination property class
     * @return The converted value
     */
    protected Object convertValue(final Object value, final String destPropertyName, final Class<?> destPropertyClass) {
        if (value == null || value.getClass() != String.class && destPropertyClass != null && destPropertyClass != String.class) {
            return value;
        }
        Converter converter = converterMap.get(destPropertyName);
        if (converter == null) {
            final Class<?> targetClass;
            if (value.getClass() != String.class) {
                targetClass = value.getClass();
            } else {
                targetClass = destPropertyClass;
            }
            if (targetClass == null) {
                return value;
            }
            for (final Class<?> clazz : iterable(targetClass, false)) {
                converter = findConverter(clazz);
                if (converter != null) {
                    break;
                }
            }
            if (converter == null && destPropertyClass != null) {
                converter = findDefaultConverter(targetClass);
            }
            if (converter == null) {
                return value;
            }
        }
        try {
            if (value.getClass() == String.class) {
                return converter.getAsObject((String) value);
            }
            return converter.getAsString(value);
        } catch (final Throwable cause) {
            throw new ConverterRuntimeException(destPropertyName, value, cause);
        }
    }

    /**
     * Finds the converter corresponding to the class.
     *
     * @param clazz
     *            The class
     * @return The converter
     */
    protected Converter findConverter(final Class<?> clazz) {
        for (final Converter c : converters) {
            if (c.isTarget(clazz)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Finds the default converter corresponding to the class.
     *
     * @param clazz
     *            The class
     * @return The converter
     */
    protected Converter findDefaultConverter(final Class<?> clazz) {
        if (clazz == java.sql.Date.class) {
            return DEFAULT_DATE_CONVERTER;
        }
        if (clazz == Time.class) {
            return DEFAULT_TIME_CONVERTER;
        }
        if (java.util.Date.class.isAssignableFrom(clazz)) {
            return DEFAULT_TIMESTAMP_CONVERTER;
        }
        return null;
    }

}
