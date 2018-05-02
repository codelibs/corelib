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
 * ファイルシステム上やJarファイル中に展開されている、クラスやリソースの集まりを横断的に処理するためのユーティリティです。
 * <p>
 * 対象となるファイルシステム上のディレクトリや、Jarファイルの一などは{@link URL}によって与えられます。 URLのプロトコルに応じて適切な
 * {@link Traverser}が返されるので、そのメソッドを呼び出すことでクラスやリソースをトラバースすることが出来ます。
 * </p>
 * <p>
 * 次のプロトコルをサポートしています。
 * </p>
 * <ul>
 * <li>{@literal file}</li>
 * <li>{@literal jar}</li>
 * <li>{@literal wsjar} (WebShpere独自プロトコル、{@literal jar}の別名)</li>
 * <li>{@literal zip} (WebLogic独自プロトコル、通常のZipファイルは{@literal jar}プロトコルを使用してください)</li>
 * <li>{@literal code-source} (Oracle AS(OC4J)独自プロトコル)</li>
 * <li>{@literal vfsfile} (JBossAS5独自プロトコル、{@literal file}の別名)</li>
 * <li>{@literal vfszip} (JBossAS5独自プロトコル)</li>
 * </ul>
 *
 * @author koichik
 * @see URLUtil#toCanonicalProtocol(String)
 * @see ClassTraversalUtil
 * @see ResourceTraversalUtil
 */
public abstract class TraversalUtil {

    /** 空の{@link Traverser}の配列です。 */
    protected static final Traverser[] EMPTY_ARRAY = new Traverser[0];

    private static final Logger logger = Logger.getLogger(TraversalUtil.class);

    /** URLのプロトコルをキー、{@link TraverserFactory}を値とするマッピングです。 */
    protected static final ConcurrentMap<String, TraverserFactory> traverserFactories = newConcurrentHashMap();
    static {
        addTraverserFactory("file", (url, rootPackage, rootDir) -> new FileSystemTraverser(getBaseDir(url, rootDir), rootPackage, rootDir));
        addTraverserFactory("jar", (url, rootPackage, rootDir) -> new JarFileTraverser(url, rootPackage, rootDir));
        addTraverserFactory("zip",
                (url, rootPackage, rootDir) -> new JarFileTraverser(JarFileUtil.create(new File(ZipFileUtil.toZipFilePath(url))),
                        rootPackage, rootDir));
        addTraverserFactory("code-source", (url, rootPackage, rootDir) -> new JarFileTraverser(URLUtil.create("jar:file:" + url.getPath()),
                rootPackage, rootDir));
        addTraverserFactory("vfszip", (url, rootPackage, rootDir) -> new VfsZipTraverser(url, rootPackage, rootDir));
    }

    /**
     * {@link TraverserFactory}を追加します。
     *
     * @param protocol
     *            URLのプロトコル。{@literal null}や空文字列であってはいけません
     * @param factory
     *            プロトコルに対応する{@link Traverser}のファクトリ。{@literal null}であってはいけません
     */
    public static void addTraverserFactory(final String protocol, final TraverserFactory factory) {
        assertArgumentNotEmpty("protocol", protocol);
        assertArgumentNotNull("factory", factory);

        traverserFactories.put(protocol, factory);
    }

    /**
     * 指定のクラスを基点とする、リソースやクラスの集まりを扱う{@link Traverser}を返します。
     * <p>
     * このメソッドが返す{@link Traverser}は、指定されたクラスをFQNで参照可能なパスをルートとします。 例えば指定されたクラスが
     * <code>foo.Bar</code>で、そのクラスファイルが <code>classes/foo/Bar.class</code>の場合、
     * このメソッドが返す {@link Traverser}は<code>classes</code>ディレクトリ以下のリソースの集合を扱います。
     * </p>
     *
     * @param referenceClass
     *            基点となるクラス。{@literal null}であってはいけません
     * @return 指定のクラスを基点とする、クラスやリソースの集まりを扱う{@link Traverser}
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
     * 指定のディレクトリを基点とする、クラスやリソースの集まりを扱う{@link Traverser}を返します。
     *
     * @param rootDir
     *            ルートディレクトリ。{@literal null}や空文字列であってはいけません
     * @return 指定のディレクトリを基点とする、クラスやリソースの集まりを扱う{@link Traverser}
     */
    public static Traverser getTraverser(final String rootDir) {
        assertArgumentNotEmpty("rootDir", rootDir);

        final URL url = ResourceUtil.getResource(rootDir.endsWith("/") ? rootDir : rootDir + '/');
        return getTraverser(url, null, rootDir);
    }

