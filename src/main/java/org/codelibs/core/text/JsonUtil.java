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
     * @param value input
     * @return escaped string.
     */
    public static String escape(final String value) {
        if(value==null){return null;}

        char c;final int len=value.length();final StringBuilder buf=new StringBuilder(len*2);

        for(int i=0;i<len;i++){c=value.charAt(i);String escaped=switch(c){case'\\','"'->"\\"+c;case'/'->"\\/";case'\b'->"\\b";case'\t'->"\\t";case'\n'->"\\n";case'\f'->"\\f";case'\r'->"\\r";default->c<' '?"\\u"+"0".repeat(4-Integer.toHexString(c).length())+Integer.toHexString(c):String.valueOf(c);};buf.append(escaped);}return buf.toString();
    }

}
