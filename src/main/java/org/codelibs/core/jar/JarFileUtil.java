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
package org.codelibs.core.jar;

import static org.codelibs.core.log.Logger.format;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.io.FileUtil;
import org.codelibs.core.log.Logger;
import org.codelibs.core.net.JarURLConnectionUtil;
import org.codelibs.core.net.URLUtil;

/**
 * Utility class for handling {@link java.util.jar.JarFile}.
 *
 * @author higa
 */
public abstract class JarFileUtil {

    /**
     * Do not instantiate.
     */
    protected JarFileUtil() {
    }

    private static final Logger logger = Logger.getLogger(JarFileUtil.class);

    /**
     * Creates and returns a <code>JarFile</code> to read the specified JAR file.
     *
     * @param file the file path (must not be {@literal null})
     * @return a <code>JarFile</code> to read the specified JAR file
     */
    public static JarFile create(final String file) {
        assertArgumentNotNull("file", file);

        try {
            return new JarFile(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates and returns a <code>JarFile</code> to read the specified JAR file.
     *
     * @param file the file (must not be {@literal null})
     * @return a <code>JarFile</code> to read the specified JAR file
     */
    public static JarFile create(final File file) {
        assertArgumentNotNull("file", file);

        try {
            return new JarFile(file);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Returns an input stream to read the contents of the specified JAR file entry.
     *
     * @param file the JAR file (must not be {@literal null})
     * @param entry the JAR file entry (must not be {@literal null})
     * @return the input stream to read the entry
     */
    public static InputStream getInputStream(final JarFile file, final ZipEntry entry) {
        assertArgumentNotNull("file", file);
        assertArgumentNotNull("entry", entry);

        try {
            return file.getInputStream(entry);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates and returns a <code>JarFile</code> to read the JAR file specified by the URL.
     *
     * @param jarUrl the URL of the JAR file (must not be {@literal null})
     * @return a <code>JarFile</code> to read the JAR file specified by the URL
     */
    public static JarFile toJarFile(final URL jarUrl) {
        assertArgumentNotNull("jarUrl", jarUrl);

        final URLConnection con = URLUtil.openConnection(jarUrl);
        if (con instanceof JarURLConnection) {
            return JarURLConnectionUtil.getJarFile((JarURLConnection) con);
        }
        return create(new File(toJarFilePath(jarUrl)));
    }

    /**
     * Returns the path of the JAR file specified by the URL.
     *
     * @param jarUrl the URL of the JAR file (must not be {@literal null})
     * @return the path of the JAR file specified by the URL
     */
    public static String toJarFilePath(final URL jarUrl) {
        assertArgumentNotNull("jarUrl", jarUrl);

        final URL nestedUrl = URLUtil.create(jarUrl.getPath());
        final String nestedUrlPath = nestedUrl.getPath();
        final int pos = nestedUrlPath.lastIndexOf('!');
        final String jarFilePath = nestedUrlPath.substring(0, pos);
        final File jarFile = new File(URLUtil.decode(jarFilePath, "UTF8"));
        return FileUtil.getCanonicalPath(jarFile);
    }

    /**
     * Closes the JAR file.
     * <p>
     * If {@link JarFile#close()} throws an exception, an error message is logged. The exception is not re-thrown.
     * </p>
     *
     * @param jarFile the JAR file (must not be {@literal null})
     */
    public static void close(final JarFile jarFile) {
        assertArgumentNotNull("jarFile", jarFile);

        try {
            jarFile.close();
        } catch (final IOException e) {
            logger.log(format("ECL0017", e.getMessage()), e);
        }
    }

}
