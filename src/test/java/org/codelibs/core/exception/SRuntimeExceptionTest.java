/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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

import static org.codelibs.core.collection.ArrayUtil.asArray;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author higa
 *
 */
public class SRuntimeExceptionTest {

    /**
     * @throws Exception
     */
    @Test
    public void testSeasarRuntimeException() throws Exception {
        final ClRuntimeException ex = new ClRuntimeException("ECL0001", asArray("hoge"));
        assertThat(ex.getMessageCode(), is("ECL0001"));
        assertThat(ex.getArgs().length, is(1));
        assertThat(ex.getArgs()[0], is((Object) "hoge"));
        System.out.println(ex.getMessage());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetCause() throws Exception {
        final Throwable t = new NullPointerException("test");
        final ClRuntimeException ex = new ClRuntimeException("ECL0017", asArray(t), t);
        assertThat(ex.getCause(), is(t));
        ex.printStackTrace();
    }

}
