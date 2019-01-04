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
package org.codelibs.core.security;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.codelibs.core.exception.ClIllegalStateException;
import org.codelibs.core.exception.NoSuchAlgorithmRuntimeException;

/**
 * {@link MessageDigest}を扱うユーティリティです。
 *
 * @author higa
 * @author shinsuke
 */
public abstract class MessageDigestUtil {

    /**
     * {@link MessageDigest#getInstance(String)}の例外処理をラップします。
     *
     * @param algorithm
     *            アルゴリズム (利用可能なアルゴリズムは{@link MessageDigest}のJavadoc等を参照してください)。
     *            {@literal null}や空文字列であってはいけません
     * @return {@link MessageDigest}
     * @throws RuntimeException
     *             {@link NoSuchAlgorithmException}が発生した場合
     */
    public static MessageDigest getInstance(final String algorithm) {
        assertArgumentNotEmpty("algorithm", algorithm);

        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        }
    }

    /**
     * 指定されたアルゴリズムでテキストをハッシュ化して文字列にします。
     *
     * @param algorithm
     *            アルゴリズム。{@literal null}や空文字列であってはいけません
     * @param text
     *            ハッシュ化する文字列
     * @return ハッシュ化された文字列
     */
    public static String digest(final String algorithm, final String text) {
        assertArgumentNotEmpty("algorithm", algorithm);

        if (text == null) {
            return null;
        }

        final MessageDigest msgDigest = getInstance(algorithm);
        try {
            msgDigest.update(text.getBytes("UTF-8"));
        } catch (final UnsupportedEncodingException e) {
            throw new ClIllegalStateException(e);
        }
        final byte[] digest = msgDigest.digest();

        final StringBuilder buffer = new StringBuilder(200);
        for (final byte element : digest) {
            final String tmp = Integer.toHexString(element & 0xff);
            if (tmp.length() == 1) {
                buffer.append('0').append(tmp);
            } else {
                buffer.append(tmp);
            }
        }
        return buffer.toString();
    }

}
