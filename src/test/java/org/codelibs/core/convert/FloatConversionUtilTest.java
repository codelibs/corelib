/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
public class FloatConversionUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testToFloat() throws Exception {
        assertEquals(new Float("1000.5"), FloatConversionUtil.toFloat("1,000.5"));
    }

    /**
     * @throws Exception
     */
    public void testToPrimitiveFloat() throws Exception {
        assertEquals(1000.5, FloatConversionUtil.toPrimitiveFloat("1,000.5"), 0);
    }

    /**
     * @throws Exception
     */
    public void testToPrimitiveFloatForEmptyString() throws Exception {
        assertEquals(0, FloatConversionUtil.toPrimitiveFloat(""), 0);
    }

    /**
     * @throws Exception
     */
    public void testToFloatForEmptyString() throws Exception {
        assertNull(FloatConversionUtil.toFloat(""));
    }
}
