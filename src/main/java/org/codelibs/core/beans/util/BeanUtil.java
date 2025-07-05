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

import static org.codelibs.core.collection.CollectionsUtil.newLinkedHashMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.PropertyDesc;
import org.codelibs.core.beans.factory.BeanDescFactory;
import org.codelibs.core.lang.ClassUtil;

/**
 * Utility for copying properties between JavaBeans or between JavaBeans and {@link Map}.
 * <p>
 * By specifying the source and destination JavaBeans, properties can be copied.
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.beans.util.BeanUtil.*;
 *
 * copyBeanToBean(srcBean, destBean);
 * </pre>
 * <p>
 * It is also possible to copy from a JavaBean to a {@link Map} or from a {@link Map} to a JavaBean.
 * </p>
 *
 * <pre>
 * copyBeanToMap(srcBean, destMap);
 * copyMapToBean(srcMap, destBean);
 * </pre>
 * <p>
 * You can also create a new instance of the destination JavaBean or {@link Map} and copy to it.
 * </p>
 *
 * <pre>
 * DestBean destBean = copyBeanToNewBean(srcBean, DestBean.class);
 * DestBean destBean = copyMapToNewBean(srcMap, DestBean.class);
 * Map&lt;String, Object&gt; destMap = copyBeanToNewMap(srcBean);
 * </pre>
 * <p>
 * It is also possible to specify options when copying.
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.beans.util.CopyOptionsUtil.*;
 *
 * copyBeanToBean(srcBean, destBean, excludeNull());
 * </pre>
 * <p>
 * Multiple options can also be specified using method chaining.
 * </p>
 *
 * <pre>
 * copyBeanToBean(srcBean, destBean, excludeNull().dateConverter("date", "MM/dd"));
 * </pre>
 *
 * @author Kimura Satoshi
 * @author higa
 * @author shinsuke
 * @see CopyOptionsUtil
 * @see CopyOptions
 */
public abstract class BeanUtil {

    /**
     * Do not instantiate.
     */
    private BeanUtil() {
    }

    /** Default options */
    protected static final CopyOptions DEFAULT_OPTIONS = new CopyOptions();

    /**
     * Copies properties from one Bean to another Bean.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param dest The destination Bean. Must not be {@literal null}.
     */
    public static void copyBeanToBean(final Object src, final Object dest) {
        copyBeanToBean(src, dest, DEFAULT_OPTIONS);
    }

    /**
     * Copies properties from one Bean to another Bean.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param dest The destination Bean. Must not be {@literal null}.
     * @param option The consumer for copy options.
     */
    public static void copyBeanToBean(final Object src, final Object dest, final Consumer<CopyOptions> option) {
        copyBeanToBean(src, dest, buildCopyOptions(option));
    }

    /**
     * Copies properties from one Bean to another Bean.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param dest The destination Bean. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @see CopyOptionsUtil
     */
    protected static void copyBeanToBean(final Object src, final Object dest, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("dest", dest);
        assertArgumentNotNull("option", options);

        final BeanDesc srcBeanDesc = BeanDescFactory.getBeanDesc(src.getClass());
        final BeanDesc destBeanDesc = BeanDescFactory.getBeanDesc(dest.getClass());
        for (final PropertyDesc srcPropertyDesc : srcBeanDesc.getPropertyDescs()) {
            final String srcPropertyName = srcPropertyDesc.getPropertyName();
            if (!srcPropertyDesc.isReadable() || !options.isTargetProperty(srcPropertyName)) {
                continue;
            }
            final String destPropertyName = options.trimPrefix(srcPropertyName);
            if (!destBeanDesc.hasPropertyDesc(destPropertyName)) {
                continue;
            }
            final PropertyDesc destPropertyDesc = destBeanDesc.getPropertyDesc(destPropertyName);
            if (!destPropertyDesc.isWritable()) {
                continue;
            }
            final Object value = srcPropertyDesc.getValue(src);
            if (!options.isTargetValue(value)) {
                continue;
            }
            final Object convertedValue = options.convertValue(value, destPropertyName, destPropertyDesc.getPropertyType());
            destPropertyDesc.setValue(dest, convertedValue);
        }
    }

    /**
     * Copies from a Bean to a {@literal Map}.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param dest The destination {@literal Map}. Must not be {@literal null}.
     */
    public static void copyBeanToMap(final Object src, final Map<String, Object> dest) {
        copyBeanToMap(src, dest, DEFAULT_OPTIONS);
    }

    /**
     * Copies from a Bean to a {@literal Map}.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param dest The destination {@literal Map}. Must not be {@literal null}.
     * @param option The consumer for copy options.
     */
    public static void copyBeanToMap(final Object src, final Map<String, Object> dest, final Consumer<CopyOptions> option) {
        copyBeanToMap(src, dest, buildCopyOptions(option));
    }

