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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.codelibs.core.beans.Converter;
import org.codelibs.core.exception.ParseRuntimeException;
import org.codelibs.core.lang.StringUtil;

/**
 * 数値用のコンバータです。
 *
 * @author higa
 */
public class NumberConverter implements Converter {

    /**
     * 数値のパターンです。
     */
    protected String pattern;

    /**
     * インスタンスを構築します。
     *
     * @param pattern
     *            数値のパターン
     */
    public NumberConverter(final String pattern) {
        assertArgumentNotEmpty("pattern", pattern);
        this.pattern = pattern;
    }

    @Override
    public Object getAsObject(final String value) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        try {
            return new DecimalFormat(pattern).parse(value);
        } catch (final ParseException e) {
            throw new ParseRuntimeException(e);
        }

    }

    @Override
    public String getAsString(final Object value) {
        if (value == null) {
            return null;
        }
        return new DecimalFormat(pattern).format(value);
    }

    @Override
    public boolean isTarget(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);
        return Number.class.isAssignableFrom(clazz);
    }

}
