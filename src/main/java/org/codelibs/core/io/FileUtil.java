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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.net.URLUtil;
import org.codelibs.core.nio.ChannelUtil;
import org.codelibs.core.timer.TimeoutManager;

/**
 * Utility class for handling {@link File}.
 *
 * @author higa
 */
public abstract class FileUtil {

    /**
     * Do not instantiate.
     */
    protected FileUtil() {
    }

    /** The encoding name for UTF-8. */
    private static final String UTF8 = "UTF-8";

    /** Default Buffer Size */
    protected static final int DEFAULT_BUF_SIZE = 4096; // 4k

    /** Max Buffer Size */
    protected static final int MAX_BUF_SIZE = 10 * 1024 * 1024; // 10m

    /**
     * Returns the canonical path string for this abstract pathname.
     *
     * @param file the file (must not be {@literal null})
     * @return the canonical pathname string representing the same file or directory as this abstract pathname
     */
    public static String getCanonicalPath(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return file.getCanonicalPath();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Converts this abstract pathname to a <code>file:</code> URL.
     *
     * @param file the file (must not be {@literal null})
     * @return a URL object representing the file URL
     */
    public static URL toURL(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return file.toURI().toURL();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Reads the contents of a file into a byte array and returns it.
     *
     * @param file
     *            The file. Must not be {@literal null}.
     * @return A byte array containing the contents of the file.
     */
    public static byte[] readBytes(final File file) {
        assertArgumentNotNull("file", file);

        final FileInputStream is = InputStreamUtil.create(file);
        try {
            final FileChannel channel = is.getChannel();
            final ByteBuffer buffer = ByteBuffer.allocate((int) ChannelUtil.size(channel));
            ChannelUtil.read(channel, buffer);
            return buffer.array();
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Reads text from a file using the default encoding.
     *
     * @param path
     *            The file path. Must not be {@literal null} or empty.
     * @return The text read from the file.
     */
    public static String readText(final String path) {
        assertArgumentNotEmpty("path", path);
        return readText(path, Charset.defaultCharset().name());
    }

    /**
     * Reads text from a file using the default encoding.
     *
     * @param file
     *            The file. Must not be {@literal null}.
     * @return The text read from the file.
     */
    public static String readText(final File file) {
        assertArgumentNotNull("file", file);
        return readText(file, Charset.defaultCharset().name());
    }

    /**
     * Reads text from a file with the specified encoding.
     *
     * @param path
     *            The file path. Must not be {@literal null} or empty.
     * @param encoding
     *            The encoding. Must not be {@literal null} or empty.
     * @return The text read from the file.
     */
    public static String readText(final String path, final String encoding) {
        assertArgumentNotEmpty("path", path);
        assertArgumentNotEmpty("encoding", encoding);

        final URL url = ResourceUtil.getResource(path);
        if (url.getProtocol().equals("file")) {
            return readText(URLUtil.toFile(url), encoding);
        }
        final InputStream is = URLUtil.openStream(url);
        try {
            final Reader reader = ReaderUtil.create(new BufferedInputStream(is, DEFAULT_BUF_SIZE), encoding);
            return read(reader, DEFAULT_BUF_SIZE);
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Reads text from a file with the specified encoding.
     *
     * @param file
     *            The file. Must not be {@literal null}.
     * @param encoding
     *            The encoding. Must not be {@literal null} or empty.
     * @return The text read from the file.
     */
    public static String readText(final File file, final String encoding) {
        assertArgumentNotNull("file", file);
        assertArgumentNotEmpty("encoding", encoding);

        final FileInputStream is = InputStreamUtil.create(file);
        try {
            final Reader reader = ReaderUtil.create(new BufferedInputStream(is, DEFAULT_BUF_SIZE), encoding);
            return read(reader, (int) ChannelUtil.size(is.getChannel()));
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Reads text from a file in UTF-8 encoding.
     *
     * @param path
     *            The path. Must not be {@literal null} or empty.
     * @return The text read from the file.
     */
    public static String readUTF8(final String path) {
        assertArgumentNotEmpty("path", path);
        return readText(path, UTF8);
    }

    /**
     * Reads text from a file in UTF-8 encoding.
     *
     * @param file
     *            The file. Must not be {@literal null}.
     * @return The text read from the file.
     */
    public static String readUTF8(final File file) {
        assertArgumentNotNull("file", file);
        return readText(file, UTF8);
    }

    /**
     * Returns the contents read from the reader as a string.
     *
     * @param reader
     *            the reader
     * @param initialCapacity
     *            the initial buffer capacity
     * @return the string read from the reader
     */
    protected static String read(final Reader reader, final int initialCapacity) {
        int bufferSize;
        if (initialCapacity > 0 && initialCapacity <= MAX_BUF_SIZE) {
            bufferSize = initialCapacity;
        } else {
            bufferSize = DEFAULT_BUF_SIZE;
        }
        char[] buf = new char[bufferSize];
        int size = 0;
        int len;
        try {
            while ((len = reader.read(buf, size, bufferSize - size)) != -1) {
                size += len;
                if (size == bufferSize) {
                    final char[] newBuf = new char[bufferSize + initialCapacity];
                    System.arraycopy(buf, 0, newBuf, 0, bufferSize);
                    buf = newBuf;
                    bufferSize += initialCapacity;
                }
            }
            return new String(buf, 0, size);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Writes the specified byte array to the file at the given pathname.
     *
     * @param pathname
     *            The path to the file.
     * @param bytes
     *            The byte array to write.
     */
    public static void writeBytes(final String pathname, final byte[] bytes) {
        try (FileOutputStream fos = OutputStreamUtil.create(new File(pathname))) {
            ChannelUtil.write(fos.getChannel(), ByteBuffer.wrap(bytes));
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Deletes the specified file in a background thread.
     *
     * @param file
     *            The file to delete.
     */
    public static void deleteInBackground(final File file) {
        if (file != null) {
            TimeoutManager.getInstance().addTimeoutTarget(() -> {
                try {
                    Files.deleteIfExists(file.toPath());
                } catch (final IOException e) {
                    throw new IORuntimeException(e);
                }
            }, 0, false);
        }
    }
}
