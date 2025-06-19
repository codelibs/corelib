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
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.net.URLUtil;
import org.codelibs.core.nio.ChannelUtil;

/**
 * Utility for copying.
 * <p>
 * The combinations of input and output types and the unit of elements copied are as follows:
 * </p>
 * <table border="1">
 * <caption>Elements for copying instances</caption>
 * <tr>
 * <th rowspan="2">Input type</th>
 * <th colspan="4">Output type</th>
 * </tr>
 * <tr>
 * <th>{@link OutputStream}</th>
 * <th>{@link Writer}</th>
 * <th>{@link File}</th>
 * <th>{@link StringBuilder}</th>
 * </tr>
 * <tr>
 * <th>{@link InputStream}</th>
 * <td>bytes</td>
 * <td>characters</td>
 * <td>bytes, characters</td>
 * <td>characters</td>
 * </tr>
 * <tr>
 * <th>{@link Reader}</th>
 * <td>characters</td>
 * <td>characters</td>
 * <td>characters</td>
 * <td>characters</td>
 * </tr>
 * <tr>
 * <th>{@link File}</th>
 * <td>bytes</td>
 * <td>characters</td>
 * <td>bytes, characters</td>
 * <td>characters</td>
 * </tr>
 * <tr>
 * <th>{@link URL}</th>
 * <td>bytes</td>
 * <td>characters</td>
 * <td>bytes, characters</td>
 * <td>characters</td>
 * </tr>
 * <tr>
 * <th>{@literal byte[]}</th>
 * <td>bytes</td>
 * <td>characters</td>
 * <td>bytes, characters</td>
 * <td>characters</td>
 * </tr>
 * <tr>
 * <th>{@link String}</th>
 * <td>characters</td>
 * <td>characters</td>
 * <td>characters</td>
 * <td>&times;</td>
 * </tr>
 * </table>
 * <p>
 * Methods that take {@link InputStream}/{@link OutputStream}/{@link Reader}/{@link Writer} as arguments do not call {@link Closeable#close()} on the arguments. The caller is responsible for closing them.
 * </p>
 * <p>
 * Any {@link IOException} thrown by these methods is wrapped and thrown as an {@link IORuntimeException}.
 * </p>
 *
 * @author koichik
 */
public abstract class CopyUtil {

    /** Buffer size used for copying. */
    protected static final int DEFAULT_BUF_SIZE = 4096;

