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
 * Utility class for handling {@link java.util.zip.ZipFile}.
 *
 * @author higa
 */
public abstract class ZipFileUtil {

    private static final Logger logger = Logger.getLogger(ZipFileUtil.class);

    /**
     * Creates and returns a <code>ZipFile</code> for reading the specified Zip file.
     *
     * @param file
     *            File path. Must not be {@literal null} or an empty string.
     * @return A <code>ZipFile</code> for reading the specified Zip file.
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
     * Creates and returns a <code>ZipFile</code> for reading the specified file.
     *
     * @param file
     *            File. Must not be {@literal null}.
     * @return A <code>ZipFile</code> for reading the specified file.
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
     * Returns an input stream for reading the contents of the specified Zip file entry.
     *
     * @param file
     *            Zip file. Must not be {@literal null}.
     * @param entry
     *            Zip file entry. Must not be {@literal null}.
     * @return An input stream for reading the contents of the specified Zip file entry.
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
     * Creates and returns a <code>ZipFile</code> for reading the Zip file specified by the URL.
     *
     * @param zipUrl
     *            URL pointing to the Zip file. Must not be {@literal null}.
     * @return A <code>ZipFile</code> for reading the specified Zip file.
     */
    public static ZipFile toZipFile(final URL zipUrl) {
        assertArgumentNotNull("zipUrl", zipUrl);

        return create(new File(toZipFilePath(zipUrl)));
    }

    /**
     * Returns the path of the Zip file specified by the URL.
     *
     * @param zipUrl
     *            URL pointing to the Zip file. Must not be {@literal null}.
     * @return The path of the Zip file specified by the URL.
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
     * Closes the Zip file.
     * <p>
     * If {@link ZipFile#close()} throws an exception, an error message is logged. The exception is not rethrown.
     * </p>
     *
     * @param zipFile
     *            Zip file. Must not be {@literal null}.
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
