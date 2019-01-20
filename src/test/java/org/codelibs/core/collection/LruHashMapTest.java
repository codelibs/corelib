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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Test;

/**
 * @author taichi
 */
public class LruHashMapTest {

    /**
     * @throws Exception
     */
    @Test
    public void testAll() throws Exception {
        final LruHashMap<String, String> lru = new LruHashMap<String, String>(3);
        lru.put("aaa", "111");
        lru.put("bbb", "222");
        lru.put("ccc", "333");
        assertThat(lru.get("aaa"), is("111"));
        Iterator<String> i = lru.keySet().iterator();
        assertThat(i.next(), is("bbb"));
        assertThat(i.next(), is("ccc"));
        assertThat(i.next(), is("aaa"));
        lru.put("ddd", "444");
        assertThat(lru.size(), is(3));
        assertThat(lru.get("bbb"), is(nullValue()));
        i = lru.keySet().iterator();
        assertThat(i.next(), is("ccc"));
        assertThat(i.next(), is("aaa"));
        assertThat(i.next(), is("ddd"));
    }

}