    // ////////////////////////////////////////////////////////////////
    // from InputStream to OutputStream
    //
    /**
     * Copies from an input stream to an output stream.
     * <p>
     * Neither the input stream nor the output stream is closed.
     * </p>
     *
     * @param in the input stream (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final InputStream in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        if (in instanceof FileInputStream) {
            if (out instanceof FileOutputStream) {
                return copyInternal((FileInputStream) in, (FileOutputStream) out);
            }
            return copyInternal((FileInputStream) in, wrap(out));
        }
        if (out instanceof FileOutputStream) {
            return copyInternal(wrap(in), (FileOutputStream) out);
        }
        return copyInternal(wrap(in), wrap(out));
    }

    // ////////////////////////////////////////////////////////////////
    // from InputStream to Writer
    //
    /**
     * Copies from an input stream to a writer using the platform default encoding.
     * <p>
     * Neither the input stream nor the writer is closed.
     * </p>
     *
     * @param in the input stream (must not be {@literal null})
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final InputStream in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(wrap(in));
        return copyInternal(is, wrap(out));
    }

    /**
     * Copies from an input stream to a writer using the specified encoding.
     * <p>
     * Neither the input stream nor the writer is closed.
     * </p>
     *
     * @param in the input stream (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final InputStream in, final String encoding, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(wrap(in), encoding);
        return copyInternal(is, wrap(out));
    }

    // ////////////////////////////////////////////////////////////////
    // from InputStream to File
    //
    /**
     * Copies from an input stream to a file.
     * <p>
     * The input stream is not closed.
     * </p>
     *
     * @param in the input stream (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final InputStream in, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final FileOutputStream os = OutputStreamUtil.create(out);
        try {
            if (in instanceof FileInputStream) {
                return copyInternal((FileInputStream) in, os);
            }
            return copyInternal(wrap(in), os);
        } finally {
            CloseableUtil.close(os);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from InputStream to StringBuilder
    //
    /**
     * Copies from an input stream to a {@link StringBuilder} using the platform default encoding.
     * <p>
     * The input stream is not closed.
     * </p>
     *
     * @param in the input stream (must not be {@literal null})
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final InputStream in, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(wrap(in));
        return copyInternal(is, out);
    }

    /**
     * Copies from an input stream to a {@link StringBuilder} using the specified encoding.
     * <p>
     * The input stream is not closed.
     * </p>
     *
     * @param in the input stream (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final InputStream in, final String encoding, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(wrap(in), encoding);
        return copyInternal(is, out);
    }

    // ////////////////////////////////////////////////////////////////
    // from Reader to OutputStream
    //
    /**
     * Copies from a reader to an output stream using the platform default encoding.
     * <p>
     * Neither the reader nor the output stream is closed.
     * </p>
     *
     * @param in the reader (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final Reader in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Writer os = new OutputStreamWriter(wrap(out));
        return copyInternal(wrap(in), os);
    }

    /**
     * Copies from a reader to an output stream using the specified encoding.
     * <p>
     * Neither the reader nor the output stream is closed.
     * </p>
     *
     * @param in the reader (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final Reader in, final OutputStream out, final String encoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("encoding", encoding);

        final Writer os = WriterUtil.create(wrap(out), encoding);
        return copyInternal(wrap(in), os);
    }

    // ////////////////////////////////////////////////////////////////
    // from Reader to Writer
    //
    /**
     * Copies from a reader to a writer.
     * <p>
     * Neither the reader nor the writer is closed.
     * </p>
     *
     * @param in the reader (must not be {@literal null})
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final Reader in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        return copyInternal(wrap(in), wrap(out));
    }

    // ////////////////////////////////////////////////////////////////
    // from Reader to File
    //
    /**
     * Copies from a reader to a file using the platform default encoding.
     * <p>
     * The reader is not closed.
     * </p>
     *
     * @param in the reader (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final Reader in, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Writer os = WriterUtil.create(out);
        try {
            return copyInternal(wrap(in), wrap(os));
        } finally {
            CloseableUtil.close(os);
        }
    }

    /**
     * Copies from a reader to a file using the specified encoding.
     *
     * @param in the reader (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final Reader in, final File out, final String encoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("encoding", encoding);

        final Writer os = WriterUtil.create(out, encoding);
        try {
            return copyInternal(wrap(in), wrap(os));
        } finally {
            CloseableUtil.close(os);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from Reader to StringBuilder
    //
    /**
     * Copies from a reader to a {@link StringBuilder}.
     * <p>
     * The reader is not closed.
     * </p>
     *
     * @param in the reader (must not be {@literal null})
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final Reader in, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        return copyInternal(wrap(in), out);
    }

    // ////////////////////////////////////////////////////////////////
    // from File to OutputStream
    //
    /**
     * Copies from a file to an output stream.
     * <p>
     * The output stream is not closed.
     * </p>
     *
     * @param in the file (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final File in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final FileInputStream is = InputStreamUtil.create(in);
        try {
            if (out instanceof FileOutputStream) {
                return copyInternal(is, (FileOutputStream) out);
            }
            return copyInternal(is, wrap(out));
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from File to Writer
    //
    /**
     * Copies from a file to a writer using the platform default encoding.
     * <p>
     * The writer is not closed.
     * </p>
     *
     * @param in the file (must not be {@literal null})
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final File in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(in);
        try {
            return copyInternal(wrap(is), wrap(out));
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a file to a writer using the specified encoding.
     * <p>
     * The writer is not closed.
     * </p>
     *
     * @param in the file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final File in, final String encoding, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(in, encoding);
        try {
            return copyInternal(wrap(is), wrap(out));
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from File to File
    //
    /**
     * Copies from a file to a file.
     *
     * @param in the input file (must not be {@literal null})
     * @param out the output file (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final File in, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final FileInputStream is = InputStreamUtil.create(in);
        try {
            final FileOutputStream os = OutputStreamUtil.create(out);
            try {
                return copyInternal(is, os);
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a file with the specified encoding to a file with the platform default encoding.
     *
     * @param in the input file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the output file (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final File in, final String encoding, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(in, encoding);
        try {
            final Writer os = WriterUtil.create(out);
            try {
                return copyInternal(wrap(is), wrap(os));
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a file with the platform default encoding to a file with the specified encoding.
     *
     * @param in the input file (must not be {@literal null})
     * @param out the output file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final File in, final File out, final String encoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("encoding", encoding);

        final Reader is = ReaderUtil.create(in);
        try {
            final Writer os = WriterUtil.create(out, encoding);
            try {
                return copyInternal(wrap(is), wrap(os));
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a file with the specified encoding to a file with the specified encoding.
     *
     * @param in the input file (must not be {@literal null})
     * @param inputEncoding the input file encoding (must not be {@literal null} or empty)
     * @param out the output file (must not be {@literal null})
     * @param outputEncoding the output file encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final File in, final String inputEncoding, final File out, final String outputEncoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("inputEncoding", inputEncoding);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("outputEncoding", outputEncoding);

        final Reader is = ReaderUtil.create(in, inputEncoding);
        try {
            final Writer os = WriterUtil.create(out, outputEncoding);
            try {
                return copyInternal(wrap(is), wrap(os));
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from File to StringBuilder
    //
    /**
     * Copies from a file to a {@link StringBuilder} using the platform default encoding.
     *
     * @param in the file (must not be {@literal null})
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final File in, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(in);
        try {
            return copyInternal(wrap(is), out);
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a file to a {@link StringBuilder} using the specified encoding.
     *
     * @param in the file (must not be {@literal null})
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final File in, final String encoding, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(in, encoding);
        try {
            return copyInternal(wrap(is), out);
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from URL to OutputStream
    //
    /**
     * Copies from a URL to an output stream.
     * <p>
     * The output stream is not closed.
     * </p>
     *
     * @param in the URL (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final URL in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final InputStream is = URLUtil.openStream(in);
        try {
            if (out instanceof FileOutputStream) {
                return copyInternal(wrap(is), (FileOutputStream) out);
            }
            return copyInternal(wrap(is), wrap(out));
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from URL to Writer
    //
    /**
     * Copies from a URL to a writer using the platform default encoding.
     * <p>
     * The writer is not closed.
     * </p>
     *
     * @param in the URL (must not be {@literal null})
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final URL in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final InputStream is = URLUtil.openStream(in);
        try {
            return copyInternal(new InputStreamReader(wrap(is)), wrap(out));
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a URL to a writer using the specified encoding.
     * <p>
     * The writer is not closed.
     * </p>
     *
     * @param in the URL (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final URL in, final String encoding, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final InputStream is = URLUtil.openStream(in);
        try {
            return copyInternal(ReaderUtil.create(wrap(is), encoding), wrap(out));
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from URL to File
    //
    /**
     * Copies from a URL to a file.
     *
     * @param in the URL (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final URL in, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final InputStream is = URLUtil.openStream(in);
        try {
            final FileOutputStream os = OutputStreamUtil.create(out);
            try {
                return copyInternal(wrap(is), os);
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a URL with the specified encoding to a file with the platform default encoding.
     *
     * @param in the URL (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the output file (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final URL in, final String encoding, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final InputStream is = URLUtil.openStream(in);
        try {
            final Writer os = WriterUtil.create(out);
            try {
                return copyInternal(ReaderUtil.create(wrap(is), encoding), wrap(os));
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a URL with the platform default encoding to a file with the specified encoding.
     *
     * @param in the URL (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final URL in, final File out, final String encoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("encoding", encoding);

        final InputStream is = URLUtil.openStream(in);
        try {
            final Writer os = WriterUtil.create(out, encoding);
            try {
                return copyInternal(new InputStreamReader(wrap(is)), wrap(os));
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a URL with the specified encoding to a file with the specified encoding.
     *
     * @param in the URL (must not be {@literal null})
     * @param inputEncoding the URL encoding (must not be {@literal null} or empty)
     * @param out the file (must not be {@literal null})
     * @param outputEncoding the file encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final URL in, final String inputEncoding, final File out, final String outputEncoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("inputEncoding", inputEncoding);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("outputEncoding", outputEncoding);

        final InputStream is = URLUtil.openStream(in);
        try {
            final Writer os = WriterUtil.create(out, outputEncoding);
            try {
                return copyInternal(ReaderUtil.create(wrap(is), inputEncoding), wrap(os));
            } finally {
                CloseableUtil.close(os);
            }
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from URL to StringBuilder
    //
    /**
     * Copies from a URL to a {@link StringBuilder} using the platform default encoding.
     *
     * @param in the URL (must not be {@literal null})
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final URL in, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final InputStream is = URLUtil.openStream(in);
        try {
            return copyInternal(new InputStreamReader(wrap(is)), out);
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * Copies from a URL to a {@link StringBuilder} using the specified encoding.
     *
     * @param in the URL (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final URL in, final String encoding, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final InputStream is = URLUtil.openStream(in);
        try {
            return copyInternal(ReaderUtil.create(wrap(is), encoding), out);
        } finally {
            CloseableUtil.close(is);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from bytes to OutputStream
    //
    /**
     * Copies from a byte array to an output stream.
     * <p>
     * The output stream is not closed.
     * </p>
     *
     * @param in the byte array (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final byte[] in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final ByteArrayInputStream is = new ByteArrayInputStream(in);
        if (out instanceof FileOutputStream) {
            return copyInternal(is, (FileOutputStream) out);
        }
        return copyInternal(is, wrap(out));
    }

    // ////////////////////////////////////////////////////////////////
    // from bytes to Writer
    //
    /**
     * Copies from a byte array to a writer using the platform default encoding.
     * <p>
     * The writer is not closed.
     * </p>
     *
     * @param in the byte array (must not be {@literal null})
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final byte[] in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(new ByteArrayInputStream(in));
        return copyInternal(is, wrap(out));
    }

    /**
     * Copies from a byte array to a writer using the specified encoding.
     * <p>
     * The writer is not closed.
     * </p>
     *
     * @param in the byte array (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final byte[] in, final String encoding, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(new ByteArrayInputStream(in), encoding);
        return copyInternal(is, wrap(out));
    }

    // ////////////////////////////////////////////////////////////////
    // from bytes to File
    //
    /**
     * Copies from a byte array to a file.
     *
     * @param in the byte array (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @return the number of bytes copied
     */
    public static int copy(final byte[] in, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final FileOutputStream os = OutputStreamUtil.create(out);
        try {
            final FileChannel channel = os.getChannel();
            final ByteBuffer buffer = ByteBuffer.wrap(in);
            return ChannelUtil.write(channel, buffer);
        } finally {
            CloseableUtil.close(os);
        }
    }

