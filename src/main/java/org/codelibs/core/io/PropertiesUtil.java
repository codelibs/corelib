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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.net.URLUtil;

/**
 * Utility class for {@link Properties} operations.
 *
 * @author higa
 */
public abstract class PropertiesUtil {

    /**
     * Do not instantiate.
     */
    protected PropertiesUtil() {
    }

    /**
     * Wraps exception handling for {@link Properties#load(InputStream)}.
     * <p>
     * The input stream is not closed.
     * </p>
     *
     * @param props the property set (must not be {@literal null})
     * @param in the input stream (must not be {@literal null})
     */
    public static void load(final Properties props, final InputStream in) {
        assertArgumentNotNull("props", props);
        assertArgumentNotNull("in", in);

        try {
            props.load(in);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Wraps exception handling for {@link Properties#load(Reader)}.
     * <p>
     * The input reader is not closed.
     * </p>
     *
     * @param props the property set (must not be {@literal null})
     * @param reader the input reader (must not be {@literal null})
     */
    public static void load(final Properties props, final Reader reader) {
        assertArgumentNotNull("props", props);
        assertArgumentNotNull("reader", reader);

        try {
            props.load(reader);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Loads the specified file into the {@link Properties} using the given encoding (wraps exception handling).
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param file
     *            File. Must not be {@literal null}.
     * @param encoding
     *            Encoding. Must not be {@literal null} or empty.
     */
    public static void load(final Properties props, final File file, final String encoding) {
        assertArgumentNotNull("props", props);
        assertArgumentNotNull("file", file);
        assertArgumentNotEmpty("encoding", encoding);

        final Reader reader = ReaderUtil.create(file, encoding);

        try {
            props.load(reader);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        } finally {
            CloseableUtil.close(reader);
        }
    }

    /**
     * Loads the specified file into the {@link Properties} using the platform default encoding (wraps exception handling).
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param file
     *            File. Must not be {@literal null}.
     */
    public static void load(final Properties props, final File file) {
        load(props, file, Charset.defaultCharset().name());
    }

    /**
     * Loads the specified {@link URL} into the {@link Properties} (wraps exception handling).
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param url
     *            URL. Must not be {@literal null}.
     */
    public static void load(final Properties props, final URL url) {
        assertArgumentNotNull("props", props);
        assertArgumentNotNull("url", url);

        final InputStream in = URLUtil.openStream(url);

        try {
            props.load(in);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        } finally {
            CloseableUtil.close(in);
        }
    }

    /**
     * Loads a resource from the context class loader into the {@link Properties} (wraps exception handling).
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param path
     *            Resource path. Must not be {@literal null} or empty.
     */
    public static void load(final Properties props, final String path) {
        assertArgumentNotNull("props", props);
        assertArgumentNotEmpty("path", path);

        load(props, ResourceUtil.getResource(path));
    }

    /**
     * Wraps exception handling for {@link Properties#store(OutputStream, String)}.
     *
     * <p>
     * The output stream is not closed.
     * </p>
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param out
     *            Output stream. Must not be {@literal null}.
     * @param comments
     *            Comments.
     */
    public static void store(final Properties props, final OutputStream out, final String comments) {
        assertArgumentNotNull("props", props);
        assertArgumentNotNull("out", out);

        try {
            props.store(out, comments);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Wraps exception handling for {@link Properties#store(Writer, String)}.
     *
     * <p>
     * The output writer is not closed.
     * </p>
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param writer
     *            Output writer. Must not be {@literal null}.
     * @param comments
     *            Comments.
     */
    public static void store(final Properties props, final Writer writer, final String comments) {
        assertArgumentNotNull("props", props);
        assertArgumentNotNull("writer", writer);

        try {
            props.store(writer, comments);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Stores the {@link Properties} to a file with the specified encoding (wraps exception handling).
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param file
     *            File. Must not be {@literal null}.
     * @param encoding
     *            Encoding. Must not be {@literal null} or empty.
     * @param comments
     *            Comments.
     */
    public static void store(final Properties props, final File file, final String encoding, final String comments) {
        assertArgumentNotNull("props", props);
        assertArgumentNotNull("file", file);
        assertArgumentNotEmpty("encoding", encoding);

        final Writer writer = WriterUtil.create(file, encoding);
        try {
            props.store(writer, comments);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        } finally {
            CloseableUtil.close(writer);
        }
    }

    /**
     * Stores the {@link Properties} to a file using the platform default encoding (wraps exception handling).
     *
     * @param props
     *            Property set. Must not be {@literal null}.
     * @param file
     *            File. Must not be {@literal null}.
     * @param comments
     *            Comments.
     */
    public static void store(final Properties props, final File file, final String comments) {
        store(props, file, Charset.defaultCharset().name(), comments);
    }

}
