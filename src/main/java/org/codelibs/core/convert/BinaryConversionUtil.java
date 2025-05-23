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
 * byte配列用の変換ユーティリティです。
 *
 * @author higa
 */
public abstract class BinaryConversionUtil {

    /**
     * {@literal byte}の配列に変換します。
     *
     * @param o
     *            変換元のオブジェクト
     * @return {@literal byte}の配列
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
