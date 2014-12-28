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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.MethodDesc;
import org.codelibs.core.exception.MethodNotStaticRuntimeException;
import org.junit.Test;

/**
 * @author koichik
 *
 */
public class MethodDescTest {

    /**
     * @throws Exception
     */
    @Test
    public void testFoo() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final MethodDesc foo = beanDesc.getMethodDesc("foo");
        assertThat(foo, is(notNullValue()));
        assertThat(foo.getBeanDesc(), is(sameInstance(beanDesc)));
        assertThat(foo.getMethod(), is(MyBean.class.getMethod("foo")));
        assertThat(foo.getMethodName(), is("foo"));
        assertThat(foo.getParameterTypes().length, is(0));
        assertThat(foo.isPublic(), is(true));
        assertThat(foo.isStatic(), is(not(true)));
        assertThat(foo.isFinal(), is(not(true)));
        assertThat(foo.isAbstract(), is(not(true)));
        assertThat(foo.invoke(new MyBean()), is((Object) "hoge"));
    }

    /**
     * @throws Exception
     */
    @Test(expected = MethodNotStaticRuntimeException.class)
    public void testFoo_InvokeStatic() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final MethodDesc foo = beanDesc.getMethodDesc("foo");
        assertThat(foo.getParameterTypes().length, is(0));
        assertThat(foo.isStatic(), is(not(true)));
        foo.invokeStatic();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testBar() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final MethodDesc bar = beanDesc.getMethodDesc("bar", String.class);
        assertThat(bar, is(notNullValue()));
        assertThat(bar.getBeanDesc(), is(sameInstance(beanDesc)));
        assertThat(bar.getMethod(),
                is(MyBean.class.getDeclaredMethod("bar", String.class)));
        assertThat(bar.getParameterTypes().length, is(1));
        assertThat(bar.getMethodName(), is("bar"));
        assertThat(bar.isPublic(), is(true));
        assertThat(bar.isStatic(), is(true));
        assertThat(bar.isFinal(), is(not(true)));
        assertThat(bar.isAbstract(), is(not(true)));
        assertThat(bar.invokeStatic("moge"), is((Object) "moge"));
    }

    /**
     */
    public static class MyBean {
        /**
         * @return String
         */
        public String foo() {
            return "hoge";
        }

        /**
         * @param arg
         * @return String
         */
        public static String bar(final String arg) {
            return arg;
        }
    }

}
