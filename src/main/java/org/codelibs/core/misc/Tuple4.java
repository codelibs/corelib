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

/**
 * 4つの値の組です。
 *
 * @author koichik
 * @param <T1>
 *            1番目の値の型
 * @param <T2>
 *            2番目の値の型
 * @param <T3>
 *            3番目の値の型
 * @param <T4>
 *            4番目の値の型
 */
public class Tuple4<T1, T2, T3, T4> {

    /** 1番目の値 */
    protected T1 value1;

    /** 2番目の値 */
    protected T2 value2;

    /** 3番目の値 */
    protected T3 value3;

    /** 4番目の値 */
    protected T4 value4;

    /**
     * 4つの値の組を作成して返します。
     *
     * @param <T1>
     *            1番目の値の型
     * @param <T2>
     *            2番目の値の型
     * @param <T3>
     *            3番目の値の型
     * @param <T4>
     *            4番目の値の型
     * @param value1
     *            1番目の値
     * @param value2
     *            2番目の値
     * @param value3
     *            3番目の値
     * @param value4
     *            4番目の値
     * @return 4つの値の組
     */
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> tuple4(final T1 value1, final T2 value2, final T3 value3, final T4 value4) {
        return new Tuple4<>(value1, value2, value3, value4);
    }

    /**
     * インスタンスを構築します。
     */
    public Tuple4() {
    }

    /**
     * インスタンスを構築します。
     *
     * @param value1
     *            1番目の値
     * @param value2
     *            2番目の値
     * @param value3
     *            3番目の値
     * @param value4
     *            4番目の値
     */
    public Tuple4(final T1 value1, final T2 value2, final T3 value3, final T4 value4) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }

    /**
     * 1番目の値を返します。
     *
     * @return 1番目の値
     */
    public T1 getValue1() {
        return value1;
    }

    /**
     * 1番目の値を設定します。
     *
     * @param value1
     *            1番目の値
     */
    public void setValue1(final T1 value1) {
        this.value1 = value1;
    }

    /**
     * 2番目の値を返します。
     *
     * @return 2番目の値
     */
    public T2 getValue2() {
        return value2;
    }

    /**
     * 2番目の値を設定します。
     *
     * @param value2
     *            2番目の値
     */
    public void setValue2(final T2 value2) {
        this.value2 = value2;
    }

    /**
     * 3番目の値を返します。
     *
     * @return 3番目の値
     */
    public T3 getValue3() {
        return value3;
    }

    /**
     * 3番目の値を設定します。
     *
     * @param value3
     *            3番目の値
     */
    public void setValue3(final T3 value3) {
        this.value3 = value3;
    }

    /**
     * 4番目の値を返します。
     *
     * @return 4番目の値
     */
    public T4 getValue4() {
        return value4;
    }

    /**
     * 4番目の値を設定します。
     *
     * @param value4
     *            4番目の値
     */
    public void setValue4(final T4 value4) {
        this.value4 = value4;
    }

    @Override
    public String toString() {
        return "{" + value1 + ", " + value2 + ", " + value3 + ", " + value4 + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value1 == null) ? 0 : value1.hashCode());
        result = prime * result + ((value2 == null) ? 0 : value2.hashCode());
        result = prime * result + ((value3 == null) ? 0 : value3.hashCode());
        result = prime * result + ((value4 == null) ? 0 : value4.hashCode());
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
        final Tuple4<T1, T2, T3, T4> other = (Tuple4<T1, T2, T3, T4>) obj;
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
        if (value4 == null) {
            if (other.value4 != null) {
                return false;
            }
        } else if (!value4.equals(other.value4)) {
            return false;
        }
        return true;
    }

}
