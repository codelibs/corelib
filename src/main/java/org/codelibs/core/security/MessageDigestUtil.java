/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
 * Utility for handling {@link MessageDigest}.
 *
 * @author higa
 * @author shinsuke
 */
public abstract class MessageDigestUtil {

    /**
     * Wraps the exception handling of {@link MessageDigest#getInstance(String)}.
     *
     * @param algorithm
     *            The algorithm (refer to the Javadoc of {@link MessageDigest} for available algorithms).
     *            Must not be {@literal null} or an empty string.
     * @return {@link MessageDigest}
     * @throws RuntimeException
     *             If a {@link NoSuchAlgorithmException} occurs
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
     * Hashes the specified text using the given algorithm and converts it to a string.
     *
     * @param algorithm
     *            The algorithm. Must not be {@literal null} or an empty string.
     * @param text
     *            The string to be hashed.
     * @return The hashed string.
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
