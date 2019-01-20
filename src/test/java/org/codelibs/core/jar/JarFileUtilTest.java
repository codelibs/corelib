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
package org.codelibs.core.jar;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

/**
 * @author koichik
 */
public class JarFileUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testToJarFilePath() throws Exception {
        final File f = new File("/Program Files/foo.jar");
        final URL url = new URL("jar:" + f.toURI().toURL() + "!/");
        final String root = new File("/").getCanonicalPath();
        assertEquals(root + "Program Files" + File.separator + "foo.jar", JarFileUtil.toJarFilePath(url));
    }

    /**
     * @throws Exception
     */
    public void testRelativePath() throws Exception {
        final File f = new File("/Program Files/foo.jar");
        URL url = new URL("jar:" + f.toURI().toURL() + "!/foo/bar/");
        url = new URL(url, "..");
        System.out.println(url.toExternalForm());
    }

}
