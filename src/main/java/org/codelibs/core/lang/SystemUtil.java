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
package org.codelibs.core.lang;

/**
 * Utility class for system operations.
 *
 * @author wyukawa
 * @author shinsuke
 *
 */
public abstract class SystemUtil {

    /**
     * Do not instantiate.
     */
    private SystemUtil() {
    }

    /**
     * <code>file.encoding</code> system property. Example: UTF-8
     */
    public static final String FILE_ENCODING = System.getProperty("file.encoding");

    /**
     * <code>line.separator</code> system property. For example, on Mac OS X: <code>"\n"</code>
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * <code>path.separator</code> system property. For example, on Mac OS X: <code>":"</code>
     */
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    /**
     * <code>os.name</code> system property. Example: <code>Mac OS X</code>
     */
    public static final String OS_NAME = System.getProperty("os.name");

    /**
     * <code>java.io.tmpdir</code> system property. Example: /tmp
     */
    public static final String JAVA_IO_TMPDIR = System.getProperty("java.io.tmpdir");

    /**
     * <code>user.dir</code> system property.
     */
    public static final String USER_DIR = System.getProperty("user.dir");

    /**
     * <code>user.home</code> system property.
     */
    public static final String USER_HOME = System.getProperty("user.home");

    /**
     * Returns the system property value for the specified key.
     *
     * @param key the property key
     * @return the property value, or null if not found
     */
    public static String getProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * Returns the system property value for the specified key, or the default value if not found.
     *
     * @param key the property key
     * @param defaultValue the default value
     * @return the property value, or the default value if not found
     */
    public static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }

    /**
     * Returns the system environment variable value for the specified key.
     *
     * @param key the environment variable key
     * @return the environment variable value, or null if not found
     */
    public static String getEnv(String key) {
        return System.getenv(key);
    }

    /**
     * Returns the system environment variable value for the specified key, or the default value if not found.
     *
     * @param key the environment variable key
     * @param defaultValue the default value
     * @return the environment variable value, or the default value if not found
     */
    public static String getEnv(String key, String defaultValue) {
        return System.getenv().getOrDefault(key, defaultValue);
    }

    /**
     * Returns the current time in milliseconds.
     *
     * @return the current time in milliseconds
     */
    public static long currentTimeMillis() {
        // TODO provider
        return System.currentTimeMillis();
    }
}
