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
package org.codelibs.core.net;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.net.JarURLConnection;
import java.util.jar.JarFile;

import org.codelibs.core.exception.IORuntimeException;

/**
 * {@link JarURLConnection}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class JarURLConnectionUtil {

    /**
     * {@link JarURLConnection#getJarFile()}の例外処理をラップするメソッドです。
     *
     * @param conn
     *            {@link JarURLConnection}。{@literal null}であってはいけません
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
