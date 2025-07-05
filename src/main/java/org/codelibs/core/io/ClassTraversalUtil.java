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
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.codelibs.core.lang.ClassUtil;
import org.codelibs.core.zip.ZipInputStreamUtil;

/**
 * Handler for traversing and processing classes.
 *
 * @author koichik
 * @see ClassHandler
 * @see TraversalUtil
 */
public abstract class ClassTraversalUtil {

    /**
     * Do not instantiate.
     */
    private ClassTraversalUtil() {
    }

    /** The file extension for class files. */
    protected static final String CLASS_SUFFIX = ".class";

    /** The file extension for WAR files. */
    protected static final String WAR_FILE_EXTENSION = ".war";

    /** The entry prefix for class files inside a WAR file. */
    protected static final String WEB_INF_CLASSES_PATH = "WEB-INF/classes/";

    /**
     * Processes the directory under the root directory.
     *
     * @param rootDir the root directory (must not be {@literal null})
     * @param handler the handler to process classes (must not be {@literal null})
     */
    public static void forEach(final File rootDir, final ClassHandler handler) {
        assertArgumentNotNull("rootDir", rootDir);
        assertArgumentNotNull("handler", handler);

        forEach(rootDir, null, handler);
    }

    /**
     * Traverses classes contained in the file system.
     *
     * @param rootDir the root directory (must not be {@literal null})
     * @param rootPackage the root package
     * @param handler the handler to process classes (must not be {@literal null})
     */
    public static void forEach(final File rootDir, final String rootPackage, final ClassHandler handler) {
        assertArgumentNotNull("rootDir", rootDir);
        assertArgumentNotNull("handler", handler);

        final File packageDir = getPackageDir(rootDir, rootPackage);
        if (packageDir.exists()) {
            traverseFileSystem(packageDir, rootPackage, handler);
        }
    }

    /**
     * Traverses classes contained in a Jar file.
     * <p>
     * If the specified Jar file has the extension <code>.war</code>, only entries whose path starts with the prefix <code>WEB-INF/classes</code> are traversed.
     * The handler receives the entry name excluding the prefix. For example, if the Jar file contains <code>/WEB-INF/classes/ccc/ddd/Eee.class</code>,
     * the handler receives the package name <code>ccc.ddd</code> and the class name <code>Eee</code>.
     * </p>
     *
     * @param jarFile the Jar file (must not be {@literal null})
     * @param handler the handler to process classes (must not be {@literal null})
     */
    public static void forEach(final JarFile jarFile, final ClassHandler handler) {
        assertArgumentNotNull("jarFile", jarFile);
        assertArgumentNotNull("handler", handler);

        if (jarFile.getName().toLowerCase().endsWith(WAR_FILE_EXTENSION)) {
            forEach(jarFile, WEB_INF_CLASSES_PATH, handler);
        } else {
            forEach(jarFile, "", handler);
        }
    }

    /**
     * Traverses classes contained in a Jar file.
     * <p>
     * Only entries whose path starts with the specified prefix are traversed. The handler receives the entry name excluding the prefix.
     * For example, if the prefix is <code>/aaa/bbb/</code> and the Jar file contains <code>/aaa/bbb/ccc/ddd/Eee.class</code>,
     * the handler receives the package name <code>ccc.ddd</code> and the class name <code>Eee</code>.
     * </p>
     *
     * @param jarFile the Jar file (must not be {@literal null})
     * @param prefix the prefix that the resource name to traverse must contain (must not be {@literal null}).
     *               If not empty, must end with a slash ('/')
     * @param handler the handler to process classes (must not be {@literal null})
     */
    public static void forEach(final JarFile jarFile, final String prefix, final ClassHandler handler) {
        assertArgumentNotNull("jarFile", jarFile);
        assertArgumentNotNull("prefix", prefix);
        assertArgumentNotNull("handler", handler);

        final int startPos = prefix.length();
        for (final JarEntry entry : iterable(jarFile.entries())) {
            final String entryName = entry.getName().replace('\\', '/');
            if (entryName.startsWith(prefix) && entryName.endsWith(CLASS_SUFFIX)) {
                final String className = entryName.substring(startPos, entryName.length() - CLASS_SUFFIX.length()).replace('/', '.');
                final int pos = className.lastIndexOf('.');
                final String packageName = pos == -1 ? null : className.substring(0, pos);
                final String shortClassName = pos == -1 ? className : className.substring(pos + 1);
                handler.processClass(packageName, shortClassName);
            }
        }
    }

