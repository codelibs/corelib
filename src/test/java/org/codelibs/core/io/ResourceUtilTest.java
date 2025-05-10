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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.codelibs.core.exception.ResourceNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public class ResourceUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testGetResourcePath() throws Exception {
        assertEquals("1", "aaa/bbb.xml", ResourceUtil.getResourcePath("aaa/bbb.xml", "xml"));
        assertEquals("2", "aaa/bbb.xml", ResourceUtil.getResourcePath("aaa.bbb", "xml"));
        assertEquals("3", "org/codelibs/core/io/ResourceUtilTest.class", ResourceUtil.getResourcePath(getClass()));
    }

    /**
     * @throws Exception
     */
    public void testGetResource() throws Exception {
        assertNotNull(ResourceUtil.getResource("java/lang/String.class", "class"));
        assertNotNull(ResourceUtil.getResource("org/codelibs"));
        try {
            ResourceUtil.getResource("hoge", "xml");
            fail("2");
        } catch (final ResourceNotFoundRuntimeException e) {
            System.out.println(e);
            assertEquals("3", "hoge.xml", e.getPath());
        }
        System.out.println(ResourceUtil.getResource("."));
    }

    /**
     * @throws Exception
     */
    public void testGetResourceAsStreamNoException() throws Exception {
        assertNotNull(ResourceUtil.getResourceAsStreamNoException("java/lang/String.class", "class"));
        assertNull(ResourceUtil.getResourceAsStreamNoException("java/lang/String2.class", "class"));
    }

    /**
     * @throws Exception
     */
    public void testGetBuildDir() throws Exception {
        final File file = ResourceUtil.getBuildDir(getClass());
        System.out.println(file);
        final File file2 = ResourceUtil.getBuildDir("org/codelibs/core/io");
        assertEquals(file, file2);
        final File junitJar = ResourceUtil.getBuildDir(TestCase.class);
        assertTrue(junitJar.exists());
        final URL url = junitJar.toURI().toURL();
        final URLClassLoader loader = new URLClassLoader(new URL[] { url });
        loader.loadClass(TestCase.class.getName());
    }

    /**
     * @throws Exception
     */
    public void testIsExist() throws Exception {
        assertEquals("1", true, ResourceUtil.isExist("CLMessages.properties"));
        assertEquals("2", false, ResourceUtil.isExist("hoge"));
    }

    /**
     * @throws Exception
     */
    public void testGetExtension() throws Exception {
        assertEquals("1", "xml", ResourceUtil.getExtension("aaa/bbb.xml"));
        assertEquals("2", null, ResourceUtil.getExtension("aaa"));
    }

    /**
     * @throws Exception
     */
    public void testRemoteExtension() throws Exception {
        assertEquals("1", "aaa/bbb", ResourceUtil.removeExtension("aaa/bbb.xml"));
        assertEquals("2", "aaa/bbb", ResourceUtil.removeExtension("aaa/bbb"));
    }

    /**
     * @throws Exception
     */
    public void testToExternalForm() throws Exception {
        final URL url = new File("/Program File").toURI().toURL();
        assertEquals("file:" + getRoot() + "Program File", ResourceUtil.toExternalForm(url));
    }

    /**
     * @throws Exception
     */
    public void testGetFileName() throws Exception {
        URL url = new File("/Program File").toURI().toURL();
        assertEquals(getRoot() + "Program File", ResourceUtil.getFileName(url));
        url = ResourceUtil.getResource("java/lang/String.class");
        assertNull(ResourceUtil.getFile(url));
    }

    private String getRoot() throws IOException {
        final String root = new File("/").getCanonicalPath().replace('\\', '/');
        if (root.startsWith("/")) {
            return root;
        }
        return "/" + root;
    }

}
