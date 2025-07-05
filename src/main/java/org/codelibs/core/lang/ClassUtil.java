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

import static org.codelibs.core.collection.CollectionsUtil.newHashMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.codelibs.core.exception.ClassNotFoundRuntimeException;
import org.codelibs.core.exception.EmptyArgumentException;
import org.codelibs.core.exception.IllegalAccessRuntimeException;
import org.codelibs.core.exception.InstantiationRuntimeException;
import org.codelibs.core.exception.NoSuchConstructorRuntimeException;
import org.codelibs.core.exception.NoSuchFieldRuntimeException;
import org.codelibs.core.exception.NoSuchMethodRuntimeException;

/**
 * Utility class for {@link Class} operations.
 *
 * @author higa
 */
public abstract class ClassUtil {

    /**
     * Do not instantiate.
     */
    private ClassUtil() {
    }

    /** Map from wrapper types to primitive types */
    protected static final Map<Class<?>, Class<?>> wrapperToPrimitiveMap = newHashMap();

    /** Map from primitive types to wrapper types */
    protected static final Map<Class<?>, Class<?>> primitiveToWrapperMap = newHashMap();

    /** Map from primitive type names to classes */
    protected static final Map<String, Class<?>> primitiveNameToClassMap = newHashMap();

    static {
        wrapperToPrimitiveMap.put(Boolean.class, Boolean.TYPE);
        wrapperToPrimitiveMap.put(Character.class, Character.TYPE);
        wrapperToPrimitiveMap.put(Byte.class, Byte.TYPE);
        wrapperToPrimitiveMap.put(Short.class, Short.TYPE);
        wrapperToPrimitiveMap.put(Integer.class, Integer.TYPE);
        wrapperToPrimitiveMap.put(Long.class, Long.TYPE);
        wrapperToPrimitiveMap.put(Float.class, Float.TYPE);
        wrapperToPrimitiveMap.put(Double.class, Double.TYPE);

        primitiveToWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveToWrapperMap.put(Character.TYPE, Character.class);
        primitiveToWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveToWrapperMap.put(Short.TYPE, Short.class);
        primitiveToWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveToWrapperMap.put(Long.TYPE, Long.class);
        primitiveToWrapperMap.put(Float.TYPE, Float.class);
        primitiveToWrapperMap.put(Double.TYPE, Double.class);

        primitiveNameToClassMap.put(Boolean.TYPE.getName(), Boolean.TYPE);
        primitiveNameToClassMap.put(Character.TYPE.getName(), Character.TYPE);
        primitiveNameToClassMap.put(Byte.TYPE.getName(), Byte.TYPE);
        primitiveNameToClassMap.put(Short.TYPE.getName(), Short.TYPE);
        primitiveNameToClassMap.put(Integer.TYPE.getName(), Integer.TYPE);
        primitiveNameToClassMap.put(Long.TYPE.getName(), Long.TYPE);
        primitiveNameToClassMap.put(Float.TYPE.getName(), Float.TYPE);
        primitiveNameToClassMap.put(Double.TYPE.getName(), Double.TYPE);
    }