    /**
     * 指定のルートパッケージを基点とする、クラスやリソースの集まりを扱う{@link Traverser}の配列を返します。
     *
     * @param rootPackage
     *            ルートパッケージ
     * @return 指定のルートパッケージを基点とするリソースの集まりを扱う{@link Traverser}の配列
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
            logger.log("WCL0014", new Object[] { rootPackage });
            return EMPTY_ARRAY;
        }
        return list.toArray(new Traverser[list.size()]);
    }

    /**
     * URLを扱う{@link Traverser}を作成して返します。
     * <p>
     * URLのプロトコルが未知の場合は<code>null</code>を返します。
     * </p>
     *
     * @param url
     *            リソースのURL
     * @param rootPackage
     *            ルートパッケージ
     * @param rootDir
     *            ルートディレクトリ
     * @return URLを扱う{@link Traverser}
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
     * パッケージ名をディレクトリ名に変換して返します。
     *
     * @param packageName
     *            パッケージ名
     * @return ディレクトリ名
     */
    protected static String toDirectoryName(final String packageName) {
        if (StringUtil.isEmpty(packageName)) {
            return null;
        }
        return packageName.replace('.', '/') + '/';
    }

    /**
     * クラス名をクラスファイルのパス名に変換して返します。
     *
     * @param className
     *            クラス名
     * @return クラスファイルのパス名
     */
    protected static String toClassFile(final String className) {
        assertArgumentNotNull("className", className);

        return className.replace('.', '/') + ".class";
    }

    /**
     * ファイルを表すURLからルートパッケージの上位となるベースディレクトリを求めて返します。
     *
     * @param url
     *            ファイルを表すURL
     * @param baseName
     *            ベース名
     * @return ルートパッケージの上位となるベースディレクトリ
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
     * {@link Traverser}のインスタンスを作成するファクトリです。
     *
     * @author koichik
     */
    public interface TraverserFactory {
        /**
         * {@link Traverser}のインスタンスを作成して返します。
         *
         * @param url
         *            リソースを表すURL
         * @param rootPackage
         *            ルートパッケージ
         * @param rootDir
         *            ルートディレクトリ
         * @return URLで表されたリソースを扱う{@link Traverser}
         */
        Traverser create(URL url, String rootPackage, String rootDir);
    }

    /**
     * ファイルシステム上のリソースの集まりを扱うオブジェクトです。
     *
     * @author koichik
     */
    public static class FileSystemTraverser implements Traverser {

        /** ベースディレクトリです。 */
        protected final File baseDir;

        /** ルートパッケージです。 */
        protected final String rootPackage;

        /** ルートディレクトリです。 */
        protected final String rootDir;

        /**
         * インスタンスを構築します。
         *
         * @param baseDir
         *            ベースディレクトリ
         * @param rootPackage
         *            ルートパッケージ
         * @param rootDir
         *            ルートディレクトリ
         */
        public FileSystemTraverser(final File baseDir, final String rootPackage, final String rootDir) {
            this.baseDir = baseDir;
            this.rootPackage = rootPackage;
            this.rootDir = rootDir;
        }

        /**
         * インスタンスを構築します。
         *
         * @param url
         *            ディレクトリを表すURL
         * @param rootPackage
         *            ルートパッケージ
         * @param rootDir
         *            ルートディレクトリ
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
     * Jarファイル中のリソースの集まりを扱うオブジェクトです。
     *
     * @author koichik
     */
    public static class JarFileTraverser implements Traverser {

        /** Jarファイルです。 */
        protected final JarFile jarFile;

        /** ルートパッケージです。 */
        protected final String rootPackage;

        /** ルートディレクトリです。 */
        protected final String rootDir;

        /**
         * インスタンスを構築します。
         *
         * @param jarFile
         *            Jarファイル
         * @param rootPackage
         *            ルートパッケージ
         * @param rootDir
         *            ルートディレクトリ
         */
        public JarFileTraverser(final JarFile jarFile, final String rootPackage, final String rootDir) {
            this.jarFile = jarFile;
            this.rootPackage = rootPackage;
            this.rootDir = rootDir;
        }

        /**
         * インスタンスを構築します。
         *
         * @param url
         *            Jarファイルを表すURL
         * @param rootPackage
         *            ルートパッケージ
         * @param rootDir
         *            ルートディレクトリ
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
     * JBossAS5のvfszipプロトコルで表されるリソースの集まりを扱うオブジェクトです。
     *
     * @author koichik
     */
    public static class VfsZipTraverser implements Traverser {

        /** WAR内の.classファイルの接頭辞です。 */
        protected static final String WAR_CLASSES_PREFIX = "/WEB-INF/CLASSES/";

        /** ルートパッケージです。 */
        protected final String rootPackage;

        /** ルートディレクトリです。 */
        protected final String rootDir;

        /** ZipのURLです。 */
        protected final URL zipUrl;

        /** Zip内のエントリの接頭辞です。 */
        protected final String prefix;

        /** Zip内のエントリ名の{@link Set}です。 */
        protected final Set<String> entryNames = newHashSet();

        /**
         * インスタンスを構築します。
         *
         * @param url
         *            ルートを表すURL
         * @param rootPackage
         *            ルートパッケージ
         * @param rootDir
         *            ルートディレクトリ
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
