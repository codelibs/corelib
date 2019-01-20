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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

/**
 * @author koichik
 */
public class ClassLoaderIteratorTest {

    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        final ClassLoader cl1 = new URLClassLoader(new URL[] { new URL("file:/foo") }, null);
        final ClassLoader cl2 = new URLClassLoader(new URL[] { new URL("file:/bar") }, cl1);
        final ClassLoader cl3 = new URLClassLoader(new URL[] { new URL("file:/baz") }, cl2);

        final ClassLoaderIterator it = new ClassLoaderIterator(cl3);
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(sameInstance(cl3)));

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(sameInstance(cl2)));

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(sameInstance(cl1)));

        assertThat(it.hasNext(), is(not(true)));
    }

}
