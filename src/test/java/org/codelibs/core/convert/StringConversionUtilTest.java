/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
package org.codelibs.core.convert;

import junit.framework.TestCase;

/**
 * @author shinsuke
 *
 */
public class StringConversionUtilTest extends TestCase {
    /**
     * @throws Exception
     */
    public void testFromWindowsMapping() throws Exception {
        assertNull(StringConversionUtil.fromWindowsMapping(null));
        assertEquals("", StringConversionUtil.fromWindowsMapping(""));
        assertEquals("abc 123", StringConversionUtil.fromWindowsMapping("abc 123"));
        assertEquals("abc\uFF5E\u2225\uFF0D\uFFE0\uFFE1\uFFE2",
                StringConversionUtil.fromWindowsMapping("abc\u301C\u2016\u2212\u00A2\u00A3\u00AC"));
    }

    /**
     * @throws Exception
     */
    public void testToWindowsMapping() throws Exception {
        assertNull(StringConversionUtil.toWindowsMapping(null));
        assertEquals("", StringConversionUtil.toWindowsMapping(""));
        assertEquals("abc 123", StringConversionUtil.toWindowsMapping("abc 123"));
        assertEquals("abc\u301C\u2016\u2212\u00A2\u00A3\u00AC",
                StringConversionUtil.toWindowsMapping("abc\uFF5E\u2225\uFF0D\uFFE0\uFFE1\uFFE2"));
    }

}
