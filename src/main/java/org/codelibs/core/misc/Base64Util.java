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

import org.codelibs.core.collection.ArrayUtil;
import org.codelibs.core.lang.StringUtil;

/**
 * Utility class for handling Base64 encoding and decoding.
 *
 * @author higa
 */
public abstract class Base64Util {

    /**
     * Do not instantiate.
     */
    private Base64Util() {
    }

    private static final char[] ENCODE_TABLE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

    private static final char PAD = '=';

    private static final byte[] DECODE_TABLE = new byte[128];
    static {
        for (int i = 0; i < DECODE_TABLE.length; i++) {
            DECODE_TABLE[i] = Byte.MAX_VALUE;
        }
        for (int i = 0; i < ENCODE_TABLE.length; i++) {
            DECODE_TABLE[ENCODE_TABLE[i]] = (byte) i;
        }
    }

    /**
     * Encodes data in Base64.
     *
     * @param inData
     *            The data to encode
     * @return The encoded data
     */
    public static String encode(final byte[] inData) {
        if (ArrayUtil.isEmpty(inData)) {
            return "";
        }
        final int mod = inData.length % 3;
        final int num = inData.length / 3;
        char[] outData = null;
        if (mod != 0) {
            outData = new char[(num + 1) * 4];
        } else {
            outData = new char[num * 4];
        }
        for (int i = 0; i < num; i++) {
            encode(inData, i * 3, outData, i * 4);
        }
        if (mod == 1) {
            encode2pad(inData, num * 3, outData, num * 4);
        } else if (mod == 2) {
            encode1pad(inData, num * 3, outData, num * 4);
        }
        return new String(outData);
    }

    /**
     * Decodes data encoded in Base64.
     *
     * @param inData
     *            The data to decode
     * @return The decoded data
     */
    public static byte[] decode(final String inData) {
        if (StringUtil.isEmpty(inData)) {
            return null;
        }
        final int num = inData.length() / 4 - 1;
        final int lastBytes = getLastBytes(inData);
        final byte[] outData = new byte[num * 3 + lastBytes];
        for (int i = 0; i < num; i++) {
            decode(inData, i * 4, outData, i * 3);
        }
        switch (lastBytes) {
        case 1:
            decode1byte(inData, num * 4, outData, num * 3);
            break;
        case 2:
            decode2byte(inData, num * 4, outData, num * 3);
            break;
        default:
            decode(inData, num * 4, outData, num * 3);
        }
        return outData;
    }

    private static void encode(final byte[] inData, final int inIndex, final char[] outData, final int outIndex) {

        final int i = ((inData[inIndex] & 0xff) << 16) + ((inData[inIndex + 1] & 0xff) << 8) + (inData[inIndex + 2] & 0xff);
        outData[outIndex] = ENCODE_TABLE[i >> 18];
        outData[outIndex + 1] = ENCODE_TABLE[i >> 12 & 0x3f];
        outData[outIndex + 2] = ENCODE_TABLE[i >> 6 & 0x3f];
        outData[outIndex + 3] = ENCODE_TABLE[i & 0x3f];
    }

    private static void encode2pad(final byte[] inData, final int inIndex, final char[] outData, final int outIndex) {

        final int i = inData[inIndex] & 0xff;
        outData[outIndex] = ENCODE_TABLE[i >> 2];
        outData[outIndex + 1] = ENCODE_TABLE[i << 4 & 0x3f];
        outData[outIndex + 2] = PAD;
        outData[outIndex + 3] = PAD;
    }

    private static void encode1pad(final byte[] inData, final int inIndex, final char[] outData, final int outIndex) {

        final int i = ((inData[inIndex] & 0xff) << 8) + (inData[inIndex + 1] & 0xff);
        outData[outIndex] = ENCODE_TABLE[i >> 10];
        outData[outIndex + 1] = ENCODE_TABLE[i >> 4 & 0x3f];
        outData[outIndex + 2] = ENCODE_TABLE[i << 2 & 0x3f];
        outData[outIndex + 3] = PAD;
    }

    private static void decode(final String inData, final int inIndex, final byte[] outData, final int outIndex) {

        final byte b0 = DECODE_TABLE[inData.charAt(inIndex)];
        final byte b1 = DECODE_TABLE[inData.charAt(inIndex + 1)];
        final byte b2 = DECODE_TABLE[inData.charAt(inIndex + 2)];
        final byte b3 = DECODE_TABLE[inData.charAt(inIndex + 3)];
        outData[outIndex] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
        outData[outIndex + 1] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
        outData[outIndex + 2] = (byte) (b2 << 6 & 0xc0 | b3 & 0x3f);
    }

    private static void decode1byte(final String inData, final int inIndex, final byte[] outData, final int outIndex) {

        final byte b0 = DECODE_TABLE[inData.charAt(inIndex)];
        final byte b1 = DECODE_TABLE[inData.charAt(inIndex + 1)];
        outData[outIndex] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
    }

    private static void decode2byte(final String inData, final int inIndex, final byte[] outData, final int outIndex) {

        final byte b0 = DECODE_TABLE[inData.charAt(inIndex)];
        final byte b1 = DECODE_TABLE[inData.charAt(inIndex + 1)];
        final byte b2 = DECODE_TABLE[inData.charAt(inIndex + 2)];
        outData[outIndex] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
        outData[outIndex + 1] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
    }

    private static int getLastBytes(final String inData) {
        final int len = inData.length();
        if (inData.charAt(len - 2) == PAD) {
            return 1;
        } else if (inData.charAt(len - 1) == PAD) {
            return 2;
        } else {
            return 3;
        }
    }
}
