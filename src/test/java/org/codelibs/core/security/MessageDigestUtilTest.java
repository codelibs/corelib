/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.codelibs.core.exception.EmptyArgumentException;
import org.codelibs.core.exception.NoSuchAlgorithmRuntimeException;
import org.junit.Test;

/**
 * @author shinsuke
 *
 */
public class MessageDigestUtilTest {
    /**
     *
     */
    @Test
    public void testDigest() {
        final String text = "hoge";
        assertNull(MessageDigestUtil.digest("MD5", null));
        assertEquals("ea703e7aa1efda0064eaa507d9e8ab7e", MessageDigestUtil.digest("MD5", text));
        assertEquals("31f30ddbcb1bf8446576f0e64aa4c88a9f055e3c", MessageDigestUtil.digest("SHA-1", text));
        assertEquals("ecb666d778725ec97307044d642bf4d160aabb76f56c0069c71ea25b1e926825", MessageDigestUtil.digest("SHA-256", text));

        try {
            MessageDigestUtil.digest(null, text);
            assertTrue(false);
        } catch (final EmptyArgumentException e) {
            assertTrue(true);
        }
        try {
            MessageDigestUtil.digest("HOGE", text);
            assertTrue(false);
        } catch (final NoSuchAlgorithmRuntimeException e) {
            assertTrue(true);
        }
    }
}
