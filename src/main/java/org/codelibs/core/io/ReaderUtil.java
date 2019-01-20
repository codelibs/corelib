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
 * {@link Reader}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class ReaderUtil {

    /** デフォルトのバッファサイズ */
    private static final int BUF_SIZE = 4096;

    /**
     * 指定のエンコーディングでファイルから入力する{@link Reader}を作成します。
     *
     * @param is
     *            入力ストリーム。{@literal null}であってはいけません
     * @param encoding
     *            入力ストリームのエンコーディング。{@literal null}や空文字列であってはいけません
     * @return ファイルかへ出力する{@link Reader}
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
     * プラットフォームデフォルトエンコーディングでファイルから入力する{@link Reader}を作成します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return ファイルから入力する{@link Reader}
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
     * 指定のエンコーディングでファイルから入力する{@link Reader}を作成します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return ファイルから入力する{@link Reader}
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
     * {@link BufferedReader}から一行読み込んで返します。
     *
     * @param reader
     *            {@link BufferedReader}。{@literal null}であってはいけません
     * @return 一行の文字列。終端に達した場合は{@literal null}
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
     * {@link Reader}からテキストを読み込みます。
     * <p>
     * {@link Reader}はクローズされません。
     * </p>
     *
     * @param reader
     *            読み込み文字ストリーム。{@literal null}であってはいけません
     * @return テキスト
     */
    public static String readText(final Reader reader) {
        assertArgumentNotNull("reader", reader);

        final StringBuilder buf = new StringBuilder(BUF_SIZE);
        CopyUtil.copy(reader, buf);
        return new String(buf);
    }

}
