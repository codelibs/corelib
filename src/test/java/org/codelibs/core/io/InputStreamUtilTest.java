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

import java.io.InputStream;

import junit.framework.TestCase;

import org.codelibs.core.lang.StringUtil;

/**
 * @author higa
 *
 */
public class InputStreamUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testGetBytes() throws Exception {
        final InputStream is = ResourceUtil.getResourceAsStream(StringUtil.replace(getClass().getName(), ".", "/") + ".class");
        try {
            assertNotNull("1", InputStreamUtil.getBytes(is));
        } finally {
            is.close();
        }
    }
}
