/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
package org.codelibs.core.lang;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * {@link Modifier}用のユーティリティクラスです。
 *
 * @author shot
 */
public abstract class ModifierUtil {

    /**
     * <code>public</code>かどうか返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return パブリックかどうか
     */
    public static boolean isPublic(final Method method) {
        assertArgumentNotNull("method", method);

        return isPublic(method.getModifiers());
    }

    /**
     * <code>public</code>かどうか返します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return パブリックかどうか
     */
    public static boolean isPublic(final Field field) {
        assertArgumentNotNull("field", field);

        return isPublic(field.getModifiers());
    }

    /**
     * <code>public</code>,<code>static</code>,<code>final</code>かどうか返します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return <code>public</code>,<code>static</code>,<code>final</code>かどうか
     */
    public static boolean isPublicStaticFinalField(final Field field) {
        assertArgumentNotNull("field", field);

        return isPublicStaticFinal(field.getModifiers());
    }

    /**
     * <code>public</code>,<code>static</code>,<code>final</code>かどうか返します。
     *
     * @param modifier
     *            モディファイヤ
     * @return <code>public</code>,<code>static</code>,<code>final</code>かどうか
     */
    public static boolean isPublicStaticFinal(final int modifier) {
        return isPublic(modifier) && isStatic(modifier) && isFinal(modifier);
    }

    /**
     * <code>public</code>かどうか返します。
     *
     * @param modifier
     *            モディファイヤ
     * @return <code>public</code>かどうか
     */
    public static boolean isPublic(final int modifier) {
        return Modifier.isPublic(modifier);
    }

    /**
     * <code>abstract</code>かどうか返します。
     *
     * @param clazz
     *            クラス。{@literal null}であってはいけません
     * @return <code>abstract</code>なら{@literal true}
     */
    public static boolean isAbstract(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return isAbstract(clazz.getModifiers());
    }

    /**
     * <code>abstract</code>かどうか返します。
     *
     * @param modifier
     *            モディファイヤ
     * @return <code>abstract</code>なら{@literal true}
     */
    public static boolean isAbstract(final int modifier) {
        return Modifier.isAbstract(modifier);
    }

    /**
     * <code>static</code>かどうか返します。
     *
     * @param modifier
     *            モディファイヤ
     * @return <code>static</code>なら{@literal true}
     */
    public static boolean isStatic(final int modifier) {
        return Modifier.isStatic(modifier);
    }

    /**
     * <code>final</code>かどうか返します。
     *
     * @param modifier
     *            モディファイヤ
     * @return <code>final</code>なら{@literal true}
     */
    public static boolean isFinal(final int modifier) {
        return Modifier.isFinal(modifier);
    }

    /**
     * <code>final</code>かどうか返します。
     *
     * @param method
     *            メソッド
     * @return <code>final</code>なら{@literal true}
     */
    public static boolean isFinal(final Method method) {
        return isFinal(method.getModifiers());
    }

    /**
     * <code>transient</code>かどうか返します。
     *
     * @param field
     *            フィールド
     * @return <code>transient</code>なら{@literal true}
     * @see #isTransient(int)
     */
    public static boolean isTransient(final Field field) {
        return isTransient(field.getModifiers());
    }

    /**
     * <code>transient</code>かどうか返します。
     *
     * @param modifier
     *            モディファイヤ
     * @return <code>transient</code>なら{@literal true}
     */
    public static boolean isTransient(final int modifier) {
        return Modifier.isTransient(modifier);
    }

    /**
     * インスタンスフィールドかどうかを返します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return インスタンスフィールドなら{@literal true}
     */
    public static boolean isInstanceField(final Field field) {
        assertArgumentNotNull("field", field);

        final int m = field.getModifiers();
        return !isStatic(m) && !isFinal(m);
    }

}
