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
package org.codelibs.core.beans.impl;

import static org.codelibs.core.TestUtil.sameClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.FieldDesc;
import org.codelibs.core.beans.MethodDesc;
import org.codelibs.core.beans.ParameterizedClassDesc;
import org.codelibs.core.beans.PropertyDesc;
import org.codelibs.core.beans.factory.BeanDescFactory;
import org.codelibs.core.exception.MethodNotFoundRuntimeException;
import org.junit.Test;

/**
 * @author higa
 * @author manhole
 */
public class BeanDescImplTest {

    /**
     * @throws Exception
     */
    @Test
    public void testPropertyDesc() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        assertThat(beanDesc.getPropertyDescSize(), is(5));
        PropertyDesc propDesc = beanDesc.getPropertyDesc("aaa");
        assertThat(propDesc.getPropertyName(), is("aaa"));
        assertThat(propDesc.getPropertyType(), is(sameClass(String.class)));
        assertThat(propDesc.getReadMethod(), is(notNullValue()));
        assertThat(propDesc.getWriteMethod(), is(nullValue()));
        assertThat(propDesc.getField(), is(notNullValue()));

        propDesc = beanDesc.getPropertyDesc("CCC");
        assertThat(propDesc.getPropertyName(), is("CCC"));
        assertThat(propDesc.getPropertyType(), is(sameClass(boolean.class)));
        assertThat(propDesc.getReadMethod(), is(notNullValue()));
        assertThat(propDesc.getWriteMethod(), is(nullValue()));

        propDesc = beanDesc.getPropertyDesc("eee");
        assertThat(propDesc.getPropertyName(), is("eee"));
        assertThat(propDesc.getPropertyType(), is(sameClass(String.class)));
        assertThat(propDesc.getReadMethod(), is(notNullValue()));
        assertThat(propDesc.getWriteMethod(), is(notNullValue()));

        propDesc = beanDesc.getPropertyDesc("fff");
        assertThat(propDesc.getPropertyName(), is("fff"));
        assertThat(propDesc.getPropertyType(), is(sameClass(Boolean.class)));

