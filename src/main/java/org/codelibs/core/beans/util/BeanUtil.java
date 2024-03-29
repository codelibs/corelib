/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
 * JavaBeansとJavaBeans、あるいはJavaBeansと{@link Map}の間でプロパティをコピーするためのユーティリティです。
 * <p>
 * コピー元とコピー先のJavaBeansを指定することで、プロパティがコピーされます。
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.beans.util.BeanUtil.*;
 *
 * copyBeanToBean(srcBean, destBean);
 * </pre>
 * <p>
 * JavaBeansから{@link Map}あるいは{@link Map}からJavaBeansへコピーすることも出来ます。
 * </p>
 *
 * <pre>
 * copyBeanToMap(srcBean, destMap);
 * copyMapToBean(srcMap, destBean);
 * </pre>
 * <p>
 * コピー先となるJavaBeansまたは{@link Map}のインスタンスを新たに生成してコピーすることも出来ます。
 * </p>
 *
 * <pre>
 * DestBean destBean = copyBeanToNewBean(srcBean, DestBean.class);
 * DestBean destBean = copyMapToNewBean(srcMap, DestBean.class);
 * Map&lt;String, Object&gt; destMap = copyBeanToNewMap(srcBean);
 * </pre>
 * <p>
 * コピーする際のオプションを指定することも出来ます。
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.beans.util.CopyOptionsUtil.*;
 *
 * copyBeanToBean(srcBean, destBean, excludeNull());
 * </pre>
 * <p>
 * メソッドチェーンでオプションを複数指定することもできます。
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

    /** デフォルトのオプション */
    protected static final CopyOptions DEFAULT_OPTIONS = new CopyOptions();

    /**
     * BeanからBeanにコピーを行います。
     *
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param dest コピー先のBean。{@literal null}であってはいけません
     */
    public static void copyBeanToBean(final Object src, final Object dest) {
        copyBeanToBean(src, dest, DEFAULT_OPTIONS);
    }

    public static void copyBeanToBean(final Object src, final Object dest, final Consumer<CopyOptions> option) {
        copyBeanToBean(src, dest, buildCopyOptions(option));
    }

    /**
     * BeanからBeanにコピーを行います。
     *
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param dest コピー先のBean。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
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
     * Beanから{@literal Map}にコピーを行います。
     *
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param dest コピー先の{@literal Map}。{@literal null}であってはいけません
     */
    public static void copyBeanToMap(final Object src, final Map<String, Object> dest) {
        copyBeanToMap(src, dest, DEFAULT_OPTIONS);
    }

    public static void copyBeanToMap(final Object src, final Map<String, Object> dest, final Consumer<CopyOptions> option) {
        copyBeanToMap(src, dest, buildCopyOptions(option));
    }

    /**
     * Beanから{@literal Map}にコピーを行います。
     *
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param dest コピー先の{@literal Map}。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
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
     * {@literal Map}からBeanにコピーを行います。
     *
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param dest コピー先のBean。{@literal null}であってはいけません
     */
    public static void copyMapToBean(final Map<String, ? extends Object> src, final Object dest) {
        copyMapToBean(src, dest, DEFAULT_OPTIONS);
    }

    public static void copyMapToBean(final Map<String, ? extends Object> src, final Object dest, final Consumer<CopyOptions> option) {
        copyMapToBean(src, dest, buildCopyOptions(option));
    }

    /**
     * {@literal Map}からBeanにコピーを行います。
     *
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param dest コピー先のBean。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
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
     * {@literal Map}から{@literal Map}にコピーを行います。
     *
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param dest コピー先の{@literal Map}。{@literal null}であってはいけません
     */
    public static void copyMapToMap(final Map<String, ? extends Object> src, final Map<String, Object> dest) {
        copyMapToMap(src, dest, DEFAULT_OPTIONS);
    }

    public static void copyMapToMap(final Map<String, ? extends Object> src, final Map<String, Object> dest,
            final Consumer<CopyOptions> option) {
        copyMapToMap(src, dest, buildCopyOptions(option));
    }

    /**
     * {@literal Map}から{@literal Map}にコピーを行います。
     *
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param dest コピー先の{@literal Map}。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
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
     * コピー元のBeanを新しいBeanのインスタンスにコピーして返します。
     *
     * @param <T> コピー先となるBeanの型
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param destClass コピー先となるBeanの型。{@literal null}であってはいけません
     * @return コピーされた新しいBean
     */
    public static <T> T copyBeanToNewBean(final Object src, final Class<T> destClass) {
        return copyBeanToNewBean(src, destClass, DEFAULT_OPTIONS);
    }

    public static <T> T copyBeanToNewBean(final Object src, final Class<T> destClass, final Consumer<CopyOptions> option) {
        return copyBeanToNewBean(src, destClass, buildCopyOptions(option));
    }

    /**
     * コピー元のBeanを新しいBeanのインスタンスにコピーして返します。
     *
     * @param <T> コピー先となるBeanの型
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param destClass コピー先となるBeanの型。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
     * @return コピーされた新しいBean
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
     * コピー元の{@literal Map}を新しいBeanのインスタンスにコピーして返します。
     *
     * @param <T> コピー先となるBeanの型
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param destClass コピー先となるBeanの型。{@literal null}であってはいけません
     * @return コピーされた新しい{@literal Map}
     */
    public static <T> T copyMapToNewBean(final Map<String, ? extends Object> src, final Class<T> destClass) {
        return copyMapToNewBean(src, destClass, DEFAULT_OPTIONS);
    }

    public static <T> T copyMapToNewBean(final Map<String, ? extends Object> src, final Class<T> destClass,
            final Consumer<CopyOptions> option) {
        return copyMapToNewBean(src, destClass, buildCopyOptions(option));
    }

    /**
     * コピー元の{@literal Map}を新しいBeanのインスタンスにコピーして返します。
     *
     * @param <T> コピー先となるBeanの型
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param destClass コピー先となるBeanの型。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
     * @return コピーされた新しい{@literal Map}
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
     * コピー元のBeanを新しい{@literal LinkedHashMap}のインスタンスにコピーして返します。
     *
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @return コピーされた新しいBean
     */
    public static Map<String, Object> copyBeanToNewMap(final Object src) {
        return copyBeanToNewMap(src, DEFAULT_OPTIONS);
    }

    public static Map<String, Object> copyBeanToNewMap(final Object src, final Consumer<CopyOptions> option) {
        return copyBeanToNewMap(src, buildCopyOptions(option));
    }

    /**
     * コピー元のBeanを新しい{@literal LinkedHashMap}のインスタンスにコピーして返します。
     *
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
     * @return コピーされた新しいBean
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
     * コピー元のBeanを新しい{@literal Map}のインスタンスにコピーして返します。
     *
     * @param <T> コピー先となる{@literal Map}の型
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param destClass コピー先となる{@literal Map}の型。{@literal null}であってはいけません
     * @return コピーされた新しい{@literal Map}
     */
    public static <T extends Map<String, Object>> T copyBeanToNewMap(final Object src, final Class<? extends T> destClass) {
        return copyBeanToNewMap(src, destClass, DEFAULT_OPTIONS);
    }

    public static <T extends Map<String, Object>> T copyBeanToNewMap(final Object src, final Class<? extends T> destClass,
            final Consumer<CopyOptions> option) {
        return copyBeanToNewMap(src, destClass, buildCopyOptions(option));
    }

    /**
     * コピー元のBeanを新しい{@literal Map}のインスタンスにコピーして返します。
     *
     * @param <T> コピー先となる{@literal Map}の型
     * @param src コピー元のBean。{@literal null}であってはいけません
     * @param destClass コピー先となる{@literal Map}の型。{@literal null}であってはいけません
     * @param options コピーのオプション
     * @return コピーされた新しい{@literal Map}
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
     * コピー元の{@literal Map}を新しい{@literal LinkedHashMap}のインスタンスにコピーして返します。
     *
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @return コピーされた新しい{@literal Map}
     */
    public static Map<String, Object> copyMapToNewMap(final Map<String, ? extends Object> src) {
        return copyMapToNewMap(src, DEFAULT_OPTIONS);
    }

    public static Map<String, Object> copyMapToNewMap(final Map<String, ? extends Object> src, final Consumer<CopyOptions> option) {
        return copyMapToNewMap(src, buildCopyOptions(option));
    }

    /**
     * コピー元の{@literal Map}を新しい{@literal LinkedHashMap}のインスタンスにコピーして返します。
     *
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
     * @return コピーされた新しい{@literal Map}
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
     * コピー元の{@literal Map}を新しい{@literal Map}のインスタンスにコピーして返します。
     *
     * @param <T> コピー先となる{@literal Map}の型
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param destClass コピー先となる{@literal Map}の型。{@literal null}であってはいけません
     * @return コピーされた新しい{@literal Map}
     */
    public static <T extends Map<String, Object>> T copyMapToNewMap(final Map<String, ? extends Object> src,
            final Class<? extends T> destClass) {
        return copyMapToNewMap(src, destClass, DEFAULT_OPTIONS);
    }

    public static <T extends Map<String, Object>> T copyMapToNewMap(final Map<String, ? extends Object> src,
            final Class<? extends T> destClass, final Consumer<CopyOptions> option) {
        return copyMapToNewMap(src, destClass, buildCopyOptions(option));
    }

    /**
     * コピー元の{@literal Map}を新しい{@literal Map}のインスタンスにコピーして返します。
     *
     * @param <T> コピー先となる{@literal Map}の型
     * @param src コピー元の{@literal Map}。{@literal null}であってはいけません
     * @param destClass コピー先となる{@literal Map}の型。{@literal null}であってはいけません
     * @param options コピーのオプション。{@literal null}であってはいけません
     * @return コピーされた新しい{@literal Map}
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

    protected static CopyOptions buildCopyOptions(final Consumer<CopyOptions> option) {
        final CopyOptions copyOptions = new CopyOptions();
        option.accept(copyOptions);
        return copyOptions;
    }
}
