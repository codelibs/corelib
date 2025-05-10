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
package org.codelibs.core.lang;

import static org.codelibs.core.TestUtil.sameClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.codelibs.core.exception.EmptyArgumentException;
import org.codelibs.core.exception.NoSuchConstructorRuntimeException;
import org.codelibs.core.exception.NoSuchFieldRuntimeException;
import org.junit.Test;

/**
 * @author higa
 */
public class ClassUtilTest {

    /** */
    public static final String HOGE = "hoge";

    /**
     * @throws Exception
     */
    @Test(expected = EmptyArgumentException.class)
    public void testForName_EmptyName() throws Exception {
        ClassUtil.forName("");
    }

    /**
     * @throws Exception
     */
    @Test(expected = EmptyArgumentException.class)
    public void testGetField_EmptyName() throws Exception {
        ClassUtil.getField(getClass(), "");
    }

    /**
     * @throws Exception
     */
    @Test(expected = EmptyArgumentException.class)
    public void testGetMethod_EmptyName() throws Exception {
        ClassUtil.getMethod(getClass(), "");
    }

    /**
     *
     */
    @Test
    public void testGetPrimitiveClass() {
        assertThat(ClassUtil.getPrimitiveClass(Integer.class), is(sameClass(int.class)));
        assertThat(ClassUtil.getPrimitiveClass(String.class), is(nullValue()));
        assertThat(ClassUtil.getPrimitiveClass(Byte.class), is(sameClass(byte.class)));
    }

    /**
     *
     */
    @Test
    public void testGetPrimitiveClassIfWrapper() {
        assertThat(ClassUtil.getPrimitiveClassIfWrapper(Integer.class), is(sameClass(int.class)));
        assertThat(ClassUtil.getPrimitiveClassIfWrapper(String.class), is(sameClass(String.class)));
        assertThat(ClassUtil.getPrimitiveClassIfWrapper(Byte.class), is(sameClass(byte.class)));
    }

    /**
     *
     */
    @Test
    public void testGetWrapperClass() {
        assertThat(ClassUtil.getWrapperClass(int.class), is(sameClass(Integer.class)));
        assertThat(ClassUtil.getWrapperClass(String.class), is(nullValue()));
        assertThat(ClassUtil.getWrapperClass(byte.class), is(sameClass(Byte.class)));
    }

    /**
     *
     */
    @Test
    public void testGetWrapperClassIfWrapper() {
        assertThat(ClassUtil.getWrapperClassIfPrimitive(int.class), is(sameClass(Integer.class)));
        assertThat(ClassUtil.getWrapperClassIfPrimitive(String.class), is(sameClass(String.class)));
        assertThat(ClassUtil.getWrapperClassIfPrimitive(byte.class), is(sameClass(Byte.class)));
    }

    /**
     *
     */
    @Test
    public void testIsAssignableFrom() {
        assertThat(ClassUtil.isAssignableFrom(Number.class, Integer.class), is(true));
        assertThat(ClassUtil.isAssignableFrom(Integer.class, Number.class), is(not(true)));
        assertThat(ClassUtil.isAssignableFrom(int.class, Integer.class), is(true));
    }

    /**
     *
     */
    @Test
    public void testGetPackageName() {
        assertThat(ClassUtil.getPackageName(getClass()), is("org.codelibs.core.lang"));
    }

    /**
     *
     */
    @Test
    public void testGetShortClassName() {
        assertThat(ClassUtil.getShortClassName(getClass().getName()), is("ClassUtilTest"));
    }

    /**
     *
     */
    @Test(expected = NoSuchConstructorRuntimeException.class)
    public void testGetConstructor() {
        ClassUtil.getConstructor(ClassUtilTest.class, Integer.class);
    }

    /**
     *
     */
    @Test(expected = NoSuchFieldRuntimeException.class)
    public void testGetField() {
        ClassUtil.getField(getClass(), "aaa");
    }

    /**
     *
     */
    @Test
    public void testGetSimpleClassName() {
        assertThat(ClassUtil.getSimpleClassName(int.class), is("int"));
        assertThat(ClassUtil.getSimpleClassName(String.class), is("java.lang.String"));
        assertThat(ClassUtil.getSimpleClassName(int[].class), is("int[]"));
        assertThat(ClassUtil.getSimpleClassName(String[][].class), is("java.lang.String[][]"));
    }

    /**
     *
     */
    @Test
    public void testConcatName() {
        assertThat(ClassUtil.concatName("aaa", "bbb"), is("aaa.bbb"));
        assertThat(ClassUtil.concatName("aaa", null), is("aaa"));
        assertThat(ClassUtil.concatName("aaa", ""), is("aaa"));
        assertThat(ClassUtil.concatName(null, "bbb"), is("bbb"));
        assertThat(ClassUtil.concatName("", "bbb"), is("bbb"));
        assertThat(ClassUtil.concatName("", "bbb"), is("bbb"));
        assertThat(ClassUtil.concatName(null, null), is(nullValue()));
        assertThat(ClassUtil.concatName(null, ""), is(nullValue()));
        assertThat(ClassUtil.concatName("", null), is(nullValue()));
        assertThat(ClassUtil.concatName("", ""), is(nullValue()));
    }

    /**
     *
     */
    @Test
    public void testGetResourcePath() {
        assertThat(ClassUtil.getResourcePath(getClass()), is("org/codelibs/core/lang/ClassUtilTest.class"));
    }

    /**
     *
     */
    @Test
    public void testSplitPackageAndShortClassName() {
        String[] ret = ClassUtil.splitPackageAndShortClassName("aaa.Hoge");
        assertThat(ret[0], is("aaa"));
        assertThat(ret[1], is("Hoge"));
        ret = ClassUtil.splitPackageAndShortClassName("Hoge");
        assertThat(ret[0], is(nullValue()));
        assertThat(ret[1], is("Hoge"));
    }

    /**
     *
     */
    @Test
    public void testConvertClass() {
        assertThat(ClassUtil.convertClass("int"), is(sameClass(int.class)));
        assertThat(ClassUtil.convertClass("java.lang.String"), is(sameClass(String.class)));
    }

}
