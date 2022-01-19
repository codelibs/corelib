/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
 * リソースをトラバースするためのクラスです。
 *
 * @author taedium
 * @see ResourceHandler
 * @see TraversalUtil
 */
public abstract class ResourceTraversalUtil {

    /**
     * ファイルシステムに含まれるリソースをトラバースします。
     *
     * @param rootDir
     *            ルートディレクトリ。{@literal null}であってはいけません
     * @param handler
     *            リソースを処理するハンドラ。{@literal null}であってはいけません
     */
    public static void forEach(final File rootDir, final ResourceHandler handler) {
        assertArgumentNotNull("rootDir", rootDir);
        assertArgumentNotNull("handler", handler);

        forEach(rootDir, null, handler);
    }

    /**
     * ファイルシステムに含まれるリソースをトラバースします。
     * <p>
     * ルートディレクトリ以下のリソースのうち、ベースディレクトリで始まるパスを持つリソースがトラバースの対象となります。
     * リソースを処理するハンドラには、ルートディレクトリからの相対パスが渡されます。 例えばルートディレクトリが
     * <code>/aaa/bbb</code>で、ベースディレクトリが<code>ccc/ddd</code>の場合、
     * <code>/aaa/bbb/ccc/ddd/eee.txt</code>というリソースが存在すると、 ハンドラには
     * <code>ccc/ddd/eee.txt</code>というパスが渡されます。
     * </p>
     *
     * @param rootDir
     *            ルートディレクトリ。{@literal null}であってはいけません
     * @param baseDirectory
     *            ベースディレクトリ
     * @param handler
     *            リソースを処理するハンドラ。{@literal null}であってはいけません
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
     * Jarファイル形式のファイルに含まれるリソースをトラバースします。
     *
     * @param jarFile
     *            jarファイル形式のファイル。{@literal null}であってはいけません
     * @param handler
     *            リソースを処理するハンドラ。{@literal null}であってはいけません
     */
    public static void forEach(final JarFile jarFile, final ResourceHandler handler) {
        assertArgumentNotNull("jarFile", jarFile);
        assertArgumentNotNull("handler", handler);

        forEach(jarFile, "", handler);
    }

    /**
     * Jarファイル形式のファイルに含まれるリソースをトラバースします。
     * <p>
     * Jarファイル内のリソースのうち、接頭辞で始まるパスを持つリソースがトラバースの対象となります。
     * リソースを処理するハンドラには、接頭辞を除くエントリ名が渡されます。 例えば接頭辞が <code>/aaa/bbb/</code>
     * で、Jarファイル内に <code>/aaa/bbb/ccc/ddd/eee.txt</code>というリソースが存在すると、 ハンドラには
     * <code>ccc/ddd/eee.txt</code>というパスが渡されます。
     * </p>
     *
     * @param jarFile
     *            jarファイル形式のファイル。{@literal null}であってはいけません
     * @param prefix
     *            トラバースするリソースの名前が含む接頭辞。{@literal null}であってはいけません。
     *            空文字列でない場合はスラッシュ('/')で終了していなければなりません
     * @param handler
     *            リソースを処理するハンドラ。{@literal null}であってはいけません
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
     * ZIPファイル形式の入力ストリームに含まれるリソースをトラバースします。
     *
     * @param zipInputStream
     *            ZIPファイル形式の入力ストリーム。{@literal null}であってはいけません
     * @param handler
     *            リソースを処理するハンドラ。{@literal null}であってはいけません
     */
    public static void forEach(final ZipInputStream zipInputStream, final ResourceHandler handler) {
        assertArgumentNotNull("zipInputStream", zipInputStream);
        assertArgumentNotNull("handler", handler);

        forEach(zipInputStream, "", handler);
    }

    /**
     * ZIPファイル形式の入力ストリームに含まれるリソースをトラバースします。
     * <p>
     * 入力ストリーム内のリソースのうち、接頭辞で始まるパスを持つリソースがトラバースの対象となります。
     * リソースを処理するハンドラには、接頭辞を除くエントリ名が渡されます。 例えば接頭辞が <code>/aaa/bbb/</code>
     * で、入力ストリーム内に <code>/aaa/bbb/ccc/ddd/eee.txt</code>というリソースが存在すると、 ハンドラには
     * <code>ccc/ddd/eee.txt</code>というパスが渡されます。
     * </p>
     *
     * @param zipInputStream
     *            ZIPファイル形式の入力ストリーム。{@literal null}であってはいけません
     * @param prefix
     *            トラバースするリソースの名前が含む接頭辞。{@literal null}であってはいけません。
     *            空文字列でない場合はスラッシュ('/')で終了していなければなりません
     * @param handler
     *            リソースを処理するハンドラ。{@literal null}であってはいけません
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
     * ファイルシステムに含まれるリソースをトラバースします。
     *
     * @param rootDir
     *            ルートディレクトリ
     * @param baseDir
     *            ベースディレクトリ
     * @param handler
     *            リソースを処理するハンドラ
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
     * ベースディレクトリを表す{@link File}を返します。
     *
     * @param rootDir
     *            ルートディレクトリ
     * @param baseDirectory
     *            ベースディレクトリ
     * @return ベースディレクトリを表す{@link File}
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
