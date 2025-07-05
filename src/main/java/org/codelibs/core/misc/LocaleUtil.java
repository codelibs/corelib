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

import java.util.Locale;
import java.util.function.Supplier;

/**
 * Utility class for {@link Locale}.
 *
 * @author higa
 */
public abstract class LocaleUtil {

    /**
     * Do not instantiate.
     */
    private LocaleUtil() {
    }

    /**
     * Returns a {@link Locale}.
     *
     * @param localeStr
     *            A string representing the locale
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

    /**
     * Returns the default locale.
     *
     * @return the default locale
     */
    public static Locale getDefault() {
        if (defaultLocaleSupplier != null) {
            return defaultLocaleSupplier.get();
        }
        return Locale.ENGLISH;
    }

    /**
     * Sets the default locale supplier.
     *
     * @param localeSupplier
     *            the supplier for the default locale
     */
    public static void setDefault(final Supplier<Locale> localeSupplier) {
        defaultLocaleSupplier = localeSupplier;
    }
}
