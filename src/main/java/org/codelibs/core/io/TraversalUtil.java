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

import static org.codelibs.core.collection.ArrayUtil.asArray;
import static org.codelibs.core.collection.CollectionsUtil.newArrayList;
import static org.codelibs.core.collection.CollectionsUtil.newConcurrentHashMap;
import static org.codelibs.core.collection.CollectionsUtil.newHashSet;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.codelibs.core.jar.JarFileUtil;
import org.codelibs.core.lang.ClassLoaderUtil;
import org.codelibs.core.lang.ClassUtil;
import org.codelibs.core.lang.StringUtil;
import org.codelibs.core.log.Logger;
import org.codelibs.core.net.URLUtil;
import org.codelibs.core.zip.ZipFileUtil;
import org.codelibs.core.zip.ZipInputStreamUtil;

/**
 * Utility for traversing collections of classes and resources on the file system or inside JAR files.
 * <p>
 * Target directories on the file system or JAR files are provided as {@link URL}. The appropriate {@link Traverser} is returned depending on the protocol of the URL, and you can traverse classes and resources by calling its methods.
 * </p>
 * <p>
 * Supported protocols:
 * </p>
 * <ul>
 * <li>{@literal file}</li>
 * <li>{@literal jar}</li>
 * <li>{@literal wsjar} (WebSphere proprietary protocol, alias for {@literal jar})</li>
 * <li>{@literal zip} (WebLogic proprietary protocol, use {@literal jar} protocol for normal zip files)</li>
 * <li>{@literal code-source} (Oracle AS(OC4J) proprietary protocol)</li>
 * <li>{@literal vfsfile} (JBossAS5 proprietary protocol, alias for {@literal file})</li>
 * <li>{@literal vfszip} (JBossAS5 proprietary protocol)</li>
 * </ul>
 *
 * @author koichik
 * @see URLUtil#toCanonicalProtocol(String)
 * @see ClassTraversalUtil
 * @see ResourceTraversalUtil
 */
public abstract class TraversalUtil {

    /**
     * Do not instantiate.
     */
    private TraversalUtil() {
    }

    /** An empty array of {@link Traverser}. */
    protected static final Traverser[] EMPTY_ARRAY = new Traverser[0];

    private static final Logger logger = Logger.getLogger(TraversalUtil.class);

    /** Mapping from URL protocol (as key) to {@link TraverserFactory} (as value). */
    protected static final ConcurrentMap<String, TraverserFactory> traverserFactories = newConcurrentHashMap();
    static {
        addTraverserFactory("file", (url, rootPackage, rootDir) -> new FileSystemTraverser(getBaseDir(url, rootDir), rootPackage, rootDir));
        addTraverserFactory("jar", JarFileTraverser::new);
        addTraverserFactory("zip", (url, rootPackage,
                rootDir) -> new JarFileTraverser(JarFileUtil.create(new File(ZipFileUtil.toZipFilePath(url))), rootPackage, rootDir));
        addTraverserFactory("code-source",
                (url, rootPackage, rootDir) -> new JarFileTraverser(URLUtil.create("jar:file:" + url.getPath()), rootPackage, rootDir));
        addTraverserFactory("vfszip", VfsZipTraverser::new);
    }

    /**
     * Adds a {@link TraverserFactory}.
     *
     * @param protocol
     *            The URL protocol. Must not be {@literal null} or empty.
     * @param factory
     *            The {@link Traverser} factory for the protocol. Must not be {@literal null}.
     */
    public static void addTraverserFactory(final String protocol, final TraverserFactory factory) {
        assertArgumentNotEmpty("protocol", protocol);
        assertArgumentNotNull("factory", factory);

        traverserFactories.put(protocol, factory);
    }

