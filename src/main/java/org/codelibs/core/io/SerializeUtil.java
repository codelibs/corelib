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
package org.codelibs.core.io;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.codelibs.core.exception.ClassNotFoundRuntimeException;
import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility for serializing objects.
 *
 * @author higa
 */
public abstract class SerializeUtil {

    /**
     * Do not instantiate.
     */
    private SerializeUtil() {
    }

    private static final int BYTE_ARRAY_SIZE = 8 * 1024;

    /**
     * Tests if the object can be serialized.
     *
     * @param obj the object to be serialized (must not be {@literal null})
     * @return the deserialized object
     */
    public static Object serialize(final Object obj) {
        assertArgumentNotNull("obj", obj);

        final byte[] binary = fromObjectToBinary(obj);
        return fromBinaryToObject(binary);
    }

    /**
     * Converts an object to a byte array.
     *
     * @param obj the object to serialize (must not be {@literal null})
     * @return the byte array of the object
     */
    public static byte[] fromObjectToBinary(final Object obj) {
        assertArgumentNotNull("obj", obj);

        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTE_ARRAY_SIZE);
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            try {
                oos.writeObject(obj);
            } finally {
                oos.close();
            }
            return baos.toByteArray();
        } catch (final IOException ex) {
            throw new IORuntimeException(ex);
        }
    }

    /**
     * Converts a byte array to an object.
     *
     * @param bytes the byte array (must not be {@literal null})
     * @return the deserialized object
     */
    public static Object fromBinaryToObject(final byte[] bytes) {
        assertArgumentNotEmpty("bytes", bytes);

        try {
            final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            final ObjectInputStream ois = new ObjectInputStream(bais);
            try {
                return ois.readObject();
            } finally {
                CloseableUtil.close(ois);
            }
        } catch (final IOException ex) {
            throw new IORuntimeException(ex);
        } catch (final ClassNotFoundException ex) {
            throw new ClassNotFoundRuntimeException(ex);
        }
    }

}
