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
package org.codelibs.core.misc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @author koichik
 *
 */
public class DisposableUtilTest {

    private int count;

    private String names = "";

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        DisposableUtil.dispose();
    }

    /**
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        DisposableUtil.add(new TestDisposable("a"));
        assertThat(DisposableUtil.disposables.size(), is(1));
        DisposableUtil.dispose();
        assertThat(count, is(1));
        assertThat(names, is("a"));
        assertThat(DisposableUtil.disposables.size(), is(0));
    }

    /**
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        DisposableUtil.add(new TestDisposable("a"));
        DisposableUtil.add(new TestDisposable("b"));
        assertThat(DisposableUtil.disposables.size(), is(2));
        DisposableUtil.dispose();
        assertThat(count, is(2));
        assertThat(names, is("ba"));
        assertThat(DisposableUtil.disposables.size(), is(0));
    }

    /**
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        DisposableUtil.add(new TestDisposable("a"));
        DisposableUtil.add(new TestDisposable2());
        DisposableUtil.add(new TestDisposable("b"));
        assertThat(DisposableUtil.disposables.size(), is(3));
        DisposableUtil.dispose();
        assertThat(count, is(3));
        assertThat(names, is("ba"));
        assertThat(DisposableUtil.disposables.size(), is(0));
    }

    /**
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        DisposableUtil.add(new TestDisposable("a"));
        DisposableUtil.addFirst(new TestDisposable("b"));
        assertThat(DisposableUtil.disposables.size(), is(2));
        DisposableUtil.dispose();
        assertThat(count, is(2));
        assertThat(names, is("ab"));
        assertThat(DisposableUtil.disposables.size(), is(0));
    }

    /**
     *
     */
    public class TestDisposable implements Disposable {
        String name;

        /**
         * @param name
         */
        public TestDisposable(final String name) {
            this.name = name;
        }

        @Override
        public void dispose() {
            ++count;
            names += name;
        }
    }

    /**
     *
     */
    public class TestDisposable2 implements Disposable {
        @Override
        public void dispose() {
            ++count;
            throw new RuntimeException();
        }
    }

}
