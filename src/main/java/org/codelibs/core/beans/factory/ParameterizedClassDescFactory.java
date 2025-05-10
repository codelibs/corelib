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
package org.codelibs.core.beans.factory;

import static org.codelibs.core.collection.ArrayIterator.iterable;
import static org.codelibs.core.collection.IndexedIterator.indexed;
import static org.codelibs.core.lang.GenericsUtil.getActualClass;
import static org.codelibs.core.lang.GenericsUtil.getGenericParameters;
import static org.codelibs.core.lang.GenericsUtil.getTypeVariableMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentArrayIndex;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.ConstructorDesc;
import org.codelibs.core.beans.FieldDesc;
import org.codelibs.core.beans.MethodDesc;
import org.codelibs.core.beans.ParameterizedClassDesc;
import org.codelibs.core.beans.PropertyDesc;
import org.codelibs.core.beans.impl.ParameterizedClassDescImpl;
import org.codelibs.core.collection.Indexed;

/**
 * Factory for creating {@link ParameterizedClassDesc} that represents the type of fields, method argument types, and return types.
 * <p>
 * This class does not cache instances of {@link ParameterizedClassDesc}. Please obtain {@link ParameterizedClassDesc} via {@link BeanDesc}.
 * </p>
 *
 * @author koichik
 * @see BeanDesc#getTypeVariables()
 * @see PropertyDesc#getParameterizedClassDesc()
 * @see FieldDesc#getParameterizedClassDesc()
 * @see ConstructorDesc#getParameterizedClassDescs()
 * @see MethodDesc#getParameterizedClassDesc()
 * @see MethodDesc#getParameterizedClassDescs()
 */
public abstract class ParameterizedClassDescFactory {

    /**
     * Returns a {@link Map} with type variables as keys and type arguments as values for a parameterized type (class or interface).
     * <p>
     * If the type is not parameterized, an empty {@link Map} is returned.
     * </p>
     *
     * @param beanClass
     *            The parameterized type (class or interface). Must not be {@literal null}.
     * @return A {@link Map} with type variables as keys and type arguments as values for the parameterized type.
     */
    public static Map<TypeVariable<?>, Type> getTypeVariables(final Class<?> beanClass) {
        assertArgumentNotNull("beanClass", beanClass);

        return getTypeVariableMap(beanClass);
    }

    /**
     * Creates and returns a {@link ParameterizedClassDesc} that represents the type of the field.
     *
     * @param field
     *            The field. Must not be {@literal null}.
     * @param map
     *            A {@link Map} with type variables as keys and type arguments as values for the parameterized type. Must not be {@literal null}.
     * @return A {@link ParameterizedClassDesc} that represents the type of the field.
     */
    public static ParameterizedClassDesc createParameterizedClassDesc(final Field field, final Map<TypeVariable<?>, Type> map) {
        assertArgumentNotNull("field", field);
        assertArgumentNotNull("map", map);

        return createParameterizedClassDesc(field.getGenericType(), map);
    }

    /**
     * Creates and returns a {@link ParameterizedClassDesc} that represents the type of the constructor argument.
     *
     * @param constructor
     *            The constructor. Must not be {@literal null}.
     * @param index
     *            The position of the argument.
     * @param map
     *            A {@link Map} with type variables as keys and type arguments as values for the parameterized type. Must not be {@literal null}.
     * @return A {@link ParameterizedClassDesc} that represents the type of the constructor argument.
     */
    public static ParameterizedClassDesc createParameterizedClassDesc(final Constructor<?> constructor, final int index,
            final Map<TypeVariable<?>, Type> map) {
        assertArgumentNotNull("constructor", constructor);
        assertArgumentNotNull("map", map);

        return createParameterizedClassDesc(constructor.getGenericParameterTypes()[index], map);
    }

    /**
     * Creates and returns a {@link ParameterizedClassDesc} that represents the type of the method argument.
     *
     * @param method
     *            The method. Must not be {@literal null}.
     * @param index
     *            The position of the argument.
     * @param map
     *            A {@link Map} with type variables as keys and type arguments as values for the parameterized type. Must not be {@literal null}.
     * @return A {@link ParameterizedClassDesc} that represents the type of the method argument.
     */
    public static ParameterizedClassDesc createParameterizedClassDesc(final Method method, final int index,
            final Map<TypeVariable<?>, Type> map) {
        assertArgumentNotNull("method", method);
        assertArgumentArrayIndex("index", index, method.getParameterTypes().length);
        assertArgumentNotNull("map", map);

        return createParameterizedClassDesc(method.getGenericParameterTypes()[index], map);
    }

    /**
     * Creates and returns a {@link ParameterizedClassDesc} that represents the return type of the method.
     *
     * @param method
     *            The method. Must not be {@literal null}.
     * @param map
     *            A {@link Map} with type variables as keys and type arguments as values for the parameterized type. Must not be {@literal null}.
     * @return A {@link ParameterizedClassDesc} that represents the return type of the method.
     */
    public static ParameterizedClassDesc createParameterizedClassDesc(final Method method, final Map<TypeVariable<?>, Type> map) {
        assertArgumentNotNull("method", method);
        assertArgumentNotNull("map", map);

        return createParameterizedClassDesc(method.getGenericReturnType(), map);
    }

    /**
     * Creates and returns a {@link ParameterizedClassDesc} that represents the {@link Type}.
     *
     * @param type
     *            The type.
     * @param map
     *            A {@link Map} with type variables as keys and type arguments as values for the parameterized type.
     * @return A {@link ParameterizedClassDesc} that represents the type.
     */
    protected static ParameterizedClassDesc createParameterizedClassDesc(final Type type, final Map<TypeVariable<?>, Type> map) {
        final Class<?> rowClass = getActualClass(type, map);
        if (rowClass == null) {
            return null;
        }
        final ParameterizedClassDescImpl desc = new ParameterizedClassDescImpl(rowClass);
        final Type[] parameterTypes = getGenericParameters(type);
        if (parameterTypes == null) {
            return desc;
        }
        final ParameterizedClassDesc[] parameterDescs = new ParameterizedClassDesc[parameterTypes.length];
        for (final Indexed<Type> parameterType : indexed(iterable(parameterTypes))) {
            parameterDescs[parameterType.getIndex()] = createParameterizedClassDesc(parameterType.getElement(), map);
        }
        desc.setArguments(parameterDescs);
        return desc;
    }

}