    /**
     * Copies from a Bean to a {@literal Map}.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param dest The destination {@literal Map}. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @see CopyOptionsUtil
     */
    protected static void copyBeanToMap(final Object src, final Map<String, Object> dest, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("dest", dest);
        assertArgumentNotNull("options", options);

        final BeanDesc srcBeanDesc = BeanDescFactory.getBeanDesc(src.getClass());
        for (final PropertyDesc srcPropertyDesc : srcBeanDesc.getPropertyDescs()) {
            final String srcPropertyName = srcPropertyDesc.getPropertyName();
            if (!srcPropertyDesc.isReadable() || !options.isTargetProperty(srcPropertyName)) {
                continue;
            }
            final Object value = srcPropertyDesc.getValue(src);
            if (!options.isTargetValue(value)) {
                continue;
            }
            final String destPropertyName = options.toMapDestPropertyName(srcPropertyName);
            final Object convertedValue = options.convertValue(value, destPropertyName, null);
            dest.put(destPropertyName, convertedValue);
        }
    }

    /**
     * Copies from a {@literal Map} to a Bean.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param dest The destination Bean. Must not be {@literal null}.
     */
    public static void copyMapToBean(final Map<String, ? extends Object> src, final Object dest) {
        copyMapToBean(src, dest, DEFAULT_OPTIONS);
    }

    /**
     * Copies from a {@literal Map} to a Bean.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param dest The destination Bean. Must not be {@literal null}.
     * @param option The consumer for copy options.
     */
    public static void copyMapToBean(final Map<String, ? extends Object> src, final Object dest, final Consumer<CopyOptions> option) {
        copyMapToBean(src, dest, buildCopyOptions(option));
    }

    /**
     * Copies from a {@literal Map} to a Bean.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param dest The destination Bean. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @see CopyOptionsUtil
     */
    protected static void copyMapToBean(final Map<String, ? extends Object> src, final Object dest, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("dest", dest);
        assertArgumentNotNull("options", options);

        final BeanDesc destBeanDesc = BeanDescFactory.getBeanDesc(dest.getClass());
        for (final Entry<String, ? extends Object> entry : src.entrySet()) {
            final String srcPropertyName = entry.getKey();
            if (!options.isTargetProperty(srcPropertyName)) {
                continue;
            }
            final String destPropertyName = options.toBeanDestPropertyName(srcPropertyName);
            if (!destBeanDesc.hasPropertyDesc(destPropertyName)) {
                continue;
            }
            final PropertyDesc destPropertyDesc = destBeanDesc.getPropertyDesc(destPropertyName);
            if (!destPropertyDesc.isWritable()) {
                continue;
            }
            final Object value = entry.getValue();
            if (!options.isTargetValue(value)) {
                continue;
            }
            final Object convertedValue = options.convertValue(value, destPropertyName, destPropertyDesc.getPropertyType());
            destPropertyDesc.setValue(dest, convertedValue);
        }
    }

    /**
     * Copies from a {@literal Map} to another {@literal Map}.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param dest The destination {@literal Map}. Must not be {@literal null}.
     */
    public static void copyMapToMap(final Map<String, ? extends Object> src, final Map<String, Object> dest) {
        copyMapToMap(src, dest, DEFAULT_OPTIONS);
    }

    /**
     * Copies from a {@literal Map} to another {@literal Map}.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param dest The destination {@literal Map}. Must not be {@literal null}.
     * @param option The consumer for copy options.
     */
    public static void copyMapToMap(final Map<String, ? extends Object> src, final Map<String, Object> dest,
            final Consumer<CopyOptions> option) {
        copyMapToMap(src, dest, buildCopyOptions(option));
    }

    /**
     * Copies from a {@literal Map} to another {@literal Map}.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param dest The destination {@literal Map}. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @see CopyOptionsUtil
     */
    protected static void copyMapToMap(final Map<String, ? extends Object> src, final Map<String, Object> dest, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("dest", dest);
        assertArgumentNotNull("options", options);

        for (final Entry<String, ? extends Object> entry : src.entrySet()) {
            final String srcPropertyName = entry.getKey();
            if (!options.isTargetProperty(srcPropertyName)) {
                continue;
            }
            final Object value = src.get(srcPropertyName);
            if (!options.isTargetValue(value)) {
                continue;
            }
            final String destPropertyName = options.trimPrefix(srcPropertyName);
            final Object convertedValue = options.convertValue(value, destPropertyName, null);
            dest.put(destPropertyName, convertedValue);
        }
    }

