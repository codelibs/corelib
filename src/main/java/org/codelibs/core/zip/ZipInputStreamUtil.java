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
package org.codelibs.core.zip;

import static org.codelibs.core.log.Logger.format;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.log.Logger;

/**
 * {@link java.util.zip.ZipInputStream}を扱うユーティリティクラスです。
 *
 * @author koichik
 */
public abstract class ZipInputStreamUtil {

    private static final Logger logger = Logger.getLogger(ZipInputStreamUtil.class);

    /**
     * {@link ZipInputStream#getNextEntry()}の例外処理をラップするメソッドです。
     *
     * @param zis
     *            {@link ZipInputStream}。{@literal null}であってはいけません
     * @return {@link ZipEntry}
     * @see ZipInputStream#getNextEntry()
     */
    public static ZipEntry getNextEntry(final ZipInputStream zis) {
        assertArgumentNotNull("zis", zis);

        try {
            return zis.getNextEntry();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * {@link ZipInputStream#reset()}の例外処理をラップするメソッドです。
     *
     * @param zis
     *            {@link ZipInputStream}。{@literal null}であってはいけません
     * @see ZipInputStream#reset()
     */
    public static void reset(final ZipInputStream zis) {
        assertArgumentNotNull("zis", zis);

        try {
            zis.reset();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * {@link ZipInputStream}をクローズします。
     * <p>
     * {@link ZipInputStream#closeEntry()}が例外をスローした場合はログにエラーメッセージを出力します。
     * 例外は再スローされません。
     * </p>
     *
     * @param zis
     *            {@link ZipInputStream}。{@literal null}であってはいけません
     * @see ZipInputStream#closeEntry()
     */
    public static void closeEntry(final ZipInputStream zis) {
        assertArgumentNotNull("zis", zis);

        try {
            zis.closeEntry();
        } catch (final IOException e) {
            logger.log(format("ECL0017", e.getMessage()), e);
        }
    }

}
