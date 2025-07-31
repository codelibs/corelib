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
package org.codelibs.core.io;

import static org.codelibs.core.collection.CollectionsUtil.newHashMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.codelibs.core.misc.LocaleUtil;

/**
 * Utility class for {@link ResourceBundle} operations.
 *
 * @author higa
 */
public abstract class ResourceBundleUtil {

    /**
     * Do not instantiate.
     */
    protected ResourceBundleUtil() {
    }

    /**
     * Returns the bundle. Returns <code>null</code> if not found.
     *
     * @param name the resource bundle name (must not be {@literal null} or empty)
     * @return {@link ResourceBundle}
     * @see ResourceBundle#getBundle(String)
     */
    public static final ResourceBundle getBundle(final String name) {
        assertArgumentNotEmpty("name", name);

        try {
            return ResourceBundle.getBundle(name);
        } catch (final MissingResourceException ignore) {
            return null;
        }
    }

    /**
     * Returns the bundle. Returns <code>null</code> if not found.
     *
     * @param name the resource bundle name (must not be {@literal null} or empty)
     * @param locale the locale
     * @return {@link ResourceBundle}
     * @see ResourceBundle#getBundle(String, Locale)
     */
    public static final ResourceBundle getBundle(final String name, final Locale locale) {
        assertArgumentNotEmpty("name", name);

        try {
            return ResourceBundle.getBundle(name, getLocale(locale));
        } catch (final MissingResourceException ignore) {
            return null;
        }
    }

    /**
     * Returns the bundle. Returns <code>null</code> if not found.
     *
     * @param name the resource bundle name (must not be {@literal null} or empty)
     * @param locale the locale
     * @param classLoader the class loader (must not be {@literal null} or empty)
     * @return {@link ResourceBundle}
     * @see ResourceBundle#getBundle(String, Locale, ClassLoader)
     */
    public static final ResourceBundle getBundle(final String name, final Locale locale, final ClassLoader classLoader) {
        assertArgumentNotNull("name", name);
        assertArgumentNotNull("classLoader", classLoader);

        try {
            return ResourceBundle.getBundle(name, getLocale(locale), classLoader);
        } catch (final MissingResourceException ignore) {
            return null;
        }
    }

    /**
     * Returns the string for the specified key from the resource bundle.
     *
     * @param bundle the resource bundle (must not be {@literal null} or empty)
     * @param key the key
     * @return the string for the specified key (must not be {@literal null} or empty)
     * @see ResourceBundle#getString(String)
     */
    public static String getString(final ResourceBundle bundle, final String key) {
        assertArgumentNotNull("bundle", bundle);
        assertArgumentNotEmpty("key", key);

        try {
            return bundle.getString(key);
        } catch (final Throwable t) {
            return null;
        }
    }

    /**
     * Converts the resource bundle to a {@link Map}.
     *
     * @param bundle the resource bundle (must not be {@literal null})
     * @return {@link Map}
     */
    public static final Map<String, String> convertMap(final ResourceBundle bundle) {
        assertArgumentNotNull("bundle", bundle);

        final Map<String, String> ret = newHashMap();
        for (final Enumeration<String> e = bundle.getKeys(); e.hasMoreElements();) {
            final String key = e.nextElement();
            final String value = bundle.getString(key);
            ret.put(key, value);
        }
        return ret;
    }

    /**
     * Converts the resource bundle to a {@link Map} and returns it.
     *
     * @param name the resource bundle name (must not be {@literal null} or empty)
     * @param locale the locale
     * @return {@link Map}
     */
    public static final Map<String, String> convertMap(final String name, final Locale locale) {
        assertArgumentNotEmpty("name", name);

        final ResourceBundle bundle = getBundle(name, locale);
        return convertMap(bundle);
    }

    /**
     * Returns the {@literal locale} if not {@literal null}, otherwise returns the default locale.
     *
     * @param locale the locale
     * @return the {@literal locale} if not {@literal null}, otherwise the default locale
     */
    protected static Locale getLocale(final Locale locale) {
        if (locale != null) {
            return locale;
        }
        return LocaleUtil.getDefault();
    }

}
