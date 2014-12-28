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
package org.codelibs.core.text;

import static org.codelibs.core.collection.CollectionsUtil.newConcurrentHashMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

import org.codelibs.core.misc.LocaleUtil;

/**
 * {@link DecimalFormatSymbols}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class DecimalFormatSymbolsUtil {

    private static final Map<Locale, DecimalFormatSymbols> CACHE = newConcurrentHashMap();

    /**
     * {@link DecimalFormatSymbols}を返します。
     *
     * @return {@link DecimalFormatSymbols}
     */
    public static DecimalFormatSymbols getDecimalFormatSymbols() {
        return getDecimalFormatSymbols(LocaleUtil.getDefault());
    }

    /**
     * {@link DecimalFormatSymbols}を返します。
     *
     * @param locale
     *            ロケール。{@literal null}であってはいけません
     * @return {@link DecimalFormatSymbols}
     */
    public static DecimalFormatSymbols getDecimalFormatSymbols(
            final Locale locale) {
        assertArgumentNotNull("locale", locale);

        DecimalFormatSymbols symbols = CACHE.get(locale);
        if (symbols == null) {
            symbols = new DecimalFormatSymbols(locale);
            CACHE.put(locale, symbols);
        }
        return symbols;
    }

}
