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

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.codelibs.core.CoreLibConstants;
import org.codelibs.core.exception.BadPaddingRuntimeException;
import org.codelibs.core.exception.IllegalBlockSizeRuntimeException;
import org.codelibs.core.exception.InvalidKeyRuntimeException;
import org.codelibs.core.exception.NoSuchAlgorithmRuntimeException;
import org.codelibs.core.exception.NoSuchPaddingRuntimeException;
import org.codelibs.core.exception.UnsupportedEncodingRuntimeException;
import org.codelibs.core.misc.Base64Util;

/**
 * A utility class for encrypting and decrypting data using a cached {@link Cipher} instance.
 * <p>
 * <strong>SECURITY WARNING:</strong> This class has several security limitations:
 * </p>
 * <ul>
 * <li>Does not use Initialization Vectors (IV), making it vulnerable to pattern analysis</li>
 * <li>Does not provide authenticated encryption (no HMAC), vulnerable to tampering</li>
 * <li>Uses older algorithms (Blowfish) instead of modern standards (AES-256-GCM)</li>
 * <li>Reuses cipher instances without proper state management</li>
 * </ul>
 * <p>
 * <strong>Recommendation:</strong> For new code, use {@code javax.crypto.Cipher} directly with
 * AES-256-GCM mode, proper key derivation (PBKDF2/Argon2), and authenticated encryption.
 * This class is maintained for backward compatibility only.
 * </p>
 *
 * @deprecated Use standard JCA APIs with AES-GCM mode for better security
 */
@Deprecated(since = "0.7.1")
public class CachedCipher {

    /**
     * Creates a new {@link CachedCipher} instance.
     */
    public CachedCipher() {
    }

    private static final String BLOWFISH = "Blowfish";

    private static final String AES = "AES";

    /**
     * The algorithm to use for the cipher.
     * Default is Blowfish for backward compatibility.
     */
    protected String algorithm = BLOWFISH;

    /**
     * The transformation to use for the cipher when using Key objects.
     * Default is Blowfish to match the algorithm default.
     * <p>
     * Note: For better security, consider using "AES/GCM/NoPadding" with proper IV handling.
     * </p>
     */
    protected String transformation = BLOWFISH;

    /**
     * The key to use for encryption/decryption.
     */
    protected String key;

    /**
     * The character set name to use for encoding/decoding strings.
     */
    protected String charsetName = CoreLibConstants.UTF_8;

    /**
     * The queue of ciphers for encryption.
     */
    protected Queue<Cipher> encryptoQueue = new ConcurrentLinkedQueue<>();

    /**
     * The queue of ciphers for decryption.
     */
    protected Queue<Cipher> decryptoQueue = new ConcurrentLinkedQueue<>();

    /**
     * Encrypts the given data.
     *
     * @param data
     *            the data to encrypt
     * @return the encrypted data
     */
    public byte[] encrypto(final byte[] data) {
        final Cipher cipher = pollEncryptoCipher();
        byte[] encrypted;
        try {
            encrypted = cipher.doFinal(data);
        } catch (final IllegalBlockSizeException e) {
            throw new IllegalBlockSizeRuntimeException(e);
        } catch (final BadPaddingException e) {
            throw new BadPaddingRuntimeException(e);
        } finally {
            offerEncryptoCipher(cipher);
        }
        return encrypted;
    }

    /**
     * Encrypts the given data with the specified key.
     *
     * @param data
     *            the data to encrypt
     * @param key
     *            the key to use for encryption
     * @return the encrypted data
     */
    public byte[] encrypto(final byte[] data, final Key key) {
        final Cipher cipher = pollEncryptoCipher(key);
        byte[] encrypted;
        try {
            encrypted = cipher.doFinal(data);
        } catch (final IllegalBlockSizeException e) {
            throw new IllegalBlockSizeRuntimeException(e);
        } catch (final BadPaddingException e) {
            throw new BadPaddingRuntimeException(e);
        } finally {
            offerEncryptoCipher(cipher);
        }
        return encrypted;
    }

