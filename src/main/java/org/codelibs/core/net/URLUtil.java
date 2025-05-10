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
package org.codelibs.core.net;

import static org.codelibs.core.collection.ArrayUtil.asArray;
import static org.codelibs.core.collection.CollectionsUtil.newHashMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import org.codelibs.core.exception.ClRuntimeException;
import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility class for handling {@link URL}.
 *
 * @author higa
 */
public abstract class URLUtil {

    /** Map for normalizing protocols */
    protected static final Map<String, String> CANONICAL_PROTOCOLS = newHashMap();
    static {
        CANONICAL_PROTOCOLS.put("wsjar", "jar"); // WebSphereがJarファイルのために使用する固有のプロトコル
        CANONICAL_PROTOCOLS.put("vfsfile", "file"); // JBossAS5がファイルシステムのために使用する固有のプロトコル
    }

    /**
     * Opens a URL and returns an {@link InputStream}.
     *
     * @param url
     *            The URL. Must not be {@literal null}.
     * @return An {@link InputStream} to read the resource represented by the URL.
     */
    public static InputStream openStream(final URL url) {
        assertArgumentNotNull("url", url);

        try {
            final URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Returns a {@link URLConnection} object that represents a connection to the remote object referred to by the URL.
     *
     * @param url
     *            The URL. Must not be {@literal null}.
     * @return A {@link URLConnection} object to the URL.
     */
    public static URLConnection openConnection(final URL url) {
        assertArgumentNotNull("url", url);

        try {
            final URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection;
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates a <code>URL</code> object from its string representation.
     *
     * @param spec
     *            A string to be parsed as a <code>URL</code>. Must not be {@literal null} or empty.
     * @return A <code>URL</code> object.
     */
    public static URL create(final String spec) {
        assertArgumentNotEmpty("spec", spec);

        try {
            return new URL(spec);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates a <code>URL</code> by parsing the specified spec within the specified context.
     *
     * @param context
     *            The context in which to parse the spec. Must not be {@literal null}.
     * @param spec
     *            A string to be parsed as a <code>URL</code>. Must not be {@literal null} or empty.
     * @return A <code>URL</code>.
     */
    public static URL create(final URL context, final String spec) {
        assertArgumentNotNull("context", context);
        assertArgumentNotEmpty("spec", spec);

        try {
            return new URL(context, spec);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Converts a string into <code>application/x-www-form-urlencoded</code>
     * format using the specified encoding scheme.
     *
     * @param s
     *            The string to be converted. Must not be {@literal null} or empty.
     * @param enc
     *            The encoding scheme. Must not be {@literal null} or empty.
     * @return The string encoded in <code>application/x-www-form-urlencoded</code> format.
     */
    public static String encode(final String s, final String enc) {
        assertArgumentNotEmpty("s", s);
        assertArgumentNotEmpty("enc", enc);

        try {
            return URLEncoder.encode(s, enc);
        } catch (final UnsupportedEncodingException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Decodes a <code>application/x-www-form-urlencoded</code> string using the specified encoding scheme.
     *
     * @param s
     *            The string encoded in <code>application/x-www-form-urlencoded</code> format.
     *            Must not be {@literal null} or empty.
     * @param enc
     *            The encoding scheme. Must not be {@literal null} or empty.
     * @return The decoded string.
     */
    public static String decode(final String s, final String enc) {
        assertArgumentNotEmpty("s", s);
        assertArgumentNotEmpty("enc", enc);

        try {
            return URLDecoder.decode(s, enc);
        } catch (final UnsupportedEncodingException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Returns the normalized protocol.
     *
     * @param protocol
     *            The protocol. Must not be {@literal null} or empty.
     * @return The normalized protocol.
     */
    public static String toCanonicalProtocol(final String protocol) {
        assertArgumentNotEmpty("protocol", protocol);

        final String canonicalProtocol = CANONICAL_PROTOCOLS.get(protocol);
        if (canonicalProtocol != null) {
            return canonicalProtocol;
        }
        return protocol;
    }

    /**
     * Returns the {@link File} object of the Jar file indicated by the URL.
     *
     * @param fileUrl
     *            The URL of the Jar file. Must not be {@literal null}.
     * @return The {@link File} of the Jar file.
     */
    public static File toFile(final URL fileUrl) {
        assertArgumentNotNull("fileUrl", fileUrl);

        try {
            final String path = URLDecoder.decode(fileUrl.getPath(), "UTF-8");
            return new File(path).getAbsoluteFile();
        } catch (final Exception e) {
            throw new ClRuntimeException("ECL0091", asArray(fileUrl), e);
        }
    }

}
