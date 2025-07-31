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
package org.codelibs.core.convert;

import java.math.BigInteger;

/**
 * Utility class for conversions related to {@link BigInteger}.
 *
 * @author higa
 */
public abstract class BigIntegerConversionUtil {

    /**
     * Do not instantiate.
     */
    protected BigIntegerConversionUtil() {
    }

    /**
     * Converts to {@link BigInteger}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link BigInteger}
     */
    public static BigInteger toBigInteger(final Object o) {
        return toBigInteger(o, null);
    }

    /**
     * Converts to {@link BigInteger}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link BigInteger}
     */
    public static BigInteger toBigInteger(final Object o, final String pattern) {
        if (o == null) {
            return null;
        } else if (o instanceof BigInteger) {
            return (BigInteger) o;
        } else {
            final Long l = LongConversionUtil.toLong(o, pattern);
            if (l == null) {
                return null;
            }
            return BigInteger.valueOf(l);
        }
    }

}
