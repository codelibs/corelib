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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.codelibs.core.exception.IllegalAccessRuntimeException;
import org.codelibs.core.exception.InvocationTargetRuntimeException;

/**
 * Utility class for method operations.
 *
 * @author higa
 */
public abstract class MethodUtil {

    /**
     * Do not instantiate.
     */
    private MethodUtil() {
    }

    /**
     * Invokes the underlying method represented by the {@link Method} object, with the specified object and parameters.
     *
     * @param <T>
     *            The return type of the method
     * @param method
     *            The method. Cannot be {@literal null}
     * @param target
     *            The object on which the underlying method is to be called. {@literal null} for {@literal static} methods
     * @param args
     *            The arguments used for the method call
     * @return The result of dispatching the method to the object using the parameters {@code args}
     * @throws IllegalAccessRuntimeException
     *             If this {@link Method} object enforces Java language access control and the underlying method is not accessible
     * @throws InvocationTargetRuntimeException
     *             If the underlying method throws an exception
     * @see Method#invoke(Object, Object[])
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(final Method method, final Object target, final Object... args)
            throws InvocationTargetRuntimeException, IllegalAccessRuntimeException {
        assertArgumentNotNull("method", method);

        try {
            return (T) method.invoke(target, args);
        } catch (final InvocationTargetException ex) {
            final Throwable t = ex.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw new InvocationTargetRuntimeException(method.getDeclaringClass(), ex);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(method.getDeclaringClass(), ex);
        }
    }

    /**
     * Invokes the underlying {@code static} method represented by the {@link Method} object, with the specified parameters.
     *
     * @param <T>
     *            The return type of the method
     * @param method
     *            The method. Cannot be {@literal null}
     * @param args
     *            The arguments used for the method call
     * @return The result of dispatching the {@code static} method using the parameters {@code args}
     * @throws IllegalAccessRuntimeException
     *             If this {@link Method} object enforces Java language access control and the underlying method is not accessible
     * @throws InvocationTargetRuntimeException
     *             If the underlying method throws an exception
     * @see Method#invoke(Object, Object[])
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeStatic(final Method method, final Object... args)
            throws InvocationTargetRuntimeException, IllegalAccessRuntimeException {
        assertArgumentNotNull("method", method);

        return (T) invoke(method, null, args);
    }

    /**
     * Returns whether the method is <code>abstract</code>.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return <code>abstract</code> if the method is abstract
     */
    public static boolean isAbstract(final Method method) {
        return Modifier.isAbstract(method.getModifiers());
    }

