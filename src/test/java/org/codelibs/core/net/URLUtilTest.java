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
package org.codelibs.core.net;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

/**
 * @author taichi
 *
 */
public class URLUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testEncode() throws Exception {
        assertEquals("Program+Files", URLUtil.encode("Program Files", "UTF-8"));
    }

    /**
     * @throws Exception
     */
    public void testDecode() throws Exception {
        assertEquals("Program Files", URLUtil.decode("Program+Files", "UTF-8"));
    }

    /**
     * @throws Exception
     */
    public void testToCanonicalProtocol() throws Exception {
        assertEquals("jar", URLUtil.toCanonicalProtocol("wsjar"));
        assertEquals("jar", URLUtil.toCanonicalProtocol("jar"));
        assertEquals("zip", URLUtil.toCanonicalProtocol("zip"));
        assertEquals("file", URLUtil.toCanonicalProtocol("file"));
    }

    /**
     * @throws Exception
     */
    public void testToFile() throws Exception {
        final File file = new File("Program Files/hoge.txt");
        final URL url = file.toURI().toURL();
        assertEquals(file.getAbsoluteFile(), URLUtil.toFile(url));
        assertEquals(file.getAbsoluteFile(), URLUtil.toFile(new URL("file:Program%20Files/hoge.txt")));
    }

}