        assertThat(beanDesc.hasPropertyDesc("hhh"), is(not(true)));
        assertThat(beanDesc.hasPropertyDesc("iii"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testNewInstance() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(Integer.class);
        assertThat((Integer) beanDesc.newInstance(10), is(10));
        assertThat((Integer) beanDesc.newInstance("10"), is(10));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testNewInstance2() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(Integer.class);
        assertThat((Integer) beanDesc.newInstance(new BigDecimal(10)), is(10));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetFieldDescs() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        assertThat(beanDesc.hasFieldDesc("HOGE"), is(true));
        final FieldDesc fieldDesc = beanDesc.getFieldDesc("HOGE");
        assertThat(fieldDesc.getFieldName(), is("HOGE"));
        assertThat(beanDesc.hasFieldDesc("aaa"), is(true));
        assertThat(beanDesc.hasFieldDesc("aaA"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasMethodDesc() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        assertThat(beanDesc.hasMethodDesc("getAaa"), is(true));
        assertThat(beanDesc.hasMethodDesc("getaaa"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test(expected = MethodNotFoundRuntimeException.class)
    public void testGetMethodDesc() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final MethodDesc methodDesc = beanDesc.getMethodDesc("getAaa");
        assertThat(methodDesc, is(notNullValue()));
        assertThat(methodDesc.getMethodName(), is("getAaa"));
        beanDesc.getMethodDesc("getaaa");
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetMethodNoException() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        MethodDesc methodDesc = beanDesc.getMethodDescNoException("getAaa");
        assertThat(methodDesc, is(notNullValue()));
        assertThat(methodDesc.getMethodName(), is("getAaa"));
        methodDesc = beanDesc.getMethodDescNoException("getaaa");
        assertThat(methodDesc, is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetMethodNames() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(getClass());
        final String[] names = beanDesc.getMethodNames();
        for (final String name : names) {
            System.out.println(name);
        }
        assertThat(names.length > 0, is(true));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testInvalidProperty() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean2.class);
        assertThat(beanDesc.hasPropertyDesc("aaa"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddFields() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final FieldDesc eee = beanDesc.getFieldDesc("eee");
        assertThat(eee.getField().isAccessible(), is(true));
        final PropertyDesc pd = beanDesc.getPropertyDesc("ggg");
        assertThat(pd, is(notNullValue()));
        assertThat(pd.getPropertyName(), is("ggg"));
        assertThat(pd.getPropertyType(), is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    public void testFieldType() throws Exception {
        BeanDesc bd = BeanDescFactory.getBeanDesc(Hoge.class);
        PropertyDesc pd = bd.getPropertyDesc("foo");
        assertThat(pd.isParameterized(), is(true));
        assertThat(pd.getElementClassOfCollection(), is(sameClass(String.class)));

        ParameterizedClassDesc pcd = pd.getParameterizedClassDesc();
        assertThat(pcd.getRawClass(), is(sameClass(List.class)));
        assertThat(pcd.getArguments().length, is(1));
        assertThat(pcd.getArguments()[0].getRawClass(), is(sameClass(String.class)));

        pd = bd.getPropertyDesc("hoge");
        assertThat(pd.isParameterized(), is(true));
        assertThat(pd.getElementClassOfCollection(), is(sameClass(Object.class)));

        pcd = pd.getParameterizedClassDesc();
        assertThat(pcd.getRawClass(), is(sameClass(List.class)));
        assertThat(pcd.getArguments().length, is(1));
        assertThat(pcd.getArguments()[0].getRawClass(), is(sameClass(Object.class)));

        bd = BeanDescFactory.getBeanDesc(Bar.class);
        pd = bd.getPropertyDesc("list");
        assertThat(pd.isParameterized(), is(true));
        assertThat(pd.getElementClassOfCollection(), is(sameClass(String.class)));

        pcd = pd.getParameterizedClassDesc();
        assertThat(pcd.getRawClass(), is(sameClass(List.class)));
        assertThat(pcd.getArguments().length, is(1));
        assertThat(pcd.getArguments()[0].getRawClass(), is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    public void testGetter() throws Exception {
        final BeanDesc bd = BeanDescFactory.getBeanDesc(Hoge.class);
        PropertyDesc pd = bd.getPropertyDesc("bar");
        assertThat(pd.isParameterized(), is(true));
        assertThat(pd.getElementClassOfCollection(), is(sameClass(Integer.class)));

        ParameterizedClassDesc pcd = pd.getParameterizedClassDesc();
        assertThat(pcd.getRawClass(), is(sameClass(Set.class)));
        assertThat(pcd.getArguments().length, is(1));
        assertThat(pcd.getArguments()[0].getRawClass(), is(sameClass(Integer.class)));

        pd = bd.getPropertyDesc("fuga");
        assertThat(pd.isParameterized(), is(true));
        assertThat(pd.getElementClassOfCollection(), is(sameClass(Enum.class)));

        pcd = pd.getParameterizedClassDesc();
        assertThat(pcd.getRawClass(), is(sameClass(Set.class)));
        assertThat(pcd.getArguments().length, is(1));
        assertThat(pcd.getArguments()[0].getRawClass(), is(sameClass(Enum.class)));
    }

    /**
     * @throws Exception
     */
    public void testSetter() throws Exception {
        final BeanDesc bd = BeanDescFactory.getBeanDesc(Hoge.class);
        PropertyDesc pd = bd.getPropertyDesc("baz");
        assertThat(pd.isParameterized(), is(true));
        assertThat(pd.getKeyClassOfMap(), is(sameClass(String.class)));
        assertThat(pd.getValueClassOfMap(), is(sameClass(Date.class)));

        ParameterizedClassDesc pcd = pd.getParameterizedClassDesc();
        assertThat(pcd.getRawClass(), is(sameClass(Map.class)));
        assertThat(pcd.getArguments().length, is(2));
        assertThat(pcd.getArguments()[0].getRawClass(), is(sameClass(String.class)));
        assertThat(pcd.getArguments()[1].getRawClass(), is(sameClass(Date.class)));

        pd = bd.getPropertyDesc("hege");
        assertThat(pd.isParameterized(), is(true));
        assertThat(pd.getKeyClassOfMap(), is(sameClass(String.class)));
        assertThat(pd.getValueClassOfMap(), is(sameClass(Number.class)));

        pcd = pd.getParameterizedClassDesc();
        assertThat(pcd.getRawClass(), is(sameClass(Map.class)));
        assertThat(pcd.getArguments().length, is(2));
        assertThat(pcd.getArguments()[0].getRawClass(), is(sameClass(String.class)));
        assertThat(pcd.getArguments()[1].getRawClass(), is(sameClass(Number.class)));
    }

    /**
     *
     */
    public static interface MyInterface {
        /**
         *
         */
        String HOGE = "hoge";
    }

    /**
     *
     */
    public static interface MyInterface2 extends MyInterface {
        /**
         *
         */
        String HOGE = "hoge2";
    }

    /**
     *
     */
    public static class MyBean implements MyInterface2 {

        private String aaa;

        private String eee;

        /**
         *
         */
        public String ggg;

        /**
         * @return String
         */
        public String getAaa() {
            return aaa;
        }

        /**
         * @param a
         * @return String
         */
        public String getBbb(final Object a) {
            return null;
        }

        /**
         * @return boolean
         */
        public boolean isCCC() {
            return true;
        }

        /**
         * @return Object
         */
        public Object isDdd() {
            return null;
        }

        /**
         * @return String
         */
        public String getEee() {
            return eee;
        }

        /**
         * @param eee
         */
        public void setEee(final String eee) {
            this.eee = eee;
        }

        /**
         * @return Boolean
         */
        public Boolean isFff() {
            return null;
        }

        /**
         * @param hhh
         * @return MyBean
         */
        public MyBean setHhh(final String hhh) {
            return this;
        }

        /**
         *
         */
        public void getIii() {
        }

        /**
         * @param arg1
         * @param arg2
         * @return Number
         */
        public Number add(final Number arg1, final Number arg2) {
            return new Integer(3);
        }

        /**
         * @param arg1
         * @param arg2
         * @return int
         */
        public int add2(final int arg1, final int arg2) {
            return arg1 + arg2;
        }

        /**
         * @param arg
         * @return Integer
         */
        public Integer echo(final Integer arg) {
            return arg;
        }

        /**
         *
         */
        public void throwException() {
            throw new IllegalStateException("hoge");
        }
    }

    /**
     *
     */
    public class MyBean2 {
        /**
         *
         */
        public MyBean2() {
        }

        /**
         * @param num
         * @param text
         * @param bean1
         * @param bean2
         */
        public MyBean2(final int num, final String text, final MyBean bean1, final MyBean2 bean2) {
        }

        /**
         * @param i
         */
        public void setAaa(final int i) {
        }

        /**
         * @param s
         */
        public void setAaa(final String s) {
        }
    }

    /** */
    public static class Hoge {

        /** */
        public List<String> foo;

        /**
         * @return Set
         */
        public Set<Integer> getBar() {
            return null;
        }

        /**
         * @param date
         */
        public void setBaz(final Map<String, Date> date) {
        }

        /** */
        public List<?> hoge;

        /**
         * @return Set
         */
        public Set<? extends Enum<?>> getFuga() {
            return null;
        }

        /**
         * @param date
         */
        public void setHege(final Map<? extends String, ? extends Number> date) {
        }

    }

    /**
     * @param <T>
     */
    public static class Foo<T> {

        /** */
        public List<T> list;

    }

    /** */
    public static class Bar extends Foo<String> {
    }

}
