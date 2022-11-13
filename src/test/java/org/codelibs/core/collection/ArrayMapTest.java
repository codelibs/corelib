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
package org.codelibs.core.collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.codelibs.core.exception.ClIllegalStateException;
import org.codelibs.core.exception.ClIndexOutOfBoundsException;
import org.codelibs.core.io.SerializeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author higa
 *
 */
public class ArrayMapTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    ArrayMap<String, String> map;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        map = new ArrayMap<String, String>();
        map.put(null, null);
        map.put("1", "test");
        map.put("2", "test2");
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        map = null;
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSize() throws Exception {
        assertThat(map.size(), equalTo(3));
        map.put("3", "test3");
        assertThat(map.size(), equalTo(4));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsEmpty() throws Exception {
        assertThat(map.isEmpty(), is(not(true)));
        map.clear();
        assertThat(map.isEmpty(), is(true));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testContainsValue() throws Exception {
        assertThat(map.containsValue("test2"), is(true));
        assertThat(map.containsValue("test3"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testContainsKey() throws Exception {
        assertThat(map.containsKey("2"), is(true));
        assertThat(map.containsKey("3"), is(not(true)));
        map.put("3", null);
        assertThat(map.containsKey("3"), is(true));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIndexOf() throws Exception {
        assertThat(map.indexOf("test"), is(1));
        assertThat(map.indexOf(null), is(0));
        assertThat(map.indexOf("test3"), is(-1));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        assertThat(map.get("1"), is("test"));
        assertThat(map.get(null), is(nullValue()));
        assertThat(map.get("test3"), is(nullValue()));
        assertThat(map.getAt(0), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPut() throws Exception {
        assertThat(map.put("1", "test3"), is("test"));
        assertThat(map.get("1"), is("test3"));
        assertThat(map.getAt(1), is("test3"));
        map.put(null, "test4");
        map.put(null, "test5");
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        assertThat(map.remove("1"), is("test"));
        assertThat(map.size(), is(2));
        assertThat(map.remove("dummy"), is(nullValue()));
        assertThat(map.remove(0), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove2() throws Exception {
        Map<String, String> m = new ArrayMap<String, String>();
        m.put("1", "d");
        m.remove("1");
        assertThat(m.containsKey("1"), is(not(true)));
        m.put("1", "d");
        m.remove("1");
        assertThat(m.containsKey("1"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove3() throws Exception {
        Map<MyKey, String> m = new ArrayMap<MyKey, String>();
        m.put(new MyKey("1"), "d");
        m.put(new MyKey("2"), "d");
        m.remove(new MyKey("1"));
        assertThat(m.containsKey(new MyKey("1")), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove4() throws Exception {
        ArrayMap<String, String> m = new ArrayMap<String, String>();
        m.put("1", "d");
        m.put("2", "d");
        System.out.println("remove before:" + m);
        m.remove("2");
        System.out.println("remove after:" + m);
        assertThat(m.containsKey("2"), is(not(true)));
        assertThat(m.containsKey("1"), is(true));
        assertThat(m.get("1"), is("d"));
        assertThat(m.get("2"), is(nullValue()));
        assertThat(m.getAt(0), is("d"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPutAll() throws Exception {
        Map<String, String> m = new HashMap<String, String>();
        m.put("3", "test3");
        m.put("4", "test4");
        map.putAll(m);
        assertThat(map.get("3"), is("test3"));
        assertThat(map.get("4"), is("test4"));
        assertThat(map.size(), is(5));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testEqaulas() throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, String> copy = (ArrayMap<String, String>) map.clone();
        assertThat(map.equals(copy), is(true));
        assertThat(map.equals(null), is(not(true)));
        map.put("3", "test3");
        assertThat(map.equals(copy), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToString() throws Exception {
        assertThat(map.toString(), is(notNullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testClear() throws Exception {
        map.clear();
        assertThat(map.size(), is(0));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testEntrySet() throws Exception {
        Iterator<Map.Entry<String, String>> i = map.entrySet().iterator();
        assertThat(i.next().getKey(), is(nullValue()));
        assertThat(i.next().getKey(), is("1"));
        assertThat(i.next().getKey(), is("2"));
    }

    /**
     * Test method for {@link org.seasar.util.collection.ArrayIterator#remove()}
     * .
     */
    @Test
    public void testArrayIteratorRemoveException() {
        exception.expect(ClIllegalStateException.class);
        exception.expectMessage(is("last == -1"));
        ArrayMap<String, String> m = new ArrayMap<String, String>();
        Iterator<Map.Entry<String, String>> i = m.entrySet().iterator();
        i.remove();
    }

    /**
     *
     */
    @Test
    public void testArrayMapIteratorNextException() {
        exception.expect(NoSuchElementException.class);
        exception.expectMessage(is("current=1"));
        ArrayMap<String, String> m = new ArrayMap<String, String>(1);
        Iterator<Map.Entry<String, String>> i = m.entrySet().iterator();
        i.next();
        i.next();
    }

    /**
     *
     */
    @Test
    public void testArrayMapGetEntryException() {
        exception.expect(ClIndexOutOfBoundsException.class);
        exception.expectMessage(is("Index:1, Size:0"));
        ArrayMap<String, String> m = new ArrayMap<String, String>(1);
        m.getEntryAt(1);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSerialize() throws Exception {
        @SuppressWarnings("unchecked")
        ArrayMap<String, String> copy = (ArrayMap<String, String>) SerializeUtil.serialize(map);
        assertThat(copy.getAt(0), is(nullValue()));
        assertThat(copy.getAt(1), is("test"));
        assertThat(copy.getAt(2), is("test2"));
        assertThat(map.equals(copy), is(true));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPerformance() throws Exception {
        int num = 100000;
        Map<String, Object> hmap = new HashMap<String, Object>();
        Map<String, Object> amap = new ArrayMap<String, Object>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            hmap.put(String.valueOf(i), null);
        }
        System.out.println("HashMap.put:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            amap.put(String.valueOf(i), null);
        }
        System.out.println("ArrayMap.put:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            hmap.get(String.valueOf(i));
        }
        System.out.println("HashMap.get:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            amap.get(String.valueOf(i));
        }
        System.out.println("ArrayMap.get:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (Iterator<Map.Entry<String, Object>> i = hmap.entrySet().iterator(); i.hasNext();) {
            i.next();
        }
        System.out.println("HashMap iteration:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (Iterator<Map.Entry<String, Object>> i = amap.entrySet().iterator(); i.hasNext();) {
            i.next();
        }
        System.out.println("ArrayMap iteration:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        SerializeUtil.serialize(hmap);
        System.out.println("HashMap serialize:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        SerializeUtil.serialize(amap);
        System.out.println("ArrayMap serialize:" + (System.currentTimeMillis() - start));
    }

    private static class MyKey {
        Object _key;

        MyKey(Object key) {
            _key = key;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || !(o instanceof MyKey)) {
                return false;
            }
            return _key.equals(((MyKey) o)._key);
        }
    }

}