    /**
     * Copies the source Bean to a new instance of the destination Bean and returns it.
     *
     * @param <T> The type of the destination Bean.
     * @param src The source Bean. Must not be {@literal null}.
     * @param destClass The type of the destination Bean. Must not be {@literal null}.
     * @return The newly copied Bean.
     */
    public static <T> T copyBeanToNewBean(final Object src, final Class<T> destClass) {
        return copyBeanToNewBean(src, destClass, DEFAULT_OPTIONS);
    }

    /**
     * Copies the source Bean to a new instance of the destination Bean and returns it.
     *
     * @param <T> The type of the destination Bean.
     * @param src The source Bean. Must not be {@literal null}.
     * @param destClass The type of the destination Bean. Must not be {@literal null}.
     * @param option The consumer for copy options.
     * @return The newly copied Bean.
     */
    public static <T> T copyBeanToNewBean(final Object src, final Class<T> destClass, final Consumer<CopyOptions> option) {
        return copyBeanToNewBean(src, destClass, buildCopyOptions(option));
    }

    /**
     * Copies the source Bean to a new instance of the destination Bean and returns it.
     *
     * @param <T> The type of the destination Bean.
     * @param src The source Bean. Must not be {@literal null}.
     * @param destClass The type of the destination Bean. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @return The newly copied Bean.
     * @see CopyOptionsUtil
     */
    protected static <T> T copyBeanToNewBean(final Object src, final Class<T> destClass, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("destClass", destClass);
        assertArgumentNotNull("options", options);

        final T dest = ClassUtil.newInstance(destClass);
        copyBeanToBean(src, dest, options);
        return dest;
    }

    /**
     * Copies the source {@literal Map} to a new instance of the destination Bean and returns it.
     *
     * @param <T> The type of the destination Bean.
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param destClass The type of the destination Bean. Must not be {@literal null}.
     * @return The newly copied {@literal Map}.
     */
    public static <T> T copyMapToNewBean(final Map<String, ? extends Object> src, final Class<T> destClass) {
        return copyMapToNewBean(src, destClass, DEFAULT_OPTIONS);
    }

    /**
     * Copies the source {@literal Map} to a new instance of the destination Bean and returns it.
     *
     * @param <T> The type of the destination Bean.
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param destClass The type of the destination Bean. Must not be {@literal null}.
     * @param option The consumer for copy options.
     * @return The newly copied {@literal Map}.
     */
    public static <T> T copyMapToNewBean(final Map<String, ? extends Object> src, final Class<T> destClass,
            final Consumer<CopyOptions> option) {
        return copyMapToNewBean(src, destClass, buildCopyOptions(option));
    }

    /**
     * Copies the source {@literal Map} to a new instance of the destination Bean and returns it.
     *
     * @param <T> The type of the destination Bean.
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param destClass The type of the destination Bean. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @return The newly copied {@literal Map}.
     * @see CopyOptionsUtil
     */
    protected static <T> T copyMapToNewBean(final Map<String, ? extends Object> src, final Class<T> destClass, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("destClass", destClass);
        assertArgumentNotNull("options", options);

        final T dest = ClassUtil.newInstance(destClass);
        copyMapToBean(src, dest, options);
        return dest;
    }

    /**
     * Copies the source Bean to a new instance of {@literal LinkedHashMap} and returns it.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @return The newly copied Bean.
     */
    public static Map<String, Object> copyBeanToNewMap(final Object src) {
        return copyBeanToNewMap(src, DEFAULT_OPTIONS);
    }

    /**
     * Copies the source Bean to a new instance of {@literal LinkedHashMap} and returns it.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param option The consumer for copy options.
     * @return The newly copied Bean.
     */
    public static Map<String, Object> copyBeanToNewMap(final Object src, final Consumer<CopyOptions> option) {
        return copyBeanToNewMap(src, buildCopyOptions(option));
    }

    /**
     * Copies the source Bean to a new instance of {@literal LinkedHashMap} and returns it.
     *
     * @param src The source Bean. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @return The newly copied Bean.
     * @see CopyOptionsUtil
     */
    protected static Map<String, Object> copyBeanToNewMap(final Object src, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("options", options);

        final Map<String, Object> dest = newLinkedHashMap();
        copyBeanToMap(src, dest, options);
        return dest;
    }

    /**
     * Copies the source Bean to a new instance of a {@literal Map} and returns it.
     *
     * @param <T> The type of the destination {@literal Map}.
     * @param src The source Bean. Must not be {@literal null}.
     * @param destClass The type of the destination {@literal Map}. Must not be {@literal null}.
     * @return The newly copied {@literal Map}.
     */
    public static <T extends Map<String, Object>> T copyBeanToNewMap(final Object src, final Class<? extends T> destClass) {
        return copyBeanToNewMap(src, destClass, DEFAULT_OPTIONS);
    }