    /**
     * Returns a {@link Traverser} that handles a collection of resources or classes based on the specified class.
     * <p>
     * The {@link Traverser} returned by this method uses the path where the specified class can be referenced by its FQN as the root.
     * For example, if the specified class is <code>foo.Bar</code> and its class file is located at <code>classes/foo/Bar.class</code>,
     * the {@link Traverser} returned by this method will handle the collection of resources under the <code>classes</code> directory.
     * </p>
     *
     * @param referenceClass
     *            The base class. Must not be {@literal null}.
     * @return A {@link Traverser} that handles a collection of classes or resources based on the specified class.
     */
    public static Traverser getTraverser(final Class<?> referenceClass) {
        assertArgumentNotNull("referenceClass", referenceClass);

        final URL url = ResourceUtil.getResource(toClassFile(referenceClass.getName()));
        final String[] path = referenceClass.getName().split("\\.");
        String baseUrl = url.toExternalForm();
        for (final String element : path) {
            final int pos = baseUrl.lastIndexOf('/');
            baseUrl = baseUrl.substring(0, pos);
        }
        return getTraverser(URLUtil.create(baseUrl + '/'), null, null);
    }

    /**
     * Returns a {@link Traverser} that handles a collection of classes or resources based on the specified directory.
     *
     * @param rootDir
     *            The root directory. Must not be {@literal null} or an empty string.
     * @return A {@link Traverser} that handles a collection of classes or resources based on the specified directory.
     */
    public static Traverser getTraverser(final String rootDir) {
        assertArgumentNotEmpty("rootDir", rootDir);

        final URL url = ResourceUtil.getResource(rootDir.endsWith("/") ? rootDir : rootDir + '/');
        return getTraverser(url, null, rootDir);
    }

    /**
     * Returns an array of {@link Traverser} instances that handle collections of classes or resources based on the specified root package.
     *
     * @param rootPackage
     *            The root package.
     * @return An array of {@link Traverser} instances that handle collections of resources based on the specified root package.
     */
    public static Traverser[] getTraversers(final String rootPackage) {
        if (StringUtil.isEmpty(rootPackage)) {
            return EMPTY_ARRAY;
        }

        final String baseName = toDirectoryName(rootPackage);
        final List<Traverser> list = newArrayList();
        for (final Iterator<URL> it = ClassLoaderUtil.getResources(baseName); it.hasNext();) {
            final URL url = it.next();
            final Traverser resourcesType = getTraverser(url, rootPackage, baseName);
            if (resourcesType != null) {
                list.add(resourcesType);
            }
        }
        if (list.isEmpty()) {
            logger.log("WCL0014", rootPackage);
            return EMPTY_ARRAY;
        }
        return list.toArray(new Traverser[list.size()]);
    }

    /**
     * Creates and returns a {@link Traverser} for handling the specified URL.
     * <p>
     * Returns <code>null</code> if the protocol of the URL is unknown.
     * </p>
     *
     * @param url
     *            The URL of the resource.
     * @param rootPackage
     *            The root package.
     * @param rootDir
     *            The root directory.
     * @return A {@link Traverser} for handling the specified URL.
     */
    protected static Traverser getTraverser(final URL url, final String rootPackage, final String rootDir) {
        final TraverserFactory factory = traverserFactories.get(URLUtil.toCanonicalProtocol(url.getProtocol()));
        if (factory != null) {
            return factory.create(url, rootPackage, rootDir);
        }
        logger.log("WCL0013", asArray(rootPackage, url));
        return null;
    }

    /**
     * Converts a package name to a directory name and returns it.
     *
     * @param packageName
     *            The package name.
     * @return The directory name.
     */
    protected static String toDirectoryName(final String packageName) {
        if (StringUtil.isEmpty(packageName)) {
            return null;
        }
        return packageName.replace('.', '/') + '/';
    }

    /**
     * Converts a class name to the corresponding class file path name and returns it.
     *
     * @param className
     *            The class name.
     * @return The class file path name.
     */
    protected static String toClassFile(final String className) {
        assertArgumentNotNull("className", className);

        return className.replace('.', '/') + ".class";
    }

