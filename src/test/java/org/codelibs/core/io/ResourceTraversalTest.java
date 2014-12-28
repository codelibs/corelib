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
package org.codelibs.core.io;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.zip.ZipInputStream;

import junit.framework.TestCase;

import org.codelibs.core.jar.JarFileUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * @author taedium
 */
public class ResourceTraversalTest {

    private static int count = 0;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        count = 0;
    }

    /**
     * @throws Exception
     */
    @Test
    public void testForEachFile() throws Exception {
        final File rootDir = ResourceUtil.getBuildDir(getClass());
        final String path = ResourceUtil.getResourcePath(getClass());
        final int pos = path.lastIndexOf("/");
        final String baseDirectory = path.substring(0, pos);
        ResourceTraversalUtil.forEach(rootDir, baseDirectory,
                (ResourceHandler) (path1, is) -> {
                    try {
                        if (count < 10) {
                            System.out.println(path1);
                        }
                        assertThat(path1, is(notNullValue()));
                        assertThat(is, is(notNullValue()));
                        count++;
                    } finally {
                        CloseableUtil.close(is);
                    }
                });
        assertTrue(count > 0);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testForEachJarFile() throws Exception {
        final String classFilePath = TestCase.class.getName().replace('.', '/')
                + ".class";
        final URL classURL = ResourceUtil.getResource(classFilePath);
        final JarURLConnection con = (JarURLConnection) classURL
                .openConnection();
        ResourceTraversalUtil
                .forEach(
                        con.getJarFile(),
                        (ResourceHandler) (path, is) -> {
                            try {
                                if (count < 10) {
                                    System.out.println(path);
                                }
                                System.out.println(path);
                                assertThat(path, is(notNullValue()));
                                assertThat(
                                        path,
                                        path.startsWith("junit")
                                                || path.startsWith("org/junit")
                                                || path.startsWith("org/hamcrest")
                                                || path.startsWith("META-INF/")
                                                || path.equals("LICENSE.txt"),
                                        is(true));
                                assertThat(is, is(notNullValue()));
                                count++;
                            } finally {
                                CloseableUtil.close(is);
                            }
                        });
        assertTrue(count > 0);
    }

    /**
     * @throws Exception
     */
    public void testForEachJarFile_withPrefix() throws Exception {
        final String classFilePath = TestCase.class.getName().replace('.', '/')
                + ".class";
        final URL classURL = ResourceUtil.getResource(classFilePath);
        final JarURLConnection con = (JarURLConnection) classURL
                .openConnection();
        ResourceTraversalUtil.forEach(con.getJarFile(), "junit/",
                (ResourceHandler) (path, is) -> {
                    try {
                        if (count < 10) {
                            System.out.println(path);
                        }
                        assertThat(path.startsWith("junit"), is(not(true)));
                        assertThat(path, is(notNullValue()));
                        assertThat(is, is(notNullValue()));
                        count++;
                    } finally {
                        CloseableUtil.close(is);
                    }
                });
        assertTrue(count > 0);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testForEachZipInputStream() throws Exception {
        final String classFilePath = TestCase.class.getName().replace('.', '/')
                + ".class";
        final URL classURL = ResourceUtil.getResource(classFilePath);
        final URL jarURL = new File(JarFileUtil.toJarFilePath(classURL))
                .toURI().toURL();
        ResourceTraversalUtil
                .forEach(
                        new ZipInputStream(jarURL.openStream()),
                        (ResourceHandler) (path, is) -> {
                            try {
                                if (count < 10) {
                                    System.out.println(path);
                                }
                                assertThat(path, is(notNullValue()));
                                assertThat(
                                        path,
                                        path.startsWith("junit")
                                                || path.startsWith("org/junit")
                                                || path.startsWith("org/hamcrest")
                                                || path.startsWith("META-INF/")
                                                || path.equals("LICENSE.txt"),
                                        is(true));
                                assertThat(is, is(notNullValue()));
                                count++;
                            } finally {
                                CloseableUtil.close(is);
                            }
                        });
        assertTrue(count > 0);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testForEachZipInputStream_withPrefix() throws Exception {
        final String classFilePath = TestCase.class.getName().replace('.', '/')
                + ".class";
        final URL classURL = ResourceUtil.getResource(classFilePath);
        final URL jarURL = new File(JarFileUtil.toJarFilePath(classURL))
                .toURI().toURL();
        ResourceTraversalUtil.forEach(new ZipInputStream(jarURL.openStream()),
                "junit/", (ResourceHandler) (path, is) -> {
                    try {
                        if (count < 10) {
                            System.out.println(path);
                        }
                        assertThat(path.startsWith("junit"), is(not(true)));
                        assertThat(path, is(notNullValue()));
                        assertThat(is, is(notNullValue()));
                        count++;
                    } finally {
                        CloseableUtil.close(is);
                    }
                });
        assertTrue(count > 0);
    }

}
