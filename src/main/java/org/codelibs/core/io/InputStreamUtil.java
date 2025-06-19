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
package org.codelibs.core.io;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility class for {@link InputStream}.
 *
 * @author higa
 */
public abstract class InputStreamUtil {

    /** Default buffer size. */
    private static final int BUF_SIZE = 4096;

    /**
     * Creates a {@link FileInputStream}.
     *
     * @param file the file (must not be {@literal null})
     * @return a {@link FileInputStream} to read from the file
     * @see FileInputStream#FileInputStream(File)
     */
    public static FileInputStream create(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return new FileInputStream(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Gets a byte array from an {@link InputStream}.
     * <p>
     * The input stream is not closed.
     * </p>
     *
     * @param is the input stream (must not be {@literal null})
     * @return the byte array
     */
    public static final byte[] getBytes(final InputStream is) {
        assertArgumentNotNull("is", is);

        final ByteArrayOutputStream os = new ByteArrayOutputStream(BUF_SIZE);
        CopyUtil.copy(is, os);
        return os.toByteArray();
    }

    /**
     * A method that wraps exception handling for {@link InputStream#available()}.
     *
     * @param is the input stream (must not be {@literal null})
     * @return the available size
     */
    public static int available(final InputStream is) {
        assertArgumentNotNull("is", is);

        try {
            return is.available();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Resets the {@link InputStream}.
     *
     * @param is the input stream (must not be {@literal null})
     * @see InputStream#reset()
     */
    public static void reset(final InputStream is) {
        assertArgumentNotNull("is", is);

        try {
            is.reset();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
