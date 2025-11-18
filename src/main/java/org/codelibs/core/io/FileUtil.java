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
import java.nio.file.Path;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.net.URLUtil;
import org.codelibs.core.nio.ChannelUtil;
import org.codelibs.core.timer.TimeoutManager;

/**
 * Utility class for handling {@link File}.
 * <p>
 * <strong>SECURITY NOTE:</strong> When accepting file paths from untrusted sources,
 * always validate them using {@link #isPathSafe(Path, Path)} to prevent path traversal attacks.
 * Methods that accept path strings do not perform automatic validation to maintain backward compatibility.
 * </p>
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
     * Validates that a given path is safe and does not attempt path traversal attacks.
     * <p>
     * This method checks if the resolved absolute path starts with the allowed base directory,
     * preventing access to files outside the intended directory through path traversal
     * techniques like "../../../etc/passwd".
     * </p>
     * <p>
     * Example usage:
     * </p>
     * <pre>
     * Path baseDir = Paths.get("/var/app/data");
     * Path userPath = Paths.get(userInput);
     * if (!FileUtil.isPathSafe(userPath, baseDir)) {
     *     throw new SecurityException("Path traversal attempt detected");
     * }
     * </pre>
     *
     * @param pathToCheck the path to validate (must not be {@literal null})
     * @param baseDirectory the base directory that the path must be within (must not be {@literal null})
     * @return true if the path is safe (within the base directory), false otherwise
     * @throws IORuntimeException if an I/O error occurs during path resolution
     */
    public static boolean isPathSafe(final Path pathToCheck, final Path baseDirectory) {
        assertArgumentNotNull("pathToCheck", pathToCheck);
        assertArgumentNotNull("baseDirectory", baseDirectory);

        try {
            final Path normalizedPath = pathToCheck.toAbsolutePath().normalize();
            final Path normalizedBase = baseDirectory.toAbsolutePath().normalize();
            return normalizedPath.startsWith(normalizedBase);
        } catch (final Exception e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Validates that a given file is safe and does not attempt path traversal attacks.
     * <p>
     * This is a convenience method that converts File objects to Path and calls
     * {@link #isPathSafe(Path, Path)}.
     * </p>
     *
     * @param fileToCheck the file to validate (must not be {@literal null})
     * @param baseDirectory the base directory that the file must be within (must not be {@literal null})
     * @return true if the file is safe (within the base directory), false otherwise
     * @throws IORuntimeException if an I/O error occurs during path resolution
     */
    public static boolean isPathSafe(final File fileToCheck, final File baseDirectory) {
        assertArgumentNotNull("fileToCheck", fileToCheck);
        assertArgumentNotNull("baseDirectory", baseDirectory);

        return isPathSafe(fileToCheck.toPath(), baseDirectory.toPath());
    }

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
     * <p>
     * <strong>Note:</strong> This method loads the entire file into memory.
     * For files larger than {@value #MAX_BUF_SIZE} bytes (10MB), an
     * {@link IORuntimeException} will be thrown to prevent OutOfMemoryError.
     * For large files, use streaming APIs instead.
     * </p>
     *
     * @param file
     *            The file. Must not be {@literal null}.
     * @return A byte array containing the contents of the file.
     * @throws IORuntimeException if the file is larger than {@value #MAX_BUF_SIZE} bytes
     */
    public static byte[] readBytes(final File file) {
        assertArgumentNotNull("file", file);

        final FileInputStream is = InputStreamUtil.create(file);
        try {
            final FileChannel channel = is.getChannel();
            final long fileSize = ChannelUtil.size(channel);

            if (fileSize > MAX_BUF_SIZE) {
                throw new IORuntimeException("File too large: " + fileSize + " bytes (max: " + MAX_BUF_SIZE + " bytes). Use streaming APIs for large files.");
            }

            final ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
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
                    // Enforce MAX_BUF_SIZE to prevent unbounded memory growth
                    final int newBufferSize = bufferSize + initialCapacity;
                    if (newBufferSize > MAX_BUF_SIZE) {
                        throw new IORuntimeException("Content too large: exceeds maximum buffer size of " + MAX_BUF_SIZE + " bytes. Use streaming APIs for large content.");
                    }
                    final char[] newBuf = new char[newBufferSize];
                    System.arraycopy(buf, 0, newBuf, 0, bufferSize);
                    buf = newBuf;
                    bufferSize = newBufferSize;
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
