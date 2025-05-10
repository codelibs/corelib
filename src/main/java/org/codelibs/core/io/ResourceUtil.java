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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.exception.ResourceNotFoundRuntimeException;
import org.codelibs.core.jar.JarFileUtil;
import org.codelibs.core.net.URLUtil;

/**
 * リソース用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class ResourceUtil {

    /**
     * リソースパスを返します。
     *
     * @param path
     *            パス。{@literal null}であってはいけません
     * @param extension
     *            拡張子
     * @return リソースパス
     */
    public static String getResourcePath(final String path, final String extension) {
        assertArgumentNotNull("path", path);

        if (extension == null) {
            return path;
        }
        final String ext = "." + extension;
        if (path.endsWith(ext)) {
            return path;
        }
        return path.replace('.', '/') + ext;
    }

    /**
     * リソースパスを返します。
     *
     * @param clazz
     *            クラス。{@literal null}であってはいけません
     * @return リソースパス
     */
    public static String getResourcePath(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return clazz.getName().replace('.', '/') + ".class";
    }

    /**
     * コンテキストクラスローダを返します。
     *
     * @return コンテキストクラスローダ
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * コンテキストクラスローダからリソースを返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @return リソースの{@link URL}
     * @see #getResource(String, String)
     */
    public static URL getResource(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResource(path, null);
    }

    /**
     * コンテキストクラスローダからリソースを返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @param extension
     *            リソースの拡張子
     * @return リソースの{@link URL}
     */
    public static URL getResource(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        final URL url = getResourceNoException(path, extension);
        if (url != null) {
            return url;
        }
        throw new ResourceNotFoundRuntimeException(getResourcePath(path, extension));
    }

    /**
     * コンテキストクラスローダからリソースを返します。見つからなかった場合は<code>null</code>を返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @return リソースの{@link URL}
     * @see #getResourceNoException(String, String)
     */
    public static URL getResourceNoException(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceNoException(path, null);
    }

    /**
     * コンテキストクラスローダからリソースを返します。見つからなかった場合は<code>null</code>を返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @param extension
     *            拡張子
     * @return リソースの{@link URL}
     * @see #getResourceNoException(String, String, ClassLoader)
     */
    public static URL getResourceNoException(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        return getResourceNoException(path, extension, Thread.currentThread().getContextClassLoader());
    }

    /**
     * 指定のクラスローダからリソースを返します。見つからなかった場合は<code>null</code>を返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @param extension
     *            リソースの拡張子
     * @param loader
     *            リソースを検索するクラスローダ。{@literal null}であってはいけません
     * @return リソース
     * @see #getResourcePath(String, String)
     */
    public static URL getResourceNoException(final String path, final String extension, final ClassLoader loader) {
        assertArgumentNotNull("loader", loader);

        if (path == null || loader == null) {
            return null;
        }
        final String p = getResourcePath(path, extension);
        return loader.getResource(p);
    }

    /**
     * コンテキストクラスローダからリソースを検索してストリームとして返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @return ストリーム
     * @see #getResourceAsStream(String, String)
     */
    public static InputStream getResourceAsStream(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceAsStream(path, null);
    }

    /**
     * コンテキストクラスローダからリソースを検索してストリームとして返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @param extension
     *            リソースの拡張子
     * @return ストリーム
     * @see #getResource(String, String)
     */
    public static InputStream getResourceAsStream(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        final URL url = getResource(path, extension);
        return URLUtil.openStream(url);
    }

    /**
     * コンテキストクラスローダからリソースを検索してストリームとして返します。 リソースが見つからなかった場合は<code>null</code>
     * を返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @return ストリーム
     * @see #getResourceAsStreamNoException(String, String)
     */
    public static InputStream getResourceAsStreamNoException(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceAsStreamNoException(path, null);
    }

    /**
     * コンテキストクラスローダからリソースを検索してストリームとして返します。 リソースが見つからなかった場合は<code>null</code>
     * を返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @param extension
     *            リソースの拡張子
     * @return ストリーム
     * @see #getResourceNoException(String, String)
     */
    public static InputStream getResourceAsStreamNoException(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        final URL url = getResourceNoException(path, extension);
        if (url == null) {
            return null;
        }
        try {
            return url.openStream();
        } catch (final IOException e) {
            return null;
        }
    }

    /**
     * コンテキストクラスローダにリソースが存在するかどうかを返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @return リソースが存在すれば{@literal true}
     * @see #getResourceNoException(String)
     */
    public static boolean isExist(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceNoException(path) != null;
    }

    /**
     * コンテキストクラスローダからプロパティファイルをロードして返します。
     *
     * @param path
     *            プロパティファイルのパス。{@literal null}や空文字列であってはいけません
     * @return プロパティファイル
     */
    public static Properties getProperties(final String path) {
        assertArgumentNotEmpty("path", path);

        final Properties props = new Properties();
        final InputStream is = getResourceAsStream(path);
        try {
            props.load(is);
            return props;
        } catch (final IOException ex) {
            throw new IORuntimeException(ex);
        } finally {
            CloseableUtil.close(is);
        }
    }

    /**
     * パスの拡張子を返します。
     *
     * @param path
     *            パス。{@literal null}であってはいけません
     * @return 拡張子
     */
    public static String getExtension(final String path) {
        assertArgumentNotNull("path", path);

        final int extPos = path.lastIndexOf(".");
        if (extPos >= 0) {
            return path.substring(extPos + 1);
        }
        return null;
    }

    /**
     * パスから拡張子を取り除きます。
     *
     * @param path
     *            パス。{@literal null}であってはいけません
     * @return 拡張子を取り除いたパス
     */
    public static String removeExtension(final String path) {
        assertArgumentNotNull("path", path);

        final int extPos = path.lastIndexOf(".");
        if (extPos >= 0) {
            return path.substring(0, extPos);
        }
        return path;
    }

    /**
     * 指定されたクラスのクラスファイルが置かれているルートディレクトリを返します。
     *
     * @param clazz
     *            クラス。{@literal null}であってはいけません
     * @return ルートディレクトリ
     * @see #getBuildDir(String)
     */
    public static File getBuildDir(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return getBuildDir(getResourcePath(clazz));
    }

    /**
     * クラスファイルが置かれているルートディレクトリを返します。
     *
     * @param path
     *            クラスファイルのパス。{@literal null}や空文字列であってはいけません
     * @return ルートディレクトリ
     */
    public static File getBuildDir(final String path) {
        assertArgumentNotEmpty("path", path);

        File dir = null;
        final URL url = getResource(path);
        if ("file".equals(url.getProtocol())) {
            final int num = path.split("/").length;
            dir = new File(getFileName(url));
            for (int i = 0; i < num; ++i) {
                dir = dir.getParentFile();
            }
        } else {
            dir = new File(JarFileUtil.toJarFilePath(url));
        }
        return dir;
    }

    /**
     * リソースのURLを外部形式に変換します。
     *
     * @param url
     *            リソースのURL。{@literal null}であってはいけません
     * @return 外部形式
     */
    public static String toExternalForm(final URL url) {
        assertArgumentNotNull("url", url);

        final String s = url.toExternalForm();
        return URLUtil.decode(s, "UTF8");
    }

    /**
     * リソースのファイル名を返します。
     *
     * @param url
     *            リソースのURL。{@literal null}であってはいけません
     * @return ファイル名
     */
    public static String getFileName(final URL url) {
        assertArgumentNotNull("url", url);

        final String s = url.getFile();
        return URLUtil.decode(s, "UTF8");
    }

    /**
     * リソースのファイルを返します。
     *
     * @param url
     *            リソースのURL。{@literal null}であってはいけません
     * @return ファイル
     */
    public static File getFile(final URL url) {
        assertArgumentNotNull("url", url);

        final File file = new File(getFileName(url));
        if (file != null && file.exists()) {
            return file;
        }
        return null;
    }

    /**
     * リソースをファイルとして返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @return ファイル
     * @see #getResourceAsFile(String, String)
     */
    public static File getResourceAsFile(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceAsFile(path, null);
    }

    /**
     * リソースをファイルとして返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @param extension
     *            リソースの拡張子
     * @return ファイル
     * @see #getFile(URL)
     */
    public static File getResourceAsFile(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        return getFile(getResource(path, extension));
    }

    /**
     * クラスファイルを表すリソースをファイルとして返します。リソースが見つからない場合は<code>null</code>を返します。
     *
     * @param clazz
     *            クラス。{@literal null}であってはいけません
     * @return ファイル
     * @see #getResourceAsFileNoException(String)
     */
    public static File getResourceAsFileNoException(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return getResourceAsFileNoException(getResourcePath(clazz));
    }

    /**
     * リソースをファイルとして返します。リソースが見つからない場合は<code>null</code>を返します。
     *
     * @param path
     *            リソースのパス。{@literal null}や空文字列であってはいけません
     * @return ファイル
     * @see #getResourceNoException(String)
     */
    public static File getResourceAsFileNoException(final String path) {
        assertArgumentNotEmpty("path", path);

        final URL url = getResourceNoException(path);
        if (url == null) {
            return null;
        }
        return getFile(url);
    }

    /**
     * パスを変換します。
     *
     * @param path
     *            リソースのパス
     * @param clazz
     *            クラス
     * @return 変換された結果
     */
    public static String convertPath(final String path, final Class<?> clazz) {
        assertArgumentNotEmpty("path", path);
        assertArgumentNotNull("clazz", clazz);

        if (isExist(path)) {
            return path;
        }
        final String prefix = clazz.getName().replace('.', '/').replaceFirst("/[^/]+$", "");
        final String extendedPath = prefix + "/" + path;
        if (ResourceUtil.getResourceNoException(extendedPath) != null) {
            return extendedPath;
        }
        return path;
    }

}
