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
package org.codelibs.core.beans.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author higa
 */
public class TimeConverterTest {

    /**
     * @throws Exception
     */
    @Test
    public void testGetAsObjectAndGetAsString() throws Exception {
        final TimeConverter converter = new TimeConverter("HH:mm:ss");
        final java.sql.Time result = (java.sql.Time) converter.getAsObject("12:34:56");
        System.out.println(result);
        assertThat(converter.getAsString(result), is("12:34:56"));
    }

    /**
     * @throws Exception
     */
    public void testIsTarget() throws Exception {
        final TimeConverter converter = new TimeConverter("yyyy/MM/dd");
        assertThat(converter.isTarget(java.sql.Time.class), is(true));
        assertThat(converter.isTarget(java.util.Date.class), is(not(true)));
    }

}
