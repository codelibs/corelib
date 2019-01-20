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
package org.codelibs.core.lang;

import static org.codelibs.core.TestUtil.sameClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.codelibs.core.exception.ClIllegalArgumentException;
import org.junit.Test;

/**
 * @author y-komori
 *
 */
public class FieldUtilTest {

    /** */
    public Object objectField;

    /** */
    public int intField;

    /** */
    public String stringField;

    /** */
    public static final int INT_DATA = 987654321;

    /** */
    public static final String STRING_DATA = "Hello World!";

    /**
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        final Field field = getClass().getField("objectField");
        final Integer testData = new Integer(123);
        FieldUtil.set(field, this, testData);
        assertThat((Integer) FieldUtil.get(field, this), is(testData));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetIntField() throws Exception {
        final Field field = getClass().getField("intField");
        final int testData = 1234567890;
        FieldUtil.set(field, this, new Integer(testData));
        assertThat(FieldUtil.getInt(field, this), is(testData));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetIntFieldObject() throws Exception {
        final Field field = getClass().getField("INT_DATA");
        assertThat(FieldUtil.getInt(field), is(INT_DATA));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetStringField() throws Exception {
        final Field field = getClass().getField("stringField");
        final String testData = "Hello World!";
        FieldUtil.set(field, this, testData);
        assertThat(FieldUtil.getString(field, this), is(testData));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetStringFieldObject() throws Exception {
        final Field field = getClass().getField("STRING_DATA");
        assertThat(FieldUtil.getString(field), is(STRING_DATA));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ClIllegalArgumentException.class)
    public void testSet() throws Exception {
        final Field field = getClass().getField("intField");
        FieldUtil.set(field, this, "abc");
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetElementType_Rawtype() throws Exception {
        assertThat(FieldUtil.getElementTypeOfCollection(Baz.class.getField("collectionOfRawtype")), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    public void testGetElementTypeOfCollection() throws Exception {
        assertEquals(String.class, FieldUtil.getElementTypeOfCollection(Baz.class.getField("collectionOfString")));
    }

    /**
     * @throws Exception
     */
    public void testGetKeyTypeOfMap() throws Exception {
        final Field f = ClassUtil.getField(Baz.class, "map");
        assertThat(FieldUtil.getKeyTypeOfMap(f), is(sameClass(String.class)));
        assertThat(FieldUtil.getValueTypeOfMap(f), is(sameClass(Integer.class)));
    }

    /**
     *
     */
    public static class Baz {

        /** */
        @SuppressWarnings("rawtypes")
        public Collection collectionOfRawtype;

        /** */
        public Collection<String> collectionOfString;

        /** */
        public Map<String, Integer> map;

    }

}
