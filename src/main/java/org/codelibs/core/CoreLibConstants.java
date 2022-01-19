/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
package org.codelibs.core;

import java.nio.charset.Charset;

/**
 * Constants class.
 *
 * @author shinsuke
 *
 */
public class CoreLibConstants {

    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";

    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);

    public static final String DATE_FORMAT_ISO_8601_BASIC = "yyyyMMdd'T'HHmmss.SSSZ";

    public static final String DATE_FORMAT_ISO_8601_EXTEND = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String DATE_FORMAT_ISO_8601_EXTEND_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String DATE_FORMAT_DIGIT_ONLY = "yyyyMMddHHmmss";

    protected CoreLibConstants() {
    }
}
