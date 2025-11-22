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

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class Base64UtilTest extends TestCase {

    private static final String ORIGINAL = "how now brown cow\r\n";

    private static final byte[] BINARY_DATA = ORIGINAL.getBytes();

    private static final String ENCODED_DATA = "aG93IG5vdyBicm93biBjb3cNCg==";

    /**
     * @throws Exception
     */
    public void testEncode() throws Exception {
        assertEquals("1", ENCODED_DATA, Base64Util.encode(BINARY_DATA));
        System.out.println(Base64Util.encode(new byte[] { 'a', 'b', 'c' }));
    }

    /**
     * @throws Exception
     */
    public void testDecode() throws Exception {
        final byte[] decodedData = Base64Util.decode(ENCODED_DATA);
        assertEquals("1", BINARY_DATA.length, decodedData.length);
        for (int i = 0; i < decodedData.length; i++) {
            assertEquals("2", BINARY_DATA[i], decodedData[i]);
        }
    }

    /**
     * Test encode with empty byte array
     *
     * @throws Exception
     */
    public void testEncode_EmptyArray() throws Exception {
        final String result = Base64Util.encode(new byte[0]);
        assertEquals("Empty array should return empty string", "", result);
    }

    /**
     * Test encode with null returns empty string
     *
     * @throws Exception
     */
    public void testEncode_Null() throws Exception {
        final String result = Base64Util.encode(null);
        assertEquals("Null should return empty string", "", result);
    }

    /**
     * Test decode with empty string
     *
     * @throws Exception
     */
    public void testDecode_EmptyString() throws Exception {
        final byte[] result = Base64Util.decode("");
        assertNull("Empty string should return null", result);
    }

    /**
     * Test decode with null
     *
     * @throws Exception
     */
    public void testDecode_Null() throws Exception {
        final byte[] result = Base64Util.decode(null);
        assertNull("Null should return null", result);
    }

    /**
     * Test encode/decode round trip with various data
     *
     * @throws Exception
     */
    public void testEncodeDecode_RoundTrip() throws Exception {
        // Test with ASCII text
        final String text = "Hello, World!";
        final byte[] textBytes = text.getBytes("UTF-8");
        final String encoded = Base64Util.encode(textBytes);
        final byte[] decoded = Base64Util.decode(encoded);
        assertEquals("Round trip should preserve data", text, new String(decoded, "UTF-8"));

        // Test with UTF-8 text
        final String utf8Text = "こんにちは世界";
        final byte[] utf8Bytes = utf8Text.getBytes("UTF-8");
        final String utf8Encoded = Base64Util.encode(utf8Bytes);
        final byte[] utf8Decoded = Base64Util.decode(utf8Encoded);
        assertEquals("Round trip should preserve UTF-8 data", utf8Text, new String(utf8Decoded, "UTF-8"));

        // Test with binary data
        final byte[] binaryData = new byte[] { 0, 1, 2, 3, 127, (byte) 128, (byte) 255 };
        final String binaryEncoded = Base64Util.encode(binaryData);
        final byte[] binaryDecoded = Base64Util.decode(binaryEncoded);
        assertEquals("Binary data length should match", binaryData.length, binaryDecoded.length);
        for (int i = 0; i < binaryData.length; i++) {
            assertEquals("Binary data should match at position " + i, binaryData[i], binaryDecoded[i]);
        }
    }

    /**
     * Test encode with single byte
     *
     * @throws Exception
     */
    public void testEncode_SingleByte() throws Exception {
        final byte[] singleByte = new byte[] { 'A' };
        final String encoded = Base64Util.encode(singleByte);
        assertNotNull("Encoded result should not be null", encoded);
        final byte[] decoded = Base64Util.decode(encoded);
        assertEquals("Decoded should have same length", 1, decoded.length);
        assertEquals("Decoded byte should match", singleByte[0], decoded[0]);
    }

    /**
     * Test encode with two bytes
     *
     * @throws Exception
     */
    public void testEncode_TwoBytes() throws Exception {
        final byte[] twoBytes = new byte[] { 'A', 'B' };
        final String encoded = Base64Util.encode(twoBytes);
        assertNotNull("Encoded result should not be null", encoded);
        final byte[] decoded = Base64Util.decode(encoded);
        assertEquals("Decoded should have same length", 2, decoded.length);
        assertEquals("First byte should match", twoBytes[0], decoded[0]);
        assertEquals("Second byte should match", twoBytes[1], decoded[1]);
    }

    /**
     * Test backward compatibility with standard Base64
     *
     * @throws Exception
     */
    public void testBackwardCompatibility() throws Exception {
        // These test cases ensure that the new java.util.Base64 implementation
        // produces the same output as the old custom implementation

        // Test case 1: Standard padding
        final byte[] data1 = "abc".getBytes();
        assertEquals("YWJj", Base64Util.encode(data1));

        // Test case 2: Single padding
        final byte[] data2 = "abcd".getBytes();
        assertEquals("YWJjZA==", Base64Util.encode(data2));

        // Test case 3: Double padding
        final byte[] data3 = "abcde".getBytes();
        assertEquals("YWJjZGU=", Base64Util.encode(data3));

        // Test case 4: No padding
        final byte[] data4 = "abcdef".getBytes();
        assertEquals("YWJjZGVm", Base64Util.encode(data4));
    }
}
