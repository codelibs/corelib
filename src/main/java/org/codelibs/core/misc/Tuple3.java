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
package org.codelibs.core.misc;

/**
 * A tuple of three values.
 *
 * @author koichik
 * @param <T1>
 *            The type of the first value
 * @param <T2>
 *            The type of the second value
 * @param <T3>
 *            The type of the third value
 */
public class Tuple3<T1, T2, T3> {

    /** The first value */
    protected T1 value1;

    /** The second value */
    protected T2 value2;

    /** The third value */
    protected T3 value3;

    /**
     * Creates and returns a tuple of three values.
     *
     * @param <T1>
     *            The type of the first value
     * @param <T2>
     *            The type of the second value
     * @param <T3>
     *            The type of the third value
     * @param value1
     *            The first value
     * @param value2
     *            The second value
     * @param value3
     *            The third value
     * @return A tuple of three values
     */
    public static <T1, T2, T3> Tuple3<T1, T2, T3> tuple3(final T1 value1, final T2 value2, final T3 value3) {
        return new Tuple3<>(value1, value2, value3);
    }

    /**
     * Constructs an instance.
     */
    public Tuple3() {
    }

    /**
     * Constructs an instance.
     *
     * @param value1
     *            The first value
     * @param value2
     *            The second value
     * @param value3
     *            The third value
     */
    public Tuple3(final T1 value1, final T2 value2, final T3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    /**
     * Returns the first value.
     *
     * @return The first value
     */
    public T1 getValue1() {
        return value1;
    }

    /**
     * Sets the first value.
     *
     * @param value1
     *            The first value
     */
    public void setValue1(final T1 value1) {
        this.value1 = value1;
    }

    /**
     * Returns the second value.
     *
     * @return The second value
     */
    public T2 getValue2() {
        return value2;
    }

    /**
     * Sets the second value.
     *
     * @param value2
     *            The second value
     */
    public void setValue2(final T2 value2) {
        this.value2 = value2;
    }

    /**
     * Returns the third value.
     *
     * @return The third value
     */
    public T3 getValue3() {
        return value3;
    }

    /**
     * Sets the third value.
     *
     * @param value3
     *            The third value
     */
    public void setValue3(final T3 value3) {
        this.value3 = value3;
    }

    @Override
    public String toString() {
        return "{" + value1 + ", " + value2 + ", " + value3 + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value1 == null) ? 0 : value1.hashCode());
        result = prime * result + ((value2 == null) ? 0 : value2.hashCode());
        result = prime * result + ((value3 == null) ? 0 : value3.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final Tuple3<T1, T2, T3> other = (Tuple3<T1, T2, T3>) obj;
        if (value1 == null) {
            if (other.value1 != null) {
                return false;
            }
        } else if (!value1.equals(other.value1)) {
            return false;
        }
        if (value2 == null) {
            if (other.value2 != null) {
                return false;
            }
        } else if (!value2.equals(other.value2)) {
            return false;
        }
        if (value3 == null) {
            if (other.value3 != null) {
                return false;
            }
        } else if (!value3.equals(other.value3)) {
            return false;
        }
        return true;
    }

}