    /**
     * Encrypts the given text.
     *
     * @param text
     *            the text to encrypt
     * @return the encrypted text
     */
    public String encryptoText(final String text) {
        try {
            return Base64Util.encode(encrypto(text.getBytes(charsetName)));
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    /**
     * Decrypts the given data.
     *
     * @param data
     *            the data to decrypt
     * @return the decrypted data
     */
    public byte[] decrypto(final byte[] data) {
        final Cipher cipher = pollDecryptoCipher();
        byte[] decrypted;
        try {
            decrypted = cipher.doFinal(data);
        } catch (final IllegalBlockSizeException e) {
            throw new IllegalBlockSizeRuntimeException(e);
        } catch (final BadPaddingException e) {
            throw new BadPaddingRuntimeException(e);
        } finally {
            offerDecryptoCipher(cipher);
        }
        return decrypted;
    }

    /**
     * Decrypts the given data with the specified key.
     *
     * @param data
     *            the data to decrypt
     * @param key
     *            the key to use for decryption
     * @return the decrypted data
     */
    public byte[] decrypto(final byte[] data, final Key key) {
        final Cipher cipher = pollDecryptoCipher(key);
        byte[] decrypted;
        try {
            decrypted = cipher.doFinal(data);
        } catch (final IllegalBlockSizeException e) {
            throw new IllegalBlockSizeRuntimeException(e);
        } catch (final BadPaddingException e) {
            throw new BadPaddingRuntimeException(e);
        } finally {
            offerDecryptoCipher(cipher);
        }
        return decrypted;
    }

    /**
     * Decrypts the given text.
     *
     * @param text
     *            the text to decrypt
     * @return the decrypted text
     */
    public String decryptoText(final String text) {
        try {
            return new String(decrypto(Base64Util.decode(text)), charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    /**
     * Polls an encryption cipher from the queue, creating a new one if none are available.
     *
     * @return an encryption cipher
     */
    protected Cipher pollEncryptoCipher() {
        Cipher cipher = encryptoQueue.poll();
        if (cipher == null) {
            try {
                final SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(charsetName), algorithm);
                cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
            } catch (final InvalidKeyException e) {
                throw new InvalidKeyRuntimeException(e);
            } catch (final NoSuchAlgorithmException e) {
                throw new NoSuchAlgorithmRuntimeException(e);
            } catch (final NoSuchPaddingException e) {
                throw new NoSuchPaddingRuntimeException(e);
            } catch (final UnsupportedEncodingException e) {
                throw new UnsupportedEncodingRuntimeException(e);
            }
        }
        return cipher;
    }

    /**
     * Polls an encryption cipher from the queue, creating a new one if none are available, using the specified key.
     *
     * @param key
     *            the key to use for the cipher
     * @return an encryption cipher
     */
    protected Cipher pollEncryptoCipher(final Key key) {
        Cipher cipher = encryptoQueue.poll();
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance(transformation);
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } catch (final InvalidKeyException e) {
                throw new InvalidKeyRuntimeException(e);
            } catch (final NoSuchAlgorithmException e) {
                throw new NoSuchAlgorithmRuntimeException(e);
            } catch (final NoSuchPaddingException e) {
                throw new NoSuchPaddingRuntimeException(e);
            }
        }
        return cipher;
    }

    /**
     * Offers an encryption cipher back to the queue.
     *
     * @param cipher
     *            the cipher to offer
     */
    protected void offerEncryptoCipher(final Cipher cipher) {
        encryptoQueue.offer(cipher);
    }

    /**
     * Polls a decryption cipher from the queue, creating a new one if none are available.
     *
     * @return a decryption cipher
     */
    protected Cipher pollDecryptoCipher() {
        Cipher cipher = decryptoQueue.poll();
        if (cipher == null) {
            try {
                final SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(charsetName), algorithm);
                cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.DECRYPT_MODE, sksSpec);
            } catch (final InvalidKeyException e) {
                throw new InvalidKeyRuntimeException(e);
            } catch (final NoSuchAlgorithmException e) {
                throw new NoSuchAlgorithmRuntimeException(e);
            } catch (final NoSuchPaddingException e) {
                throw new NoSuchPaddingRuntimeException(e);
            } catch (final UnsupportedEncodingException e) {
                throw new UnsupportedEncodingRuntimeException(e);
            }
        }
        return cipher;
    }

    /**
     * Polls a decryption cipher from the queue, creating a new one if none are available, using the specified key.
     *
     * @param key
     *            the key to use for the cipher
     * @return a decryption cipher
     */
    protected Cipher pollDecryptoCipher(final Key key) {
        Cipher cipher = decryptoQueue.poll();
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance(transformation);
                cipher.init(Cipher.DECRYPT_MODE, key);
            } catch (final InvalidKeyException e) {
                throw new InvalidKeyRuntimeException(e);
            } catch (final NoSuchAlgorithmException e) {
                throw new NoSuchAlgorithmRuntimeException(e);
            } catch (final NoSuchPaddingException e) {
                throw new NoSuchPaddingRuntimeException(e);
            }
        }
        return cipher;
    }

    /**
     * Offers a decryption cipher back to the queue.
     *
     * @param cipher
     *            the cipher to offer
     */
    protected void offerDecryptoCipher(final Cipher cipher) {
        decryptoQueue.offer(cipher);
    }

    /**
     * Returns the algorithm.
     *
     * @return the algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Sets the algorithm.
     *
     * @param algorithm
     *            the algorithm
     */
    public void setAlgorithm(final String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Returns the transformation.
     *
     * @return the transformation
     */
    public String getTransformation() {
        return transformation;
    }

    /**
     * Sets the transformation.
     *
     * @param transformation
     *            the transformation
     */
    public void setTransformation(final String transformation) {
        this.transformation = transformation;
    }

    /**
     * Returns the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * Returns the charset name.
     *
     * @return the charset name
     */
    public String getCharsetName() {
        return charsetName;
    }

    /**
     * Sets the charset name.
     *
     * @param charsetName
     *            the charset name
     */
    public void setCharsetName(final String charsetName) {
        this.charsetName = charsetName;
    }
}
