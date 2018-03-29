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

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.net.URLUtil;
import org.codelibs.core.nio.ChannelUtil;

/**
 * {@link File}を扱うユーティリティ・クラスです。
 *
 * @author higa
 */
public abstract class FileUtil {

    /** UTF-8のエンコーディング名 */
    private static final String UTF8 = "UTF-8";

    /** Default Buffer Size */
    protected static final int DEFAULT_BUF_SIZE = 4096; // 4k

    /** Max Buffer Size */
    protected static final int MAX_BUF_SIZE = 10 * 1024 * 1024; // 10m

    /**
     * この抽象パス名の正規の形式を返します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return この抽象パス名と同じファイルまたはディレクトリを示す正規パス名文字列
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
     * この抽象パス名を<code>file:</code> URLに変換します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return ファイルURLを表すURLオブジェクト
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
     * ファイルの内容をバイト配列に読み込んで返します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return ファイルの内容を読み込んだバイト配列
     */
    public static byte[] readBytes(final File file) {
        assertArgumentNotNull("file", file);

        final FileInputStream is = InputStreamUtil.create(file);
        try {
            final FileChannel channel = is.getChannel();
            final ByteBuffer buffer = ByteBuffer.allocate((int) ChannelUtil
                    .size(channel));
            ChannelUtil.read(channel, buffer);
            return buffer.array();
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * デフォルトエンコーディングでファイルからテキストを読み込みます。
     *
     * @param path
     *            ファイルのパス。{@literal null}や空文字列であってはいけません
     * @return 読み込んだテキスト
     */
    public static String readText(final String path) {
        assertArgumentNotEmpty("path", path);
        return readText(path, Charset.defaultCharset().name());
    }

    /**
     * デフォルトエンコーディングでファイルからテキストを読み込みます。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return 読み込んだテキスト
     */
    public static String readText(final File file) {
        assertArgumentNotNull("file", file);
        return readText(file, Charset.defaultCharset().name());
    }

    /**
     * 指定のエンコーディングでファイルからテキストを読み込みます。
     *
     * @param path
     *            パス。{@literal null}や空文字列であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return 読み込んだテキスト
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
            final Reader reader = ReaderUtil.create(new BufferedInputStream(is,
                    DEFAULT_BUF_SIZE), encoding);
            return read(reader, DEFAULT_BUF_SIZE);
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * 指定のエンコーディングでファイルからテキストを読み込みます。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return 読み込んだテキスト
     */
    public static String readText(final File file, final String encoding) {
        assertArgumentNotNull("file", file);
        assertArgumentNotEmpty("encoding", encoding);

        final FileInputStream is = InputStreamUtil.create(file);
        try {
            final Reader reader = ReaderUtil.create(new BufferedInputStream(is,
                    DEFAULT_BUF_SIZE), encoding);
            return read(reader, (int) ChannelUtil.size(is.getChannel()));
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * UTF8でファイルからテキストを読み込みます。
     *
     * @param path
     *            パス。{@literal null}や空文字列であってはいけません
     * @return 読み込んだテキスト
     */
    public static String readUTF8(final String path) {
        assertArgumentNotEmpty("path", path);
        return readText(path, UTF8);
    }

    /**
     * UTF8でファイルからテキストを読み込みます。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return 読み込んだテキスト
     */
    public static String readUTF8(final File file) {
        assertArgumentNotNull("file", file);
        return readText(file, UTF8);
    }

    /**
     * リーダーから読み込んだ内容を文字列で返します。
     *
     * @param reader
     *            リーダー
     * @param initialCapacity
     *            バッファの初期容量
     * @return リーダーから読み込んだ文字列
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

    public static void writeBytes(final String pathname, final byte[] bytes) {
        try (FileOutputStream fos = OutputStreamUtil.create(new File(pathname))) {
            ChannelUtil.write(fos.getChannel(), ByteBuffer.wrap(bytes));
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
