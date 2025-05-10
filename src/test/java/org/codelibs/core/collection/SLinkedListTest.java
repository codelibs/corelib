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
package org.codelibs.core.collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.codelibs.core.exception.ClIndexOutOfBoundsException;
import org.codelibs.core.io.SerializeUtil;
import org.junit.Test;

/**
 * @author higa
 */
public class SLinkedListTest {

    private final SLinkedList<String> list = new SLinkedList<String>();

    /**
     * @throws Exception
     */
    @Test
    public void testGetFirstEntry() throws Exception {
        assertThat(list.getFirstEntry(), is(nullValue()));
        list.addFirst("1");
        assertThat(list.getFirstEntry().getElement(), is("1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetFirst() throws Exception {
        try {
            list.getFirst();
            fail();
        } catch (final NoSuchElementException ex) {
            System.out.println(ex);
        }
        list.addFirst("1");
        assertThat(list.getFirst(), is("1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetLastEntry() throws Exception {
        assertThat(list.getLastEntry(), is(nullValue()));
        list.addLast("1");
        assertThat(list.getLastEntry().getElement(), is("1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetLast() throws Exception {
        try {
            list.getLast();
            fail();
        } catch (final NoSuchElementException ex) {
            System.out.println(ex);
        }
        list.addLast("1");
        assertThat(list.getLast(), is("1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemoveFirst() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.removeFirst();
        assertThat(list.getFirst(), is("2"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemoveLast() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.removeLast();
        assertThat(list.getLast(), is("1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddFirst() throws Exception {
        list.addFirst("1");
        list.addFirst("2");
        assertThat(list.getFirst(), is("2"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddLast() throws Exception {
        list.addLast("1");
        list.addLast("2");
        assertThat(list.getLast(), is("2"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSize() throws Exception {
        assertThat(list.size(), is(0));
        list.addLast("1");
        assertThat(list.size(), is(1));
        list.removeFirst();
        assertThat(list.size(), is(0));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsEmpty() throws Exception {
        assertThat(list.isEmpty(), is(true));
        list.addLast("1");
        assertThat(list.isEmpty(), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testContaines() throws Exception {
        assertThat(list.contains(null), is(not(true)));
        assertThat(list.contains("1"), is(not(true)));
        list.addLast("1");
        assertThat(list.contains("1"), is(true));
        assertThat(list.contains("2"), is(not(true)));
        assertThat(list.contains(null), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        list.addLast(null);
        list.addLast("1");
        list.addLast("2");
        assertThat(list.remove("3"), is(not(true)));
        assertThat(list.remove("1"), is(true));
        assertThat(list.remove(null), is(true));
        list.clear();
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        list.remove(1);
        assertThat(list.get(0), is("1"));
        assertThat(list.get(1), is("3"));

        list.clear();
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        final SLinkedList<String>.Entry e = list.getEntry(1);
        e.remove();
        assertThat(e.getNext().getElement(), is("3"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testClear() throws Exception {
        list.addLast(null);
        list.addLast("1");
        list.addLast("2");
        list.clear();
        assertThat(list.size(), is(0));
        assertThat(list.getFirstEntry(), is(nullValue()));
        assertThat(list.getLastEntry(), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetEntry() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        assertThat(list.getEntry(0).getElement(), is("1"));
        assertThat(list.getEntry(1).getElement(), is("2"));
        assertThat(list.getEntry(2).getElement(), is("3"));
        try {
            list.getEntry(-1);
            fail();
        } catch (final ClIndexOutOfBoundsException ex) {
            System.out.println(ex);
        }
        try {
            list.getEntry(3);
            fail();
        } catch (final ClIndexOutOfBoundsException ex) {
            System.out.println(ex);
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        assertThat(list.get(0), is("1"));
        assertThat(list.get(1), is("2"));
        assertThat(list.get(2), is("3"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSerialize() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        assertThat(SerializeUtil.serialize(list), is(notNullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSet() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        list.set(1, "4");
        assertThat(list.get(1), is("4"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testEntry() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        SLinkedList<String>.Entry e = list.getFirstEntry();
        assertThat(e.getPrevious(), is(nullValue()));
        assertThat(e.getNext().getElement(), is("2"));
        e = list.getLastEntry();
        assertThat(e.getNext(), is(nullValue()));
        assertThat(e.getPrevious().getElement(), is("2"));
        list.getEntry(1).remove();
        assertThat(list.getFirst(), is("1"));
        assertThat(list.getLast(), is("3"));
        list.getLastEntry().remove();
        assertThat(list.getLast(), is("1"));
        list.getLastEntry().remove();
        assertThat(list.size(), is(0));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAdd() throws Exception {
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        list.add(1, "4");
        assertThat(list.get(1), is("4"));
        assertThat(list.get(2), is("2"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIndexOf() throws Exception {
        list.addLast(null);
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        assertThat(list.indexOf(null), is(0));
        assertThat(list.indexOf("1"), is(1));
        assertThat(list.indexOf("2"), is(2));
        assertThat(list.indexOf("3"), is(3));
        assertThat(list.indexOf("4"), is(-1));
    }

}
