/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
package org.codelibs.core.beans.factory;

import static org.codelibs.core.TestUtil.sameClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codelibs.core.beans.ParameterizedClassDesc;
import org.junit.Test;

/**
 * @author koichik
 */
public class ParameterizedClassDescFactoryTest {

    /**
     * @throws Exception
     */
    @Test
    public void testFieldType() throws Exception {
        final Map<TypeVariable<?>, Type> map = ParameterizedClassDescFactory.getTypeVariables(Hoge.class);
        final Field field = Hoge.class.getField("foo");
        final ParameterizedClassDesc desc = ParameterizedClassDescFactory.createParameterizedClassDesc(field, map);
        assertThat(desc.getRawClass(), is(sameClass(Map.class)));

        final ParameterizedClassDesc[] args = desc.getArguments();
        assertThat(args.length, is(2));

        final ParameterizedClassDesc arg1 = args[0];
        assertThat(arg1.getRawClass(), is(sameClass(String.class)));
        assertThat(arg1.getArguments(), is(nullValue()));

        final ParameterizedClassDesc arg2 = args[1];
        assertThat(arg2.getRawClass(), is(sameClass(Set[].class)));

        final ParameterizedClassDesc[] args2 = arg2.getArguments();
        assertThat(args2.length, is(1));

        final ParameterizedClassDesc arg2_1 = args2[0];
        assertThat(arg2_1.getRawClass(), is(sameClass(Integer.class)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testMethodParameterType() throws Exception {
        final Map<TypeVariable<?>, Type> map = ParameterizedClassDescFactory.getTypeVariables(Hoge.class);
        final Method method = Hoge.class.getMethod("foo", Set.class, Map.class);
        ParameterizedClassDesc desc = ParameterizedClassDescFactory.createParameterizedClassDesc(method, 0, map);
        assertThat(desc.getRawClass(), is(sameClass(Set.class)));
        ParameterizedClassDesc[] args = desc.getArguments();
        assertThat(args.length, is(1));
        assertThat(args[0].getRawClass(), is(sameClass(Integer.class)));

        desc = ParameterizedClassDescFactory.createParameterizedClassDesc(method, 1, map);
        assertThat(desc.getRawClass(), is(sameClass(Map.class)));
        args = desc.getArguments();
        assertThat(args.length, is(2));
        assertThat(args[0].getRawClass(), is(sameClass(String.class)));
        assertThat(args[0].getArguments(), is(nullValue()));
        assertThat(args[1].getRawClass(), is(sameClass(Integer.class)));
        assertThat(args[1].getArguments(), is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testMethodReturnType() throws Exception {
        final Map<TypeVariable<?>, Type> map = ParameterizedClassDescFactory.getTypeVariables(Hoge.class);
        final Method method = Hoge.class.getMethod("foo", Set.class, Map.class);
        final ParameterizedClassDesc desc = ParameterizedClassDescFactory.createParameterizedClassDesc(method, map);
        assertThat(desc.getRawClass(), is(sameClass(List.class)));
        final ParameterizedClassDesc[] args = desc.getArguments();
        assertThat(args.length, is(1));
        assertThat(args[0].getRawClass(), is(sameClass(String.class)));
        assertThat(args[0].getArguments(), is(nullValue()));
    }

    /**
     * @author koichik
     */
    public interface Hoge {

        /** */
        public static Map<String, Set<Integer>[]> foo = null;

        /**
         * @param arg1
         * @param arg2
         * @return List
         */
        List<String> foo(Set<Integer> arg1, Map<String, Integer> arg2);
    }

}
