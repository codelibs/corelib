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
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import org.codelibs.core.exception.ClassNotFoundRuntimeException;
import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility for serializing objects with security protections.
 * <p>
 * This utility provides object serialization and deserialization with built-in
 * security protections against deserialization attacks. By default, it uses an
 * ObjectInputFilter to restrict which classes can be deserialized.
 * </p>
 * <p>
 * The default filter allows common safe classes like primitives, arrays, String,
 * Number types, collections, and classes in the org.codelibs package. For custom
 * requirements, use the overloaded methods that accept a custom filter.
 * </p>
 *
 * @author higa
 */
public abstract class SerializeUtil {

    /**
     * Do not instantiate.
     */
    protected SerializeUtil() {
    }

    private static final int BYTE_ARRAY_SIZE = 8 * 1024;

    /**
     * Default set of allowed class name patterns for deserialization.
     * This helps prevent deserialization attacks by restricting which classes can be instantiated.
     */
    private static final Set<String> DEFAULT_ALLOWED_PATTERNS = Set.of(
        "java.lang.*",
        "java.util.*",
        "java.time.*",
        "java.math.*",
        "org.codelibs.*",
        "[*" // Allow arrays
    );

    /**
     * Default ObjectInputFilter that only allows safe classes to be deserialized.
     * This filter rejects potentially dangerous classes while allowing common safe types.
     */
    private static final ObjectInputFilter DEFAULT_FILTER = filterInfo -> {
        final Class<?> serialClass = filterInfo.serialClass();
        if (serialClass == null) {
            return ObjectInputFilter.Status.UNDECIDED;
        }

        final String className = serialClass.getName();

        // Allow primitive types and their wrappers
        if (serialClass.isPrimitive() || serialClass.isArray()) {
            return ObjectInputFilter.Status.ALLOWED;
        }

        // Check against allowed patterns
        for (String allowedPattern : DEFAULT_ALLOWED_PATTERNS) {
            if (allowedPattern.endsWith("*")) {
                String prefix = allowedPattern.substring(0, allowedPattern.length() - 1);
                if (className.startsWith(prefix)) {
                    return ObjectInputFilter.Status.ALLOWED;
                }
            } else if (className.equals(allowedPattern)) {
                return ObjectInputFilter.Status.ALLOWED;
            }
        }

        // Reject everything else
        return ObjectInputFilter.Status.REJECTED;
    };

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
     * Converts a byte array to an object using the default security filter.
     * <p>
     * This method applies a default ObjectInputFilter to prevent deserialization attacks.
     * Only classes matching the default allowed patterns can be deserialized.
     * </p>
     *
     * @param bytes the byte array (must not be {@literal null})
     * @return the deserialized object
     * @throws IORuntimeException if an I/O error occurs or if a class is rejected by the filter
     * @throws ClassNotFoundRuntimeException if the class of a serialized object cannot be found
     */
    public static Object fromBinaryToObject(final byte[] bytes) {
        return fromBinaryToObject(bytes, DEFAULT_FILTER);
    }

    /**
     * Converts a byte array to an object using a custom security filter.
     * <p>
     * This method allows you to specify a custom ObjectInputFilter for fine-grained
     * control over which classes can be deserialized. Use this when the default filter
     * is too restrictive or permissive for your use case.
     * </p>
     *
     * @param bytes the byte array (must not be {@literal null})
     * @param filter the ObjectInputFilter to use, or null to disable filtering
     * @return the deserialized object
     * @throws IORuntimeException if an I/O error occurs or if a class is rejected by the filter
     * @throws ClassNotFoundRuntimeException if the class of a serialized object cannot be found
     */
    public static Object fromBinaryToObject(final byte[] bytes, final ObjectInputFilter filter) {
        assertArgumentNotEmpty("bytes", bytes);

        try {
            final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            final ObjectInputStream ois = new ObjectInputStream(bais);
            if (filter != null) {
                ois.setObjectInputFilter(filter);
            }
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

    /**
     * Creates a permissive filter that allows all classes to be deserialized.
     * <p>
     * WARNING: Use this only when you completely trust the data source and have
     * other security measures in place. Unrestricted deserialization can lead to
     * remote code execution vulnerabilities.
     * </p>
     *
     * @return an ObjectInputFilter that allows all classes
     */
    public static ObjectInputFilter createPermissiveFilter() {
        return filterInfo -> ObjectInputFilter.Status.ALLOWED;
    }

    /**
     * Creates a custom filter that allows only the specified class patterns.
     * <p>
     * Patterns can be exact class names or use wildcards with '*' at the end.
     * For example: "com.example.*" allows all classes in the com.example package.
     * </p>
     *
     * @param allowedPatterns the patterns of classes to allow
     * @return an ObjectInputFilter configured with the specified patterns
     */
    public static ObjectInputFilter createCustomFilter(final Set<String> allowedPatterns) {
        assertArgumentNotNull("allowedPatterns", allowedPatterns);

        return filterInfo -> {
            final Class<?> serialClass = filterInfo.serialClass();
            if (serialClass == null) {
                return ObjectInputFilter.Status.UNDECIDED;
            }

            final String className = serialClass.getName();

            // Allow primitive types and their wrappers
            if (serialClass.isPrimitive() || serialClass.isArray()) {
                return ObjectInputFilter.Status.ALLOWED;
            }

            // Check against allowed patterns
            for (String allowedPattern : allowedPatterns) {
                if (allowedPattern.endsWith("*")) {
                    String prefix = allowedPattern.substring(0, allowedPattern.length() - 1);
                    if (className.startsWith(prefix)) {
                        return ObjectInputFilter.Status.ALLOWED;
                    }
                } else if (className.equals(allowedPattern)) {
                    return ObjectInputFilter.Status.ALLOWED;
                }
            }

            // Reject everything else
            return ObjectInputFilter.Status.REJECTED;
        };
    }

}
