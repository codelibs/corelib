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
package org.codelibs.core.lang;

import static org.codelibs.core.TestUtil.sameClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

/**
 * @author koichik
 */
public class GenericsUtilTest {

    /**
     * @throws Exception
     */
    @Test
    public void testClass() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Foo.class);
        assertThat(map, is(notNullValue()));
        assertThat(map.isEmpty(), is(false));
        assertThat(map.get(Foo.class.getTypeParameters()[0]), is(sameClass(Object.class)));
    }

    /**
     * @throws Exception
     */
    public void testGenericMethod() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Fuga.class);
        assertThat(map, is(notNullValue()));
        assertThat(map.isEmpty(), is(true));
        final Method m = Fuga.class.getMethod("getFuga");
        final Class<?> returnClass = GenericsUtil.getActualClass(m.getGenericReturnType(), map);
        assertThat(returnClass, is(sameClass(Object.class)));
    }

    /**
     * @throws Exception
     */
    public void testArray() throws Exception {
        final Method m1 = ArrayType.class.getMethod("arrayOfStringClass");
        final Type t1 = m1.getGenericReturnType();
        final Type t2 = GenericsUtil.getElementTypeOfArray(t1);
        assertThat(GenericsUtil.getRawClass(t2), is(sameClass(Class.class)));
        assertThat(GenericsUtil.getGenericParameter(t2, 0), is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    public void testList() throws Exception {
        final Method m1 = ListType.class.getMethod("listOfString");
        final Type t1 = m1.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t1, List.class), is(true));
        assertThat(GenericsUtil.getElementTypeOfList(t1), is(sameClass(String.class)));

        final Method m2 = ListType.class.getMethod("listOfClass");
        final Type t2 = m2.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t2, List.class), is(true));
        assertThat(GenericsUtil.isTypeOf(GenericsUtil.getElementTypeOfList(t2), Class.class), is(true));

        final Method m3 = ListType.class.getMethod("listOfWildcard");
        final Type t3 = m3.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t3, List.class), is(true));
        assertThat(WildcardType.class.isInstance(GenericsUtil.getElementTypeOfList(t3)), is(true));
    }

    /**
     * @throws Exception
     */
    public void testSet() throws Exception {
        final Method m1 = SetType.class.getMethod("setOfString");
        final Type t1 = m1.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t1, Set.class), is(true));
        assertThat(GenericsUtil.getElementTypeOfSet(t1), is(sameClass(String.class)));

        final Method m2 = SetType.class.getMethod("setOfClass");
        final Type t2 = m2.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t2, Set.class), is(true));
        assertThat(GenericsUtil.isTypeOf(GenericsUtil.getElementTypeOfSet(t2), Class.class), is(true));

        final Method m3 = SetType.class.getMethod("setOfWildcard");
        final Type t3 = m3.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t3, Set.class), is(true));
        assertThat(WildcardType.class.isInstance(GenericsUtil.getElementTypeOfSet(t3)), is(true));
    }

    /**
     * @throws Exception
     */
    public void testMap() throws Exception {
        final Method m1 = MapType.class.getMethod("mapOfStringToObject");
        final Type t1 = m1.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t1, Map.class), is(true));
        assertThat(GenericsUtil.getKeyTypeOfMap(t1), is(sameClass(String.class)));
        assertThat(GenericsUtil.getValueTypeOfMap(t1), is(sameClass(Object.class)));

        final Method m2 = MapType.class.getMethod("mapOfClassToString");
        final Type t2 = m2.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t2, Map.class), is(true));
        assertThat(GenericsUtil.isTypeOf(GenericsUtil.getKeyTypeOfMap(t2), Class.class), is(true));
        assertThat(GenericsUtil.getValueTypeOfMap(t2), is(sameClass(String.class)));

        final Method m3 = MapType.class.getMethod("mapOfWildcard");
        final Type t3 = m3.getGenericReturnType();
        assertThat(GenericsUtil.isTypeOf(t3, Map.class), is(true));
        assertThat(WildcardType.class.isInstance(GenericsUtil.getKeyTypeOfMap(t3)), is(true));
        assertThat(WildcardType.class.isInstance(GenericsUtil.getValueTypeOfMap(t3)), is(true));
    }

    /**
     * @throws Exception
     */
    public void testGetTypeVariableMap() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Hoge.class);
        assertThat(map.size(), is(4));
        final Iterator<Entry<TypeVariable<?>, Type>> it = map.entrySet().iterator();
        Entry<TypeVariable<?>, Type> entry = it.next();
        assertThat(entry.getKey().getName(), is("T1"));
        assertThat(entry.getValue(), is(sameClass(Integer.class)));
        entry = it.next();
        assertThat(entry.getKey().getName(), is("T2"));
        assertThat(entry.getValue(), is(sameClass(Long.class)));
        entry = it.next();
        assertThat(entry.getKey().getName(), is("T1"));
        assertThat(entry.getValue(), is(sameClass(String.class)));
        entry = it.next();
        assertThat(entry.getKey().getName(), is("T2"));
        assertThat(entry.getValue(), is(sameClass(Boolean.class)));
    }

    /**
     * @throws Exception
     */
    public void testGetActualClass() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Hoge.class);

        Method method = Hoge.class.getMethod("foo", Object.class);
        assertThat(GenericsUtil.getActualClass(method.getGenericParameterTypes()[0], map), is(sameClass(Integer.class)));
        assertThat(GenericsUtil.getActualClass(method.getGenericReturnType(), map), is(sameClass(Long.class)));

        method = Hoge.class.getMethod("array");
        assertThat(GenericsUtil.getActualClass(method.getGenericReturnType(), map), is(sameClass(String[].class)));

        method = Hoge.class.getMethod("list");
        assertThat(GenericsUtil.getActualClass(method.getGenericReturnType(), map), is(sameClass(List.class)));

        method = Hoge.class.getMethod("set");
        assertThat(GenericsUtil.getActualClass(method.getGenericReturnType(), map), is(sameClass(Set.class)));

        method = Hoge.class.getMethod("map");
        assertThat(GenericsUtil.getActualClass(method.getGenericReturnType(), map), is(sameClass(Map.class)));
    }

    /**
     * @throws Exception
     */
    public void testGetActualElementClassOfArray() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Hoge.class);
        final Method method = Hoge.class.getMethod("array");
        assertThat(GenericsUtil.getActualElementClassOfArray(method.getGenericReturnType(), map), is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    public void testGetActualElementClassOfList() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Hoge.class);
        final Method method = Hoge.class.getMethod("list");
        assertThat(GenericsUtil.getActualElementClassOfList(method.getGenericReturnType(), map), is(sameClass(Boolean.class)));
    }

    /**
     * @throws Exception
     */
    public void testGetActualElementClassOfSet() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Hoge.class);
        final Method method = Hoge.class.getMethod("set");
        assertThat(GenericsUtil.getActualElementClassOfSet(method.getGenericReturnType(), map), is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    public void testGetActualKeyClassOfMap() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Hoge.class);
        final Method method = Hoge.class.getMethod("map");
        assertThat(GenericsUtil.getActualKeyClassOfMap(method.getGenericReturnType(), map), is(sameClass(String.class)));
    }

    /**
     * @throws Exception
     */
    public void testGetActualValueClassOfMap() throws Exception {
        final Map<TypeVariable<?>, Type> map = GenericsUtil.getTypeVariableMap(Hoge.class);
        final Method method = Hoge.class.getMethod("map");
        assertThat(GenericsUtil.getActualValueClassOfMap(method.getGenericReturnType(), map), is(sameClass(Boolean.class)));
    }

    /**
     *
     */
    public interface ArrayType {

        /**
         * @return Class
         */
        Class<String>[] arrayOfStringClass();
    }

    /**
     *
     */
    public interface ListType {

        /**
         * @return List
         */
        List<String> listOfString();

        /**
         * @return List
         */
        List<Class<?>> listOfClass();

        /**
         * @return List
         */
        List<?> listOfWildcard();
    }

    /**
     *
     */
    public interface SetType {

        /**
         * @return Set
         */
        Set<String> setOfString();

        /**
         * @return Set
         */
        Set<Class<?>> setOfClass();

        /**
         * @return Set
         */
        Set<?> setOfWildcard();
    }

    /**
     *
     */
    public interface MapType {

        /**
         * @return Map
         */
        Map<String, Object> mapOfStringToObject();

        /**
         * @return Map
         */
        Map<Class<?>, String> mapOfClassToString();

        /**
         * @return Map
         */
        Map<?, ?> mapOfWildcard();
    }

    /**
     * @param <T1>
     * @param <T2>
     *
     */
    public interface Foo<T1, T2> {

        /**
         * @param foo
         * @return T2
         */
        T2 foo(T1 foo);
    }

    /**
     *
     */
    public interface Bar extends Foo<Integer, Long> {
    }

    /**
     * @param <T1>
     * @param <T2>
     *
     */
    public interface Baz<T1, T2> {

        /**
         * @return T1
         */
        T1[] array();

        /**
         * @return List
         */
        List<T2> list();

        /**
         * @return Set
         */
        Set<T1> set();

        /**
         * @return Map
         */
        Map<T1, T2> map();
    }

    /**
     *
     */
    public static abstract class Hoge implements Bar, Baz<String, Boolean> {
    }

    /**
     *
     */
    public interface Fuga {
        /**
         * @param <T>
         * @return T
         */
        <T> T getFuga();
    }

}
