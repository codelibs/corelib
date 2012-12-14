/*
 * Copyright 2012 the CodeLibs Project and the Others.
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
}
