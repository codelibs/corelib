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
 * Utility class for resource handling.
 *
 * @author higa
 */
public abstract class ResourceUtil {

    /**
     * Do not instantiate.
     */
    protected ResourceUtil() {
    }

    /**
     * Returns the resource path.
     *
     * @param path
     *            The path. Must not be {@literal null}.
     * @param extension
     *            The extension.
     * @return The resource path.
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
     * Returns the resource path.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The resource path.
     */
    public static String getResourcePath(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return clazz.getName().replace('.', '/') + ".class";
    }

    /**
     * Returns the context class loader.
     *
     * @return the context class loader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Returns the resource from the context class loader.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @return The resource {@link URL}
     * @see #getResource(String, String)
     */
    public static URL getResource(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResource(path, null);
    }

    /**
     * Returns the resource from the context class loader.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @param extension
     *            The resource extension.
     * @return The resource {@link URL}
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
     * Returns the resource from the context class loader. Returns <code>null</code> if not found.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @return The resource {@link URL}
     * @see #getResourceNoException(String, String)
     */
    public static URL getResourceNoException(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceNoException(path, null);
    }

    /**
     * Returns the resource from the context class loader. Returns <code>null</code> if not found.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @param extension
     *            The extension.
     * @return The resource {@link URL}
     * @see #getResourceNoException(String, String, ClassLoader)
     */
    public static URL getResourceNoException(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        return getResourceNoException(path, extension, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Returns the resource from the specified class loader. Returns <code>null</code> if not found.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @param extension
     *            The resource extension.
     * @param loader
     *            The class loader to search for the resource. Must not be {@literal null}.
     * @return The resource {@link URL}
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
     * Returns the resource as a stream from the context class loader.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @return The input stream
     * @see #getResourceAsStream(String, String)
     */
    public static InputStream getResourceAsStream(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceAsStream(path, null);
    }

    /**
     * Returns the resource as a stream from the context class loader.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @param extension
     *            The resource extension.
     * @return The input stream
     * @see #getResource(String, String)
     */
    public static InputStream getResourceAsStream(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        final URL url = getResource(path, extension);
        return URLUtil.openStream(url);
    }

    /**
     * Returns the resource as a stream from the context class loader.
     * Returns <code>null</code> if the resource is not found.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @return The input stream, or <code>null</code> if not found.
     * @see #getResourceAsStreamNoException(String, String)
     */
    public static InputStream getResourceAsStreamNoException(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceAsStreamNoException(path, null);
    }

    /**
     * Returns the resource as a stream from the context class loader.
     * Returns <code>null</code> if the resource is not found.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @param extension
     *            The resource extension.
     * @return The input stream, or <code>null</code> if not found.
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
     * Returns whether the resource exists in the context class loader.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @return {@literal true} if the resource exists
     * @see #getResourceNoException(String)
     */
    public static boolean isExist(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceNoException(path) != null;
    }

    /**
     * Loads and returns a properties file from the context class loader.
     *
     * @param path
     *            The path to the properties file. Must not be {@literal null} or empty string.
     * @return The properties file.
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
     * Returns the extension of the path.
     *
     * @param path
     *            The path. Must not be {@literal null}.
     * @return The extension.
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
     * Removes the extension from the path.
     *
     * @param path
     *            The path. Must not be {@literal null}.
     * @return The path without the extension.
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
     * Returns the root directory where the class file of the specified class is located.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The root directory.
     * @see #getBuildDir(String)
     */
    public static File getBuildDir(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return getBuildDir(getResourcePath(clazz));
    }

    /**
     * Returns the root directory where the class file is located.
     *
     * @param path
     *            The path to the class file. Must not be {@literal null} or empty string.
     * @return The root directory.
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
     * Converts the resource URL to an external form.
     *
     * @param url
     *            The resource URL. Must not be {@literal null}.
     * @return The external form.
     */
    public static String toExternalForm(final URL url) {
        assertArgumentNotNull("url", url);

        final String s = url.toExternalForm();
        return URLUtil.decode(s, "UTF8");
    }

    /**
     * Returns the file name of the resource.
     *
     * @param url
     *            The resource URL. Must not be {@literal null}.
     * @return The file name.
     */
    public static String getFileName(final URL url) {
        assertArgumentNotNull("url", url);

        final String s = url.getFile();
        return URLUtil.decode(s, "UTF8");
    }

    /**
     * Returns the file of the resource.
     *
     * @param url
     *            The resource URL. Must not be {@literal null}.
     * @return The file.
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
     * Returns the resource as a file.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @return The file.
     * @see #getResourceAsFile(String, String)
     */
    public static File getResourceAsFile(final String path) {
        assertArgumentNotEmpty("path", path);

        return getResourceAsFile(path, null);
    }

    /**
     * Returns the resource as a file.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @param extension
     *            The resource extension.
     * @return The file.
     * @see #getFile(URL)
     */
    public static File getResourceAsFile(final String path, final String extension) {
        assertArgumentNotEmpty("path", path);

        return getFile(getResource(path, extension));
    }

    /**
     * Returns the resource representing the class file as a file. Returns <code>null</code> if the resource is not found.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The file.
     * @see #getResourceAsFileNoException(String)
     */
    public static File getResourceAsFileNoException(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return getResourceAsFileNoException(getResourcePath(clazz));
    }

    /**
     * Returns the resource as a file. Returns <code>null</code> if the resource is not found.
     *
     * @param path
     *            The resource path. Must not be {@literal null} or empty string.
     * @return The file.
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
     * Converts the path.
     *
     * @param path
     *            The resource path.
     * @param clazz
     *            The class.
     * @return The converted result.
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
