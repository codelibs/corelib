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
package org.codelibs.core.collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.codelibs.core.exception.ClUnsupportedOperationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author wyukawa
 *
 */
public class EmptyIteratorTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test method for
     * {@link org.codelibs.core.collection.EmptyIterator#EmptyIterator()}.
     */
    @Test
    public void testEmptyIterator() {
        final EmptyIterator<String> emptyIterator = new EmptyIterator<String>();
        assertThat(emptyIterator, is(notNullValue()));
    }

    /**
     * Test method for {@link org.codelibs.core.collection.EmptyIterator#remove()}
     * .
     */
    @Test
    public void testRemove() {
        exception.expect(ClUnsupportedOperationException.class);
        exception.expectMessage(is("remove"));
        final EmptyIterator<String> emptyIterator = new EmptyIterator<String>();
        emptyIterator.remove();
    }

    /**
     * Test method for
     * {@link org.codelibs.core.collection.EmptyIterator#hasNext()}.
     */
    @Test
    public void testHasNext() {
        final EmptyIterator<String> emptyIterator = new EmptyIterator<String>();
        assertThat(emptyIterator.hasNext(), is(false));
    }

    /**
     * Test method for {@link org.codelibs.core.collection.EmptyIterator#next()}.
     */
    @Test
    public void testNext() {
        exception.expect(ClUnsupportedOperationException.class);
        exception.expectMessage(is("next"));
        final EmptyIterator<String> emptyIterator = new EmptyIterator<String>();
        emptyIterator.next();
    }

}
