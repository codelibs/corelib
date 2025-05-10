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

import java.util.Map;

import org.codelibs.core.beans.Converter;
import org.codelibs.core.beans.converter.DateConverter;
import org.codelibs.core.beans.converter.NumberConverter;
import org.codelibs.core.beans.converter.SqlDateConverter;
import org.codelibs.core.beans.converter.TimeConverter;
import org.codelibs.core.beans.converter.TimestampConverter;

/**
 * A utility for {@literal static import} to facilitate the instantiation of {@link CopyOptions}.
 *
 * <pre>
 * import static org.codelibs.core.beans.util.CopyOptionsUtil.*;
 *
 * copyBeanToBean(srcBean, destBean, excludeNull());
 * </pre>
 * <p>
 * The return value of {@literal CopyOptionsUtil} is {@link CopyOptions},
 * so you can specify multiple options using method chaining.
 * </p>
 *
 * <pre>
 * copyBeanToBean(srcBean, destBean, excludeNull().dateConverter("date", "MM/dd"));
 * </pre>
 *
 * @author koichik
 */
public abstract class CopyOptionsUtil {

    /**
     * Returns a {@link CopyOptions} with the specified property names to include in the operation.
     *
     * @param propertyNames
     *            An array of property names. Must not be {@literal null} or an empty array.
     * @return A {@link CopyOptions} with the specified property names to include in the operation.
     * @see CopyOptions#include(CharSequence...)
     */
    public static CopyOptions include(final CharSequence... propertyNames) {
        return new CopyOptions().include(propertyNames);
    }

    /**
     * Returns a {@link CopyOptions} with the specified property names to exclude from the operation.
     *
     * @param propertyNames
     *            An array of property names. Must not be {@literal null} or an empty array.
     * @return A {@link CopyOptions} with the specified property names to exclude from the operation.
     * @see CopyOptions#exclude(CharSequence...)
     */
    public static CopyOptions exclude(final CharSequence... propertyNames) {
        return new CopyOptions().exclude(propertyNames);
    }

    /**
     * Returns a {@link CopyOptions} that excludes properties with {@literal null} values from the operation.
     *
     * @return A {@link CopyOptions} that excludes properties with {@literal null} values from the operation.
     * @see CopyOptions#excludeNull()
     */
    public static CopyOptions excludeNull() {
        return new CopyOptions().excludeNull();
    }

    /**
     * Returns a {@link CopyOptions} that excludes properties with whitespace values from the operation.
     *
     * @return A {@link CopyOptions} that excludes properties with whitespace values from the operation.
     * @see CopyOptions#excludeWhitespace()
     */
    public static CopyOptions excludeWhitespace() {
        return new CopyOptions().excludeWhitespace();
    }

    /**
     * Returns a {@link CopyOptions} with the specified prefix.
     * <p>
     * When a prefix is specified, only the properties from the source whose names start with the prefix will be copied.
     * Additionally, the prefix will be removed from the source property names to determine the target property names.
     * </p>
     *
     * @param prefix
     *            The prefix. Must not be {@literal null} or an empty string.
     * @return A {@link CopyOptions} with the specified prefix.
     * @see CopyOptions#prefix(CharSequence)
     */
    public static CopyOptions prefix(final CharSequence prefix) {
        return new CopyOptions().prefix(prefix);
    }

    /**
     * Returns a {@link CopyOptions} with the specified delimiter for JavaBeans.
     * <p>
     * When copying between JavaBeans and {@link Map}, you can change the delimiter for property names.
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
     *            The delimiter for JavaBeans.
     * @return A {@link CopyOptions} with the specified delimiter for JavaBeans.
     * @see CopyOptions#beanDelimiter(char)
     */
    public static CopyOptions beanDelimiter(final char beanDelimiter) {
        return new CopyOptions().beanDelimiter(beanDelimiter);
    }

    /**
     * Returns a {@link CopyOptions} with the specified delimiter for {@literal Map}.
     * <p>
     * When copying between JavaBeans and {@link Map}, you can change the delimiter for property names.
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
     *            The delimiter for {@literal Map}.
     * @return A {@link CopyOptions} with the specified delimiter for {@literal Map}.
     * @see CopyOptions#mapDelimiter(char)
     */
    public static CopyOptions mapDelimiter(final char mapDelimiter) {
        return new CopyOptions().mapDelimiter(mapDelimiter);
    }

    /**
     * Returns a {@link CopyOptions} with the specified converter applied.
     *
     * @param converter
     *            The converter. Must not be {@literal null}.
     * @param propertyNames
     *            The property names to which this converter will be applied. Each element must not be {@literal null} or an empty string.
     * @return A {@link CopyOptions} with the specified converter applied.
     * @see CopyOptions#converter(Converter, CharSequence...)
     */
    public static CopyOptions converter(final Converter converter, final CharSequence... propertyNames) {
        return new CopyOptions().converter(converter, propertyNames);
    }

    /**
     * Returns a {@link CopyOptions} with a date converter applied.
     *
     * @param pattern
     *            The date pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            An array of property names. Each element must not be {@literal null} or an empty string.
     * @return A {@link CopyOptions} with a date converter applied.
     * @see CopyOptions#dateConverter(String, CharSequence...)
     * @see DateConverter
     */
    public static CopyOptions dateConverter(final String pattern, final CharSequence... propertyNames) {
        return new CopyOptions().dateConverter(pattern, propertyNames);
    }

    /**
     * Returns a {@link CopyOptions} with a SQL date converter applied.
     *
     * @param pattern
     *            The date pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            An array of property names. Each element must not be {@literal null} or an empty string.
     * @return A {@link CopyOptions} with a SQL date converter applied.
     * @see CopyOptions#sqlDateConverter(String, CharSequence...)
     * @see SqlDateConverter
     */
    public static CopyOptions sqlDateConverter(final String pattern, final CharSequence... propertyNames) {
        return new CopyOptions().sqlDateConverter(pattern, propertyNames);
    }

    /**
     * Returns a {@link CopyOptions} with a time converter applied.
     *
     * @param pattern
     *            The time pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            An array of property names. Each element must not be {@literal null} or an empty string.
     * @return A {@link CopyOptions} with a time converter applied.
     * @see CopyOptions#timeConverter(String, CharSequence...)
     * @see TimeConverter
     */
    public static CopyOptions timeConverter(final String pattern, final CharSequence... propertyNames) {
        return new CopyOptions().timeConverter(pattern, propertyNames);
    }

    /**
     * Returns a {@link CopyOptions} with a timestamp converter applied.
     *
     * @param pattern
     *            The timestamp pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            An array of property names. Each element must not be {@literal null} or an empty string.
     * @return A {@link CopyOptions} with a timestamp converter applied.
     * @see CopyOptions#timestampConverter(String, CharSequence...)
     * @see TimestampConverter
     */
    public static CopyOptions timestampConverter(final String pattern, final CharSequence... propertyNames) {
        return new CopyOptions().timestampConverter(pattern, propertyNames);
    }

    /**
     * Returns a {@link CopyOptions} with a number converter applied.
     *
     * @param pattern
     *            The number pattern. Must not be {@literal null} or an empty string.
     * @param propertyNames
     *            An array of property names. Each element must not be {@literal null} or an empty string.
     * @return A {@link CopyOptions} with a number converter applied.
     * @see CopyOptions#numberConverter(String, CharSequence...)
     * @see NumberConverter
     */
    public static CopyOptions numberConverter(final String pattern, final CharSequence... propertyNames) {
        return new CopyOptions().numberConverter(pattern, propertyNames);
    }

}
