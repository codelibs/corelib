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
package org.codelibs.core.crypto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

/**
 * Tests for {@link CachedCipher}.
 */
public class CachedCipherTest {

    @Test
    public void testEncryptDecryptBytes() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final byte[] original = "Hello World".getBytes();
        final byte[] encrypted = cipher.encrypt(original);
        final byte[] decrypted = cipher.decrypt(encrypted);

        assertThat(encrypted, is(not(original)));
        assertArrayEquals(original, decrypted);
    }

    @Test
    public void testEncryptDecryptText() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final String original = "Hello World";
        final String encrypted = cipher.encryptText(original);
        final String decrypted = cipher.decryptText(encrypted);

        assertThat(encrypted, is(not(original)));
        assertThat(decrypted, is(original));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedEncryptoDecryptoBytes() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final byte[] original = "Hello World".getBytes();
        final byte[] encrypted = cipher.encrypto(original);
        final byte[] decrypted = cipher.decrypto(encrypted);

        assertThat(encrypted, is(not(original)));
        assertArrayEquals(original, decrypted);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedEncryptoDecryptoText() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final String original = "Hello World";
        final String encrypted = cipher.encryptoText(original);
        final String decrypted = cipher.decryptoText(encrypted);

        assertThat(encrypted, is(not(original)));
        assertThat(decrypted, is(original));
    }

    @Test
    public void testBackwardCompatibilityEncryptDecrypt() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final String original = "Hello World";

        // Encrypt with new method, decrypt with old method
        @SuppressWarnings("deprecation")
        final String encrypted1 = cipher.encryptText(original);
        @SuppressWarnings("deprecation")
        final String decrypted1 = cipher.decryptoText(encrypted1);
        assertThat(decrypted1, is(original));

        // Encrypt with old method, decrypt with new method
        @SuppressWarnings("deprecation")
        final String encrypted2 = cipher.encryptoText(original);
        final String decrypted2 = cipher.decryptText(encrypted2);
        assertThat(decrypted2, is(original));
    }

    @Test
    public void testDifferentKeys() {
        final CachedCipher cipher1 = new CachedCipher();
        cipher1.setKey("key1");

        final CachedCipher cipher2 = new CachedCipher();
        cipher2.setKey("key2");

        final String original = "Hello World";
        final String encrypted1 = cipher1.encryptText(original);
        final String encrypted2 = cipher2.encryptText(original);

        assertThat(encrypted1, is(not(encrypted2)));
        assertThat(cipher1.decryptText(encrypted1), is(original));
        assertThat(cipher2.decryptText(encrypted2), is(original));
    }

    @Test
    public void testEmptyString() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final String original = "";
        final String encrypted = cipher.encryptText(original);
        final String decrypted = cipher.decryptText(encrypted);

        assertThat(decrypted, is(original));
    }

    @Test
    public void testLongText() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("Long text for testing. ");
        }
        final String original = sb.toString();
        final String encrypted = cipher.encryptText(original);
        final String decrypted = cipher.decryptText(encrypted);

        assertThat(decrypted, is(original));
    }

    @Test
    public void testUnicodeText() {
        final CachedCipher cipher = new CachedCipher();
        cipher.setKey("mySecretKey");

        final String original = "Hello 世界 مرحبا мир";
        final String encrypted = cipher.encryptText(original);
        final String decrypted = cipher.decryptText(encrypted);

        assertThat(decrypted, is(original));
    }

    @Test
    public void testKeyBasedCipherReinitializedPerKey() {
        // Regression: a pooled cipher must be re-initialized with the given key on every
        // operation. Before the fix, encrypt(data, keyB) reused the cipher still initialized
        // with keyA, so keyB was silently ignored (encA == encB) and decrypt(encB, keyB) failed.
        final CachedCipher cipher = new CachedCipher();
        final Key keyA = new SecretKeySpec("0123456789abcdef".getBytes(), "Blowfish");
        final Key keyB = new SecretKeySpec("fedcba9876543210".getBytes(), "Blowfish");

        final byte[] data = "Hello World".getBytes();

        final byte[] encA = cipher.encrypt(data, keyA);
        final byte[] encB = cipher.encrypt(data, keyB);

        assertThat(encA, is(not(encB)));
        assertArrayEquals(data, cipher.decrypt(encA, keyA));
        assertArrayEquals(data, cipher.decrypt(encB, keyB));
    }

    @Test
    public void testStringKeyChangeInvalidatesPooledCipher() {
        // Regression: changing the key on a reused instance must take effect. Before the fix,
        // the pooled cipher retained the previous key and encrypted with it.
        final CachedCipher cipher = new CachedCipher();

        cipher.setKey("keyOne");
        final byte[] data = "Hello World".getBytes();
        final byte[] encOne = cipher.encrypt(data);

        cipher.setKey("keyTwo");
        final byte[] encTwo = cipher.encrypt(data);

        assertThat(encOne, is(not(encTwo)));
        assertArrayEquals(data, cipher.decrypt(encTwo));
    }
}
