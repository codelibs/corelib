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
package org.codelibs.core.io;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import junit.textui.ResultPrinter;
import junit.textui.TestRunner;

import org.codelibs.core.io.TraversalUtil.FileSystemTraverser;
import org.codelibs.core.io.TraversalUtil.JarFileTraverser;
import org.codelibs.core.io.xxx.DummyTest;
import org.codelibs.core.lang.ClassUtil;
import org.junit.Test;

/**
 * @author koichik
 */
public class TraverserUtilTest {

    /**
     * @throws Exception
     */
    @Test
    public void testFromClass_FileSystem() throws Exception {
        final Traverser traverser = TraversalUtil.getTraverser(DummyTest.class);
        assertThat(traverser, is(notNullValue()));
        assertThat(traverser instanceof FileSystemTraverser, is(true));

        assertThat(traverser.isExistClass(DummyTest.class.getName()), is(true));
        assertThat(traverser.isExistClass(TestCase.class.getName()), is(not(true)));

        final Set<String> set = new HashSet<String>();
        traverser.forEach((ClassHandler) (packageName, shortClassName) -> set.add(ClassUtil.concatName(packageName, shortClassName)));
        assertThat(set.size() > 0, is(true));
        assertThat(set.contains(DummyTest.class.getName()), is(true));
        assertThat(set.contains(TraverserUtilTest.class.getName()), is(true));
        assertThat(set.contains(TestCase.class.getName()), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFromClass_JarFile() throws Exception {
        final Traverser traverser = TraversalUtil.getTraverser(TestCase.class);
        assertThat(traverser, is(notNullValue()));
        assertThat(traverser instanceof JarFileTraverser, is(true));

        assertThat(traverser.isExistClass(TestCase.class.getName()), is(true));
        assertThat(traverser.isExistClass(DummyTest.class.getName()), is(not(true)));

        final Set<String> set = new HashSet<String>();
        traverser.forEach((ClassHandler) (packageName, shortClassName) -> set.add(ClassUtil.concatName(packageName, shortClassName)));
        assertThat(set.size() > 0, is(true));
        assertThat(set.contains(TestCase.class.getName()), is(true));
        assertThat(set.contains(DummyTest.class.getName()), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFromDir_FileSystem() throws Exception {
        final Traverser traverser = TraversalUtil.getTraverser("org/codelibs/core/io/xxx");
        assertThat(traverser, is(notNullValue()));
        assertThat(traverser instanceof FileSystemTraverser, is(true));

        final List<String> list = new ArrayList<String>();
        traverser.forEach((ResourceHandler) (path, is) -> list.add(path));
        assertThat(list.size(), is(1));
        assertThat(list.get(0).endsWith("DummyTest.class"), is(true));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFromDir_JarFile() throws Exception {
        final Traverser traverser = TraversalUtil.getTraverser("junit/textui");
        assertThat(traverser, is(notNullValue()));
        assertThat(traverser instanceof JarFileTraverser, is(true));

        final List<String> list = new ArrayList<String>();
        traverser.forEach((ResourceHandler) (path, is) -> list.add(path));
        list.sort((s1, s2) -> s1.compareTo(s2));
        assertThat(list.size(), is(2));
        assertThat(list.get(0), is("junit/textui/ResultPrinter.class"));
        assertThat(list.get(1), is("junit/textui/TestRunner.class"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFromRootPackage_FileSystem() throws Exception {
        final Traverser[] traversers = TraversalUtil.getTraversers("org.codelibs.core.io.xxx");
        assertThat(traversers, is(notNullValue()));
        assertThat(traversers.length, is(1));

        final Traverser traverser = traversers[0];
        assertThat(traverser instanceof FileSystemTraverser, is(true));

        assertThat(traverser.isExistClass("DummyTest"), is(true));
        assertThat(traverser.isExistClass("TestCase"), is(not(true)));

        final Set<String> set = new HashSet<String>();
        traverser.forEach((ClassHandler) (packageName, shortClassName) -> set.add(ClassUtil.concatName(packageName, shortClassName)));
        assertThat(set.size() > 0, is(true));
        assertThat(set.contains(DummyTest.class.getName()), is(true));
        assertThat(set.contains(TraverserUtilTest.class.getName()), is(not(true)));
        assertThat(set.contains(TestCase.class.getName()), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFromRootPackage_JarFile() throws Exception {
        final Traverser[] traversers = TraversalUtil.getTraversers("junit.textui");
        assertThat(traversers, is(notNullValue()));
        assertThat(traversers.length, is(1));

        final Traverser traverser = traversers[0];
        assertThat(traverser instanceof JarFileTraverser, is(true));

        assertThat(traverser.isExistClass("TestRunner"), is(true));
        assertThat(traverser.isExistClass("DummyTest"), is(not(true)));

        final Set<String> set = new HashSet<String>();
        traverser.forEach((ClassHandler) (packageName, shortClassName) -> set.add(ClassUtil.concatName(packageName, shortClassName)));
        assertThat(set.size(), is(2));
        assertThat(set.contains(ResultPrinter.class.getName()), is(true));
        assertThat(set.contains(TestRunner.class.getName()), is(true));
        assertThat(set.contains("junit.textui.package-info"), is(false));
        assertThat(set.contains(junit.extensions.TestDecorator.class.getName()), is(not(true)));
    }

}
