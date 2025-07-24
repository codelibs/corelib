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
package org.codelibs.core.convert;

import java.text.SimpleDateFormat;

import org.codelibs.core.lang.StringUtil;
import org.codelibs.core.text.DecimalFormatUtil;

/**
 * Utility class for conversions related to {@link Integer}.
 *
 * @author higa
 */
public abstract class IntegerConversionUtil {

    /**
     * Do not instantiate.
     */
    private IntegerConversionUtil() {
    }

    /**
     * Converts to {@link Integer}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@link Integer}
     */
    public static Integer toInteger(final Object o) {
        return toInteger(o, null);
    }

    /**
     * Converts to {@link Integer}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@link Integer}
     */
    public static Integer toInteger(final Object o, final String pattern) {
        return switch(o){case null->null;case Integer i->i;case Number n->n.intValue();case String s->toInteger(s);case java.util.Date d->pattern!=null?Integer.valueOf(new SimpleDateFormat(pattern).format(d)):(int)d.getTime();case Boolean b->b?1:0;default->toInteger(o.toString());};
    }

    private static Integer toInteger(final String s) {
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Integer.valueOf(DecimalFormatUtil.normalize(s));
    }

    /**
     * Converts to {@literal int}.
     *
     * @param o
     *            The object to convert
     * @return The converted {@literal int}
     */
    public static int toPrimitiveInt(final Object o) {
        return toPrimitiveInt(o, null);
    }

    /**
     * Converts to {@literal int}.
     *
     * @param o
     *            The object to convert
     * @param pattern
     *            The pattern string
     * @return The converted {@literal int}
     */
    public static int toPrimitiveInt(final Object o, final String pattern) {
        return switch(o){case null->0;case Number n->n.intValue();case String s->toPrimitiveInt(s);case java.util.Date d->pattern!=null?Integer.parseInt(new SimpleDateFormat(pattern).format(d)):(int)d.getTime();case Boolean b->b?1:0;default->toPrimitiveInt(o.toString());};
    }

    private static int toPrimitiveInt(final String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        return Integer.parseInt(DecimalFormatUtil.normalize(s));
    }

}
