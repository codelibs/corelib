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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.codelibs.core.CoreLibConstants;
import org.codelibs.core.exception.BadPaddingRuntimeException;
import org.codelibs.core.exception.IllegalBlockSizeRuntimeException;
import org.codelibs.core.exception.InvalidAlgorithmParameterRuntimeException;
import org.codelibs.core.exception.InvalidKeyRuntimeException;
import org.codelibs.core.exception.NoSuchAlgorithmRuntimeException;
import org.codelibs.core.exception.NoSuchPaddingRuntimeException;
import org.codelibs.core.exception.UnsupportedEncodingRuntimeException;
import org.codelibs.core.misc.Base64Util;

/**
 * A high-performance utility class for encrypting and decrypting data using cached {@link Cipher} instances.
 * <p>
 * This class provides efficient encryption/decryption by pooling and reusing cipher instances,
 * reducing the overhead of repeated cipher initialization. It supports both string-based keys
 * and {@link Key} objects, with configurable algorithms and character encodings.
 * </p>
 * <p>
 * <strong>Key Features:</strong>
 * </p>
 * <ul>
 * <li>Thread-safe cipher pooling using {@link ConcurrentLinkedQueue}</li>
 * <li>Configurable encryption algorithms (default: Blowfish)</li>
 * <li>Proper charset handling for key generation (UTF-8 by default)</li>
 * <li>Base64 encoding for text operations</li>
 * </ul>
 * <p>
 * <strong>Security Considerations:</strong>
 * </p>
 * <ul>
 * <li>Default Blowfish algorithm is suitable for general-purpose encryption</li>
 * <li>For high-security applications, consider using AES with GCM mode via {@link #setAlgorithm(String)} and {@link #setTransformation(String)}</li>
 * <li>Ensure keys are securely generated and stored</li>
 * <li>For production systems with stringent security requirements, consider using authenticated encryption modes</li>
 * </ul>
 * <p>
 * <strong>Usage Example:</strong>
 * </p>
 * <pre>
 * CachedCipher cipher = new CachedCipher();
 * cipher.setKey("mySecretKey");
 *
 * // Encrypt text
 * String encrypted = cipher.encryptText("Hello World");
 *
 * // Decrypt text
 * String decrypted = cipher.decryptText(encrypted);
 *
 * // For AES encryption
 * CachedCipher aesCipher = new CachedCipher();
 * aesCipher.setAlgorithm("AES");
 * aesCipher.setTransformation("AES");
 * aesCipher.setKey("0123456789abcdef"); // 16-byte key for AES-128
 * </pre>
 *
 * @author higa
 */
public class CachedCipher {

    /**
     * Creates a new {@link CachedCipher} instance.
     */
    public CachedCipher() {
    }

    private static final String BLOWFISH = "Blowfish";

    private static final String AES = "AES";

    /** Transformation used by the authenticated {@link #encryptWithIv}/{@link #decryptWithIv} API. */
    private static final String AES_GCM_TRANSFORMATION = "AES/GCM/NoPadding";

    /** Standard GCM nonce length in bytes. */
    private static final int GCM_IV_LENGTH = 12;

    /** GCM authentication tag length in bits. */
    private static final int GCM_TAG_LENGTH_BITS = 128;

