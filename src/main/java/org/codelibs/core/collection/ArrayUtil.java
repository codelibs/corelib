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
 * Utility class for array operations.
 *
 * @author higa
 */
public abstract class ArrayUtil {

    /**
     * Do not instantiate.
     */
    private ArrayUtil() {
    }

    /**
     * Returns an array of {@literal boolean}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static boolean[] asBooleanArray(final boolean... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal char}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static char[] asCharArray(final char... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal byte}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static byte[] asByteArray(final byte... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal short}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static short[] asShortArray(final short... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal int}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static int[] asIntArray(final int... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal long}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static long[] asLongArray(final long... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal float}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static float[] asFloatArray(final float... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal double}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static double[] asDoubleArray(final double... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Object}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Object[] asArray(final Object... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal String}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static String[] asStringArray(final String... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Boolean}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Boolean[] asArray(final Boolean... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Character}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Character[] asArray(final Character... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Byte}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Byte[] asArray(final Byte... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Short}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Short[] asArray(final Short... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Integer}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Integer[] asArray(final Integer... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Long}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Long[] asArray(final Long... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Float}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Float[] asArray(final Float... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal Double}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static Double[] asArray(final Double... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal BigInteger}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static BigInteger[] asArray(final BigInteger... elements) {
        return elements;
    }

    /**
     * Returns an array of {@literal BigDecimal}.
     *
     * @param elements the elements of the array
     * @return the array
     */
    public static BigDecimal[] asArray(final BigDecimal... elements) {
        return elements;
    }

