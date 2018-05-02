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
package org.codelibs.core.jar;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.codelibs.core.exception.IORuntimeException;

/**
 * {@link JarInputStream}用のユーティリティクラスです。
 *
 * @author koichik
 */
public abstract class JarInputStreamUtil {

    /**
     * {@link JarInputStream}を作成します。
     *
     * @param is
     *            入力ストリーム。{@literal null}であってはいけません
     * @return {@link JarInputStream}
     * @throws IORuntimeException
     *             {@link IOException}が発生した場合
     * @see JarInputStream#JarInputStream(InputStream)
     */
    public static JarInputStream create(final InputStream is) throws IORuntimeException {
        assertArgumentNotNull("is", is);

        try {
            return new JarInputStream(is);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * {@link JarInputStream#getNextJarEntry()}の例外処理をラップするメソッドです。
     *
     * @param is
     *            入力ストリーム。{@literal null}であってはいけません
     * @return {@link JarEntry}
     * @throws IORuntimeException
     *             {@link IOException}が発生した場合
     * @see JarInputStream#getNextJarEntry()
     */
    public static JarEntry getNextJarEntry(final JarInputStream is) throws IORuntimeException {
        assertArgumentNotNull("is", is);

        try {
            return is.getNextJarEntry();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
