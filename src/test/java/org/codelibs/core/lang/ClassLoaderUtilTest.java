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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author koichik
 */
public class ClassLoaderUtilTest {

    /**
     * @throws Exception
     */
    @Test
    public void testGetClassLoader() throws Exception {
        assertThat(ClassLoaderUtil.getClassLoader(Object.class), is(sameInstance(ClassLoaderUtil.class.getClassLoader())));

        assertThat(ClassLoaderUtil.getClassLoader(TestCase.class), is(sameInstance(TestCase.class.getClassLoader())));

        final ClassLoader context = Thread.currentThread().getContextClassLoader();
        try {
            final ClassLoader cl = new URLClassLoader(new URL[0], getClass().getClassLoader());
            Thread.currentThread().setContextClassLoader(cl);
            assertThat(ClassLoaderUtil.getClassLoader(TestCase.class), is(sameInstance(cl)));
        } finally {
            Thread.currentThread().setContextClassLoader(context);
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFindLoadedClass() throws Exception {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Class<?> clazz = ClassLoaderUtil.findLoadedClass(loader, getClass().getName());
        assertThat(clazz, is(sameClass(getClass())));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetResources() throws Exception {
        final String name = TestCase.class.getName().replace('.', '/') + ".class";
        final Iterator<URL> itr = ClassLoaderUtil.getResources(this.getClass(), name);
        assertThat(itr, is(notNullValue()));
        final URL url = itr.next();
        assertThat(url, is(notNullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsAncestor() throws Exception {
        final ClassLoader cl1 = new URLClassLoader(new URL[] { new URL("file:/foo") }, null);
        final ClassLoader cl2 = new URLClassLoader(new URL[] { new URL("file:/bar") }, cl1);
        final ClassLoader cl3 = new URLClassLoader(new URL[] { new URL("file:/baz") }, cl2);

        assertThat(ClassLoaderUtil.isAncestor(cl3, cl2), is(true));
        assertThat(ClassLoaderUtil.isAncestor(cl3, cl1), is(true));
        assertThat(ClassLoaderUtil.isAncestor(cl2, cl1), is(true));

        assertThat(ClassLoaderUtil.isAncestor(cl1, cl2), is(not(true)));
        assertThat(ClassLoaderUtil.isAncestor(cl1, cl3), is(not(true)));
    }

}
