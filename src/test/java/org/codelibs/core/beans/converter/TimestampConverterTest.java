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

import org.junit.Test;

/**
 * @author higa
 */
public class TimestampConverterTest {

    /**
     * @throws Exception
     */
    @Test
    public void testGetAsObjectAndGetAsString() throws Exception {
        final TimestampConverter converter = new TimestampConverter("yyyy/MM/dd HH:mm:ss");
        final java.sql.Timestamp result = (java.sql.Timestamp) converter.getAsObject("2008/12/31 12:34:56");
        System.out.println(result);
        assertThat(converter.getAsString(result), is("2008/12/31 12:34:56"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTarget() throws Exception {
        final TimestampConverter converter = new TimestampConverter("yyyy/MM/dd");
        assertThat(converter.isTarget(java.sql.Timestamp.class), is(true));
        assertThat(converter.isTarget(java.util.Date.class), is(not(true)));
    }

}
