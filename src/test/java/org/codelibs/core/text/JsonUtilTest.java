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
package org.codelibs.core.text;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author shinsuke
 *
 */
public class JsonUtilTest {
    @Test
    public void escape() {
        assertThat(JsonUtil.escape("abc123あア亜"), is("abc123あア亜"));
        assertThat(JsonUtil.escape("\\\"/\b\t\n\f\r\0"), is("\\\\\\\"\\/\\b\\t\\n\\f\\r\\u0000"));
    }
}
