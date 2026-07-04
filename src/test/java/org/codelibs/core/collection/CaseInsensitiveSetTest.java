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

import java.util.Set;

import org.codelibs.core.io.SerializeUtil;
import org.junit.Test;

/**
 * @author higa
 */
public class CaseInsensitiveSetTest {

    /**
     * @throws Exception
     */
    @Test
    public void testContains() throws Exception {
        final Set<String> set = new CaseInsensitiveSet();
        set.add("one");
        assertThat(set.contains("ONE"), is(true));
    }

    /**
     * The set must survive serialization even though the backing map is {@code transient}.
     *
     * @throws Exception
     */
    @Test
    public void testSerialize() throws Exception {
        final CaseInsensitiveSet set = new CaseInsensitiveSet();
        set.add("one");
        set.add("two");
        final CaseInsensitiveSet copy = (CaseInsensitiveSet) SerializeUtil.serialize(set);
        assertThat(copy.size(), is(2));
        assertThat(copy.contains("ONE"), is(true));
        assertThat(copy.contains("Two"), is(true));
        assertThat(copy.contains("three"), is(not(true)));
    }

}
