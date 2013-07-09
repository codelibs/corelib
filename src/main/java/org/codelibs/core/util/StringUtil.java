/*
 * Copyright 2013 the CodeLibs Project and the Others.
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
package org.codelibs.core.util;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.codelibs.core.exception.UnsupportedEncodingRuntimeException;

/**
 * Extended StringUtil.
 * 
 * @author shinsuke
 * 
 */
public class StringUtil extends org.seasar.util.lang.StringUtil {
    /**
     * A default constructor.
     */
    protected StringUtil() {
    }

    /**
     * Creates a new string with charset.
     * 
     * @param bytes
     * @param charsetName
     * @return
     */
    public static String newString(final byte[] bytes, final String charsetName) {
        try {
            return new String(bytes, charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    /**
     * Gets bytes with charset.
     * 
     * @param str
     * @param charsetName
     * @return
     */
    public static byte[] getBytes(String str, String charsetName) {
        try {
            return str.getBytes(charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    /**
     * Check if a collection of a string is empty.
     * 
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection<String> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        for (String text : c) {
            if (isNotEmpty(text)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a collection of a string is not empty.
     * 
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection<String> c) {
        return !isEmpty(c);
    }

    /**
     * Check if a collection of a string is blank.
     * 
     * @param c
     * @return
     */
    public static boolean isBlank(Collection<String> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        for (String text : c) {
            if (isNotBlank(text)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a collection of a string is not blank.
     * 
     * @param c
     * @return
     */
    public static boolean isNotBlank(Collection<String> c) {
        return !isBlank(c);
    }
}
