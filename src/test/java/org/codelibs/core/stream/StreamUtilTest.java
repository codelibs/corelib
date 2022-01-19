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
package org.codelibs.core.stream;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class StreamUtilTest extends TestCase {

    public void test_ofValues() {
        String[] values = { "value1", "value2" };
        StreamUtil.stream(values[0], values[1]).of(s -> {
            Object[] array = s.toArray();
            for (int i = 0; i < 2; i++) {
                assertEquals(values[i], array[i]);
            }
        });
    }

    public void test_ofNull() {
        assertEquals(0, (int) StreamUtil.stream().get(s -> s.toArray().length));
        Object[] o = {};
        assertEquals(0, (int) StreamUtil.stream(o).get(s -> s.toArray().length));
        Map<Object, Object> map = new HashMap<Object, Object>();
        assertEquals(0, (int) StreamUtil.stream(map).get(s -> s.toArray().length));
    }

    public void test_ofMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        StreamUtil.stream(map).of(s -> s.forEach(m -> assertEquals(map.get(m.getKey()), m.getValue())));
    }

}
