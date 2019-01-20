/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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
 * {@link Properties}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class PropertiesUtil {

    /**
     * {@link Properties#load(InputStream)}の例外処理をラップします。
     * <p>
     * 入力ストリームはクローズされません。
     * </p>
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param in
     *            入力ストリーム。{@literal null}であってはいけません
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
     * {@link Properties#load(Reader)}の例外処理をラップします。
     * <p>
     * 入力リーダはクローズされません。
     * </p>
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param reader
     *            入力リーダ。{@literal null}であってはいけません
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
     * 指定のエンコーディングでファイルを読み込んで{@link Properties}にロードします（例外処理はラップします）。
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
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
     * プラットフォームデフォルトエンコーディングでファイルを読み込んで{@link Properties}にロードします（例外処理はラップします）。
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param file
     *            ファイル。{@literal null}であってはいけません
     */
    public static void load(final Properties props, final File file) {
        load(props, file, Charset.defaultCharset().name());
    }

    /**
     * {@link URL}を読み込んで{@link Properties}にロードします（例外処理はラップします）。
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param url
     *            URL。{@literal null}であってはいけません
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
     * コンテキストクラスローダからリソースを読み込んで{@link Properties}にロードします（例外処理はラップします）。
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     */
    public static void load(final Properties props, final String path) {
        assertArgumentNotNull("props", props);
        assertArgumentNotEmpty("path", path);

        load(props, ResourceUtil.getResource(path));
    }

    /**
     * {@link Properties#store(OutputStream, String)}の例外処理をラップします。
     *
     * <p>
     * 出力ストリームはクローズされません。
     * </p>
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param out
     *            出力ストリーム。{@literal null}であってはいけません
     * @param comments
     *            コメント
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
     * {@link Properties#store(Writer, String)}の例外処理をラップします。
     *
     * <p>
     * 出力ライタはクローズされません。
     * </p>
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param writer
     *            出力ライタ。{@literal null}であってはいけません
     * @param comments
     *            コメント
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
     * 指定のエンコーディングでファイルを書き出して{@link Properties}をストアします（例外処理はラップします）。
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @param comments
     *            コメント
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
     * プラットフォームデフォルトエンコーディングでファイルを書き出して{@link Properties}をストアします（例外処理はラップします）。
     *
     * @param props
     *            プロパティセット。{@literal null}であってはいけません
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @param comments
     *            コメント
     */
    public static void store(final Properties props, final File file, final String comments) {
        store(props, file, Charset.defaultCharset().name(), comments);
    }

}
