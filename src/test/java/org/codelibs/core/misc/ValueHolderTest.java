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
package org.codelibs.core.misc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for {@link ValueHolder}.
 */
public class ValueHolderTest {

    /**
     * A null value must not raise a {@link NullPointerException} from {@link ValueHolder#toString()}.
     *
     * @throws Exception
     */
    @Test
    public void testToString_nullValue() throws Exception {
        assertEquals("null", new ValueHolder<String>().toString());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testToString_nonNullValue() throws Exception {
        assertEquals("abc", new ValueHolder<>("abc").toString());
    }
}
