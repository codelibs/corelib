/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.codelibs.core.exception.ClIllegalArgumentException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author wyukawa
 *
 */
public class BinaryConversionUtilTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test method for
     * {@link org.codelibs.core.convert.BinaryConversionUtil#toBinary(java.lang.Object)}
     * .
     */
    @Test
    public void testToBinary() {
        assertThat(BinaryConversionUtil.toBinary(null), nullValue());
        final byte[] b = { 0x00, 0x01 };
        assertThat(BinaryConversionUtil.toBinary(b), is(b));
        assertThat(BinaryConversionUtil.toBinary("hoge"), is("hoge".getBytes()));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.convert.BinaryConversionUtil#toBinary(java.lang.Object)}
     * .
     */
    @Test
    public void testToBinaryException() {
        exception.expect(ClIllegalArgumentException.class);
        exception.expectMessage(is("[ECL0009]argument[o] is illegal. because class java.lang.Object."));
        BinaryConversionUtil.toBinary(new Object());
    }

}
