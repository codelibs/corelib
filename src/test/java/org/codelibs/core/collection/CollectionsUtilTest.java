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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class CollectionsUtilTest {

    /**
     * Test method for
     * {@link org.codelibs.core.collection.CollectionsUtil#isEmpty(java.util.Collection)}
     * .
     */
    @Test
    public void testIsEmptyCollectionOfQ() {
        Collection<String> c = null;
        assertThat(CollectionsUtil.isEmpty(c), is(true));
        c = new ArrayList<String>();
        assertThat(CollectionsUtil.isEmpty(c), is(true));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.collection.CollectionsUtil#isNotEmpty(java.util.Collection)}
     * .
     */
    @Test
    public void testIsNotEmptyCollectionOfQ() {
        final Collection<String> c = new ArrayList<String>();
        c.add("hoge");
        assertThat(CollectionsUtil.isNotEmpty(c), is(true));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.collection.CollectionsUtil#isEmpty(java.util.Map)}
     * .
     */
    @Test
    public void testIsEmptyMapOfQQ() {
        HashMap<String, String> map = null;
        assertThat(CollectionsUtil.isEmpty(map), is(true));
        map = new HashMap<String, String>();
        assertThat(CollectionsUtil.isEmpty(map), is(true));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.collection.CollectionsUtil#isNotEmpty(java.util.Map)}
     * .
     */
    @Test
    public void testIsNotEmptyMapOfQQ() {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        assertThat(CollectionsUtil.isNotEmpty(map), is(true));
    }

}