    /**
     * Returns the base directory above the root package from a URL representing a file.
     *
     * @param url
     *            The URL representing the file.
     * @param baseName
     *            The base name.
     * @return The base directory above the root package.
     */
    protected static File getBaseDir(final URL url, final String baseName) {
        assertArgumentNotNull("url", url);

        File file = URLUtil.toFile(url);
        final String[] paths = StringUtil.split(baseName, "/");
        for (final String path : paths) {
            file = file.getParentFile();
        }
        return file;
    }

    /**
     * Factory for creating instances of {@link Traverser}.
     *
     * @author koichik
     */
    public interface TraverserFactory {
        /**
         * Creates and returns an instance of {@link Traverser}.
         *
         * @param url
         *            The URL representing the resource.
         * @param rootPackage
         *            The root package.
         * @param rootDir
         *            The root directory.
         * @return A {@link Traverser} that handles the resource represented by the URL.
         */
        Traverser create(URL url, String rootPackage, String rootDir);
    }

    /**
     * Object that handles a collection of resources on the file system.
     *
     * @author koichik
     */
    public static class FileSystemTraverser implements Traverser {

        /** The base directory. */
        protected final File baseDir;

        /** The root package. */
        protected final String rootPackage;

        /** The root directory. */
        protected final String rootDir;

        /**
         * Constructs an instance.
         *
         * @param baseDir
         *            The base directory.
         * @param rootPackage
         *            The root package.
         * @param rootDir
         *            The root directory.
         */
        public FileSystemTraverser(final File baseDir, final String rootPackage, final String rootDir) {
            this.baseDir = baseDir;
            this.rootPackage = rootPackage;
            this.rootDir = rootDir;
        }

        /**
         * Constructs an instance.
         *
         * @param url
         *            The URL representing the directory.
         * @param rootPackage
         *            The root package.
         * @param rootDir
         *            The root directory.
         */
        public FileSystemTraverser(final URL url, final String rootPackage, final String rootDir) {
            this(URLUtil.toFile(url), rootPackage, rootDir);
        }

        @Override
        public boolean isExistClass(final String className) {
            final File file = new File(baseDir, toClassFile(ClassUtil.concatName(rootPackage, className)));
            return file.exists();
        }

        @Override
        public void forEach(final ClassHandler handler) {
            ClassTraversalUtil.forEach(baseDir, rootPackage, handler);
        }

        @Override
        public void forEach(final ResourceHandler handler) {
            ResourceTraversalUtil.forEach(baseDir, rootDir, handler);
        }

        @Override
        public void close() {
        }

    }

    /**
     * Object that handles a collection of resources inside a Jar file.
     *
     * @author koichik
     */
    public static class JarFileTraverser implements Traverser {

        /** The Jar file. */
        protected final JarFile jarFile;

        /** The root package. */
        protected final String rootPackage;

        /** The root directory. */
        protected final String rootDir;

        /**
         * Constructs an instance.
         *
         * @param jarFile
         *            The Jar file.
         * @param rootPackage
         *            The root package.
         * @param rootDir
         *            The root directory.
         */
        public JarFileTraverser(final JarFile jarFile, final String rootPackage, final String rootDir) {
            this.jarFile = jarFile;
            this.rootPackage = rootPackage;
            this.rootDir = rootDir;
        }

        /**
         * Constructs an instance.
         *
         * @param url
         *            The URL representing the Jar file.
         * @param rootPackage
         *            The root package.
         * @param rootDir
         *            The root directory.
         */
        public JarFileTraverser(final URL url, final String rootPackage, final String rootDir) {
            this(JarFileUtil.toJarFile(url), rootPackage, rootDir);
        }

        @Override
        public boolean isExistClass(final String className) {
            return jarFile.getEntry(toClassFile(ClassUtil.concatName(rootPackage, className))) != null;
        }

        @Override
        public void forEach(final ClassHandler handler) {
            ClassTraversalUtil.forEach(jarFile, (ClassHandler) (packageName, shortClassName) -> {
                if (rootPackage == null || packageName != null && packageName.startsWith(rootPackage)) {
                    handler.processClass(packageName, shortClassName);
                }
            });
        }

