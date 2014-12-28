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
package org.codelibs.core.collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.codelibs.core.exception.ClNoSuchElementException;
import org.codelibs.core.exception.ClUnsupportedOperationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author shot
 * @author manhole
 */
public class ArrayIteratorTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     *
     */
    @Test
    public void testNext() {
        final ArrayIterator<String> itr = new ArrayIterator<String>("a", "b",
                "c");
        assertThat(itr.next(), equalTo("a"));
        assertThat(itr.next(), equalTo("b"));
        assertThat(itr.next(), equalTo("c"));
    }

    /**
     *
     */
    @Test
    public void testNoSuchElement() {
        exception.expect(ClNoSuchElementException.class);
        exception.expectMessage(is("index=2"));
        final ArrayIterator<Integer> itr = new ArrayIterator<Integer>(
                new Integer[] { 1, 2 });
        itr.next();
        itr.next();
        itr.next();
    }

    /**
     *
     */
    @Test
    public void testHasNext() {
        final ArrayIterator<String> itr = new ArrayIterator<String>("A", "B");
        assertThat(itr.hasNext(), is(true));
        itr.next();
        assertThat(itr.hasNext(), is(true));
        itr.next();
        assertThat(itr.hasNext(), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        exception.expect(ClUnsupportedOperationException.class);
        exception.expectMessage(is("remove"));
        final ArrayIterator<String> itr = new ArrayIterator<String>("1", "2");
        itr.remove();
    }

}
