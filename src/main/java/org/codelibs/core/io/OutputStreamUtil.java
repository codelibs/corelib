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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility class for {@link OutputStream} operations.
 *
 * @author shot
 */
public abstract class OutputStreamUtil {

    /**
     * Do not instantiate.
     */
    protected OutputStreamUtil() {
    }

    /**
     * Creates a {@link FileOutputStream}.
     *
     * @param file the file (must not be {@literal null})
     * @return a {@link FileOutputStream} to output to the file
     * @see FileOutputStream#FileOutputStream(File)
     */
    public static FileOutputStream create(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return new FileOutputStream(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Flushes the {@link OutputStream}.
     *
     * @param out the output stream
     */
    public static void flush(final OutputStream out) {
        if (out == null) {
            return;
        }
        try {
            out.flush();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
