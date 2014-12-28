/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

/**
 * JSON utilities.
 *
 * @author shinsuke
 *
 */
public class JsonUtil {

    /**
     * Defualt constructor.
     */
    protected JsonUtil() {
    }

    /**
     * Escapes a value as Json string.
     *
     * @param value
     * @return escaped string.
     */
    public static String escape(final String value) {
        if (value == null) {
            return null;
        }

        char c;
        final int len = value.length();
        final StringBuilder buf = new StringBuilder(len * 2);

        for (int i = 0; i < len; i++) {
            c = value.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    buf.append('\\');
                    buf.append(c);
                    break;
                case '/':
                    buf.append('\\');
                    buf.append(c);
                    break;
                case '\b':
                    buf.append("\\b");
                    break;
                case '\t':
                    buf.append("\\t");
                    break;
                case '\n':
                    buf.append("\\n");
                    break;
                case '\f':
                    buf.append("\\f");
                    break;
                case '\r':
                    buf.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        final String hex = Integer.toHexString(c);
                        buf.append("\\u");
                        for (int j = 0; j < 4 - hex.length(); j++) {
                            buf.append('0');

                        }
                        buf.append(hex);
                    } else {
                        buf.append(c);
                    }
            }
        }
        return buf.toString();
    }

}
