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

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.FieldDesc;
import org.codelibs.core.beans.factory.BeanDescFactory;
import org.codelibs.core.exception.ClRuntimeException;
import org.codelibs.core.exception.IORuntimeException;

/**
 * {@link URL}を扱うユーティリティ・クラスです。
 *
 * @author higa
 */
public abstract class URLUtil {

    /** プロトコルを正規化するためのマップ */
    protected static final Map<String, String> CANONICAL_PROTOCOLS = newHashMap();
    static {
        CANONICAL_PROTOCOLS.put("wsjar", "jar"); // WebSphereがJarファイルのために使用する固有のプロトコル
        CANONICAL_PROTOCOLS.put("vfsfile", "file"); // JBossAS5がファイルシステムのために使用する固有のプロトコル
    }

    /**
     * URLをオープンして{@link InputStream}を返します。
     *
     * @param url
     *            URL。{@literal null}であってはいけません
     * @return URLが表すリソースを読み込むための{@link InputStream}
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
     * URLが参照するリモートオブジェクトへの接続を表す{@link URLConnection}オブジェクトを返します。
     *
     * @param url
     *            URL。{@literal null}であってはいけません
     * @return URLへの{@link URLConnection}オブジェクト
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
     * 文字列表現から<code>URL</code>オブジェクトを作成します。
     *
     * @param spec
     *            <code>URL</code>として構文解析される文字列。{@literal null}や空文字列であってはいけません
     * @return <code>URL</code>
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
     * 指定されたコンテキスト内の指定された仕様で構文解析することによって、<code>URL</code>を生成します。
     *
     * @param context
     *            仕様を構文解析するコンテキスト。{@literal null}であってはいけません
     * @param spec
     *            <code>URL</code>として構文解析される文字列。{@literal null}や空文字列であってはいけません
     * @return <code>URL</code>
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
     * 指定のエンコーディング方式を使用して文字列を<code>application/x-www-form-urlencoded</code>
     * 形式に変換します。
     *
     * @param s
     *            変換対象の文字列。{@literal null}や空文字列であってはいけません
     * @param enc
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return <code>application/x-www-form-urlencoded</code>でエンコード文字列
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
     * 指定のエンコーディング方式で<code>application/x-www-form-urlencoded</code>文字列をデコードします。
     *
     * @param s
     *            <code>application/x-www-form-urlencoded</code>でエンコードされた文字列。
     *            {@literal null}や空文字列であってはいけません
     * @param enc
     *            エンコーディング。{@literal null}や空文字列であってはいけません
     * @return デコードされた文字列
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
     * プロトコルを正規化して返します。
     *
     * @param protocol
     *            プロトコル。{@literal null}や空文字列であってはいけません
     * @return 正規化されたプロトコル
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
     * URLが示すJarファイルの{@link File}オブジェクトを返します。
     *
     * @param fileUrl
     *            JarファイルのURL。{@literal null}であってはいけません
     * @return Jarファイルの{@link File}
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

    /**
     * <a
     * href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4167874">このバグ<
     * /a> に対する対応です。
     *
     */
    public static void disableURLCaches() {
        final BeanDesc bd = BeanDescFactory.getBeanDesc(URLConnection.class);
        final FieldDesc fd = bd.getFieldDesc("defaultUseCaches");
        fd.setStaticFieldValue(false);
    }

}
