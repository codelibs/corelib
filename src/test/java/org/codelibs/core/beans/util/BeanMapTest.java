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
package org.codelibs.core.beans.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.codelibs.core.exception.IllegalKeyOfBeanMapException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author higa
 */
public class BeanMapTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        final BeanMap map = new BeanMap();
        map.put("aaa", 1);
        map.put("bbb", 2);
        assertThat(map.get("aaa"), is((Object) 1));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGet_NotContains() throws Exception {
        exception.expect(IllegalKeyOfBeanMapException.class);
        exception.expectMessage(is("[ECL0016]key[xxx] is not included in this BeanMap : {aaa=1, bbb=2}."));
        final BeanMap map = new BeanMap();
        map.put("aaa", 1);
        map.put("bbb", 2);
        map.get("xxx");
    }

}
