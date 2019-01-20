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

import static org.codelibs.core.collection.CollectionsUtil.newArrayList;
import static org.codelibs.core.collection.IndexedIterator.indexed;
import static org.codelibs.core.io.LineIterator.iterable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class IndexedIteratorTest {

    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        final List<String> list = newArrayList();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        final IndexedIterator<String> it = new IndexedIterator<String>(list.iterator());

        assertThat(it.hasNext(), is(true));

        final Indexed<String> indexed1 = it.next();
        assertThat(indexed1.getIndex(), is(0));
        assertThat(indexed1.getElement(), is("aaa"));

        final Indexed<String> indexed2 = it.next();
        assertThat(indexed2.getIndex(), is(1));
        assertThat(indexed2.getElement(), is("bbb"));

        final Indexed<String> indexed3 = it.next();
        assertThat(indexed3.getIndex(), is(2));
        assertThat(indexed3.getElement(), is("ccc"));

        assertThat(it.hasNext(), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testForEach() throws Exception {
        final List<String> list = newArrayList();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        for (final Indexed<String> indexed : indexed(list)) {
            System.out.println(indexed.getIndex());
            System.out.println(indexed.getElement());
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testForEachLineIterator() throws Exception {
        final StringReader reader = new StringReader("aaa\nbbb\nccc\n");
        for (final Indexed<String> indexed : indexed(iterable(reader))) {
            System.out.println(indexed.getIndex());
            System.out.println(indexed.getElement());
        }
    }

}
