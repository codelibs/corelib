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
package org.codelibs.core.jar;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility class for {@link JarInputStream} operations.
 *
 * @author koichik
 */
public abstract class JarInputStreamUtil {

    /**
     * Do not instantiate.
     */
    private JarInputStreamUtil() {
    }

    /**
     * Creates a {@link JarInputStream}.
     *
     * @param is the input stream (must not be {@literal null})
     * @return {@link JarInputStream}
     * @throws IORuntimeException if an {@link IOException} occurs
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
     * Method that wraps exception handling for {@link JarInputStream#getNextJarEntry()}.
     *
     * @param is the input stream (must not be {@literal null})
     * @return {@link JarEntry}
     * @throws IORuntimeException if an {@link IOException} occurs
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
