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
package org.codelibs.core.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class SIndexOutOfBoundsExceptionTest {

    /**
     * Test method for
     * {@link org.codelibs.core.exception.ClIndexOutOfBoundsException#SIndexOutOfBoundsException()}
     * .
     */
    @Test
    public void testSIndexOutOfBoundsException() {
        final ClIndexOutOfBoundsException clIndexOutOfBoundsException = new ClIndexOutOfBoundsException();
        assertThat(clIndexOutOfBoundsException, is(notNullValue()));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.exception.ClIndexOutOfBoundsException#SIndexOutOfBoundsException(java.lang.String)}
     * .
     */
    @Test
    public void testSIndexOutOfBoundsExceptionString() {
        final ClIndexOutOfBoundsException clIndexOutOfBoundsException = new ClIndexOutOfBoundsException(
                "hoge");
        assertThat(clIndexOutOfBoundsException.getMessage(), is("hoge"));
    }

}