    /**
     * Copies the source Bean to a new instance of a {@literal Map} and returns it.
     *
     * @param <T> The type of the destination {@literal Map}.
     * @param src The source Bean. Must not be {@literal null}.
     * @param destClass The type of the destination {@literal Map}. Must not be {@literal null}.
     * @param option The consumer for copy options.
     * @return The newly copied {@literal Map}.
     */
    public static <T extends Map<String, Object>> T copyBeanToNewMap(final Object src, final Class<? extends T> destClass,
            final Consumer<CopyOptions> option) {
        return copyBeanToNewMap(src, destClass, buildCopyOptions(option));
    }

    /**
     * Copies the source Bean to a new instance of a {@literal Map} and returns it.
     *
     * @param <T> The type of the destination {@literal Map}.
     * @param src The source Bean. Must not be {@literal null}.
     * @param destClass The type of the destination {@literal Map}. Must not be {@literal null}.
     * @param options The copy options.
     * @return The newly copied {@literal Map}.
     * @see CopyOptionsUtil
     */
    protected static <T extends Map<String, Object>> T copyBeanToNewMap(final Object src, final Class<? extends T> destClass,
            final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("destClass", destClass);
        assertArgumentNotNull("options", options);

        final T dest = ClassUtil.newInstance(destClass);
        copyBeanToMap(src, dest, options);
        return dest;
    }

    /**
     * Copies the source {@literal Map} to a new instance of {@literal LinkedHashMap} and returns it.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @return The newly copied {@literal Map}.
     */
    public static Map<String, Object> copyMapToNewMap(final Map<String, ? extends Object> src) {
        return copyMapToNewMap(src, DEFAULT_OPTIONS);
    }

    /**
     * Copies the source {@literal Map} to a new instance of {@literal LinkedHashMap} and returns it.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param option The consumer for copy options.
     * @return The newly copied {@literal Map}.
     */
    public static Map<String, Object> copyMapToNewMap(final Map<String, ? extends Object> src, final Consumer<CopyOptions> option) {
        return copyMapToNewMap(src, buildCopyOptions(option));
    }

    /**
     * Copies the source {@literal Map} to a new instance of {@literal LinkedHashMap} and returns it.
     *
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @return The newly copied {@literal Map}.
     * @see CopyOptionsUtil
     */
    protected static Map<String, Object> copyMapToNewMap(final Map<String, ? extends Object> src, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("options", options);

        final Map<String, Object> dest = newLinkedHashMap();
        copyMapToMap(src, dest, options);
        return dest;
    }

    /**
     * Copies the source {@literal Map} to a new instance of a {@literal Map} and returns it.
     *
     * @param <T> The type of the destination {@literal Map}.
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param destClass The type of the destination {@literal Map}. Must not be {@literal null}.
     * @return The newly copied {@literal Map}.
     */
    public static <T extends Map<String, Object>> T copyMapToNewMap(final Map<String, ? extends Object> src,
            final Class<? extends T> destClass) {
        return copyMapToNewMap(src, destClass, DEFAULT_OPTIONS);
    }

    /**
     * Copies the source {@literal Map} to a new instance of a {@literal Map} and returns it.
     *
     * @param <T> The type of the destination {@literal Map}.
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param destClass The type of the destination {@literal Map}. Must not be {@literal null}.
     * @param option The consumer for copy options.
     * @return The newly copied {@literal Map}.
     */
    public static <T extends Map<String, Object>> T copyMapToNewMap(final Map<String, ? extends Object> src,
            final Class<? extends T> destClass, final Consumer<CopyOptions> option) {
        return copyMapToNewMap(src, destClass, buildCopyOptions(option));
    }

    /**
     * Copies the source {@literal Map} to a new instance of a {@literal Map} and returns it.
     *
     * @param <T> The type of the destination {@literal Map}.
     * @param src The source {@literal Map}. Must not be {@literal null}.
     * @param destClass The type of the destination {@literal Map}. Must not be {@literal null}.
     * @param options The copy options. Must not be {@literal null}.
     * @return The newly copied {@literal Map}.
     * @see CopyOptionsUtil
     */
    protected static <T extends Map<String, Object>> T copyMapToNewMap(final Map<String, ? extends Object> src,
            final Class<? extends T> destClass, final CopyOptions options) {
        assertArgumentNotNull("src", src);
        assertArgumentNotNull("destClass", destClass);
        assertArgumentNotNull("options", options);

        final T dest = ClassUtil.newInstance(destClass);
        copyMapToMap(src, dest, options);
        return dest;
    }

    /**
     * Builds {@link CopyOptions} from a {@link Consumer}.
     *
     * @param option
     *            the option
     * @return the copy options
     */
    protected static CopyOptions buildCopyOptions(final Consumer<CopyOptions> option) {
        final CopyOptions copyOptions = new CopyOptions();
        option.accept(copyOptions);
        return copyOptions;
    }
}