    /**
     * Returns the {@link Class} object associated with the class or interface with the given string name,
     * using the current thread's context class loader.
     *
     * @param <T>
     *            The class represented by the returned {@link Class} object
     * @param className
     *            The fully qualified name of the desired class. Must not be {@literal null} or empty.
     * @return The {@link Class} object for the class with the specified name
     * @throws ClassNotFoundRuntimeException
     *             If the class cannot be found
     * @see Class#forName(String, boolean, ClassLoader)
     */
    public static <T> Class<T> forName(final String className) throws ClassNotFoundRuntimeException {
        assertArgumentNotEmpty("className", className);

        return forName(className, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Returns the {@link Class} object associated with the class or interface with the given string name,
     * using the specified class loader.
     *
     * @param <T>
     *            The class represented by the returned {@link Class} object
     * @param className
     *            The fully qualified name of the desired class. Must not be {@literal null} or empty.
     * @param loader
     *            The class loader to use to load the class
     * @return The {@link Class} object for the class with the specified name
     * @throws EmptyArgumentException
     *             If the class name is {@literal null} or empty
     * @throws ClassNotFoundRuntimeException
     *             If the class cannot be found
     * @see Class#forName(String, boolean, ClassLoader)
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forName(final String className, final ClassLoader loader) throws ClassNotFoundRuntimeException {
        assertArgumentNotEmpty("className", className);
        try {
            return (Class<T>) Class.forName(className, true, loader);
        } catch (final ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }

    /**
     * Returns the {@link Class} object associated with the class or interface with the given string name,
     * using the current thread's context class loader.
     * <p>
     * Returns {@code null} if the class cannot be found.
     * </p>
     *
     * @param <T>
     *            The class represented by the returned {@link Class} object
     * @param className
     *            The fully qualified name of the desired class
     * @return The {@link Class} object for the class with the specified name, or {@code null} if not found
     * @see Class#forName(String)
     */
    public static <T> Class<T> forNameNoException(final String className) {
        return forNameNoException(className, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Returns the {@link Class} object associated with the class or interface with the given string name,
     * using the specified class loader.
     * <p>
     * Returns {@code null} if the class cannot be found.
     * </p>
     *
     * @param <T>
     *            The class represented by the returned {@link Class} object
     * @param className
     *            The fully qualified name of the desired class
     * @param loader
     *            The class loader to use to load the class
     * @return The {@link Class} object for the class with the specified name, or {@code null} if not found
     * @see Class#forName(String)
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forNameNoException(final String className, final ClassLoader loader) {
        if (StringUtil.isEmpty(className)) {
            return null;
        }
        try {
            return (Class<T>) Class.forName(className, true, loader);
        } catch (final Throwable ignore) {
            return null;
        }
    }

    /**
     * If the class is a primitive type, returns its wrapper class.
     *
     * @param className
     *            The class name. Must not be {@literal null} or empty.
     * @return {@link Class}
     * @throws EmptyArgumentException
     *             If the class name is {@literal null} or empty.
     * @throws ClassNotFoundRuntimeException
     *             If a {@link ClassNotFoundException} occurs.
     * @see #forName(String)
     */
    public static Class<?> convertClass(final String className) throws ClassNotFoundRuntimeException {
        assertArgumentNotEmpty("className", className);
        final Class<?> clazz = primitiveNameToClassMap.get(className);
        if (clazz != null) {
            return clazz;
        }
        return forName(className);
    }

    /**
     * Creates and initializes a new instance of the class using its default constructor.
     *
     * @param <T>
     *            The class represented by the {@link Class} object
     * @param clazz
     *            The {@link Class} object representing the class. Must not be {@literal null}.
     * @return A new object created by invoking the default constructor
     * @throws InstantiationRuntimeException
     *             If the underlying constructor represents an abstract class
     * @throws IllegalAccessRuntimeException
     *             If the number of actual and formal parameters differ, if unwrapping of primitive arguments fails,
     *             or if after unwrapping, the parameter values cannot be converted to the corresponding formal parameter types,
     *             or if the constructor is related to an enum type
     * @see Constructor#newInstance(Object[])
     */
    public static <T> T newInstance(final Class<T> clazz) throws InstantiationRuntimeException, IllegalAccessRuntimeException {
        assertArgumentNotNull("clazz", clazz);

        try {
            return clazz.newInstance();
        } catch (final InstantiationException e) {
            throw new InstantiationRuntimeException(clazz, e);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(clazz, e);
        }
    }

    /**
     * Retrieves the specified class using the context class loader and creates and initializes a new instance of the class using its default constructor.
     *
     * @param <T>
     *            The type of the instance to be created
     * @param className
     *            The class name. Must not be {@literal null} or empty.
     * @return A new object created by invoking the default constructor
     * @throws ClassNotFoundRuntimeException
     *             If the class cannot be found
     * @throws InstantiationRuntimeException
     *             If the underlying constructor represents an abstract class
     * @throws IllegalAccessRuntimeException
     *             If the number of actual and formal parameters differ, if unwrapping of primitive arguments fails, or if after unwrapping,
     *             the parameter values cannot be converted to the corresponding formal parameter types, or if the constructor is related to an enum type
     * @see #newInstance(Class)
     * @see #forName(String)
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(final String className)
            throws ClassNotFoundRuntimeException, InstantiationRuntimeException, IllegalAccessRuntimeException {
        assertArgumentNotNull("className", className);

        return (T) newInstance(forName(className));
    }

    /**
     * Retrieves the specified class using the given class loader and creates and initializes a new instance of the class using its default constructor.
     *
     * @param <T>
     *            The type of the instance to be created
     * @param className
     *            The class name. Must not be {@literal null} or empty.
     * @param loader
     *            The class loader to use to load the class
     * @return A new object created by invoking the default constructor
     * @throws ClassNotFoundRuntimeException
     *             If the class cannot be found
     * @throws InstantiationRuntimeException
     *             If the underlying constructor represents an abstract class
     * @throws IllegalAccessRuntimeException
     *             If the number of actual and formal parameters differ, if unwrapping of primitive arguments fails, or if after unwrapping,
     *             the parameter values cannot be converted to the corresponding formal parameter types, or if the constructor is related to an enum type
     * @see #newInstance(Class)
     * @see #forName(String, ClassLoader)
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(final String className, final ClassLoader loader)
            throws ClassNotFoundRuntimeException, InstantiationRuntimeException, IllegalAccessRuntimeException {
        assertArgumentNotEmpty("className", className);

        return (T) newInstance(forName(className, loader));
    }

    /**
     * Returns whether the class can be assigned from another class.
     *
     * @param toClass
     *            The target class. Must not be {@literal null}.
     * @param fromClass
     *            The source class. Must not be {@literal null}.
     * @return Whether assignment is possible.
     * @see Class#isAssignableFrom(Class)
     */
    public static boolean isAssignableFrom(final Class<?> toClass, Class<?> fromClass) {
        assertArgumentNotNull("toClass", toClass);
        assertArgumentNotNull("fromClass", fromClass);

        if (toClass == Object.class && !fromClass.isPrimitive()) {
            return true;
        }
        if (toClass.isPrimitive()) {
            fromClass = getPrimitiveClassIfWrapper(fromClass);
        }
        return toClass.isAssignableFrom(fromClass);
    }

    /**
     * Converts a wrapper class to its corresponding primitive class.
     *
     * @param clazz
     *            The wrapper class. Must not be {@literal null}.
     * @return The primitive class if the argument is a wrapper class, otherwise {@literal null}.
     */
    public static Class<?> getPrimitiveClass(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return wrapperToPrimitiveMap.get(clazz);
    }

    /**
     * If the class is a wrapper class, returns its corresponding primitive class; otherwise, returns the class itself.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The primitive class if the argument is a wrapper class, otherwise the class passed as the argument.
     */
    public static Class<?> getPrimitiveClassIfWrapper(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        final Class<?> ret = getPrimitiveClass(clazz);
        if (ret != null) {
            return ret;
        }
        return clazz;
    }

    /**
     * Converts a primitive class to its corresponding wrapper class.
     *
     * @param clazz
     *            The primitive class. Must not be {@literal null}.
     * @return The wrapper class if the argument is a primitive class, otherwise {@literal null}.
     */
    public static Class<?> getWrapperClass(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return primitiveToWrapperMap.get(clazz);
    }

    /**
     * If the class is a primitive type, returns its wrapper class; otherwise, returns the class itself.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The wrapper class if the argument is a primitive class, otherwise the class passed as the argument.
     */
    public static Class<?> getWrapperClassIfPrimitive(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        final Class<?> ret = getWrapperClass(clazz);
        if (ret != null) {
            return ret;
        }
        return clazz;
    }

    /**
     * Returns a {@link Constructor} object that reflects the specified {@code public} constructor of the class represented by the {@link Class} object.
     *
     * @param <T>
     *            The class represented by the {@link Class} object
     * @param clazz
     *            The {@link Class} object representing the class. Must not be {@literal null}.
     * @param argTypes
     *            The parameter types array
     * @return The {@link Constructor} object for the {@code public} constructor matching the specified {@code argTypes}
     * @throws NoSuchConstructorRuntimeException
     *             If a matching constructor is not found
     * @see Class#getConstructor(Class...)
     */
    public static <T> Constructor<T> getConstructor(final Class<T> clazz, final Class<?>... argTypes)
            throws NoSuchConstructorRuntimeException {
        assertArgumentNotNull("clazz", clazz);

        try {
            return clazz.getConstructor(argTypes);
        } catch (final NoSuchMethodException e) {
            throw new NoSuchConstructorRuntimeException(clazz, argTypes, e);
        }
    }

    /**
     * Returns a {@link Constructor} object that reflects the specified constructor of the class represented by the {@link Class} object.
     *
     * @param <T>
     *            The class represented by the {@link Class} object
     * @param clazz
     *            The {@link Class} object representing the class. Must not be {@literal null}.
     * @param argTypes
     *            The parameter types array
     * @return The {@link Constructor} object for the constructor matching the specified {@code argTypes}
     * @throws NoSuchConstructorRuntimeException
     *             If a matching constructor is not found
     * @see Class#getDeclaredConstructor(Class...)
     */
    public static <T> Constructor<T> getDeclaredConstructor(final Class<T> clazz, final Class<?>... argTypes)
            throws NoSuchConstructorRuntimeException {
        assertArgumentNotNull("clazz", clazz);

        try {
            return clazz.getDeclaredConstructor(argTypes);
        } catch (final NoSuchMethodException e) {
            throw new NoSuchConstructorRuntimeException(clazz, argTypes, e);
        }
    }

    /**
     * Returns a {@link Field} object that reflects the specified {@code public} member field of the class or interface represented by the {@link Class} object.
     *
     * @param clazz
     *            The {@link Class} object representing the class. Must not be {@literal null}.
     * @param name
     *            The field name. Must not be {@literal null} or empty.
     * @return The {@link Field} object for the field specified by {@code name} in this class.
     * @throws EmptyArgumentException
     *             If the field name is {@literal null} or empty.
     * @throws NoSuchFieldRuntimeException
     *             If a field with the specified name cannot be found.
     * @see Class#getField(String)
     */
    public static Field getField(final Class<?> clazz, final String name) throws NoSuchFieldRuntimeException {
        assertArgumentNotNull("clazz", clazz);
        assertArgumentNotEmpty("name", name);

        try {
            return clazz.getField(name);
        } catch (final NoSuchFieldException e) {
            throw new NoSuchFieldRuntimeException(clazz, name, e);
        }
    }

    /**
     * Returns a {@link Field} object that reflects the specified declared field of the class or interface represented by the {@link Class} object.
     *
     * @param clazz
     *            The {@link Class} object representing the class. Must not be {@literal null}.
     * @param name
     *            The field name. Must not be {@literal null} or empty.
     * @return The {@link Field} object for the field specified by {@code name} in this class.
     * @throws NoSuchFieldRuntimeException
     *             If a field with the specified name cannot be found.
     * @see Class#getDeclaredField(String)
     */
    public static Field getDeclaredField(final Class<?> clazz, final String name) throws NoSuchFieldRuntimeException {
        assertArgumentNotNull("clazz", clazz);
        assertArgumentNotEmpty("name", name);

        try {
            return clazz.getDeclaredField(name);
        } catch (final NoSuchFieldException e) {
            throw new NoSuchFieldRuntimeException(clazz, name, e);
        }
    }

    /**
     * Returns a {@link Method} object that reflects the specified {@code public} member method of the class or interface represented by the {@link Class} object.
     *
     * @param clazz
     *            The {@link Class} object representing the class. Must not be {@literal null}.
     * @param name
     *            The method name. Must not be {@literal null} or empty.
     * @param argTypes
     *            The list of parameter types.
     * @return The {@link Method} object matching the specified {@code name} and {@code argTypes}.
     * @throws EmptyArgumentException
     *             If the method name is {@literal null} or empty.
     * @throws NoSuchMethodRuntimeException
     *             If a matching method cannot be found.
     * @see Class#getMethod(String, Class...)
     */
    public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... argTypes)
            throws NoSuchMethodRuntimeException {
        assertArgumentNotNull("clazz", clazz);
        assertArgumentNotEmpty("name", name);

        try {
            return clazz.getMethod(name, argTypes);
        } catch (final NoSuchMethodException e) {
            throw new NoSuchMethodRuntimeException(clazz, name, argTypes, e);
        }
    }

    /**
     * Returns a {@link Method} object that reflects the specified declared member method of the class or interface represented by the {@link Class} object.
     *
     * @param clazz
     *            The {@link Class} object representing the class. Must not be {@literal null}.
     * @param name
     *            The method name. Must not be {@literal null} or empty.
     * @param argTypes
     *            The list of parameter types.
     * @return The {@link Method} object matching the specified {@code name} and {@code argTypes}.
     * @throws NoSuchMethodRuntimeException
     *             If a matching method cannot be found.
     * @see Class#getDeclaredMethod(String, Class...)
     */
    public static Method getDeclaredMethod(final Class<?> clazz, final String name, final Class<?>... argTypes)
            throws NoSuchMethodRuntimeException {
        assertArgumentNotNull("clazz", clazz);
        assertArgumentNotEmpty("name", name);

        try {
            return clazz.getDeclaredMethod(name, argTypes);
        } catch (final NoSuchMethodException e) {
            throw new NoSuchMethodRuntimeException(clazz, name, argTypes, e);
        }
    }

    /**
     * Returns the package name of the specified class.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The package name.
     */
    public static String getPackageName(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        final String fqcn = clazz.getName();
        final int pos = fqcn.lastIndexOf('.');
        if (pos > 0) {
            return fqcn.substring(0, pos);
        }
        return null;
    }

    /**
     * Returns the class name without the package name from the fully qualified class name (FQCN).
     *
     * @param className
     *            The class name. Must not be {@literal null} or empty.
     * @return The class name without the package name from the FQCN.
     */
    public static String getShortClassName(final String className) {
        assertArgumentNotEmpty("className", className);

        final int i = className.lastIndexOf('.');
        if (i > 0) {
            return className.substring(i + 1);
        }
        return className;
    }

    /**
     * Splits a fully qualified class name (FQCN) into the package name and the class name without the package.
     *
     * @param className
     *            The class name. Must not be {@literal null} or empty.
     * @return An array containing the package name and the class name without the package.
     */
    public static String[] splitPackageAndShortClassName(final String className) {
        assertArgumentNotEmpty("className", className);

        final String[] ret = new String[2];
        final int i = className.lastIndexOf('.');
        if (i > 0) {
            ret[0] = className.substring(0, i);
            ret[1] = className.substring(i + 1);
        } else {
            ret[1] = className;
        }
        return ret;
    }

    /**
     * Returns the class name itself, or for arrays, the element class name with {@literal []} appended.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The class name.
     */
    public static String getSimpleClassName(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        if (clazz.isArray()) {
            return getSimpleClassName(clazz.getComponentType()) + "[]";
        }
        return clazz.getName();
    }

    /**
     * Returns the resource path representation of the class name.
     *
     * @param clazz
     *            The class. Must not be {@literal null}.
     * @return The resource path.
     * @see #getResourcePath(String)
     */
    public static String getResourcePath(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        return getResourcePath(clazz.getName());
    }

    /**
     * Returns the resource path representation of the class name.
     *
     * @param className
     *            The class name. Must not be {@literal null} or empty.
     * @return The resource path.
     */
    public static String getResourcePath(final String className) {
        assertArgumentNotEmpty("className", className);

        return StringUtil.replace(className, ".", "/") + ".class";
    }

    /**
     * Concatenates elements of a class name.
     *
     * @param s1
     *            The first element of the class name
     * @param s2
     *            The second element of the class name
     * @return The concatenated name
     */
    public static String concatName(final String s1, final String s2) {
        if (StringUtil.isEmpty(s1) && StringUtil.isEmpty(s2)) {
            return null;
        }
        if (!StringUtil.isEmpty(s1) && StringUtil.isEmpty(s2)) {
            return s1;
        }
        if (StringUtil.isEmpty(s1) && !StringUtil.isEmpty(s2)) {
            return s2;
        }
        return s1 + '.' + s2;
    }

}