    /**
     * Returns whether the method is <code>public</code>.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return <code>public</code> if the method is public
     */
    public static boolean isPublic(final Method method) {
        assertArgumentNotNull("method", method);

        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * Returns whether the method is <code>static</code>.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return <code>static</code> if the method is static
     */
    public static boolean isStatic(final Method method) {
        assertArgumentNotNull("method", method);

        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * Returns whether the method is <code>final</code>.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return <code>final </code> if the method is final
     */
    public static boolean isFinal(final Method method) {
        assertArgumentNotNull("method", method);

        return Modifier.isFinal(method.getModifiers());
    }

    /**
     * Returns the string representation of the method signature.
     *
     * @param methodName
     *            The method name. Cannot be {@literal null} or empty
     * @param argTypes
     *            The argument types
     * @return The string representation of the method signature
     */
    public static String getSignature(final String methodName, final Class<?>... argTypes) {
        assertArgumentNotEmpty("methodName", methodName);

        final StringBuilder buf = new StringBuilder(100);
        buf.append(methodName).append("(");
        if (argTypes != null && argTypes.length > 0) {
            for (final Class<?> argType : argTypes) {
                buf.append(argType.getName()).append(", ");
            }
            buf.setLength(buf.length() - 2);
        }
        buf.append(")");
        return new String(buf);
    }

    /**
     * Returns the string representation of the method signature.
     *
     * @param methodName
     *            The method name. Cannot be {@literal null} or empty
     * @param methodArgs
     *            The method arguments
     * @return The string representation of the method signature
     */
    public static String getSignature(final String methodName, final Object... methodArgs) {
        assertArgumentNotEmpty("methodName", methodName);

        final StringBuilder buf = new StringBuilder(100);
        buf.append(methodName).append("(");
        if (methodArgs != null && methodArgs.length > 0) {
            for (final Object arg : methodArgs) {
                buf.append(arg == null ? null : arg.getClass().getName()).append(", ");
            }
            buf.setLength(buf.length() - 2);
        }
        buf.append(")");
        return buf.toString();
    }

    /**
     * Returns whether the method is the {@literal equals(Object)} method.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return {@literal true} if the method is {@literal equals(Object)}
     */
    public static boolean isEqualsMethod(final Method method) {
        assertArgumentNotNull("method", method);

        return method != null && method.getName().equals("equals") && method.getReturnType() == boolean.class
                && method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == Object.class;
    }

    /**
     * Returns whether the method is the {@literal hashCode()} method.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return {@literal true} if the method is {@literal hashCode()}
     */
    public static boolean isHashCodeMethod(final Method method) {
        assertArgumentNotNull("method", method);

        return method != null && method.getName().equals("hashCode") && method.getReturnType() == int.class
                && method.getParameterTypes().length == 0;
    }

    /**
     * Returns whether the method is the {@literal toString()} method.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return {@literal true} if the method is {@literal toString()}
     */
    public static boolean isToStringMethod(final Method method) {
        assertArgumentNotNull("method", method);

        return method != null && method.getName().equals("toString") && method.getReturnType() == String.class
                && method.getParameterTypes().length == 0;
    }

    /**
     * Returns the element type of the parameterized collection declared as the method's argument type.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @param position
     *            The position of the parameterized collection in the method's argument list
     * @return The element type of the parameterized collection
     */
    public static Class<?> getElementTypeOfCollectionFromParameterType(final Method method, final int position) {
        assertArgumentNotNull("method", method);

        final Type[] parameterTypes = method.getGenericParameterTypes();
        return GenericsUtil.getRawClass(GenericsUtil.getElementTypeOfCollection(parameterTypes[position]));
    }

    /**
     * Returns the element type of the parameterized collection declared as the method's return type.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return The element type of the parameterized collection
     */
    public static Class<?> getElementTypeOfCollectionFromReturnType(final Method method) {
        assertArgumentNotNull("method", method);

        final Type returnType = method.getGenericReturnType();
        return GenericsUtil.getRawClass(GenericsUtil.getElementTypeOfCollection(returnType));
    }

    /**
     * Returns the key type of the parameterized map declared as the method's argument type.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @param position
     *            The position of the parameterized map in the method's argument list
     * @return The key type of the parameterized map
     */
    public static Class<?> getKeyTypeOfMapFromParameterType(final Method method, final int position) {
        assertArgumentNotNull("method", method);

        final Type[] parameterTypes = method.getGenericParameterTypes();
        return GenericsUtil.getRawClass(GenericsUtil.getKeyTypeOfMap(parameterTypes[position]));
    }

    /**
     * Returns the key type of the parameterized map declared as the method's return type.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return The key type of the parameterized map
     */
    public static Class<?> getKeyTypeOfMapFromReturnType(final Method method) {
        assertArgumentNotNull("method", method);

        final Type returnType = method.getGenericReturnType();
        return GenericsUtil.getRawClass(GenericsUtil.getKeyTypeOfMap(returnType));
    }

    /**
     * Returns the value type of the parameterized map declared as the method's argument type.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @param position
     *            The position of the parameterized map in the method's argument list
     * @return The value type of the parameterized map
     */
    public static Class<?> getValueTypeOfMapFromParameterType(final Method method, final int position) {
        assertArgumentNotNull("method", method);

        final Type[] parameterTypes = method.getGenericParameterTypes();
        return GenericsUtil.getRawClass(GenericsUtil.getValueTypeOfMap(parameterTypes[position]));
    }

    /**
     * Returns the value type of the parameterized map declared as the method's return type.
     *
     * @param method
     *            The method. Cannot be {@literal null}
     * @return The value type of the parameterized map
     */
    public static Class<?> getValueTypeOfMapFromReturnType(final Method method) {
        assertArgumentNotNull("method", method);

        final Type returnType = method.getGenericReturnType();
        return GenericsUtil.getRawClass(GenericsUtil.getValueTypeOfMap(returnType));
    }

}
