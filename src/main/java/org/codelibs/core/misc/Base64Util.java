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

import java.util.Base64;

import org.codelibs.core.collection.ArrayUtil;
import org.codelibs.core.lang.StringUtil;

/**
 * Utility class for handling Base64 encoding and decoding.
 * <p>
 * This class now uses the standard {@link java.util.Base64} implementation
 * instead of a custom implementation, providing better security and performance.
 * The API remains backward compatible with previous versions.
 * </p>
 *
 * @author higa
 */
public abstract class Base64Util {

    /**
     * Do not instantiate.
     */
    protected Base64Util() {
    }

    /**
     * Encodes data in Base64.
     * <p>
     * This method uses {@link java.util.Base64.Encoder} for encoding.
     * </p>
     *
     * @param inData
     *            The data to encode
     * @return The encoded data, or an empty string if the input is null or empty
     */
    public static String encode(final byte[] inData) {
        if (ArrayUtil.isEmpty(inData)) {
            return "";
        }
        return Base64.getEncoder().encodeToString(inData);
    }

    /**
     * Decodes data encoded in Base64.
     * <p>
     * This method uses {@link java.util.Base64.Decoder} for decoding.
     * </p>
     *
     * @param inData
     *            The data to decode
     * @return The decoded data, or null if the input is null or empty
     */
    public static byte[] decode(final String inData) {
        if (StringUtil.isEmpty(inData)) {
            return null;
        }
        return Base64.getDecoder().decode(inData);
    }
}
