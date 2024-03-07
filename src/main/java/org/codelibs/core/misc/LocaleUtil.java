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
package org.codelibs.core.misc;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * {@link Locale}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class LocaleUtil {

    /**
     * {@link Locale}を返します。
     *
     * @param localeStr
     *            ロケールを表す文字列
     * @return {@link Locale}
     */
    public static Locale getLocale(final String localeStr) {
        // TODO replace with Fess
        Locale locale = LocaleUtil.getDefault();
        if (localeStr != null) {
            final int index = localeStr.indexOf('_');
            if (index < 0) {
                locale = new Locale(localeStr);
            } else {
                final String language = localeStr.substring(0, index);
                final String country = localeStr.substring(index + 1);
                locale = new Locale(language, country);
            }
        }
        return locale;
    }

    private static Supplier<Locale> defaultLocaleSupplier;

    public static Locale getDefault() {
        if (defaultLocaleSupplier != null) {
            return defaultLocaleSupplier.get();
        }
        return Locale.ENGLISH;
    }

    public static void setDefault(final Supplier<Locale> localeSupplier) {
        defaultLocaleSupplier = localeSupplier;
    }
}
