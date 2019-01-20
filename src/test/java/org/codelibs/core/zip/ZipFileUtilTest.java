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
package org.codelibs.core.zip;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import junit.framework.TestCase;

/**
 * @author koichik
 */
public class ZipFileUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testToJarFilePath() throws Exception {
        final URL url = new URL(null, "zip:/Program Files/foo.zip!/", new URLStreamHandler() {
            @Override
            protected void parseURL(final URL u, final String spec, final int start, final int limit) {
                setURL(u, "zip", null, 0, null, null, spec.substring(4), null, null);
            }

            @Override
            protected URLConnection openConnection(final URL u) throws IOException {
                return null;
            }
        });
        final String root = new File("/").getCanonicalPath();
        assertEquals(root + "Program Files" + File.separator + "foo.zip", ZipFileUtil.toZipFilePath(url));
    }

}
