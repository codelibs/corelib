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
package org.codelibs.core.net;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.net.JarURLConnection;
import java.util.jar.JarFile;

import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility class for {@link JarURLConnection}.
 *
 * @author higa
 */
public abstract class JarURLConnectionUtil {

    /**
     * A method that wraps the exception handling of {@link JarURLConnection#getJarFile()}.
     *
     * @param conn
     *            {@link JarURLConnection}. Must not be {@literal null}.
     * @return {@link JarFile}
     */
    public static JarFile getJarFile(final JarURLConnection conn) {
        assertArgumentNotNull("conn", conn);

        try {
            return conn.getJarFile();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