    /**
     * Returns a new array with the specified object appended to the end.
     *
     * @param <T>
     *            the type of the array elements
     * @param array
     *            the array. Must not be {@literal null}
     * @param obj
     *            the object to add
     * @return a new array with the object appended
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
     * Returns a new array with the specified boolean value appended to the end of the boolean array.
     *
     * @param array
     *            the array. Must not be {@literal null}
     * @param value
     *            the value to add
     * @return a new array with the value appended
     */
    public static boolean[] add(final boolean[] array, final boolean value) {
        assertArgumentNotNull("array", array);
        final boolean[] newArray = (boolean[]) Array.newInstance(boolean.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Returns a new array with the specified byte value appended to the end of the byte array.
     *
     * @param array
     *            the array. Must not be {@literal null}
     * @param value
     *            the value to add
     * @return a new array with the value appended
     */
    public static byte[] add(final byte[] array, final byte value) {
        assertArgumentNotNull("array", array);

        final byte[] newArray = (byte[]) Array.newInstance(byte.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Returns a new array with the specified short value appended to the end of the short array.
     *
     * @param array
     *            the array. Must not be {@literal null}
     * @param value
     *            the value to add
     * @return a new array with the value appended
     */
    public static short[] add(final short[] array, final short value) {
        assertArgumentNotNull("array", array);

        final short[] newArray = (short[]) Array.newInstance(short.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Returns a new array with the specified int value appended to the end of the int array.
     *
     * @param array
     *            the array. Must not be {@literal null}
     * @param value
     *            the value to add
     * @return a new array with the value appended
     */
    public static int[] add(final int[] array, final int value) {
        assertArgumentNotNull("array", array);

        final int[] newArray = (int[]) Array.newInstance(int.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Returns a new array with the specified long value appended to the end of the long array.
     *
     * @param array
     *            the array. Must not be {@literal null}
     * @param value
     *            the value to add
     * @return a new array with the value appended
     */
    public static long[] add(final long[] array, final long value) {
        assertArgumentNotNull("array", array);

        final long[] newArray = (long[]) Array.newInstance(long.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Returns a new array with the specified float value appended to the end of the float array.
     *
     * @param array
     *            the array. Must not be {@literal null}
     * @param value
     *            the value to add
     * @return a new array with the value appended
     */
    public static float[] add(final float[] array, final float value) {
        assertArgumentNotNull("array", array);

        final float[] newArray = (float[]) Array.newInstance(float.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Returns a new array with the specified double value appended to the end of the double array.
     *
     * @param array
     *            the array. Must not be {@literal null}
     * @param value
     *            the value to add
     * @return a new array with the value appended
     */
    public static double[] add(final double[] array, final double value) {
        assertArgumentNotNull("array", array);

        final double[] newArray = (double[]) Array.newInstance(double.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param <T>
     *            the type of the array elements
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns an array that is the concatenation of two arrays.
     * <p>
     * If either or both arrays are {@literal null}, the other array is returned as is.
     * If either or both arrays have a length of {@literal 0}, the other array is returned as is.
     * In any case, the returned array is the same instance as the argument array and is not copied.
     * </p>
     *
     * @param a
     *            first array
     * @param b
     *            second array
     * @return the concatenated result array
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
     * Returns the index of the first occurrence of the specified object in the array.
     *
     * @param <T>
     *            the type of the array elements
     * @param array
     *            the array
     * @param obj
     *            the object to search for
     * @return the index of the first occurrence of the object in the array
     */
    public static <T> int indexOf(final T[] array, final T obj) {
        return indexOf(array, obj, 0);
    }

    /**
     * Returns the index of the first occurrence of the specified object in the array, starting the search at the specified index.
     *
     * @param <T>
     *            the type of the array elements
     * @param array
     *            the array
     * @param obj
     *            the object to search for
     * @param fromIndex
     *            the index to start the search from
     * @return the index of the first occurrence of the object in the array, or {@literal -1} if not found
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns the index of the first occurrence of the specified value in the array.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return the index of the first occurrence of the value in the array
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
     * Returns a new array with the first occurrence of the specified object removed.
     * <p>
     * If the array does not contain the object, the original array is returned as is.
     * </p>
     *
     * @param <T>
     *            the type of the array elements
     * @param array
     *            the array
     * @param obj
     *            the element to remove from the array
     * @return the array after removal
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
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param <T>
     *            the type of the array elements
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static <T> boolean isEmpty(final T[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param <T>
     *            the type of the array elements
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static <T> boolean isNotEmpty(final T[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final boolean[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final boolean[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final byte[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final byte[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final short[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final short[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final int[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final int[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final long[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final long[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final float[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final float[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final double[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final double[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array is {@literal null} or has a length of 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is {@literal null} or has a length of 0
     */
    public static boolean isEmpty(final char[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * Returns {@literal true} if the array is not {@literal null} and its length is not 0.
     *
     * @param arrays
     *            the array
     * @return {@literal true} if the array is not {@literal null} and its length is not 0
     */
    public static boolean isNotEmpty(final char[] arrays) {
        return arrays != null && arrays.length != 0;
    }

    /**
     * Returns {@literal true} if the array contains the specified object.
     *
     * @param <T>
     *            the type of the array elements
     * @param array
     *            the array
     * @param obj
     *            the object to search for
     * @return {@literal true} if the array contains the specified object
     */
    public static <T> boolean contains(final T[] array, final T obj) {
        return indexOf(array, obj) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified value
     */
    public static boolean contains(final boolean[] array, final boolean value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified value
     */
    public static boolean contains(final byte[] array, final byte value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified value
     */
    public static boolean contains(final short[] array, final short value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified value
     */
    public static boolean contains(final int[] array, final int value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified value
     */
    public static boolean contains(final long[] array, final long value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified value
     */
    public static boolean contains(final float[] array, final float value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified value
     */
    public static boolean contains(final double[] array, final double value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the array contains the specified value.
     *
     * @param array
     *            the array
     * @param value
     *            the value to search for
     * @return {@literal true} if the array contains the specified character
     */
    public static boolean contains(final char[] array, final char value) {
        return indexOf(array, value) > -1;
    }

    /**
     * Returns {@literal true} if the two arrays are equal, ignoring the order of elements.
     *
     * @param <T>
     *            the type of the array elements
     * @param array1
     *            the first array
     * @param array2
     *            the second array
     * @return {@literal true} if the two arrays are equal, ignoring the order of elements
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
     * Converts an array to an object array ({@literal Object[]}).
     * <p>
     * The source array can be a primitive array. In that case, the resulting array will contain the corresponding wrapper types.
     * </p>
     *
     * @param array
     *            the array
     * @return the object array
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
     * Converts an array to a list.
     * <p>
     * The source array can be a primitive array. In that case, the resulting list will contain the corresponding wrapper types.
     * </p>
     *
     * @param <T>
     *            the type of the array elements
     * @param array
     *            the array
     * @return the list
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
     * Returns {@literal true} if the object is an array. Returns {@literal false} if the object is {@literal null}.
     *
     * @param object
     *            the object
     * @return {@literal true} if the object is an array; {@literal false} if the object is {@literal null}
     */
    public static boolean isArray(final Object object) {
        return object != null && object.getClass().isArray();
    }

    /**
     * Returns {@literal true} if the object is not an array. Returns {@literal true} if the object is {@literal null}.
     *
     * @param object
     *            the object
     * @return {@literal true} if the object is not an array, or if the object is {@literal null}
     */
    public static boolean isNotArray(final Object object) {
        return !isArray(object);
    }

}
