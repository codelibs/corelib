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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentArrayIndex;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codelibs.core.collection.CollectionsUtil;

/**
 * Utility class for handling generics in Java.
 *
 * @author koichik
 */
public abstract class GenericsUtil {

    /**
     * Returns <code>true</code> if the raw type of <code>type</code> can be assigned to <code>clazz</code>,
     * <code>false</code> otherwise.
     *
     * @param type
     *            the type to check. Cannot be null.
     * @param clazz
     *            the class to check against. Cannot be null.
     * @return <code>true</code> if the raw type of <code>type</code> can be assigned to <code>clazz</code>
     */
    public static boolean isTypeOf(final Type type, final Class<?> clazz) {
        assertArgumentNotNull("type", type);
        assertArgumentNotNull("clazz", clazz);

        if (type instanceof Class) {
            return clazz.isAssignableFrom((Class<?>) type);
        }
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
            return isTypeOf(parameterizedType.getRawType(), clazz);
        }
        return false;
    }

    /**
     * Returns the raw class of the specified type.
     * <ul>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is an array, the raw class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @return the raw class of the specified type
     */
    public static Class<?> getRawClass(final Type type) {
        if (type instanceof Class) {
            return Class.class.cast(type);
        }
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
            return getRawClass(parameterizedType.getRawType());
        }
        if (type instanceof WildcardType) {
            final WildcardType wildcardType = WildcardType.class.cast(type);
            final Type[] types = wildcardType.getUpperBounds();
            return getRawClass(types[0]);
        }
        if (type instanceof GenericArrayType) {
            final GenericArrayType genericArrayType = GenericArrayType.class.cast(type);
            final Class<?> rawClass = getRawClass(genericArrayType.getGenericComponentType());
            return Array.newInstance(rawClass, 0).getClass();
        }
        return null;
    }

    /**
     * Returns the array of type arguments for the specified type.
     * <p>
     * If <code>type</code> is an array type, the element type(s) of the array are analyzed recursively.
     * </p>
     * <p>
     * If <code>type</code> is a parameterized type but has no direct type arguments, an empty array is returned.
     * This includes cases where the parameterized type contains nested types without type arguments.
     * </p>
     * <p>
     * If <code>type</code> is not a parameterized type, <code>null</code> is returned.
     * </p>
     *
     * @param type
     *            the type to analyze
     * @return the array of type arguments for the specified type
     * @see ParameterizedType#getActualTypeArguments()
     */
    public static Type[] getGenericParameters(final Type type) {
        if (type instanceof ParameterizedType) {
            return ParameterizedType.class.cast(type).getActualTypeArguments();
        }
        if (type instanceof GenericArrayType) {
            return getGenericParameters(GenericArrayType.class.cast(type).getGenericComponentType());
        }
        return null;
    }

    /**
     * Returns the generic type of the specified class for the given index.
     *
     * @param clazz the class to analyze
     * @param index the index of the generic type
     * @return the generic type class, or null if not found
     */
    public static Type getGenericParameter(final Type type, final int index) {
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        final Type[] genericParameter = getGenericParameters(type);
        if (genericParameter == null) {
            return null;
        }
        assertArgumentArrayIndex("index", index, genericParameter.length);
        return genericParameter[index];
    }

    /**
     * Returns the generic type of the specified class for the given index, or the default class if not found.
     *
     * @param clazz the class to analyze
     * @param index the index of the generic type
     * @param defaultClass the default class to return if not found
     * @return the generic type class, or the default class
     */
    public static Type getGenericParameter(final Type type, final int index, final Class<?> defaultClass) {
        final Type genericParameter = getGenericParameter(type, index);
        return genericParameter != null ? genericParameter : defaultClass;
    }

    /**
     * Returns the generic type of the specified field for the given index.
     *
     * @param field the field to analyze
     * @param index the index of the generic type
     * @return the generic type class, or null if not found
     */
    public static Type getGenericParameter(final java.lang.reflect.Field field, final int index) {
        return getGenericParameter(field.getGenericType(), index);
    }

    /**
     * Returns the generic type of the specified field for the given index, or the default class if not found.
     *
     * @param field the field to analyze
     * @param index the index of the generic type
     * @param defaultClass the default class to return if not found
     * @return the generic type class, or the default class
     */
    public static Type getGenericParameter(final java.lang.reflect.Field field, final int index, final Class<?> defaultClass) {
        return getGenericParameter(field.getGenericType(), index, defaultClass);
    }

    /**
     * Returns the generic type of the specified method for the given index.
     *
     * @param method the method to analyze
     * @param index the index of the generic type
     * @return the generic type class, or null if not found
     */
    public static Type getGenericParameter(final java.lang.reflect.Method method, final int index) {
        return getGenericParameter(method.getGenericReturnType(), index);
    }

    /**
     * Returns the generic type of the specified method for the given index, or the default class if not found.
     *
     * @param method the method to analyze
     * @param index the index of the generic type
     * @param defaultClass the default class to return if not found
     * @return the generic type class, or the default class
     */
    public static Type getGenericParameter(final java.lang.reflect.Method method, final int index, final Class<?> defaultClass) {
        return getGenericParameter(method.getGenericReturnType(), index, defaultClass);
    }

    /**
     * Returns the element type of an array with parameterized types.
     * <p>
     * If <code>type</code> is not an array of parameterized types, <code>null</code> is returned.
     * </p>
     *
     * @param type
     *            the type to analyze
     * @return the element type of the array, or null if not an array of parameterized types
     */
    public static Type getElementTypeOfArray(final Type type) {
        if (!(type instanceof GenericArrayType)) {
            return null;
        }
        return GenericArrayType.class.cast(type).getGenericComponentType();
    }

    /**
     * Returns the element type of a parameterized {@link Collection}.
     * <p>
     * If <code>type</code> is not a parameterized {@link Collection}, <code>null</code> is returned.
     * </p>
     *
     * @param type
     *            the type to analyze
     * @return the element type of the collection, or null if not a parameterized collection
     */
    public static Type getElementTypeOfCollection(final Type type) {
        if (!isTypeOf(type, Collection.class)) {
            return null;
        }
        return getGenericParameter(type, 0);
    }

    /**
     * Returns the element type of a parameterized {@link List}.
     * <p>
     * If <code>type</code> is not a parameterized {@link List}, <code>null</code> is returned.
     * </p>
     *
     * @param type
     *            the type to analyze
     * @return the element type of the list, or null if not a parameterized list
     */
    public static Type getElementTypeOfList(final Type type) {
        if (!isTypeOf(type, List.class)) {
            return null;
        }
        return getGenericParameter(type, 0);
    }

    /**
     * Returns the element type of a parameterized {@link Set}.
     * <p>
     * If <code>type</code> is not a parameterized {@link Set}, <code>null</code> is returned.
     * </p>
     *
     * @param type
     *            the type to analyze
     * @return the element type of the set, or null if not a parameterized set
     */
    public static Type getElementTypeOfSet(final Type type) {
        if (!isTypeOf(type, Set.class)) {
            return null;
        }
        return getGenericParameter(type, 0);
    }

    /**
     * Returns the key type of a parameterized {@link Map}.
     * <p>
     * If <code>type</code> is not a parameterized {@link Map}, <code>null</code> is returned.
     * </p>
     *
     * @param type
     *            the type to analyze
     * @return the key type of the map, or null if not a parameterized map
     */
    public static Type getKeyTypeOfMap(final Type type) {
        if (!isTypeOf(type, Map.class)) {
            return null;
        }
        return getGenericParameter(type, 0);
    }

    /**
     * Returns the value type of a parameterized {@link Map}.
     * <p>
     * If <code>type</code> is not a parameterized {@link Map}, <code>null</code> is returned.
     * </p>
     *
     * @param type
     *            the type to analyze
     * @return the value type of the map, or null if not a parameterized map
     */
    public static Type getValueTypeOfMap(final Type type) {
        if (!isTypeOf(type, Map.class)) {
            return null;
        }
        return getGenericParameter(type, 1);
    }

    /**
     * Returns a {@link Map} where the keys are the type variables and the values are the type arguments
     * of the specified parameterized type (class or interface).
     *
     * @param clazz
     *            the parameterized type (class or interface) to analyze. Cannot be null.
     * @return a {@link Map} where the keys are the type variables and the values are the type arguments
     */
    public static Map<TypeVariable<?>, Type> getTypeVariableMap(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        final Map<TypeVariable<?>, Type> map = CollectionsUtil.newLinkedHashMap();

        final TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
        for (final TypeVariable<?> typeParameter : typeParameters) {
            map.put(typeParameter, getActualClass(typeParameter.getBounds()[0], map));
        }

        final Class<?> superClass = clazz.getSuperclass();
        final Type superClassType = clazz.getGenericSuperclass();
        if (superClass != null) {
            gatherTypeVariables(superClass, superClassType, map);
        }

        final Class<?>[] interfaces = clazz.getInterfaces();
        final Type[] interfaceTypes = clazz.getGenericInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            gatherTypeVariables(interfaces[i], interfaceTypes[i], map);
        }

        return map;
    }

    /**
     * Gathers the type variables and type arguments of the specified parameterized type (class or interface)
     * and adds them to the given map.
     *
     * @param clazz
     *            the class to analyze
     * @param type
     *            the type to analyze
     * @param map
     *            the map to which the type variables and type arguments are added
     */
    protected static void gatherTypeVariables(final Class<?> clazz, final Type type, final Map<TypeVariable<?>, Type> map) {
        if (clazz == null) {
            return;
        }
        gatherTypeVariables(type, map);

        final Class<?> superClass = clazz.getSuperclass();
        final Type superClassType = clazz.getGenericSuperclass();
        if (superClass != null) {
            gatherTypeVariables(superClass, superClassType, map);
        }

        final Class<?>[] interfaces = clazz.getInterfaces();
        final Type[] interfaceTypes = clazz.getGenericInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            gatherTypeVariables(interfaces[i], interfaceTypes[i], map);
        }
    }

    /**
     * Gathers the type variables and type arguments of the specified type and adds them to the given map.
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map to which the type variables and type arguments are added
     */
    protected static void gatherTypeVariables(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
            final TypeVariable<?>[] typeVariables = GenericDeclaration.class.cast(parameterizedType.getRawType()).getTypeParameters();
            final Type[] actualTypes = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < actualTypes.length; ++i) {
                map.put(typeVariables[i], actualTypes[i]);
            }
        }
    }

    /**
     * Returns the actual class of the specified type.
     * <ul>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is a type variable and is a key in the given map, its actual type argument is returned.</li>
     * <li>If <code>type</code> is a type variable and is not a key in the given map, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is an array, the actual class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map that contains the type variables and type arguments
     * @return the actual class of the specified type
     */
    public static Class<?> getActualClass(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (type instanceof Class) {
            return Class.class.cast(type);
        }
        if (type instanceof ParameterizedType) {
            return getActualClass(ParameterizedType.class.cast(type).getRawType(), map);
        }
        if (type instanceof WildcardType) {
            return getActualClass(WildcardType.class.cast(type).getUpperBounds()[0], map);
        }
        if (type instanceof TypeVariable) {
            final TypeVariable<?> typeVariable = TypeVariable.class.cast(type);
            if (map.containsKey(typeVariable)) {
                return getActualClass(map.get(typeVariable), map);
            }
            return getActualClass(typeVariable.getBounds()[0], map);
        }
        if (type instanceof GenericArrayType) {
            final GenericArrayType genericArrayType = GenericArrayType.class.cast(type);
            final Class<?> componentClass = getActualClass(genericArrayType.getGenericComponentType(), map);
            return Array.newInstance(componentClass, 0).getClass();
        }
        return null;
    }

    /**
     * Returns the actual element type of an array with parameterized types.
     * <ul>
     * <li>If <code>type</code> is not an array of parameterized types, <code>null</code> is returned.</li>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is a type variable, its actual type argument is returned.</li>
     * <li>If <code>type</code> is an array, the actual class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map that contains the type variables and type arguments
     * @return the actual element type of the array, or null if not an array of parameterized types
     */
    public static Class<?> getActualElementClassOfArray(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (!(type instanceof GenericArrayType)) {
            return null;
        }
        return getActualClass(GenericArrayType.class.cast(type).getGenericComponentType(), map);
    }

    /**
     * Returns the actual element type of a parameterized {@link Collection}.
     * <ul>
     * <li>If <code>type</code> is not a parameterized {@link Collection}, <code>null</code> is returned.</li>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is a type variable, its actual type argument is returned.</li>
     * <li>If <code>type</code> is an array, the actual class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map that contains the type variables and type arguments
     * @return the actual element type of the collection, or null if not a parameterized collection
     */
    public static Class<?> getActualElementClassOfCollection(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (!isTypeOf(type, Collection.class)) {
            return null;
        }
        return getActualClass(getGenericParameter(type, 0), map);
    }

    /**
     * Returns the actual element type of a parameterized {@link List}.
     * <ul>
     * <li>If <code>type</code> is not a parameterized {@link List}, <code>null</code> is returned.</li>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is a type variable, its actual type argument is returned.</li>
     * <li>If <code>type</code> is an array, the actual class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map that contains the type variables and type arguments
     * @return the actual element type of the list, or null if not a parameterized list
     */
    public static Class<?> getActualElementClassOfList(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (!isTypeOf(type, List.class)) {
            return null;
        }
        return getActualClass(getGenericParameter(type, 0), map);
    }

    /**
     * Returns the actual element type of a parameterized {@link Set}.
     * <ul>
     * <li>If <code>type</code> is not a parameterized {@link Set}, <code>null</code> is returned.</li>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is a type variable, its actual type argument is returned.</li>
     * <li>If <code>type</code> is an array, the actual class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map that contains the type variables and type arguments
     * @return the actual element type of the set, or null if not a parameterized set
     */
    public static Class<?> getActualElementClassOfSet(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (!isTypeOf(type, Set.class)) {
            return null;
        }
        return getActualClass(getGenericParameter(type, 0), map);
    }

    /**
     * Returns the actual key type of a parameterized {@link Map}.
     * <ul>
     * <li>If the key type is not a parameterized {@link Map}, <code>null</code> is returned.</li>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is a type variable, its actual type argument is returned.</li>
     * <li>If <code>type</code> is an array, the actual class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map that contains the type variables and type arguments
     * @return the actual key type of the map, or null if not a parameterized map
     */
    public static Class<?> getActualKeyClassOfMap(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (!isTypeOf(type, Map.class)) {
            return null;
        }
        return getActualClass(getGenericParameter(type, 0), map);
    }

    /**
     * Returns the actual value type of a parameterized {@link Map}.
     * <ul>
     * <li>If <code>type</code> is not a parameterized {@link Map}, <code>null</code> is returned.</li>
     * <li>If <code>type</code> is a <code>Class</code>, it is returned as-is.</li>
     * <li>If <code>type</code> is a parameterized type, its raw type is returned.</li>
     * <li>If <code>type</code> is a wildcard type, its (first) upper bound is returned.</li>
     * <li>If <code>type</code> is a type variable, its actual type argument is returned.</li>
     * <li>If <code>type</code> is an array, the actual class of its elements is returned.</li>
     * <li>Otherwise, <code>null</code> is returned.</li>
     * </ul>
     *
     * @param type
     *            the type to analyze
     * @param map
     *            the map that contains the type variables and type arguments
     * @return the actual value type of the map, or null if not a parameterized map
     */
    public static Class<?> getActualValueClassOfMap(final Type type, final Map<TypeVariable<?>, Type> map) {
        if (!isTypeOf(type, Map.class)) {
            return null;
        }
        return getActualClass(getGenericParameter(type, 1), map);
    }

}
