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

import static org.codelibs.core.misc.AssertionUtil.assertArgument;

/**
 * Utility class for conversions related to byte arrays.
 *
 * @author higa
 */
public abstract class BinaryConversionUtil {

    /**
     * Do not instantiate.
     */
    private BinaryConversionUtil() {
    }

    /**
     * Converts to a {@literal byte} array.
     *
     * @param o
     *            The object to convert
     * @return The {@literal byte} array
     */
    public static byte[] toBinary(final Object o) {
        if (o instanceof byte[]) {
            return (byte[]) o;
        } else if (o == null) {
            return null;
        } else {
            assertArgument("o", o instanceof String, o.getClass().toString());
            return ((String) o).getBytes();
        }
    }

}
