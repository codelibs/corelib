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

import static org.codelibs.core.collection.EnumerationIterator.iterable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Enumeration;
import java.util.Vector;

import org.codelibs.core.exception.ClUnsupportedOperationException;
import org.codelibs.core.exception.NullArgumentException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author shot
 * @author manhole
 */
public class EnumerationIteratorTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     *
     */
    @Test
    public void testEnumerationIterator() {
        final Vector<String> vector = new Vector<String>();
        vector.add("a");
        final EnumerationIterator<String> itr = new EnumerationIterator<String>(vector.elements());
        assertThat(itr.hasNext(), is(true));
        assertThat(itr.next(), is("a"));
        assertThat(itr.hasNext(), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        exception.expect(ClUnsupportedOperationException.class);
        exception.expectMessage(is("remove"));
        final Vector<String> vector = new Vector<String>();
        vector.add("a");
        final EnumerationIterator<String> itr = new EnumerationIterator<String>(vector.elements());
        itr.remove();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testNext() throws Exception {
        final EnumerationIterator<String> itr = new EnumerationIterator<String>(new Vector<String>().elements());
        assertThat(itr.hasNext(), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test(expected = NullArgumentException.class)
    public void testConstructorWithNull() throws Exception {
        new EnumerationIterator<String>((Enumeration<String>) null);
    }

    /**
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @Test
    public void testIterable() throws Exception {
        final Vector<String> vector = new Vector<String>();
        vector.add("a");
        vector.add("b");
        int count = 0;
        for (final String s : iterable(vector.elements())) {
            ++count;
        }
        assertThat(count, is(2));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.collection.EnumerationIterator#EnumerationIterator(Enumeration)}
     * .
     */
    @Test
    public void testCopyDestNull() {
        exception.expect(NullArgumentException.class);
        exception.expectMessage(is("[ECL0008]argument[enumeration] is null."));
        new EnumerationIterator<Object>(null);
    }

}
