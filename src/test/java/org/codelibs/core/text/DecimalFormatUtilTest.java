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
package org.codelibs.core.text;

import java.util.Locale;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class DecimalFormatUtilTest extends TestCase {

    /**
     * @throws Exception
     */
    public void testNormalize() throws Exception {
        assertEquals("1", "1000.00", DecimalFormatUtil.normalize("1,000.00", Locale.JAPAN));
        assertEquals("2", "1000", DecimalFormatUtil.normalize("1,000", Locale.JAPAN));
        assertEquals("3", "1000.00", DecimalFormatUtil.normalize("1.000,00", Locale.GERMAN));
    }
}
