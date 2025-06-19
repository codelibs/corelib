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

import static org.codelibs.core.log.Logger.format;

import java.io.Closeable;
import java.io.IOException;

import org.codelibs.core.log.Logger;

/**
 * Utility class for {@link Closeable} operations.
 *
 * @author koichik
 */
public abstract class CloseableUtil {

    private static final Logger logger = Logger.getLogger(CloseableUtil.class);

    /**
     * Closes a {@link Closeable}.
     * <p>
     * If an exception is thrown by {@link Closeable#close()}, an error message is logged. The exception is not rethrown. This prevents the original exception from being lost in situations like the following:
     * </p>
     * <pre>
     * InputStream is = ...;
     * try {
     *   is.read(...);
     * } finally {
     *   close(is);
     * }
     * </pre>
     * <p>
     * If an exception occurs in the try block, there is a possibility that an exception will also occur in the finally block's {@link #close(Closeable)}. If the exception is thrown from the finally block, the original exception from the try block will be lost.
     * </p>
     *
     * @param closeable the closeable object
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
