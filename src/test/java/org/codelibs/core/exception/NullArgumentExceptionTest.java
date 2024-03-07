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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.codelibs.core.misc.LocaleUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class NullArgumentExceptionTest {

    @Before
    public void setUp() throws Exception {
        LocaleUtil.setDefault(() -> Locale.JAPANESE);
    }

    @After
    public void tearDown() throws Exception {
        LocaleUtil.setDefault(null);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testErrorMessage_ja() throws Exception {
        // ## Arrange ##
        Locale.setDefault(Locale.JAPANESE);
        final NullArgumentException nullArgumentException = new NullArgumentException("hoge");
        assertThat(nullArgumentException.getArgName(), is("hoge"));
        assertThat(nullArgumentException.getMessage(), is("[ECL0008]argument[hoge] is null."));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testErrorMessage_en() throws Exception {
        // ## Arrange ##
        Locale.setDefault(Locale.ENGLISH);
        final NullArgumentException nullArgumentException = new NullArgumentException("hoge");
        assertThat(nullArgumentException.getArgName(), is("hoge"));
        assertThat(nullArgumentException.getMessage(), is("[ECL0008]argument[hoge] is null."));
    }
}
