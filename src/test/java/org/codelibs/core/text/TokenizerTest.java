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
package org.codelibs.core.text;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author higa
 *
 */
public class TokenizerTest {

    /**
     * @throws Exception
     */
    @Test
    public void testEOF() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("");
        assertThat(tokenizer.nextToken(), is(Tokenizer.TT_EOF));
        assertThat(tokenizer.nextToken(), is(Tokenizer.TT_EOF));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testWhitespace() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("\t       \n");
        assertThat(tokenizer.nextToken(), is(Tokenizer.TT_EOF));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHyphen() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("       - ");
        assertThat(tokenizer.nextToken(), is((int) '-'));
        assertThat(tokenizer.nextToken(), is(Tokenizer.TT_EOF));
    }

    /**
     * @throws Exception
     */
    @Test
    public void pend_testDot() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("abc.hoge");
        assertThat(tokenizer.nextToken(), is(Tokenizer.TT_WORD));
        assertThat(tokenizer.getStringValue(), is("abc.hoge"));
        assertThat(tokenizer.nextToken(), is(Tokenizer.TT_EOF));
    }

}
