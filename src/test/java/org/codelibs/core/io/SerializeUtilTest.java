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
package org.codelibs.core.io;

import java.io.ObjectInputFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codelibs.core.exception.IORuntimeException;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class SerializeUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testSerialize() throws Exception {
        final String[] a = new String[] { "1", "2" };
        final String[] b = (String[]) SerializeUtil.serialize(a);
        assertEquals("1", b.length, a.length);
        assertEquals("2", "1", b[0]);
        assertEquals("3", "2", b[1]);
    }

    /**
     * @throws Exception
     */
    public void testObjectAndBinary() throws Exception {
        final String o = "hoge";
        final byte[] binary = SerializeUtil.fromObjectToBinary(o);
        assertEquals(o, SerializeUtil.fromBinaryToObject(binary));
    }

    /**
     * Test default filter allows common safe classes
     *
     * @throws Exception
     */
    public void testFromBinaryToObject_DefaultFilter_AllowsSafeClasses() throws Exception {
        // Test String
        final String str = "test string";
        byte[] binary = SerializeUtil.fromObjectToBinary(str);
        assertEquals(str, SerializeUtil.fromBinaryToObject(binary));

        // Test Integer
        final Integer num = 42;
        binary = SerializeUtil.fromObjectToBinary(num);
        assertEquals(num, SerializeUtil.fromBinaryToObject(binary));

        // Test String array
        final String[] arr = new String[] { "a", "b", "c" };
        binary = SerializeUtil.fromObjectToBinary(arr);
        final String[] result = (String[]) SerializeUtil.fromBinaryToObject(binary);
        assertEquals(arr.length, result.length);
        assertEquals(arr[0], result[0]);

        // Test ArrayList
        final List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        binary = SerializeUtil.fromObjectToBinary(list);
        @SuppressWarnings("unchecked")
        final List<String> resultList = (List<String>) SerializeUtil.fromBinaryToObject(binary);
        assertEquals(list.size(), resultList.size());
        assertEquals(list.get(0), resultList.get(0));

        // Test HashMap
        final Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        binary = SerializeUtil.fromObjectToBinary(map);
        @SuppressWarnings("unchecked")
        final Map<String, Integer> resultMap = (Map<String, Integer>) SerializeUtil.fromBinaryToObject(binary);
        assertEquals(map.size(), resultMap.size());
        assertEquals(map.get("one"), resultMap.get("one"));
    }

    /**
     * Test custom filter functionality
     *
     * @throws Exception
     */
    public void testFromBinaryToObject_CustomFilter() throws Exception {
        final Set<String> allowedPatterns = new HashSet<>();
        allowedPatterns.add("java.lang.*");
        allowedPatterns.add("java.util.*");

        final ObjectInputFilter customFilter = SerializeUtil.createCustomFilter(allowedPatterns);

        // Test allowed class
        final String str = "test";
        final byte[] binary = SerializeUtil.fromObjectToBinary(str);
        final Object result = SerializeUtil.fromBinaryToObject(binary, customFilter);
        assertEquals(str, result);
    }

    /**
     * Test permissive filter allows all classes
     *
     * @throws Exception
     */
    public void testFromBinaryToObject_PermissiveFilter() throws Exception {
        final ObjectInputFilter permissiveFilter = SerializeUtil.createPermissiveFilter();

        // Create a custom class instance
        final TestSerializableClass obj = new TestSerializableClass("test", 123);
        final byte[] binary = SerializeUtil.fromObjectToBinary(obj);

        // Should work with permissive filter
        final TestSerializableClass result = (TestSerializableClass) SerializeUtil.fromBinaryToObject(binary, permissiveFilter);
        assertEquals(obj.name, result.name);
        assertEquals(obj.value, result.value);
    }

    /**
     * Test null filter disables filtering
     *
     * @throws Exception
     */
    public void testFromBinaryToObject_NullFilter() throws Exception {
        final String str = "test";
        final byte[] binary = SerializeUtil.fromObjectToBinary(str);
        final Object result = SerializeUtil.fromBinaryToObject(binary, null);
        assertEquals(str, result);
    }

    /**
     * Test that default filter allows org.codelibs classes
     *
     * @throws Exception
     */
    public void testFromBinaryToObject_DefaultFilter_AllowsCodelibsClasses() throws Exception {
        // The default filter should allow org.codelibs.* classes
        final TestSerializableClass obj = new TestSerializableClass("test", 456);
        final byte[] binary = SerializeUtil.fromObjectToBinary(obj);

        // Should work because TestSerializableClass is in org.codelibs package
        final TestSerializableClass result = (TestSerializableClass) SerializeUtil.fromBinaryToObject(binary);
        assertEquals(obj.name, result.name);
        assertEquals(obj.value, result.value);
    }

    /**
     * Test createCustomFilter with null throws exception
     */
    public void testCreateCustomFilter_NullPatterns() {
        try {
            SerializeUtil.createCustomFilter(null);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Test serialization with empty array
     *
     * @throws Exception
     */
    public void testFromBinaryToObject_EmptyArray() throws Exception {
        final String[] emptyArray = new String[0];
        final byte[] binary = SerializeUtil.fromObjectToBinary(emptyArray);
        final String[] result = (String[]) SerializeUtil.fromBinaryToObject(binary);
        assertEquals(0, result.length);
    }

    /**
     * Test serialization with null values in collection
     *
     * @throws Exception
     */
    public void testFromBinaryToObject_CollectionWithNulls() throws Exception {
        final List<String> list = new ArrayList<>();
        list.add("first");
        list.add(null);
        list.add("third");
        final byte[] binary = SerializeUtil.fromObjectToBinary(list);
        @SuppressWarnings("unchecked")
        final List<String> result = (List<String>) SerializeUtil.fromBinaryToObject(binary);
        assertEquals(3, result.size());
        assertEquals("first", result.get(0));
        assertNull(result.get(1));
        assertEquals("third", result.get(2));
    }

    /**
     * Test helper class for serialization tests
     */
    public static class TestSerializableClass implements Serializable {
        private static final long serialVersionUID = 1L;

        public String name;
        public int value;

        public TestSerializableClass(final String name, final int value) {
            this.name = name;
            this.value = value;
        }
    }
}
