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
package org.codelibs.core.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import org.codelibs.core.lang.StringUtil;

/**
 * Utility for creating UUIDs.
 *
 * @author higa
 */
public abstract class UuidUtil {

    private static final byte[] DEFAULT_ADDRESS = new byte[] { (byte) 127, (byte) 0, (byte) 0, (byte) 1 };

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String BASE = StringUtil.toHex(getAddress()) + StringUtil.toHex(System.identityHashCode(RANDOM));

    /**
     * Creates a UUID.
     *
     * @return the UUID string
     */
    public static String create() {
        final StringBuilder buf = new StringBuilder(BASE.length() * 2);
        buf.append(BASE);
        final int lowTime = (int) (System.currentTimeMillis() >> 32);
        StringUtil.appendHex(buf, lowTime);
        StringUtil.appendHex(buf, RANDOM.nextInt());
        return buf.toString();
    }

    private static byte[] getAddress() {
        try {
            return InetAddress.getLocalHost().getAddress();
        } catch (final UnknownHostException ignore) {
            return DEFAULT_ADDRESS;
        }
    }
}
