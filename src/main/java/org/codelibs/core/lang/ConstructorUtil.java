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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.codelibs.core.exception.IllegalAccessRuntimeException;
import org.codelibs.core.exception.InstantiationRuntimeException;
import org.codelibs.core.exception.InvocationTargetRuntimeException;

/**
 * Utility class for {@link Constructor} operations.
 *
 * @author higa
 */
public abstract class ConstructorUtil {

    /**
     * Creates and initializes a new instance of the class declared by the specified constructor with the given initialization parameters.
     *
     * @param <T> the type of the object
     * @param constructor the constructor (must not be {@literal null})
     * @param args the array of objects to be passed as arguments to the constructor invocation
     * @return a new object created by invoking the constructor
     * @throws InstantiationRuntimeException if the class declaring the constructor is abstract
     * @throws IllegalAccessRuntimeException if the number of actual and formal parameters differ, if primitive arguments cannot be converted, or if the class is related to enums
     * @see Constructor#newInstance(Object[])
     */
    public static <T> T newInstance(final Constructor<T> constructor, final Object... args)
            throws InstantiationRuntimeException, IllegalAccessRuntimeException {
        assertArgumentNotNull("constructor", constructor);

        try {
            return constructor.newInstance(args);
        } catch (final InstantiationException e) {
            throw new InstantiationRuntimeException(constructor.getDeclaringClass(), e);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(constructor.getDeclaringClass(), e);
        } catch (final InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(constructor.getDeclaringClass(), e);
        }
    }

    /**
     * Returns whether the constructor is <code>public</code>.
     *
     * @param constructor the constructor (must not be {@literal null} or empty string)
     * @return whether the constructor is <code>public</code>
     */
    public static boolean isPublic(final Constructor<?> constructor) {
        assertArgumentNotNull("constructor", constructor);

        return Modifier.isPublic(constructor.getModifiers());
    }

}
