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
package org.codelibs.core.zip;

import static org.codelibs.core.log.Logger.format;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.io.FileUtil;
import org.codelibs.core.log.Logger;
import org.codelibs.core.net.URLUtil;

/**
 * {@link java.util.zip.ZipFile}を扱うユーティリティクラスです。
 *
 * @author higa
 */
public abstract class ZipFileUtil {

    private static final Logger logger = Logger.getLogger(ZipFileUtil.class);

    /**
     * 指定されたZipファイルを読み取るための<code>ZipFile</code>を作成して返します。
     *
     * @param file
     *            ファイルパス。{@literal null}や空文字列であってはいけません
     * @return 指定されたZipファイルを読み取るための<code>ZipFile</code>
     */
    public static ZipFile create(final String file) {
        assertArgumentNotEmpty("file", file);

        try {
            return new ZipFile(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 指定されたZipファイルを読み取るための<code>ZipFile</code>を作成して返します。
     *
     * @param file
     *            ファイル。{@literal null}であってはいけません
     * @return 指定されたZipファイルを読み取るための<code>ZipFile</code>
     */
    public static ZipFile create(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return new ZipFile(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 指定されたZipファイルエントリの内容を読み込むための入力ストリームを返します。
     *
     * @param file
     *            Zipファイル。{@literal null}であってはいけません
     * @param entry
     *            Zipファイルエントリ。{@literal null}であってはいけません
     * @return 指定されたZipファイルエントリの内容を読み込むための入力ストリーム
     */
    public static InputStream getInputStream(final ZipFile file, final ZipEntry entry) {
        assertArgumentNotNull("file", file);
        assertArgumentNotNull("entry", entry);

        try {
            return file.getInputStream(entry);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * URLで指定されたZipファイルを読み取るための<code>ZipFile</code>を作成して返します。
     *
     * @param zipUrl
     *            Zipファイルを示すURL。{@literal null}であってはいけません
     * @return 指定されたZipファイルを読み取るための<code>ZipFile</code>
     */
    public static ZipFile toZipFile(final URL zipUrl) {
        assertArgumentNotNull("zipUrl", zipUrl);

        return create(new File(toZipFilePath(zipUrl)));
    }

    /**
     * URLで指定されたZipファイルのパスを返します。
     *
     * @param zipUrl
     *            Zipファイルを示すURL。{@literal null}であってはいけません
     * @return URLで指定されたZipファイルのパス
     */
    public static String toZipFilePath(final URL zipUrl) {
        assertArgumentNotNull("zipUrl", zipUrl);

        final String urlString = zipUrl.getPath();
        final int pos = urlString.lastIndexOf('!');
        final String zipFilePath = urlString.substring(0, pos);
        final File zipFile = new File(URLUtil.decode(zipFilePath, "UTF8"));
        return FileUtil.getCanonicalPath(zipFile);
    }

    /**
     * Zipファイルをクローズします。
     * <p>
     * {@link ZipFile#close()}が例外をスローした場合はログにエラーメッセージを出力します。 例外は再スローされません。
     * </p>
     *
     * @param zipFile
     *            Zipファイル。{@literal null}であってはいけません
     */
    public static void close(final ZipFile zipFile) {
        assertArgumentNotNull("zipFile", zipFile);

        try {
            zipFile.close();
        } catch (final IOException e) {
            logger.log(format("ECL0017", e.getMessage()), e);
        }
    }

}