        @Override
        public void forEach(final ResourceHandler handler) {
            ResourceTraversalUtil.forEach(jarFile, (ResourceHandler) (path, is) -> {
                if (rootDir == null || path.startsWith(rootDir)) {
                    handler.processResource(path, is);
                }
            });
        }

        @Override
        public void close() {
            JarFileUtil.close(jarFile);
        }

    }

    /**
     * Object that handles a collection of resources represented by the vfszip protocol of JBossAS5.
     *
     * @author koichik
     */
    public static class VfsZipTraverser implements Traverser {

        /** Prefix for .class files inside a WAR. */
        protected static final String WAR_CLASSES_PREFIX = "/WEB-INF/CLASSES/";

        /** The root package. */
        protected final String rootPackage;

        /** The root directory. */
        protected final String rootDir;

        /** The URL of the zip file. */
        protected final URL zipUrl;

        /** Prefix of entries inside the zip. */
        protected final String prefix;

        /** Set of entry names inside the zip. */
        protected final Set<String> entryNames = newHashSet();

        /**
         * Constructs an instance.
         *
         * @param url
         *            The URL representing the root.
         * @param rootPackage
         *            The root package.
         * @param rootDir
         *            The root directory.
         */
        public VfsZipTraverser(final URL url, final String rootPackage, final String rootDir) {
            URL zipUrl = url;
            String prefix = "";
            if (rootPackage != null) {
                final String[] paths = rootPackage.split("\\.");
                for (final String path : paths) {
                    zipUrl = URLUtil.create(zipUrl, "..");
                }
            }
            loadFromZip(zipUrl);
            if (entryNames.isEmpty()) {
                final String zipUrlString = zipUrl.toExternalForm();
                if (zipUrlString.toUpperCase().endsWith(WAR_CLASSES_PREFIX)) {
                    final URL warUrl = URLUtil.create(zipUrl, "../..");
                    final String path = warUrl.getPath();
                    zipUrl = FileUtil.toURL(new File(path.substring(0, path.length() - 1)));
                    prefix = zipUrlString.substring(warUrl.toExternalForm().length());
                    loadFromZip(zipUrl);
                }
            }

            this.rootPackage = rootPackage;
            this.rootDir = rootDir;
            this.zipUrl = zipUrl;
            this.prefix = prefix;
        }

        private void loadFromZip(final URL zipUrl) {
            final ZipInputStream zis = new ZipInputStream(URLUtil.openStream(zipUrl));
            try {
                ZipEntry entry = null;
                while ((entry = ZipInputStreamUtil.getNextEntry(zis)) != null) {
                    entryNames.add(entry.getName());
                    ZipInputStreamUtil.closeEntry(zis);
                }
            } finally {
                CloseableUtil.close(zis);
            }
        }

        @Override
        public boolean isExistClass(final String className) {
            final String entryName = prefix + toClassFile(ClassUtil.concatName(rootPackage, className));
            return entryNames.contains(entryName);
        }

        @Override
        public void forEach(final ClassHandler handler) {
            final ZipInputStream zis = new ZipInputStream(URLUtil.openStream(zipUrl));
            try {
                ClassTraversalUtil.forEach(zis, prefix, (ClassHandler) (packageName, shortClassName) -> {
                    if (rootPackage == null || packageName != null && packageName.startsWith(rootPackage)) {
                        handler.processClass(packageName, shortClassName);
                    }
                });
            } finally {
                CloseableUtil.close(zis);
            }
        }

        @Override
        public void forEach(final ResourceHandler handler) {
            final ZipInputStream zis = new ZipInputStream(URLUtil.openStream(zipUrl));
            try {
                ResourceTraversalUtil.forEach(zis, prefix, (ResourceHandler) (path, is) -> {
                    if (rootDir == null || path.startsWith(rootDir)) {
                        handler.processResource(path, is);
                    }
                });
            } finally {
                CloseableUtil.close(zis);
            }
        }

        @Override
        public void close() {
        }

    }

}
