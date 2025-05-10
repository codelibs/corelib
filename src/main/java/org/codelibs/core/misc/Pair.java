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

/**
 * A pair of two values.
 *
 * @author koichik
 * @param <T1>
 *            The type of the first value
 * @param <T2>
 *            The type of the second value
 */
public class Pair<T1, T2> {

    /** The first value */
    protected T1 first;

    /** The second value */
    protected T2 second;

    /**
     * Creates and returns a pair of two values.
     *
     * @param <T1>
     *            The type of the first value
     * @param <T2>
     *            The type of the second value
     * @param first
     *            The first value
     * @param second
     *            The second value
     * @return A pair of two values
     */
    public static <T1, T2> Pair<T1, T2> pair(final T1 first, final T2 second) {
        return new Pair<>(first, second);
    }

    /**
     * Constructs an instance.
     */
    public Pair() {
    }

    /**
     * Constructs an instance.
     *
     * @param first
     *            The first value
     * @param second
     *            The second value
     */
    public Pair(final T1 first, final T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first value.
     *
     * @return The first value
     */
    public T1 getFirst() {
        return first;
    }

    /**
     * Sets the first value.
     *
     * @param first
     *            The first value
     */
    public void setFirst(final T1 first) {
        this.first = first;
    }

    /**
     * Returns the second value.
     *
     * @return The second value
     */
    public T2 getSecond() {
        return second;
    }

    /**
     * Sets the second value.
     *
     * @param second
     *            The second value
     */
    public void setSecond(final T2 second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "{" + first + ", " + second + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
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
        final Pair<T1, T2> other = (Pair<T1, T2>) obj;
        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }
        if (second == null) {
            if (other.second != null) {
                return false;
            }
        } else if (!second.equals(other.second)) {
            return false;
        }
        return true;
    }

}
