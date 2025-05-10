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

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class ByteConversionUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testToByte() throws Exception {
        assertEquals(new Byte("100"), ByteConversionUtil.toByte("100"));
    }

    /**
     * @throws Exception
     */
    public void testToPrimitiveByte() throws Exception {
        assertEquals(100, ByteConversionUtil.toPrimitiveByte("100"));
    }

    /**
     * @throws Exception
     */
    public void testToPrimitiveByte_emptyString() throws Exception {
        assertEquals(0, ByteConversionUtil.toPrimitiveByte(""));
    }

    /**
     * @throws Exception
     */
    public void testToByteForEmptyString() throws Exception {
        assertNull(ByteConversionUtil.toByte(""));
    }
}