    /**
     * Copies from a byte array with the specified encoding to a file with the platform default encoding.
     *
     * @param in the byte array (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @param out the file (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final byte[] in, final String encoding, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(new ByteArrayInputStream(in), encoding);
        final Writer os = WriterUtil.create(out);
        try {
            return copyInternal(is, wrap(os));
        } finally {
            CloseableUtil.close(os);
        }
    }

    /**
     * Copies from a byte array with the platform default encoding to a file with the specified encoding.
     *
     * @param in the byte array (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final byte[] in, final File out, final String encoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("encoding", encoding);

        final Reader is = new InputStreamReader(new ByteArrayInputStream(in));
        final Writer os = WriterUtil.create(out, encoding);
        try {
            return copyInternal(is, wrap(os));
        } finally {
            CloseableUtil.close(os);
        }
    }

    /**
     * Copies from a byte array with the specified encoding to a file with the specified encoding.
     *
     * @param in the byte array (must not be {@literal null})
     * @param inputEncoding the input encoding (must not be {@literal null} or empty)
     * @param out the file (must not be {@literal null})
     * @param outputEncoding the output encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final byte[] in, final String inputEncoding, final File out, final String outputEncoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("inputEncoding", inputEncoding);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("outputEncoding", outputEncoding);

        final Reader is = ReaderUtil.create(new ByteArrayInputStream(in), inputEncoding);
        final Writer os = WriterUtil.create(out, outputEncoding);
        try {
            return copyInternal(is, wrap(os));
        } finally {
            CloseableUtil.close(os);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // from bytes to StringBuilder
    //
    /**
     * Copies from a byte array to a {@link StringBuilder} using the platform default encoding.
     *
     * @param in the byte array (must not be {@literal null})
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final byte[] in, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(new ByteArrayInputStream(in));
        return copyInternal(is, out);
    }

    /**
     * Copies from a byte array with the specified encoding to a {@link StringBuilder} using the platform default encoding.
     *
     * @param in the byte array (must not be {@literal null})
     * @param out the {@link StringBuilder} (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final byte[] in, final String encoding, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotEmpty("encoding", encoding);
        assertArgumentNotNull("out", out);

        final Reader is = ReaderUtil.create(new ByteArrayInputStream(in), encoding);
        return copyInternal(is, out);
    }

    // ////////////////////////////////////////////////////////////////
    // from String to OutputStream
    //
    /**
     * Copies a string to an output stream using the platform default encoding.
     * <p>
     * The output stream is not closed.
     * </p>
     *
     * @param in the string (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final String in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Writer os = new OutputStreamWriter(wrap(out));
        return copyInternal(new StringReader(in), os);
    }

    /**
     * Copies a string to an output stream using the specified encoding.
     * <p>
     * The output stream is not closed.
     * </p>
     *
     * @param in the string (must not be {@literal null})
     * @param out the output stream (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final String in, final OutputStream out, final String encoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("encoding", encoding);

        final Writer os = WriterUtil.create(wrap(out), encoding);
        return copyInternal(new StringReader(in), os);
    }

    // ////////////////////////////////////////////////////////////////
    // from String to Writer
    //
    /**
     * Copies a string to a writer.
     * <p>
     * The writer is not closed.
     * </p>
     *
     * @param in the string (must not be {@literal null})
     * @param out the writer (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final String in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        return copyInternal(new StringReader(in), wrap(out));
    }

    // ////////////////////////////////////////////////////////////////
    // from String to File
    //
    /**
     * Copies a string to a file using the platform default encoding.
     *
     * @param in the string (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @return the number of characters copied
     */
    public static int copy(final String in, final File out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Writer os = WriterUtil.create(out);
        try {
            return copyInternal(new StringReader(in), wrap(os));
        } finally {
            CloseableUtil.close(os);
        }
    }

