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
package org.codelibs.core.collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author koichik
 *
 */
public class SingleValueIteratorTest {

    /**
     *
     */
    @Test
    public void test() {
        final SingleValueIterator<String> it = new SingleValueIterator<String>("Foo");
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("Foo"));
        assertThat(it.hasNext(), is(not(true)));
    }

}
