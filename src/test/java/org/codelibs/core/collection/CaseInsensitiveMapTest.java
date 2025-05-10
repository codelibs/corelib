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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author higa
 */
public class CaseInsensitiveMapTest {

    CaseInsensitiveMap<String> map;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        map = new CaseInsensitiveMap<String>();
        map.put("one", "1");
        map.put("two", "2");
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
    public void testContainsKey() throws Exception {
        assertThat(map.containsKey("ONE"), is(true));
        assertThat(map.containsKey("one"), is(true));
        assertThat(map.containsKey("onex"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        assertThat(map.get("ONE"), is("1"));
        assertThat(map.get("One"), is("1"));
        assertThat(map.get("hoge"), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPut() throws Exception {
        assertThat(map.put("One", "11"), is("1"));
        assertThat(map.get("one"), is("11"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        assertThat(map.remove("ONE"), is("1"));
        assertThat(map.size(), is(1));
        assertThat(map.remove("dummy"), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPutAll() throws Exception {
        final Map<String, String> m = new HashMap<String, String>();
        m.put("three", "3");
        m.put("four", "4");
        map.putAll(m);
        assertThat(map.get("THREE"), is("3"));
        assertThat(map.get("FOUR"), is("4"));
        assertThat(map.size(), is(4));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPerformance() throws Exception {
        for (int j = 0; j < 3; ++j) {
            final int num = 100000;
            final Map<String, String> hmap = new HashMap<String, String>();
            final Map<String, String> cimap = new CaseInsensitiveMap<String>();

            long start = System.currentTimeMillis();
            for (int i = 0; i < num; i++) {
                hmap.put("a" + String.valueOf(i), null);
            }
            System.out.println("HashMap.put:" + (System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            for (int i = 0; i < num; i++) {
                cimap.put("a" + String.valueOf(i), null);
            }
            System.out.println("CaseInsensitiveMap.put:" + (System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            for (int i = 0; i < num; i++) {
                hmap.get("a" + String.valueOf(i));
            }
            System.out.println("HashMap.get:" + (System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            for (int i = 0; i < num; i++) {
                cimap.get("a" + String.valueOf(i));
            }
            System.out.println("CaseInsensitiveMap.get:" + (System.currentTimeMillis() - start));
        }
    }

}
