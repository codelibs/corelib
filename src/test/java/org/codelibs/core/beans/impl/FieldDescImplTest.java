/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.FieldDesc;
import org.codelibs.core.beans.ParameterizedClassDesc;
import org.codelibs.core.exception.FieldNotStaticRuntimeException;
import org.junit.Test;

/**
 * @author koichik
 */
public class FieldDescImplTest {

    /**
     * @throws Exception
     */
    @Test
    public void testHOGE() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final FieldDesc hoge = beanDesc.getFieldDesc("HOGE");
        assertThat(hoge.getBeanDesc(), is(sameInstance(beanDesc)));
        assertThat(hoge.getField(), is(MyBean.class.getDeclaredField("HOGE")));
        assertThat(hoge.getFieldName(), is("HOGE"));
        assertThat(hoge.getFieldType(), is(sameClass(String.class)));
        assertThat(hoge.isPublic(), is(true));
        assertThat(hoge.isStatic(), is(true));
        assertThat(hoge.isFinal(), is(true));
        assertThat(hoge.isParameterized(), is(not(true)));
        final ParameterizedClassDesc parameterizedClassDesc = hoge
                .getParameterizedClassDesc();
        assertThat(parameterizedClassDesc, is(notNullValue()));
        assertThat(hoge.getParameterizedClassDesc().isParameterizedClass(),
                is(not(true)));
        assertThat(hoge.getStaticFieldValue(), is((Object) "hoge"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAaa() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final FieldDesc aaa = beanDesc.getFieldDesc("aaa");
        assertThat(aaa.getBeanDesc(), is(sameInstance(beanDesc)));
        assertThat(aaa.getField(), is(MyBean.class.getDeclaredField("aaa")));
        assertThat(aaa.getFieldName(), is("aaa"));
        assertThat(aaa.getFieldType(), is(sameClass(Class.class)));
        assertThat(aaa.isPublic(), is(not(true)));
        assertThat(aaa.isStatic(), is(not(true)));
        assertThat(aaa.isFinal(), is(not(true)));
        assertThat(aaa.isParameterized(), is(true));
        final ParameterizedClassDesc parameterizedClassDesc = aaa
                .getParameterizedClassDesc();
        assertThat(parameterizedClassDesc, is(notNullValue()));
        assertThat(parameterizedClassDesc.isParameterizedClass(), is(true));
        assertThat(parameterizedClassDesc.getRawClass(),
                is(sameClass(Class.class)));
        final MyBean myBean = new MyBean();
        aaa.setFieldValue(myBean, String.class);
        assertThat(aaa.getFieldValue(myBean), is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    @Test(expected = FieldNotStaticRuntimeException.class)
    public void testAaa_GetStaticFieldValue() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final FieldDesc aaa = beanDesc.getFieldDesc("aaa");
        aaa.getStaticFieldValue();
    }

    /**
     * @throws Exception
     */
    @Test(expected = FieldNotStaticRuntimeException.class)
    public void testAaa_SetStaticFieldValue() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final FieldDesc aaa = beanDesc.getFieldDesc("aaa");
        aaa.setStaticFieldValue(String.class);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final FieldDesc hoge = beanDesc.getFieldDesc("list");
        assertThat(hoge.getElementClassOfCollection(),
                is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testMap() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final FieldDesc hoge = beanDesc.getFieldDesc("map");
        assertThat(hoge.getKeyClassOfMap(), is(sameClass(String.class)));
        assertThat(hoge.getValueClassOfMap(), is(sameClass(Integer.class)));
    }

    /**
     *
     */
    @SuppressWarnings("unused")
    public static class MyBean {
        /** */
        public static final String HOGE = "hoge";

        private Class<?> aaa;

        private List<String> list;

        private Map<String, Integer> map;
    }

}