    /**
     * Copies a string to a file using the specified encoding.
     *
     * @param in the string (must not be {@literal null})
     * @param out the file (must not be {@literal null})
     * @param encoding the encoding (must not be {@literal null} or empty)
     * @return the number of characters copied
     */
    public static int copy(final String in, final File out, final String encoding) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);
        assertArgumentNotEmpty("encoding", encoding);

        final Writer os = WriterUtil.create(out, encoding);
        try {
            return copyInternal(new StringReader(in), wrap(os));
        } finally {
            CloseableUtil.close(os);
        }
    }

    // ////////////////////////////////////////////////////////////////
    // internal methods
    //
    /**
     * Copies the contents of an input stream to an output stream.
     * <p>
     * Neither the input stream nor the output stream is closed.
     * </p>
     *
     * @param in the input stream
     * @param out the output stream
     * @return the number of bytes copied
     */
    protected static int copyInternal(final InputStream in, final OutputStream out) {
        try {
            final byte[] buf = new byte[DEFAULT_BUF_SIZE];
            int len;
            int amount = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                amount += len;
            }
            out.flush();
            return amount;
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Copies the contents of a file input stream to an output stream.
     * <p>
     * Neither the file input stream nor the output stream is closed.
     * </p>
     *
     * @param in the file input stream
     * @param out the output stream
     * @return the number of bytes copied
     */
    protected static int copyInternal(final FileInputStream in, final OutputStream out) {
        try {
            final FileChannel channel = in.getChannel();
            final ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUF_SIZE);
            final byte[] buf = buffer.array();
            int len;
            int amount = 0;
            while ((len = ChannelUtil.read(channel, buffer, amount)) != -1) {
                out.write(buf, 0, len);
                buffer.clear();
                amount += len;
            }
            out.flush();
            return amount;
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Copies the contents of an input stream to a file output stream.
     * <p>
     * Neither the input stream nor the file output stream is closed.
     * </p>
     *
     * @param in the input stream
     * @param out the file output stream
     * @return the number of bytes copied
     */
    protected static int copyInternal(final InputStream in, final FileOutputStream out) {
        try {
            final FileChannel channel = out.getChannel();
            final ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUF_SIZE);
            final byte[] buf = buffer.array();
            int len;
            int amount = 0;
            while ((len = in.read(buf)) != -1) {
                buffer.limit(len);
                channel.write(buffer, amount);
                buffer.clear();
                amount += len;
            }
            out.flush();
            return amount;
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Copies the contents of a file input stream to a file output stream.
     * <p>
     * Neither the file input stream nor the file output stream is closed.
     * </p>
     *
     * @param in the file input stream
     * @param out the file output stream
     * @return the number of bytes copied
     */
    protected static int copyInternal(final FileInputStream in, final FileOutputStream out) {
        final FileChannel ic = in.getChannel();
        final FileChannel oc = out.getChannel();
        return (int) ChannelUtil.transfer(ic, oc);
    }

    /**
     * Copies the contents of a reader to a writer.
     * <p>
     * Neither the reader nor the writer is closed.
     * </p>
     *
     * @param in the reader
     * @param out the writer
     * @return the number of characters copied
     */
    protected static int copyInternal(final Reader in, final Writer out) {
        try {
            final char[] buf = new char[DEFAULT_BUF_SIZE];
            int len;
            int amount = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                amount += len;
            }
            out.flush();
            return amount;
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Copies the contents of a reader to a {@link StringBuilder}.
     *
     * @param in the reader
     * @param out the {@link StringBuilder}
     * @return the number of characters copied
     */
    protected static int copyInternal(final Reader in, final StringBuilder out) {
        try {
            final char[] buf = new char[DEFAULT_BUF_SIZE];
            int len;
            int amount = 0;
            while ((len = in.read(buf)) != -1) {
                out.append(buf, 0, len);
                amount += len;
            }
            return amount;
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Wraps the input stream with a {@link BufferedInputStream} if necessary.
     *
     * @param is the input stream
     * @return the wrapped input stream
     */
    protected static InputStream wrap(final InputStream is) {
        if (is instanceof BufferedInputStream) {
            return is;
        }
        if (is instanceof ByteArrayInputStream) {
            return is;
        }
        return new BufferedInputStream(is, DEFAULT_BUF_SIZE);
    }

    /**
     * Wraps the output stream with a {@link BufferedOutputStream} if necessary.
     *
     * @param os the output stream
     * @return the wrapped output stream
     */
    protected static OutputStream wrap(final OutputStream os) {
        if (os instanceof BufferedOutputStream) {
            return os;
        }
        if (os instanceof ByteArrayOutputStream) {
            return os;
        }
        return new BufferedOutputStream(os, DEFAULT_BUF_SIZE);
    }

    /**
     * Wraps the reader with a {@link BufferedReader} if necessary.
     *
     * @param reader the reader
     * @return the wrapped reader
     */
    protected static Reader wrap(final Reader reader) {
        if (reader instanceof BufferedReader) {
            return reader;
        }
        if (reader instanceof StringReader) {
            return reader;
        }
        return new BufferedReader(reader, DEFAULT_BUF_SIZE);
    }

    /**
     * Wraps the writer with a {@link BufferedWriter} if necessary.
     *
     * @param writer the writer
     * @return the wrapped writer
     */
    protected static Writer wrap(final Writer writer) {
        if (writer instanceof BufferedWriter) {
            return writer;
        }
        if (writer instanceof StringWriter) {
            return writer;
        }
        return new BufferedWriter(writer, DEFAULT_BUF_SIZE);
    }

}
