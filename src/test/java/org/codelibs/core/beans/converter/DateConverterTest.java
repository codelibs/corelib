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
package org.codelibs.core.beans.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

/**
 * @author higa
 *
 */
public class DateConverterTest {

    /**
     * @throws Exception
     */
    @Test
    public void testGetAsObjectAndGetAsString() throws Exception {
        final DateConverter converter = new DateConverter("yyyy/MM/dd");
        final Date result = (Date) converter.getAsObject("2008/01/16");
        System.out.println(result);
        assertThat(converter.getAsString(result), is("2008/01/16"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTarget() throws Exception {
        final DateConverter converter = new DateConverter("yyyy/MM/dd");
        assertThat(converter.isTarget(Date.class), is(true));
        assertThat(converter.isTarget(Timestamp.class), is(not(true)));
    }

}
