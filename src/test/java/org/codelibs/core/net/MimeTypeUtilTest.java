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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URLConnection;

import org.codelibs.core.exception.EmptyArgumentException;
import org.codelibs.core.io.ResourceUtil;
import org.codelibs.core.lang.ClassUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author shot
 */
public class MimeTypeUtilTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * @throws Exception
     */
    @Test
    public void testGetFromStream() throws Exception {
        final String path = ClassUtil.getPackageName(this.getClass())
                .replaceAll("\\.", "/") + "/aaa.html";
        final String contentType = MimeTypeUtil.guessContentType(path);
        assertEquals("text/html", contentType);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetFromStream_gif() throws Exception {
        final String path = ClassUtil.getPackageName(this.getClass())
                .replaceAll("\\.", "/") + "/ccc.gif";
        final String contentType = MimeTypeUtil.guessContentType(path);
        assertEquals("image/gif", contentType);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetFromPath() throws Exception {
        final String path = ClassUtil.getPackageName(this.getClass())
                .replaceAll("\\.", "/") + "/bbb.html";
        final String s = URLConnection.guessContentTypeFromStream(ResourceUtil
                .getResourceAsStream(path));
        assertNull(s);
        final String contentType = MimeTypeUtil.guessContentType(path);
        assertEquals("text/html", contentType);
    }

    /**
     * Test method for
     * {@link org.codelibs.core.net.MimeTypeUtil#guessContentType(String)} .
     */
    @Test
    public void testGuessContentType() {
        exception.expect(EmptyArgumentException.class);
        exception
                .expectMessage(is("[ECL0010]argument[path] is null or empty string."));
        MimeTypeUtil.guessContentType(null);
    }

}
