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
package org.codelibs.core.lang;

/**
 * システムプロパティ用のユーティリティクラスです。
 *
 * @author wyukawa
 * @author shinsuke
 *
 */
public abstract class SystemUtil {

    /**
     * <code>file.encoding</code>システムプロパティ。例:UTF-8
     */
    public static final String FILE_ENCODING = System.getProperty("file.encoding");

    /**
     * <code>line.separator</code> システムプロパティ。例えばMac OS Xなら
     * <code>&quot;\n&quot;</code>
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * <code>path.separator</code> システムプロパティ。例えばMac OS Xなら
     * <code>&quot;:&quot;</code>
     */
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    /**
     * <code>os.name</code> システムプロパティ。例:<code>Mac OS X</code>
     */
    public static final String OS_NAME = System.getProperty("os.name");

    /**
     * <code>java.io.tmpdir</code> システムプロパティ。例：/tmp
     */
    public static final String JAVA_IO_TMPDIR = System.getProperty("java.io.tmpdir");

    /**
     * <code>user.dir</code> システムプロパティ。
     */
    public static final String USER_DIR = System.getProperty("user.dir");

    /**
     * <code>user.home</code> システムプロパティ。
     */
    public static final String USER_HOME = System.getProperty("user.home");

    public static long currentTimeMillis() {
        // TODO provider
        return System.currentTimeMillis();
    }
}
