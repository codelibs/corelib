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
package org.codelibs.core.lang;

import static org.codelibs.core.lang.ClassLoaderIterator.iterable;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import org.codelibs.core.collection.EnumerationIterator;
import org.codelibs.core.exception.ClIllegalStateException;
import org.codelibs.core.exception.ClassNotFoundRuntimeException;
import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.message.MessageFormatter;

/**
 * Utility class for handling {@link ClassLoader}.
 *
 * @author koichik
 */
public abstract class ClassLoaderUtil {

    /**
     * Returns the class loader.
     * <p>
     * The class loader is searched in the following order:
     * </p>
     * <ol>
     * <li>If the context class loader is set for the calling thread, that context class loader</li>
     * <li>If the class loader that loaded the target class can be obtained, that class loader</li>
     * <li>If the class loader that loaded this class can be obtained, that class loader</li>
     * <li>If the system class loader can be obtained, that class loader</li>
     * </ol>
     * <p>
     * However, if both the class loader that loaded the target class and the class loader that loaded this class can be obtained,
     * and the class loader that loaded the target class is an ancestor of the class loader that loaded this class,
     * the class loader that loaded this class is returned.
     * </p>
     *
     * @param targetClass the target class (must not be {@literal null})
     * @return the class loader
     * @throws IllegalStateException if the class loader could not be obtained
     */
    public static ClassLoader getClassLoader(final Class<?> targetClass) {
        assertArgumentNotNull("targetClass", targetClass);

        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }

        final ClassLoader targetClassLoader = targetClass.getClassLoader();
        final ClassLoader thisClassLoader = ClassLoaderUtil.class.getClassLoader();
        if (targetClassLoader != null && thisClassLoader != null) {
            if (isAncestor(thisClassLoader, targetClassLoader)) {
                return thisClassLoader;
            }
            return targetClassLoader;
        }
        if (targetClassLoader != null) {
            return targetClassLoader;
        }
        if (thisClassLoader != null) {
            return thisClassLoader;
        }

        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        if (systemClassLoader != null) {
            return systemClassLoader;
        }

        throw new ClIllegalStateException(MessageFormatter.getMessage("ECL0001", "ClassLoader"));
    }

    /**
     * Returns <code>true</code> if the class loader <code>other</code> is an ancestor of the class loader <code>cl</code>.
     *
     * @param cl
     *            the class loader
     * @param other
     *            the class loader to check as ancestor
     * @return <code>true</code> if <code>other</code> is an ancestor of <code>cl</code>
     */
    protected static boolean isAncestor(final ClassLoader cl, final ClassLoader other) {
        for (final ClassLoader loader : iterable(cl)) {
            if (loader == other) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for all resources with the specified name from the context class loader.
     *
     * @param name
     *            The resource name. Must not be {@literal null} or an empty string.
     * @return An iterator of URL objects for the resources. If no resources are found, the iterator will be empty.
     *         Resources that the class loader does not have access to will not be included.
     * @see java.lang.ClassLoader#getResources(String)
     */
    public static Iterator<URL> getResources(final String name) {
        assertArgumentNotEmpty("name", name);

        return getResources(Thread.currentThread().getContextClassLoader(), name);
    }

    /**
     * Searches for all resources with the specified name from the class loader returned by {@link #getClassLoader(Class)}.
     *
     * @param targetClass
     *            The target class. Must not be {@literal null}.
     * @param name
     *            The resource name. Must not be {@literal null} or an empty string.
     * @return An iterator of URL objects for the resources. If no resources are found, the iterator will be empty.
     *         Resources that the class loader does not have access to will not be included.
     * @see java.lang.ClassLoader#getResources(String)
     */
    public static Iterator<URL> getResources(final Class<?> targetClass, final String name) {
        assertArgumentNotNull("targetClass", targetClass);
        assertArgumentNotNull("name", name);

        return getResources(getClassLoader(targetClass), name);
    }

    /**
     * Searches for all resources with the specified name from the specified class loader.
     *
     * @param loader
     *            The class loader. Must not be {@literal null}.
     * @param name
     *            The resource name. Must not be {@literal null} or an empty string.
     * @return An enumeration of URL objects for the resources. If no resources are found, the enumeration will be empty.
     *         Resources that the class loader does not have access to will not be included.
     * @see java.lang.ClassLoader#getResources(String)
     */
    public static Iterator<URL> getResources(final ClassLoader loader, final String name) {
        assertArgumentNotNull("loader", loader);
        assertArgumentNotEmpty("name", name);

        try {
            final Enumeration<URL> e = loader.getResources(name);
            return new EnumerationIterator<>(e);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Loads the class with the specified binary name.
     *
     * @param loader
     *            The class loader. Must not be {@literal null}.
     * @param className
     *            The binary name of the class. Must not be {@literal null} or an empty string.
     * @return The resulting <code>Class</code> object.
     * @throws ClassNotFoundRuntimeException
     *             If the class could not be found.
     * @see java.lang.ClassLoader#loadClass(String)
     */
    public static Class<?> loadClass(final ClassLoader loader, final String className) {
        assertArgumentNotNull("loader", loader);
        assertArgumentNotEmpty("className", className);

        try {
            return loader.loadClass(className);
        } catch (final ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }

}
