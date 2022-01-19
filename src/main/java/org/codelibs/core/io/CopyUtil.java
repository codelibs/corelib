/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
 * コピーのためのユーティリティです。
 * <p>
 * コピー可能な入力と出力の組み合わせと、コピーされる要素の単位は以下のとおりです。
 * </p>
 * <table border="1">
 * <caption>Elements for coping instances</caption>
 * <tr>
 * <th rowspan="2">入力の型</th>
 * <th colspan="4">出力の型</th>
 * </tr>
 * <tr>
 * <th>{@link OutputStream}</th>
 * <th>{@link Writer}</th>
 * <th>{@link File}</th>
 * <th>{@link StringBuilder}</th>
 * </tr>
 * <tr>
 * <th>{@link InputStream}</th>
 * <td>バイト</td>
 * <td>文字</td>
 * <td>バイト、文字</td>
 * <td>文字</td>
 * </tr>
 * <tr>
 * <th>{@link Reader}</th>
 * <td>文字</td>
 * <td>文字</td>
 * <td>文字</td>
 * <td>文字</td>
 * </tr>
 * <tr>
 * <th>{@link File}</th>
 * <td>バイト</td>
 * <td>文字</td>
 * <td>バイト、文字</td>
 * <td>文字</td>
 * </tr>
 * <tr>
 * <th>{@link URL}</th>
 * <td>バイト</td>
 * <td>文字</td>
 * <td>バイト、文字</td>
 * <td>文字</td>
 * </tr>
 * <tr>
 * <th>{@literal byte[]}</th>
 * <td>バイト</td>
 * <td>文字</td>
 * <td>バイト、文字</td>
 * <td>文字</td>
 * </tr>
 * <tr>
 * <th>{@link String}</th>
 * <td>文字</td>
 * <td>文字</td>
 * <td>文字</td>
 * <td>×</td>
 * </tr>
 * </table>
 * <p>
 * 引数に{@link InputStream}/{@link OutputStream}/{@link Reader}/{@link Writer}
 * を受け取るメソッドは、 どれも引数に対して{@link Closeable#close()}を呼び出しません。 クローズする責務は呼び出し側にあります。
 * </p>
 * <p>
 * どのメソッドも発生した{@link IOException}は{@link IORuntimeException}にラップしてスローされます。
 * </p>
 *
 * @author koichik
 */
public abstract class CopyUtil {

    /** コピーで使用するバッファサイズ */
    protected static final int DEFAULT_BUF_SIZE = 4096;

    // ////////////////////////////////////////////////////////////////
    // from InputStream to OutputStream
    //
    /**
     * 入力ストリームから出力ストリームへコピーします。
     * <p>
     * 入力ストリーム、出力ストリームともクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * プラットフォームのデフォルトエンコーディングで入力ストリームからライターへコピーします。
     * <p>
     * 入力ストリーム、ライターともクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム。{@literal null}であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
     */
    public static int copy(final InputStream in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(wrap(in));
        return copyInternal(is, wrap(out));
    }

    /**
     * 指定のエンコーディングで入力ストリームからライターへコピーします。
     * <p>
     * 入力ストリーム、ライターともクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            ライター
     * @return コピーした文字数。{@literal null}であってはいけません
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
     * 入力ストリームからファイルへコピーします。
     * <p>
     * 入力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * プラットフォームのデフォルトエンコーディングで入力ストリームから{@link StringBuilder}へコピーします。
     * <p>
     * 入力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム。{@literal null}であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
     */
    public static int copy(final InputStream in, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(wrap(in));
        return copyInternal(is, out);
    }

    /**
     * 指定のエンコーディングで入力ストリームから{@link StringBuilder}へコピーします。
     * <p>
     * 入力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * プラットフォームのデフォルトエンコーディングでリーダーから出力ストリームへコピーします。
     * <p>
     * リーダー、出力ストリームともクローズされません。
     * </p>
     *
     * @param in
     *            リーダー。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @return コピーした文字数
     */
    public static int copy(final Reader in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Writer os = new OutputStreamWriter(wrap(out));
        return copyInternal(wrap(in), os);
    }

    /**
     * 指定のエンコーディングでリーダーから出力ストリームへコピーします。
     * <p>
     * リーダー、出力ストリームともクローズされません。
     * </p>
     *
     * @param in
     *            リーダー。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * リーダーからライターへコピーします。
     * <p>
     * リーダー、ライターともクローズされません。
     * </p>
     *
     * @param in
     *            リーダー。{@literal null}であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * プラットフォームのデフォルトエンコーディングでリーダーからファイルへコピーします。
     * <p>
     * リーダーはクローズされません。
     * </p>
     *
     * @param in
     *            リーダー。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * 指定のエンコーディングでリーダーからファイルへコピーします。
     *
     * @param in
     *            リーダー。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * リーダーから{@link StringBuilder}へコピーします。
     * <p>
     * リーダーはクローズされません。
     * </p>
     *
     * @param in
     *            リーダー。{@literal null}であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * ファイルから出力ストリームへコピーします。
     * <p>
     * 出力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            ファイル。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * プラットフォームのデフォルトエンコーディングでファイルからライターへコピーします。
     * <p>
     * ライターはクローズされません。
     * </p>
     *
     * @param in
     *            ファイル。{@literal null}であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * 指定のエンコーディングでファイルからライターへコピーします。
     * <p>
     * ライターはクローズされません。
     * </p>
     *
     * @param in
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * ファイルからファイルへコピーします。
     *
     * @param in
     *            入力ファイル。{@literal null}であってはいけません
     * @param out
     *            出力ファイル。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * 指定されたエンコーディングのファイルからプラットフォームデフォルトエンコーディングのファイルへコピーします。
     *
     * @param in
     *            入力ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            出力ファイル。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * プラットフォームデフォルトエンコーディングのファイルから指定されたエンコーディングのファイルへコピーします。
     *
     * @param in
     *            入力ファイル。{@literal null}であってはいけません
     * @param out
     *            出力ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * 指定されたエンコーディングのファイルから指定されたエンコーディングのファイルへコピーします。
     *
     * @param in
     *            入力ファイル。{@literal null}であってはいけません
     * @param inputEncoding
     *            入力ファイルのエンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            出力ファイル。{@literal null}であってはいけません
     * @param outputEncoding
     *            出力ファイルのエンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * プラットフォームのデフォルトエンコーディングでファイルから{@link StringBuilder}へコピーします。
     *
     * @param in
     *            ファイル。{@literal null}であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * 指定されたエンコーディングでファイルから{@link StringBuilder}へコピーします。
     *
     * @param in
     *            ファイル。{@literal null}であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * URLから出力ストリームへコピーします。
     * <p>
     * 出力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * プラットフォームのデフォルトエンコーディングでURLからライターへコピーします。
     * <p>
     * ライターはクローズされません。
     * </p>
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * 指定のエンコーディングでURLからライターへコピーします。
     * <p>
     * ライターはクローズされません。
     * </p>
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * URLからファイルへコピーします。
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * 指定されたエンコーディングのURLからプラットフォームデフォルトエンコーディングのファイルへコピーします。
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            出力ファイル。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * プラットフォームデフォルトエンコーディングのURLから指定されたエンコーディングのファイルへコピーします。
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * 指定されたエンコーディングのURLから指定されたエンコーディングのファイルへコピーします。
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param inputEncoding
     *            URLのエンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @param outputEncoding
     *            ファイルのエンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * プラットフォームのデフォルトエンコーディングでURLから{@link StringBuilder}へコピーします。
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * 指定されたエンコーディングでURLから{@link StringBuilder}へコピーします。
     *
     * @param in
     *            URL。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * バイト配列から出力ストリームへコピーします。
     * <p>
     * 出力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * プラットフォームのデフォルトエンコーディングでバイト配列からライターへコピーします。
     * <p>
     * ライターはクローズされません。
     * </p>
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
     */
    public static int copy(final byte[] in, final Writer out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(new ByteArrayInputStream(in));
        return copyInternal(is, wrap(out));
    }

    /**
     * 指定されたエンコーディングでバイト配列からライターへコピーします。
     * <p>
     * ライターはクローズされません。
     * </p>
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * バイト配列からファイルへコピーします。
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @return コピーしたバイト数
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
     * 指定されたエンコーディングのバイト配列からプラットフォームデフォルトエンコーディングのファイルへコピーします。
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * プラットフォームデフォルトエンコーディングのバイト配列から指定されたエンコーディングのファイルへコピーします。
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング
     * @return コピーした文字数
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
     * 指定されたエンコーディングのバイト配列から指定されたエンコーディングのファイルへコピーします。
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param inputEncoding
     *            入力のエンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @param outputEncoding
     *            出力のエンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * プラットフォームのデフォルトエンコーディングでバイト配列から{@link StringBuilder}へコピーします。
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
     */
    public static int copy(final byte[] in, final StringBuilder out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Reader is = new InputStreamReader(new ByteArrayInputStream(in));
        return copyInternal(is, out);
    }

    /**
     * 指定されたエンコーディングのバイト配列からプラットフォームデフォルトエンコーディングの{@link StringBuilder}へコピーします。
     *
     * @param in
     *            バイト配列。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param out
     *            {@link StringBuilder}。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * プラットフォームのデフォルトエンコーディングで文字列を出力ストリームへコピーします。
     * <p>
     * 出力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            文字列。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @return コピーした文字数
     */
    public static int copy(final String in, final OutputStream out) {
        assertArgumentNotNull("in", in);
        assertArgumentNotNull("out", out);

        final Writer os = new OutputStreamWriter(wrap(out));
        return copyInternal(new StringReader(in), os);
    }

    /**
     * 指定されたエンコーディングで文字列を出力ストリームへコピーします。
     * <p>
     * 出力ストリームはクローズされません。
     * </p>
     *
     * @param in
     *            文字列。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * 文字列をライターへコピーします。
     * <p>
     * ライターはクローズされません。
     * </p>
     *
     * @param in
     *            文字列。{@literal null}であってはいけません
     * @param out
     *            ライター。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * プラットフォームのデフォルトエンコーディングで文字列をファイルへコピーします。
     *
     * @param in
     *            文字列。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @return コピーした文字数
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
     * 指定されたエンコーディングで文字列をファイルへコピーします。
     *
     * @param in
     *            文字列。{@literal null}であってはいけません
     * @param out
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return コピーした文字数
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
     * 入力ストリームの内容を出力ストリームにコピーします。
     * <p>
     * 入力ストリーム、出力ストリームともクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム
     * @param out
     *            出力ストリーム
     * @return コピーしたバイト数
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
     * ファイル入力ストリームの内容を出力ストリームにコピーします。
     * <p>
     * ファイル入力ストリーム、出力ストリームともクローズされません。
     * </p>
     *
     * @param in
     *            ファイル入力ストリーム
     * @param out
     *            出力ストリーム
     * @return コピーしたバイト数
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
     * 入力ストリームの内容をファイル出力ストリームにコピーします。
     * <p>
     * 入力ストリーム、ファイル出力ストリームともクローズされません。
     * </p>
     *
     * @param in
     *            入力ストリーム
     * @param out
     *            ファイル出力ストリーム
     * @return コピーしたバイト数
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
     * ファイル入力ストリームの内容をファイル出力ストリームにコピーします。
     * <p>
     * ファイル入力ストリーム、ファイル出力ストリームともクローズされません。
     * </p>
     *
     * @param in
     *            ファイル入力ストリーム
     * @param out
     *            ファイル出力ストリーム
     * @return コピーしたバイト数
     */
    protected static int copyInternal(final FileInputStream in, final FileOutputStream out) {
        final FileChannel ic = in.getChannel();
        final FileChannel oc = out.getChannel();
        return (int) ChannelUtil.transfer(ic, oc);
    }

    /**
     * リーダーの内容をライターにコピーします。
     * <p>
     * リーダー、ライターともクローズされません。
     * </p>
     *
     * @param in
     *            リーダー
     * @param out
     *            ライター
     * @return コピーした文字数
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
     * リーダーの内容を{@link StringBuilder}にコピーします。
     *
     * @param in
     *            リーダー
     * @param out
     *            {@link StringBuilder}
     * @return コピーした文字数
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
     * 必要があれば入力ストリームを{@link BufferedInputStream}でラップします。
     *
     * @param is
     *            入力ストリーム
     * @return ラップされた入力ストリーム
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
     * 必要があれば出力ストリームを{@link BufferedOutputStream}でラップします。
     *
     * @param os
     *            出力ストリーム
     * @return ラップされた出力ストリーム
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
     * 必要があればリーダーを{@link BufferedReader}でラップします。
     *
     * @param reader
     *            リーダー
     * @return ラップされたリーダー
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
     * 必要があればライターを{@link BufferedWriter}でラップします。
     *
     * @param writer
     *            ライター
     * @return ラップされたライター
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
