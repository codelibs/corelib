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
package org.codelibs.core.beans.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;

import org.junit.Test;

/**
 * @author higa
 */
public class NumberConverterTest {

    /**
     * @throws Exception
     */
    @Test
    public void testGetAsObject() throws Exception {
        final NumberConverter converter = new NumberConverter("##0");
        assertThat(converter.getAsObject("100"), is((Object) Long.valueOf("100")));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetAsString() throws Exception {
        final NumberConverter converter = new NumberConverter("##0");
        assertThat(converter.getAsString(new Integer("100")), is("100"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTarget() throws Exception {
        final NumberConverter converter = new NumberConverter("##0");
        assertThat(converter.isTarget(Integer.class), is(true));
        assertThat(converter.isTarget(Timestamp.class), is(not(true)));
    }

}
