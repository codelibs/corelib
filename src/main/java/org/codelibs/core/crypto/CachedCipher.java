/*
 * Copyright 2013 the CodeLibs Project and the Others.
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

public class CachedCipher {

    private static final String BLOWFISH = "Blowfish";

    private static final String RSA = "RSA";

    public String algorithm = BLOWFISH;

    public String transformation = RSA;

    public String key;

    public String charsetName = CoreLibConstants.UTF_8;

    protected Queue<Cipher> encryptoQueue = new ConcurrentLinkedQueue<Cipher>();

    protected Queue<Cipher> decryptoQueue = new ConcurrentLinkedQueue<Cipher>();

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

    public String encryptoText(final String text) {
        try {
            return Base64Util.encode(encrypto(text.getBytes(charsetName)));
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

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

    public String decryptoText(final String text) {
        try {
            return new String(decrypto(Base64Util.decode(text)), charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    protected Cipher pollEncryptoCipher() {
        Cipher cipher = encryptoQueue.poll();
        if (cipher == null) {
            final SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(),
                    algorithm);
            try {
                cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
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

    protected void offerEncryptoCipher(final Cipher cipher) {
        encryptoQueue.offer(cipher);
    }

    protected Cipher pollDecryptoCipher() {
        Cipher cipher = decryptoQueue.poll();
        if (cipher == null) {
            final SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(),
                    algorithm);
            try {
                cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.DECRYPT_MODE, sksSpec);
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

    protected void offerDecryptoCipher(final Cipher cipher) {
        decryptoQueue.offer(cipher);
    }
}
