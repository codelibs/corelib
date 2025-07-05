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
 * Utility class for assertions.
 *
 * @author shot
 */
public abstract class AssertionUtil {

    /**
     * Do not instantiate.
     */
    private AssertionUtil() {
    }

    /**
     * Asserts that the argument is not <code>null</code>.
     *
     * @param argName
     *            The name of the argument that must not be {@code null}.
     * @param argValue
     *            The value of the argument.
     * @throws NullArgumentException
     *             If the argument is <code>null</code>.
     */
    public static void assertArgumentNotNull(final String argName, final Object argValue) {
        if (argValue == null) {
            throw new NullArgumentException(argName);
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty string.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty string.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty string.
     */
    public static void assertArgumentNotEmpty(final String argName, final String argValue) {
        if (StringUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0010", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty string.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty string.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty string.
     */
    public static void assertArgumentNotEmpty(final String argName, final CharSequence argValue) {
        if (argValue == null || argValue.length() == 0) {
            throw new EmptyArgumentException(argName, "ECL0010", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final Object[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final boolean[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final byte[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final short[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final int[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final long[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final float[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final double[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty array.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty array.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty array.
     */
    public static void assertArgumentNotEmpty(final String argName, final char[] argValue) {
        if (ArrayUtil.isEmpty(argValue)) {
            throw new EmptyArgumentException(argName, "ECL0011", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty {@link Collection}.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty {@link Collection}.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty {@link Collection}.
     */
    public static void assertArgumentNotEmpty(final String argName, final Collection<?> argValue) {
        if (argValue == null || argValue.isEmpty()) {
            throw new EmptyArgumentException(argName, "ECL0012", asArray(argName));
        }
    }

    /**
     * Asserts that the argument is neither <code>null</code> nor an empty {@link Map}.
     *
     * @param argName
     *            The name of the argument that must not be {@code null} or an empty {@link Map}.
     * @param argValue
     *            The value of the argument.
     * @throws EmptyArgumentException
     *             If the argument is <code>null</code> or an empty {@link Map}.
     */
    public static void assertArgumentNotEmpty(final String argName, final Map<?, ?> argValue) {
        if (argValue == null || argValue.isEmpty()) {
            throw new EmptyArgumentException(argName, "ECL0013", asArray(argName));
        }
    }

    /**
     * Asserts that the index is valid.
     *
     * @param argName
     *            The name of the argument that must not be {@code null}.
     * @param argValue
     *            The value of the index.
     * @param arraySize
     *            The length of the array the index refers to.
     * @throws ClIllegalArgumentException
     *             If the argument is invalid as an array index.
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
     * Asserts that the argument is valid.
     *
     * @param argName
     *            The name of the argument that must not be invalid.
     * @param expression
     *            The precondition.
     * @param description
     *            The description of why the argument is invalid.
     * @throws ClIllegalArgumentException
     *             If {@code expression} is false.
     */
    public static void assertArgument(final String argName, final boolean expression, final String description) {
        if (!expression) {
            throw new ClIllegalArgumentException(argName, "ECL0009", asArray(argName, description));
        }
    }

    /**
     * Asserts that the state is not invalid.
     *
     * @param expression
     *            The precondition.
     * @param description
     *            The description of why the state is invalid.
     * @throws ClIllegalStateException
     *             If {@code expression} is false.
     */
    public static void assertState(final boolean expression, final String description) {
        if (!expression) {
            throw new ClIllegalStateException(description);
        }
    }

    /**
     * Asserts that the index is not invalid.
     *
     * @param expression
     *            The precondition.
     * @param description
     *            The description of why the index is invalid.
     * @throws ClIndexOutOfBoundsException
     *             If {@code expression} is false.
     */
    public static void assertIndex(final boolean expression, final String description) {
        if (!expression) {
            throw new ClIndexOutOfBoundsException(description);
        }
    }

}
