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

import static org.codelibs.core.collection.EnumerationIterator.iterable;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.codelibs.core.jar.JarFileUtil;
import org.codelibs.core.zip.ZipInputStreamUtil;

/**
 * Class for traversing resources.
 *
 * @author taedium
 * @see ResourceHandler
 * @see TraversalUtil
 */
public abstract class ResourceTraversalUtil {

    /**
     * Traverses resources contained in the file system.
     *
     * @param rootDir the root directory (must not be {@literal null})
     * @param handler the handler to process resources (must not be {@literal null})
     */
    public static void forEach(final File rootDir, final ResourceHandler handler) {
        assertArgumentNotNull("rootDir", rootDir);
        assertArgumentNotNull("handler", handler);

        forEach(rootDir, null, handler);
    }

    /**
     * Traverses resources contained in the file system.
     * <p>
     * Of the resources under the root directory, only those with paths starting with the base directory are traversed. The handler receives the relative path from the root directory. For example, if the root directory is <code>/aaa/bbb</code> and the base directory is <code>ccc/ddd</code>, and the resource <code>/aaa/bbb/ccc/ddd/eee.txt</code> exists, the handler receives the path <code>ccc/ddd/eee.txt</code>.
     * </p>
     *
     * @param rootDir the root directory (must not be {@literal null})
     * @param baseDirectory the base directory
     * @param handler the handler to process resources (must not be {@literal null})
     */
    public static void forEach(final File rootDir, final String baseDirectory, final ResourceHandler handler) {
        assertArgumentNotNull("rootDir", rootDir);
        assertArgumentNotNull("handler", handler);

        final File baseDir = getBaseDir(rootDir, baseDirectory);
        if (baseDir.exists()) {
            traverseFileSystem(rootDir, baseDir, handler);
        }
    }

    /**
     * Traverses resources contained in a Jar file.
     *
     * @param jarFile
     *            the Jar file (must not be {@literal null})
     * @param handler
     *            the handler to process resources (must not be {@literal null})
     */
    public static void forEach(final JarFile jarFile, final ResourceHandler handler) {
        assertArgumentNotNull("jarFile", jarFile);
        assertArgumentNotNull("handler", handler);

        forEach(jarFile, "", handler);
    }

    /**
     * Traverses resources contained in a Jar file.
     * <p>
     * Of the resources in the Jar file, only those with paths starting with the specified prefix are traversed.
     * The handler receives the entry name with the prefix removed. For example, if the prefix is <code>/aaa/bbb/</code>
     * and the Jar file contains a resource <code>/aaa/bbb/ccc/ddd/eee.txt</code>, the handler receives
     * the path <code>ccc/ddd/eee.txt</code>.
     * </p>
     *
     * @param jarFile
     *            the Jar file (must not be {@literal null})
     * @param prefix
     *            the prefix that resource names must start with (must not be {@literal null}).
     *            If not empty, must end with a slash ('/').
     * @param handler
     *            the handler to process resources (must not be {@literal null})
     */
    public static void forEach(final JarFile jarFile, final String prefix, final ResourceHandler handler) {
        assertArgumentNotNull("jarFile", jarFile);
        assertArgumentNotNull("prefix", prefix);
        assertArgumentNotNull("handler", handler);

        final int pos = prefix.length();
        for (final JarEntry entry : iterable(jarFile.entries())) {
            if (!entry.isDirectory()) {
                final String entryName = entry.getName().replace('\\', '/');
                if (!entryName.startsWith(prefix)) {
                    continue;
                }
                final InputStream is = JarFileUtil.getInputStream(jarFile, entry);
                try {
                    handler.processResource(entryName.substring(pos), is);
                } finally {
                    CloseableUtil.close(is);
                }
            }
        }
    }

    /**
     * Traverses resources contained in a ZIP file input stream.
     *
     * @param zipInputStream
     *            the ZIP file input stream (must not be {@literal null})
     * @param handler
     *            the handler to process resources (must not be {@literal null})
     */
    public static void forEach(final ZipInputStream zipInputStream, final ResourceHandler handler) {
        assertArgumentNotNull("zipInputStream", zipInputStream);
        assertArgumentNotNull("handler", handler);

        forEach(zipInputStream, "", handler);
    }

    /**
     * Traverses resources contained in a ZIP file input stream.
     * <p>
     * Of the resources in the input stream, only those with paths starting with the specified prefix are traversed.
     * The handler receives the entry name with the prefix removed. For example, if the prefix is <code>/aaa/bbb/</code>
     * and the input stream contains a resource <code>/aaa/bbb/ccc/ddd/eee.txt</code>, the handler receives
     * the path <code>ccc/ddd/eee.txt</code>.
     * </p>
     *
     * @param zipInputStream
     *            the ZIP file input stream (must not be {@literal null})
     * @param prefix
     *            the prefix that resource names must start with (must not be {@literal null}).
     *            If not empty, must end with a slash ('/').
     * @param handler
     *            the handler to process resources (must not be {@literal null})
     */
    public static void forEach(final ZipInputStream zipInputStream, final String prefix, final ResourceHandler handler) {
        assertArgumentNotNull("zipInputStream", zipInputStream);
        assertArgumentNotNull("prefix", prefix);
        assertArgumentNotNull("handler", handler);

        final int pos = prefix.length();
        ZipEntry entry = null;
        while ((entry = ZipInputStreamUtil.getNextEntry(zipInputStream)) != null) {
            if (!entry.isDirectory()) {
                final String entryName = entry.getName().replace('\\', '/');
                if (!entryName.startsWith(prefix)) {
                    continue;
                }
                handler.processResource(entryName.substring(pos), new FilterInputStream(zipInputStream) {
                    @Override
                    public void close() throws IOException {
                        ZipInputStreamUtil.closeEntry(zipInputStream);
                    }
                });
            }
        }
    }

    /**
     * Traverses resources contained in the file system.
     *
     * @param rootDir
     *            the root directory
     * @param baseDir
     *            the base directory
     * @param handler
     *            the handler to process resources
     */
    protected static void traverseFileSystem(final File rootDir, final File baseDir, final ResourceHandler handler) {
        for (final File file : baseDir.listFiles()) {
            if (file.isDirectory()) {
                traverseFileSystem(rootDir, file, handler);
            } else {
                final int pos = FileUtil.getCanonicalPath(rootDir).length();
                final String filePath = FileUtil.getCanonicalPath(file);
                final String resourcePath = filePath.substring(pos + 1).replace('\\', '/');
                final InputStream is = InputStreamUtil.create(file);
                try {
                    handler.processResource(resourcePath, is);
                } finally {
                    CloseableUtil.close(is);
                }
            }
        }
    }

    /**
     * Returns a {@link File} representing the base directory.
     *
     * @param rootDir
     *            the root directory
     * @param baseDirectory
     *            the base directory
     * @return a {@link File} representing the base directory
     */
    protected static File getBaseDir(final File rootDir, final String baseDirectory) {
        assertArgumentNotNull("rootDir", rootDir);

        File baseDir = rootDir;
        if (baseDirectory != null) {
            for (final String name : baseDirectory.split("/")) {
                baseDir = new File(baseDir, name);
            }
        }
        return baseDir;
    }

}
