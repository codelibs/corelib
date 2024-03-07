/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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

import static org.codelibs.core.log.Logger.format;

import java.io.Closeable;
import java.io.IOException;

import org.codelibs.core.log.Logger;

/**
 * {@link Closeable}用のユーティリティクラスです。
 *
 * @author koichik
 */
public abstract class CloseableUtil {

    private static final Logger logger = Logger.getLogger(CloseableUtil.class);

    /**
     * {@link Closeable}をクローズします。
     * <p>
     * {@link Closeable#close()}が例外をスローした場合はログにエラーメッセージを出力します。
     * 例外は再スローされません。これは、次のような状況で元の例外が失われるのを防ぐためです。
     * </p>
     *
     * <pre>
     * InputStream is = ...;
     * try {
     *   is.read(...);
     * } finaly {
     *   close(is);
     * }
     * </pre>
     * <p>
     * {@literal try}ブロックで例外が発生した場合、{@literal finally}ブロックの
     * {@link #close(Closeable)}でも 例外が発生する可能性があります。その場合に{@literal finally}
     * ブロックから例外をスローすると、 {@literal try}ブロックで発生した元の例外が失われてしまいます。
     * </p>
     *
     * @param closeable
     *            クローズ可能なオブジェクト
     * @see Closeable#close()
     */
    public static void close(final Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (final IOException e) {
            logger.log(format("ECL0017", e.getMessage()), e);
        }
    }

    public static void closeQuietly(final Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (final IOException e) {
            // ignore
        }
    }
}
