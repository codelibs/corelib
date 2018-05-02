/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
package org.codelibs.core.collection;

import static org.codelibs.core.collection.CollectionsUtil.newArrayList;
import static org.codelibs.core.misc.AssertionUtil.assertArgument;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.codelibs.core.message.MessageFormatter;

/**
 * 配列に対するユーティリティクラスです。
 *
 * @author higa
 */
public abstract class ArrayUtil {

    /**
     * {@literal boolean}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static boolean[] asBooleanArray(final boolean... elements) {
        return elements;
    }

    /**
     * {@literal char}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static char[] asCharArray(final char... elements) {
        return elements;
    }

    /**
     * {@literal byte}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static byte[] asByteArray(final byte... elements) {
        return elements;
    }

    /**
     * {@literal short}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static short[] asShortArray(final short... elements) {
        return elements;
    }

    /**
     * {@literal int}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static int[] asIntArray(final int... elements) {
        return elements;
    }

    /**
     * {@literal long}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static long[] asLongArray(final long... elements) {
        return elements;
    }

    /**
     * {@literal float}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static float[] asFloatArray(final float... elements) {
        return elements;
    }

    /**
     * {@literal double}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static double[] asDoubleArray(final double... elements) {
        return elements;
    }

    /**
     * {@literal Object}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Object[] asArray(final Object... elements) {
        return elements;
    }

    /**
     * {@literal String}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static String[] asStringArray(final String... elements) {
        return elements;
    }

    /**
     * {@literal Boolean}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Boolean[] asArray(final Boolean... elements) {
        return elements;
    }

    /**
     * {@literal Character}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Character[] asArray(final Character... elements) {
        return elements;
    }

    /**
     * {@literal Byte}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Byte[] asArray(final Byte... elements) {
        return elements;
    }

    /**
     * {@literal Short}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Short[] asArray(final Short... elements) {
        return elements;
    }

    /**
     * {@literal Integer}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Integer[] asArray(final Integer... elements) {
        return elements;
    }

    /**
     * {@literal Long}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Long[] asArray(final Long... elements) {
        return elements;
    }

    /**
     * {@literal Float}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Float[] asArray(final Float... elements) {
        return elements;
    }

    /**
     * {@literal Double}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static Double[] asArray(final Double... elements) {
        return elements;
    }

    /**
     * {@literal BigInteger}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static BigInteger[] asArray(final BigInteger... elements) {
        return elements;
    }

    /**
     * {@literal BigDecimal}の配列を返します。
     *
     * @param elements
     *            配列の要素
     * @return 配列
     */
    public static BigDecimal[] asArray(final BigDecimal... elements) {
        return elements;
    }

