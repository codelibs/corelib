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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.codelibs.core.exception.IORuntimeException;

/**
 * {@link Writer}用のユーティリティクラスです。
 *
 * @author koichik
 */
public abstract class WriterUtil {

    /**
     * 指定のエンコーディングでストリームへ出力する{@link Writer}を作成します。
     *
     * @param os
     *            ストリーム。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return ストリームへ出力する{@link Writer}
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
     * プラットフォームデフォルトエンコーディングでファイルへ出力する{@link Writer}を作成します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return ファイルへ出力する{@link Writer}
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
     * 指定のエンコーディングでファイルへ出力する{@link Writer}を作成します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @param encoding
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return ファイルへ出力する{@link Writer}
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
