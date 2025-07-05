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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility class for {@link Writer} operations.
 *
 * @author koichik
 */
public abstract class WriterUtil {

    /**
     * Do not instantiate.
     */
    private WriterUtil() {
    }

    /**
     * Creates a {@link Writer} to output to a stream with the specified encoding.
     *
     * @param os the stream (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return a {@link Writer} to output to the stream
     */
    public static Writer create(final OutputStream os, final String encoding) {
        assertArgumentNotNull("os", os);
        assertArgumentNotEmpty("encoding", encoding);

        try {
            return new OutputStreamWriter(os, encoding);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates a {@link Writer} to output to a file with the default encoding.
     *
     * @param file the file (must not be {@literal null})
     * @return a {@link Writer} to output to the file
     */
    public static Writer create(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return new FileWriter(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates a {@link Writer} to output to a file with the specified encoding.
     *
     * @param file the file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return a {@link Writer} to output to the file
     */
    public static Writer create(final File file, final String encoding) {
        assertArgumentNotNull("file", file);
        assertArgumentNotEmpty("encoding", encoding);

        try {
            return new OutputStreamWriter(new FileOutputStream(file), encoding);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
