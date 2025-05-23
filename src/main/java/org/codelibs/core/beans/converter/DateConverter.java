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
package org.codelibs.core.beans.converter;

import static org.codelibs.core.lang.StringUtil.isEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Date;

import org.codelibs.core.beans.Converter;
import org.codelibs.core.convert.DateConversionUtil;
import org.codelibs.core.convert.StringConversionUtil;

/**
 * Converter for dates.
 *
 * @author higa
 */
public class DateConverter implements Converter {

    /**
     * The date pattern.
     */
    protected String pattern;

    /**
     * Constructs an instance.
     *
     * @param pattern
     *            the date pattern
     */
    public DateConverter(final String pattern) {
        assertArgumentNotEmpty("pattern", pattern);
        this.pattern = pattern;
    }

    @Override
    public Object getAsObject(final String value) {
        if (isEmpty(value)) {
            return null;
        }
        return DateConversionUtil.toDate(value, pattern);
    }

    @Override
    public String getAsString(final Object value) {
        if (value == null) {
            return null;
        }
        return StringConversionUtil.toString((Date) value, pattern);
    }

    @Override
    public boolean isTarget(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);
        return clazz == Date.class;
    }

}