    /**
     * 配列の末尾にオブジェクトを追加した配列を返します。
     *
     * @param <T>
     *            配列の要素型
     * @param array
     *            配列。{@literal null}であってはいけません
     * @param obj
     *            オブジェクト
     * @return オブジェクトが追加された結果の配列
     */
    public static <T> T[] add(final T[] array, final T obj) {
        assertArgumentNotNull("array", array);

        final int length = array.length;
        @SuppressWarnings("unchecked")
        final T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length + 1);
        System.arraycopy(array, 0, newArray, 0, length);
        newArray[length] = obj;
        return newArray;
    }

    /**
     * {@literal boolean}配列の末尾に{@literal boolean}の値を追加した配列を返します。
     *
     * @param array
     *            配列。{@literal null}であってはいけません
     * @param value
     *            値
     * @return 値が追加された結果の配列
     */
    public static boolean[] add(final boolean[] array, final boolean value) {
        assertArgumentNotNull("array", array);
        final boolean[] newArray = (boolean[]) Array.newInstance(boolean.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * {@literal byte}配列の末尾に{@literal byte}の値を追加した配列を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 値が追加された結果の配列
     */
    public static byte[] add(final byte[] array, final byte value) {
        assertArgumentNotNull("array", array);

        final byte[] newArray = (byte[]) Array.newInstance(byte.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * {@literal short}配列の末尾に{@literal short}の値を追加した配列を返します。
     *
     * @param array
     *            配列。{@literal null}であってはいけません
     * @param value
     *            値
     * @return 値が追加された結果の配列
     */
    public static short[] add(final short[] array, final short value) {
        assertArgumentNotNull("array", array);

        final short[] newArray = (short[]) Array.newInstance(short.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * {@literal int}配列の末尾に{@literal int}の値を追加した配列を返します。
     *
     * @param array
     *            配列。{@literal null}であってはいけません
     * @param value
     *            値
     * @return 値が追加された結果の配列
     */
    public static int[] add(final int[] array, final int value) {
        assertArgumentNotNull("array", array);

        final int[] newArray = (int[]) Array.newInstance(int.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * {@literal long}配列の末尾に{@literal long}の値を追加した配列を返します。
     *
     * @param array
     *            配列。{@literal null}であってはいけません
     * @param value
     *            値
     * @return 値が追加された結果の配列
     */
    public static long[] add(final long[] array, final long value) {
        assertArgumentNotNull("array", array);

        final long[] newArray = (long[]) Array.newInstance(long.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * {@literal float}配列の末尾に{@literal float}の値を追加した配列を返します。
     *
     * @param array
     *            配列。{@literal null}であってはいけません
     * @param value
     *            値
     * @return 値が追加された結果の配列
     */
    public static float[] add(final float[] array, final float value) {
        assertArgumentNotNull("array", array);

        final float[] newArray = (float[]) Array.newInstance(float.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * {@literal double}配列の末尾に{@literal double}の値を追加した配列を返します。
     *
     * @param array
     *            配列。{@literal null}であってはいけません
     * @param value
     *            値
     * @return 値が追加された結果の配列
     */
    public static double[] add(final double[] array, final double value) {
        assertArgumentNotNull("array", array);

        final double[] newArray = (double[]) Array.newInstance(double.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param <T>
     *            配列の要素の型
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static <T> T[] addAll(final T[] a, final T[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        @SuppressWarnings("unchecked")
        final T[] array = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static boolean[] addAll(final boolean[] a, final boolean[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final boolean[] array = (boolean[]) Array.newInstance(boolean.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static byte[] addAll(final byte[] a, final byte[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final byte[] array = (byte[]) Array.newInstance(byte.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static short[] addAll(final short[] a, final short[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final short[] array = (short[]) Array.newInstance(short.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static int[] addAll(final int[] a, final int[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final int[] array = (int[]) Array.newInstance(int.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static long[] addAll(final long[] a, final long[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final long[] array = (long[]) Array.newInstance(long.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static float[] addAll(final float[] a, final float[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final float[] array = (float[]) Array.newInstance(float.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static double[] addAll(final double[] a, final double[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final double[] array = (double[]) Array.newInstance(double.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 二つの配列を連結した配列を返します。
     * <p>
     * どちらかあるいは両方の配列が{@literal null}の場合、他方の配列がそのまま返されます。 どちらかあるいは両方の配列の長さが
     * {@literal 0}の場合、他方の配列がそのまま返されます。 いずれの場合も返される配列は引数に渡された配列そのものでコピーはされません。
     * </p>
     *
     * @param a
     *            配列1
     * @param b
     *            配列2
     * @return 配列が連結された結果の配列
     */
    public static char[] addAll(final char[] a, final char[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        final char[] array = (char[]) Array.newInstance(char.class, a.length + b.length);
        System.arraycopy(a, 0, array, 0, a.length);
        System.arraycopy(b, 0, array, a.length, b.length);
        return array;
    }

    /**
     * 配列中からオジェクトが最初に見つかったインデックスを返します。
     *
     * @param <T>
     *            配列の要素の型
     * @param array
     *            配列
     * @param obj
     *            検索するオブジェクト
     * @return 配列中からオジェクトが最初に見つかったインデックス
     */
    public static <T> int indexOf(final T[] array, final T obj) {
        return indexOf(array, obj, 0);
    }

    /**
     * 配列中からオジェクトが最初に見つかったインデックスを返します。
     *
     * @param <T>
     *            配列の要素の型
     * @param array
     *            配列
     * @param obj
     *            検索するオブジェクト
     * @param fromIndex
     *            検索を始めるインデックス
     * @return 配列中からオジェクトが最初に見つかったインデックス。見つからなかった場合は{@literal -1}
     */
    public static <T> int indexOf(final T[] array, final T obj, final int fromIndex) {
        if (array != null) {
            for (int i = fromIndex; i < array.length; ++i) {
                final Object o = array[i];
                if (o != null) {
                    if (o.equals(obj)) {
                        return i;
                    }
                } else if (obj == null) {
                    return i;

                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final boolean[] array, final boolean value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final byte[] array, final byte value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final short[] array, final short value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final int[] array, final int value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final long[] array, final long value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final float[] array, final float value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final double[] array, final double value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列中から値が最初に見つかったインデックスを返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列中から値が最初に見つかったインデックス
     */
    public static int indexOf(final char[] array, final char value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 配列から最初に見つかったオブジェクトを削除した結果の配列を返します。
     * <p>
     * 配列にオブジェクトが含まれていない場合は引数で渡された配列をそのまま返します。
     * </p>
     *
     * @param <T>
     *            配列の要素の型
     * @param array
     *            配列
     * @param obj
     *            配列から削除する要素
     * @return 削除後の配列
     */
    public static <T> T[] remove(final T[] array, final T obj) {
        final int index = indexOf(array, obj);
        if (index < 0) {
            return array;
        }
        @SuppressWarnings("unchecked")
        final T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);
        if (index > 0) {
            System.arraycopy(array, 0, newArray, 0, index);
        }
        if (index < array.length - 1) {
            System.arraycopy(array, index + 1, newArray, index, newArray.length - index);
        }
        return newArray;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param <T>
     *            配列の要素の型
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static <T> boolean isEmpty(final T[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param <T>
     *            配列の要素の型
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static <T> boolean isNotEmpty(final T[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final boolean[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isNotEmpty(final boolean[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final byte[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final byte[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final short[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final short[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final int[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final int[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final long[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final long[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final float[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final float[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final double[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final double[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列が{@literal null}または長さが0の場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}または長さが0の場合は{@literal true}
     */
    public static boolean isEmpty(final char[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 配列が{@literal null}でも長さが0でもない場合は{@literal true}を返します。
     *
     * @param arrays
     *            配列
     * @return 配列が{@literal null}でも長さが0でもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final char[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * 配列にオブジェクトが含まれていれば{@literal true}を返します。
     *
     * @param <T>
     *            配列の要素の型
     * @param array
     *            配列
     * @param obj
     *            オブジェクト
     * @return 配列にオブジェクトが含まれていれば{@literal true}
     */
    public static <T> boolean contains(final T[] array, final T obj) {
        return indexOf(array, obj) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に値が含まれていれば{@literal true}
     */
    public static boolean contains(final boolean[] array, final boolean value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に値が含まれていれば{@literal true}
     */
    public static boolean contains(final byte[] array, final byte value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に値が含まれていれば{@literal true}
     */
    public static boolean contains(final short[] array, final short value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に値が含まれていれば{@literal true}
     */
    public static boolean contains(final int[] array, final int value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に値が含まれていれば{@literal true}
     */
    public static boolean contains(final long[] array, final long value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に値が含まれていれば{@literal true}
     */
    public static boolean contains(final float[] array, final float value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に値が含まれていれば{@literal true}
     */
    public static boolean contains(final double[] array, final double value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 配列に値が含まれていれば{@literal true}を返します。
     *
     * @param array
     *            配列
     * @param value
     *            値
     * @return 配列に文字が含まれていれば{@literal true}
     */
    public static boolean contains(final char[] array, final char value) {
        return indexOf(array, value) > -1;
    }

    /**
     * 順番は無視して2つの配列が等しければ{@literal true}を返します。
     *
     * @param <T>
     *            配列の要素の型
     * @param array1
     *            配列1
     * @param array2
     *            配列2
     * @return 順番は無視して2つの配列が等しければ{@literal true}
     */
    public static <T> boolean equalsIgnoreSequence(final T[] array1, final T[] array2) {
        if (array1 == null && array2 == null) {
            return true;
        } else if (array1 == null || array2 == null) {
            return false;
        }
        if (array1.length != array2.length) {
            return false;
        }
        final T[] copyOfArray2 = Arrays.copyOf(array2, array2.length);
        for (int i = 0; i < array1.length; i++) {
            final T o1 = array1[i];
            final int j = indexOf(copyOfArray2, o1, i);
            if (j == -1) {
                return false;
            }
            if (i != j) {
                final T o2 = copyOfArray2[i];
                copyOfArray2[i] = array2[j];
                copyOfArray2[j] = o2;
            }
        }
        return true;
    }

    /**
     * 配列をオブジェクトの配列({@literal Object[]})に変換します。
     * <p>
     * 変換元の配列にはプリミティブ型の配列を渡すことができます。 その場合、変換された配列の要素型はプリミティブ型に対応するラッパー型の配列となります。
     * </p>
     *
     * @param array
     *            配列
     * @return オブジェクトの配列
     */
    public static Object[] toObjectArray(final Object array) {
        assertArgumentNotNull("array", array);
        assertArgument("array", array.getClass().isArray(), MessageFormatter.getSimpleMessage("ECL0104", array));

        final int length = Array.getLength(array);
        final Object[] objectArray = new Object[length];
        for (int i = 0; i < length; i++) {
            objectArray[i] = Array.get(array, i);
        }
        return objectArray;
    }

    /**
     * 配列をリストに変換します。
     * <p>
     * 変換元の配列にはプリミティブ型の配列を渡すことができます。その場合、変換されたリストの要素型はプリミティブ型に対応するラッパー型の配列となります。
     * </p>
     *
     * @param <T>
     *            配列の要素の型
     * @param array
     *            配列
     * @return リスト
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(final Object array) {
        assertArgumentNotNull("array", array);
        assertArgument("array", array.getClass().isArray(), MessageFormatter.getSimpleMessage("ECL0104", array));

        final int length = Array.getLength(array);
        final List<Object> list = newArrayList(length);
        for (int i = 0; i < length; i++) {
            list.add(Array.get(array, i));
        }
        return (List<T>) list;
    }

    /**
     * オブジェクトが配列の場合は{@literal true}を返します。{@literal null}の場合は{@literal false}
     * を返します。
     *
     * @param object
     *            オブジェクト
     * @return オブジェクトが配列の場合は{@literal true}。{@literal null}の場合は{@literal false}
     */
    public static boolean isArray(final Object object) {
        return object != null && object.getClass().isArray();
    }

    /**
     * オブジェクトが配列でない場合は{@literal true}を返します。{@literal null}の場合は{@literal true}
     * を返します。
     *
     * @param object
     *            オブジェクト
     * @return オブジェクトが配列でない場合は{@literal true}。{@literal null}の場合は{@literal true}
     */
    public static boolean isNotArray(final Object object) {
        return !isArray(object);
    }

}
