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
package org.codelibs.core.exception;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class SIllegalStateExceptionTest {

    /**
     * Test method for
     * {@link org.codelibs.core.exception.ClIllegalStateException#SIllegalStateException()}
     * .
     */
    @Test
    public void testSIllegalStateException() {
        final ClIllegalStateException clIllegalStateException = new ClIllegalStateException();
        assertThat(clIllegalStateException, is(notNullValue()));

    }

    /**
     * Test method for
     * {@link org.codelibs.core.exception.ClIllegalStateException#SIllegalStateException(java.lang.String)}
     * .
     */
    @Test
    public void testSIllegalStateExceptionString() {
        final ClIllegalStateException clIllegalStateException = new ClIllegalStateException("hoge");
        assertThat(clIllegalStateException.getMessage(), is("hoge"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.exception.ClIllegalStateException#SIllegalStateException(java.lang.String, java.lang.Throwable)}
     * .
     */
    @Test
    public void testSIllegalStateExceptionStringThrowable() {
        final ClIllegalStateException clIllegalStateException = new ClIllegalStateException("hoge", new NullPointerException());
        assertThat(clIllegalStateException.getMessage(), is("hoge"));
        assertThat(clIllegalStateException.getCause(), instanceOf(NullPointerException.class));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.exception.ClIllegalStateException#SIllegalStateException(java.lang.Throwable)}
     * .
     */
    @Test
    public void testSIllegalStateExceptionThrowable() {
        final ClIllegalStateException clIllegalStateException = new ClIllegalStateException(new NullPointerException());
        assertThat(clIllegalStateException.getCause(), instanceOf(NullPointerException.class));
    }

}
