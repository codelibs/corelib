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
import static org.junit.Assert.assertThat;

import org.codelibs.core.io.SerializeUtil;
import org.junit.Test;

/**
 * @author shinsuke
 */
public class LruHashSetTest {

    /**
     * @throws Exception
     */
    @Test
    public void testLimit() throws Exception {
        final LruHashSet<String> set = new LruHashSet<>(2);
        set.add("aaa");
        set.add("bbb");
        set.add("ccc");
        assertThat(set.size(), is(2));
        assertThat(set.contains("aaa"), is(not(true)));
        assertThat(set.contains("bbb"), is(true));
        assertThat(set.contains("ccc"), is(true));
    }

    /**
     * The set stores a non-serializable sentinel as the backing map value, so it must
     * serialize the elements (and the limit size) itself rather than the map values.
     *
     * @throws Exception
     */
    @Test
    public void testSerialize() throws Exception {
        final LruHashSet<String> set = new LruHashSet<>(3);
        set.add("aaa");
        set.add("bbb");
        @SuppressWarnings("unchecked")
        final LruHashSet<String> copy = (LruHashSet<String>) SerializeUtil.serialize(set);
        assertThat(copy.size(), is(2));
        assertThat(copy.contains("aaa"), is(true));
        assertThat(copy.contains("bbb"), is(true));
        assertThat(copy.contains("ccc"), is(not(true)));
        // The limit size must survive serialization.
        copy.add("ccc");
        copy.add("ddd");
        assertThat(copy.size(), is(3));
    }

}