    /**
     * Traverses classes contained in a ZIP file input stream.
     *
     * @param zipInputStream the ZIP file input stream (must not be {@literal null})
     * @param handler the handler to process classes (must not be {@literal null})
     */
    public static void forEach(final ZipInputStream zipInputStream, final ClassHandler handler) {
        assertArgumentNotNull("zipInputStream", zipInputStream);
        assertArgumentNotNull("handler", handler);

        forEach(zipInputStream, "", handler);
    }

    /**
     * Traverses classes contained in a ZIP file input stream.
     * <p>
     * Only entries whose path starts with the specified prefix are traversed. The handler receives the entry name excluding the prefix.
     * For example, if the prefix is <code>/aaa/bbb/</code> and the input stream contains <code>/aaa/bbb/ccc/ddd/Eee.class</code>,
     * the handler receives the package name <code>ccc.ddd</code> and the class name <code>Eee</code>.
     * </p>
     *
     * @param zipInputStream the ZIP file input stream (must not be {@literal null})
     * @param prefix the prefix that the resource name to traverse must contain (must not be {@literal null}).
     *               If not empty, must end with a slash ('/')
     * @param handler the handler to process classes (must not be {@literal null})
     */
    public static void forEach(final ZipInputStream zipInputStream, final String prefix, final ClassHandler handler) {
        assertArgumentNotNull("zipInputStream", zipInputStream);
        assertArgumentNotNull("prefix", prefix);
        assertArgumentNotNull("handler", handler);

        final int startPos = prefix.length();
        ZipEntry entry = null;
        while ((entry = ZipInputStreamUtil.getNextEntry(zipInputStream)) != null) {
            try {
                final String entryName = entry.getName().replace('\\', '/');
                if (entryName.startsWith(prefix) && entryName.endsWith(CLASS_SUFFIX)) {
                    final String className = entryName.substring(startPos, entryName.length() - CLASS_SUFFIX.length()).replace('/', '.');
                    final int pos = className.lastIndexOf('.');
                    final String packageName = pos == -1 ? null : className.substring(0, pos);
                    final String shortClassName = pos == -1 ? className : className.substring(pos + 1);
                    handler.processClass(packageName, shortClassName);
                }
            } finally {
                ZipInputStreamUtil.closeEntry(zipInputStream);
            }
        }
    }

    /**
     * Traverses classes contained in the file system.
     *
     * @param dir the base directory
     * @param packageName the package name to traverse
     * @param handler the handler to process classes
     */
    protected static void traverseFileSystem(final File dir, final String packageName, final ClassHandler handler) {
        for (final File file : dir.listFiles()) {
            final String fileName = file.getName();
            if (file.isDirectory()) {
                traverseFileSystem(file, ClassUtil.concatName(packageName, fileName), handler);
            } else if (fileName.endsWith(".class")) {
                final String shortClassName = fileName.substring(0, fileName.length() - CLASS_SUFFIX.length());
                handler.processClass(packageName, shortClassName);
            }
        }
    }

    /**
     * Returns a {@link File} representing the directory corresponding to the root package.
     *
     * @param rootDir the root directory
     * @param rootPackage the root package
     * @return a {@link File} representing the directory corresponding to the package
     */
    protected static File getPackageDir(final File rootDir, final String rootPackage) {
        File packageDir = rootDir;
        if (rootPackage != null) {
            for (final String name : rootPackage.split("\\.")) {
                packageDir = new File(packageDir, name);
            }
        }
        return packageDir;
    }

}
