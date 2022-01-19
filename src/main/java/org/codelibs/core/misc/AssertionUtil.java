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
package org.codelibs.core.misc;

import static org.codelibs.core.collection.ArrayUtil.asArray;

import java.util.Collection;
import java.util.Map;

import org.codelibs.core.collection.ArrayUtil;
import org.codelibs.core.exception.ClIllegalArgumentException;
import org.codelibs.core.exception.ClIllegalStateException;
import org.codelibs.core.exception.ClIndexOutOfBoundsException;
import org.codelibs.core.exception.EmptyArgumentException;
import org.codelibs.core.exception.NullArgumentException;
import org.codelibs.core.lang.StringUtil;

/**
 * 表明についてのユーティリティクラスです。
 *
 * @author shot
 */
public abstract class AssertionUtil {

    /**
     * 引数が<code>null</code>でないことを表明します。
     *
     * @param argName
     *            {@code null} であってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws NullArgumentException
     *             引数が<code>null</code>の場合。
     */
    public static void assertArgumentNotNull(final String argName, final Object argValue) {
        if (argValue == null) {
            throw new NullArgumentException(argName);
        }
    }

    /**
     * 引数が<code>null</code>でも空文字列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空文字列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空文字列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final String argValue) {
        if (StringUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0010", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空文字列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空文字列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空文字列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final CharSequence argValue) {
        if (argValue == null || argValue.length() == 0) {
            throw new EmptyArgumentException(argName, "ECL0010", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final Object[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final boolean[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final byte[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final short[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final int[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final long[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final float[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final double[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の配列でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の配列でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の配列の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final char[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の{@link Collection}でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の{@link Collection}でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の{@link Collection}の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final Collection<?> argValue) {
        if (argValue == null || argValue.isEmpty()) {
            throw new EmptyArgumentException(argName, "ECL0012", asArray(argName));
        }
    }

    /**
     * 引数が<code>null</code>でも空の{@link Map}でもないことを表明します。
     *
     * @param argName
     *            {@code null} でも空の{@link Map}でもあってはならない引数の名前
     * @param argValue
     *            引数の値
     * @throws EmptyArgumentException
     *             引数が<code>null</code>または空の{@link Map}の場合。
     */
    public static void assertArgumentNotEmpty(final String argName, final Map<?, ?> argValue) {
        if (argValue == null || argValue.isEmpty()) {
            throw new EmptyArgumentException(argName, "ECL0013", asArray(argName));
        }
    }

    /**
     * インデックスが不正でないことを表明します。
     *
     * @param argName
     *            {@code null} であってはならない引数の名前
     * @param argValue
     *            インデックスの値
     * @param arraySize
     *            インデックスが参照する配列の長さ
     * @throws ClIllegalArgumentException
     *             引数が配列のインデックスとして不正な場合場合。
     */
    public static void assertArgumentArrayIndex(final String argName, final int argValue, final int arraySize) {
        if (argValue < 0) {
            throw new ClIllegalArgumentException(argName, "ECL0014", asArray(argName));
        }
        if (argValue >= arraySize) {
            throw new ClIllegalArgumentException(argName, "ECL0015", asArray(argName, arraySize));
        }
    }

    /**
     * 引数が不正でないことを表明します。
     *
     * @param argName
     *            不正であってはならない引数の名前
     * @param expression
     *            事前条件
     * @param description
     *            不正な引数であることの説明
     * @throws ClIllegalArgumentException
     *             {@code expression}がfalseの場合。
     */
    public static void assertArgument(final String argName, final boolean expression, final String description) {
        if (!expression) {
            throw new ClIllegalArgumentException(argName, "ECL0009", asArray(argName, description));
        }
    }

    /**
     * 状態が不正でないことを表明します。
     *
     * @param expression
     *            事前条件
     * @param description
     *            不正な状態であることの説明
     * @throws ClIllegalStateException
     *             {@code expression}がfalseの場合。
     */
    public static void assertState(final boolean expression, final String description) {
        if (!expression) {
            throw new ClIllegalStateException(description);
        }
    }

    /**
     * indexが不正でないことを表明します。
     *
     * @param expression
     *            事前条件
     * @param description
     *            不正なindexであることの説明
     * @throws ClIndexOutOfBoundsException
     *             {@code expression}がfalseの場合。
     */
    public static void assertIndex(final boolean expression, final String description) {
        if (!expression) {
            throw new ClIndexOutOfBoundsException(description);
        }
    }

}
