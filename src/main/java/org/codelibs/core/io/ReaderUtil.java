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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility class for {@link Reader} operations.
 *
 * @author higa
 */
public abstract class ReaderUtil {

    /**
     * Do not instantiate.
     */
    private ReaderUtil() {
    }

    /** Default buffer size */
    private static final int BUF_SIZE = 4096;

    /**
     * Creates a {@link Reader} to read from a file with the specified encoding.
     *
     * @param is
     *            the input stream (must not be {@literal null})
     * @param encoding
     *            the encoding of the input stream (must not be {@literal null} or empty)
     * @return a {@link Reader} to read from the file
     */
    public static InputStreamReader create(final InputStream is, final String encoding) {
        assertArgumentNotNull("is", is);
        assertArgumentNotEmpty("encoding", encoding);

        try {
            return new InputStreamReader(is, encoding);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates a {@link Reader} to read from a file with the default encoding.
     *
     * @param file
     *            the file (must not be {@literal null})
     * @return a {@link Reader} to read from the file
     */
    public static Reader create(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return new FileReader(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates a {@link Reader} to read from a file with the specified encoding.
     *
     * @param file
     *            the file (must not be {@literal null})
     * @param encoding
     *            the encoding (must not be {@literal null} or empty)
     * @return a {@link Reader} to read from the file
     */
    public static Reader create(final File file, final String encoding) {
        assertArgumentNotNull("file", file);
        assertArgumentNotEmpty("encoding", encoding);

        try {
            return new InputStreamReader(new FileInputStream(file), encoding);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Reads a single line from the given {@link BufferedReader}.
     *
     * @param reader
     *            the {@link BufferedReader} (must not be {@literal null})
     * @return a line of text, or {@literal null} if the end of the stream has been reached
     * @see BufferedReader#readLine()
     */
    public static String readLine(final BufferedReader reader) {
        assertArgumentNotNull("reader", reader);

        try {
            return reader.readLine();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Reads text from the given {@link Reader}.
     * <p>
     * The {@link Reader} is not closed by this method.
     * </p>
     *
     * @param reader
     *            the character input stream to read from (must not be {@literal null})
     * @return the text read from the reader
     */
    public static String readText(final Reader reader) {
        assertArgumentNotNull("reader", reader);

        final StringBuilder buf = new StringBuilder(BUF_SIZE);
        CopyUtil.copy(reader, buf);
        return new String(buf);
    }

}