    /** Secure source of randomness for IV generation. */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

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
    public byte[] encrypt(final byte[] data) {
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
     * Encrypts the given data.
     *
     * @param data
     *            the data to encrypt
     * @return the encrypted data
     * @deprecated Use {@link #encrypt(byte[])} instead. This method name contains a typo.
     */
    @Deprecated
    public byte[] encrypto(final byte[] data) {
        return encrypt(data);
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
    public byte[] encrypt(final byte[] data, final Key key) {
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
     * Encrypts the given data with the specified key.
     *
     * @param data
     *            the data to encrypt
     * @param key
     *            the key to use for encryption
     * @return the encrypted data
     * @deprecated Use {@link #encrypt(byte[], Key)} instead. This method name contains a typo.
     */
    @Deprecated
    public byte[] encrypto(final byte[] data, final Key key) {
        return encrypt(data, key);
    }

    /**
     * Encrypts the given text.
     *
     * @param text
     *            the text to encrypt
     * @return the encrypted text
     */
    public String encryptText(final String text) {
        try {
            return Base64Util.encode(encrypt(text.getBytes(charsetName)));
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    /**
     * Encrypts the given text.
     *
     * @param text
     *            the text to encrypt
     * @return the encrypted text
     * @deprecated Use {@link #encryptText(String)} instead. This method name contains a typo.
     */
    @Deprecated
    public String encryptoText(final String text) {
        return encryptText(text);
    }

    /**
     * Decrypts the given data.
     *
     * @param data
     *            the data to decrypt
     * @return the decrypted data
     */
    public byte[] decrypt(final byte[] data) {
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
     * Decrypts the given data.
     *
     * @param data
     *            the data to decrypt
     * @return the decrypted data
     * @deprecated Use {@link #decrypt(byte[])} instead. This method name contains a typo.
     */
    @Deprecated
    public byte[] decrypto(final byte[] data) {
        return decrypt(data);
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
    public byte[] decrypt(final byte[] data, final Key key) {
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
     * Decrypts the given data with the specified key.
     *
     * @param data
     *            the data to decrypt
     * @param key
     *            the key to use for decryption
     * @return the decrypted data
     * @deprecated Use {@link #decrypt(byte[], Key)} instead. This method name contains a typo.
     */
    @Deprecated
    public byte[] decrypto(final byte[] data, final Key key) {
        return decrypt(data, key);
    }

    /**
     * Decrypts the given text.
     *
     * @param text
     *            the text to decrypt
     * @return the decrypted text
     */
    public String decryptText(final String text) {
        try {
            return new String(decrypt(Base64Util.decode(text)), charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    /**
     * Decrypts the given text.
     *
     * @param text
     *            the text to decrypt
     * @return the decrypted text
     * @deprecated Use {@link #decryptText(String)} instead. This method name contains a typo.
     */
    @Deprecated
    public String decryptoText(final String text) {
        return decryptText(text);
    }

    /**
     * Encrypts data using AES in GCM mode (authenticated encryption) with a freshly generated
     * random IV. The 12-byte IV is prepended to the returned value, followed by the ciphertext
     * and the GCM authentication tag.
     * <p>
     * This is an opt-in, self-contained alternative to the pooled {@code encrypt}/{@code decrypt}
     * methods (which default to Blowfish in ECB mode and cannot carry an IV). It generates a
     * unique nonce per call and does not reuse or affect the cipher pool, so it is safe for
     * confidential data. The instance-level configuration ({@link #setKey(String)},
     * {@link #setAlgorithm(String)}, {@link #setTransformation(String)}) is not used; the caller
     * supplies the AES {@link Key} directly.
     * </p>
     *
     * @param data
     *            the plaintext to encrypt. Must not be {@literal null}.
     * @param key
     *            the AES key. Must not be {@literal null}.
     * @return the 12-byte IV followed by the ciphertext and GCM tag
     */
    public byte[] encryptWithIv(final byte[] data, final Key key) {
        assertArgumentNotNull("data", data);
        assertArgumentNotNull("key", key);

        final byte[] iv = new byte[GCM_IV_LENGTH];
        SECURE_RANDOM.nextBytes(iv);
        try {
            final Cipher cipher = Cipher.getInstance(AES_GCM_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));
            final byte[] encrypted = cipher.doFinal(data);
            final byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
            return result;
        } catch (final NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        } catch (final NoSuchPaddingException e) {
            throw new NoSuchPaddingRuntimeException(e);
        } catch (final InvalidKeyException e) {
            throw new InvalidKeyRuntimeException(e);
        } catch (final InvalidAlgorithmParameterException e) {
            throw new InvalidAlgorithmParameterRuntimeException(e);
        } catch (final IllegalBlockSizeException e) {
            throw new IllegalBlockSizeRuntimeException(e);
        } catch (final BadPaddingException e) {
            throw new BadPaddingRuntimeException(e);
        }
    }

    /**
     * Decrypts data produced by {@link #encryptWithIv(byte[], Key)}. The 12-byte IV is read from
     * the front of {@code data} and the remaining bytes are decrypted and authenticated.
     *
     * @param data
     *            the IV followed by the ciphertext and GCM tag. Must not be {@literal null}.
     * @param key
     *            the AES key. Must not be {@literal null}.
     * @return the decrypted plaintext
     */
    public byte[] decryptWithIv(final byte[] data, final Key key) {
        assertArgumentNotNull("data", data);
        assertArgumentNotNull("key", key);
        if (data.length <= GCM_IV_LENGTH) {
            throw new IllegalBlockSizeRuntimeException(new IllegalBlockSizeException("Encrypted data is too short to contain an IV."));
        }

        final byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(data, 0, iv, 0, GCM_IV_LENGTH);
        try {
            final Cipher cipher = Cipher.getInstance(AES_GCM_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));
            return cipher.doFinal(data, GCM_IV_LENGTH, data.length - GCM_IV_LENGTH);
        } catch (final NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        } catch (final NoSuchPaddingException e) {
            throw new NoSuchPaddingRuntimeException(e);
        } catch (final InvalidKeyException e) {
            throw new InvalidKeyRuntimeException(e);
        } catch (final InvalidAlgorithmParameterException e) {
            throw new InvalidAlgorithmParameterRuntimeException(e);
        } catch (final IllegalBlockSizeException e) {
            throw new IllegalBlockSizeRuntimeException(e);
        } catch (final BadPaddingException e) {
            throw new BadPaddingRuntimeException(e);
        }
    }

    /**
     * Polls an encryption cipher from the queue, creating a new one if none are available.
     *
     * @return an encryption cipher
     */
    protected Cipher pollEncryptoCipher() {
        Cipher cipher = encryptoQueue.poll();
        try {
            if (cipher == null) {
                cipher = Cipher.getInstance(algorithm);
            }
            // Always (re-)initialize so a pooled cipher never retains a stale key.
            final SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(charsetName), algorithm);
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
        try {
            if (cipher == null) {
                cipher = Cipher.getInstance(transformation);
            }
            // Always (re-)initialize with the given key; a pooled cipher may hold a different key.
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (final InvalidKeyException e) {
            throw new InvalidKeyRuntimeException(e);
        } catch (final NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        } catch (final NoSuchPaddingException e) {
            throw new NoSuchPaddingRuntimeException(e);
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
        try {
            if (cipher == null) {
                cipher = Cipher.getInstance(algorithm);
            }
            // Always (re-)initialize so a pooled cipher never retains a stale key.
            final SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(charsetName), algorithm);
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
        try {
            if (cipher == null) {
                cipher = Cipher.getInstance(transformation);
            }
            // Always (re-)initialize with the given key; a pooled cipher may hold a different key.
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (final InvalidKeyException e) {
            throw new InvalidKeyRuntimeException(e);
        } catch (final NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmRuntimeException(e);
        } catch (final NoSuchPaddingException e) {
            throw new NoSuchPaddingRuntimeException(e);
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
     * Discards all pooled ciphers so that subsequent operations rebuild them with the
     * current algorithm/transformation.
     * <p>
     * This is required when the algorithm or transformation changes because those values
     * are fixed on a {@link Cipher} instance at creation time and cannot be changed by
     * re-initialization. Configuration setters are expected to be called before concurrent
     * use.
     * </p>
     */
    protected void clearCipherQueues() {
        encryptoQueue.clear();
        decryptoQueue.clear();
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
        clearCipherQueues();
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
        clearCipherQueues();
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
