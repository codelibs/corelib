/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
package org.codelibs.core.io;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.StringReader;

import org.junit.Test;

/**
 * @author koichik
 */
public class LineIteratorTest {

    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        final StringReader reader = new StringReader("aaa\nbbb\nccc\n");
        final LineIterator it = new LineIterator(reader);
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("aaa"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("bbb"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("ccc"));
        assertThat(it.hasNext(), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testForEach() throws Exception {
        final StringReader reader = new StringReader("aaa\nbbb\nccc\n");
        for (final String line : LineIterator.iterable(reader)) {
            System.out.println(line);
        }
    }

}
