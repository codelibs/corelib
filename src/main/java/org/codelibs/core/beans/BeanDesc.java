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
package org.codelibs.core.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import org.codelibs.core.beans.factory.BeanDescFactory;

/**
 * Interface for handling JavaBeans metadata.
 * <p>
 * Instances of {@link BeanDesc} are obtained from {@link BeanDescFactory}.
 * </p>
 *
 * <pre>
 * BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Foo.class);
 * </pre>
 * <p>
 * From the obtained {@link BeanDesc}, you can retrieve metadata of the properties, fields, constructors, and methods of the target JavaBeans.
 * </p>
 *
 * <pre>
 * for (PropertyDesc propertyDesc : beanDesc.getPropertyDescs()) {
 *     propertyDesc.getValue(foo); // Retrieve the value of Foo's property
 * }
 *
 * for (FieldDesc fieldDesc : beanDesc.getFieldDescs()) {
 *     fieldDesc.getFieldValue(foo); // Retrieve the value of Foo's field
 * }
 *
 * for (ConstructorDesc constructorDesc : beanDesc.getConstructorDescs()) {
 *     constructorDesc.newInstance(...); // Create an instance of Foo
 * }
 *
 * for (String methodName : beanDesc.getMethodNames()) {
 *     for (MethodDesc methodDesc : beanDesc.getMethodDescs(methodName)) {
 *         methodDesc.invoke(foo, ...); // Invoke Foo's method
 *     }
 * }
 * </pre>
 *
 * @author higa
 * @see BeanDescFactory
 */
public interface BeanDesc {

    /**
     * Returns the class of the Bean.
     *
     * @param <T>
     *            The class of the Bean
     * @return The class of the Bean
     */
    <T> Class<T> getBeanClass();

    /**
     * Returns a map from type variables to type arguments.
     *
     * @return A map from type variables to type arguments
     */
    Map<TypeVariable<?>, Type> getTypeVariables();

    /**
     * Returns whether the {@link PropertyDesc} exists.
     *
     * @param propertyName
     *            The property name. Must not be {@literal null} or empty string
     * @return Whether the {@link PropertyDesc} exists
     */
    boolean hasPropertyDesc(String propertyName);

    /**
     * Returns the {@link PropertyDesc}.
     *
     * @param propertyName
     *            The property name. Must not be {@literal null} or empty string
     * @return {@link PropertyDesc}
     */
    PropertyDesc getPropertyDesc(String propertyName);

    /**
     * Returns the {@link PropertyDesc}.
     *
     * @param index
     *            The index of the {@link PropertyDesc}
     * @return {@link PropertyDesc}
     */
    PropertyDesc getPropertyDesc(int index);

    /**
     * Returns the number of {@link PropertyDesc}.
     *
     * @return The number of {@link PropertyDesc}
     */
    int getPropertyDescSize();

    /**
     * Returns an {@link Iterable} of {@link PropertyDesc}.
     *
     * @return An {@link Iterable} of {@link PropertyDesc}
     */
    Iterable<PropertyDesc> getPropertyDescs();

    /**
     * Returns whether the {@link FieldDesc} exists.
     *
     * @param fieldName
     *            The field name. Must not be {@literal null} or empty string
     * @return Whether the {@link FieldDesc} exists
     */
    boolean hasFieldDesc(String fieldName);

    /**
     * Returns the {@link FieldDesc}.
     *
     * @param fieldName
     *            The field name. Must not be {@literal null} or empty string
     * @return {@link FieldDesc}
     */
    FieldDesc getFieldDesc(String fieldName);

    /**
     * Returns the {@link FieldDesc}.
     *
     * @param index
     *            The index of the {@link FieldDesc}
     * @return {@link FieldDesc}
     */
    FieldDesc getFieldDesc(int index);

    /**
     * Returns the number of {@link FieldDesc}.
     *
     * @return The number of {@link FieldDesc}
     */
    int getFieldDescSize();

    /**
     * Returns an {@link Iterable} of {@link FieldDesc}.
     *
     * @return An {@link Iterable} of {@link FieldDesc}
     */
    Iterable<FieldDesc> getFieldDescs();

    /**
     * Creates a new instance.
     *
     * @param <T>
     *            The type of the Bean class
     * @param args
     *            The arguments to pass to the constructor
     * @return A new instance
     */
    <T> T newInstance(Object... args);

    /**
     * Returns the {@link ConstructorDesc} for the given parameter types.
     *
     * @param paramTypes
     *            The array of parameter types for the constructor
     * @return The {@link ConstructorDesc} for the given parameter types
     */
    ConstructorDesc getConstructorDesc(Class<?>... paramTypes);

    /**
     * Returns the {@link ConstructorDesc} that matches the given arguments.
     *
     * @param args
     *            The arguments to pass to the constructor
     * @return The {@link ConstructorDesc} that matches the given arguments
     */
    ConstructorDesc getSuitableConstructorDesc(Object... args);

    /**
     * Returns the {@link ConstructorDesc}.
     *
     * @param index
     *            The index of the {@link ConstructorDesc}
     * @return {@link ConstructorDesc}
     */
    ConstructorDesc getConstructorDesc(int index);

    /**
     * Returns the number of {@link ConstructorDesc}.
     *
     * @return The number of {@link ConstructorDesc}
     */
    int getConstructorDescSize();

    /**
     * Returns an {@link Iterable} of {@link ConstructorDesc}.
     *
     * @return An {@link Iterable} of {@link ConstructorDesc}
     */
    Iterable<ConstructorDesc> getConstructorDescs();

    /**
     * Returns the {@link MethodDesc} for the given parameter types.
     *
     * @param methodName
     *            The method name. Must not be {@literal null} or empty string
     * @param paramTypes
     *            The array of parameter types for the method
     * @return The {@link MethodDesc} for the given parameter types
     */
    MethodDesc getMethodDesc(String methodName, Class<?>... paramTypes);

    /**
     * Returns the {@link MethodDesc} that matches the given parameter types. If not found, returns {@literal null}.
     *
     * @param methodName
     *            The method name. Must not be {@literal null} or empty string
     * @param paramTypes
     *            The array of parameter types for the method
     * @return The {@link MethodDesc} that matches the given parameter types
     */
    MethodDesc getMethodDescNoException(String methodName, Class<?>... paramTypes);

    /**
     * Returns the {@link MethodDesc} that matches the given arguments.
     *
     * @param methodName
     *            The method name. Must not be {@literal null} or empty string
     * @param args
     *            The array of arguments for the method
     * @return The {@link MethodDesc} that matches the given arguments
     */
    MethodDesc getSuitableMethodDesc(String methodName, Object... args);

    /**
     * Returns whether the {@link MethodDesc} exists.
     *
     * @param methodName
     *            The method name. Must not be {@literal null} or empty string
     * @return Whether the {@link MethodDesc} exists
     */
    boolean hasMethodDesc(String methodName);

    /**
     * Returns an array of {@link MethodDesc}.
     *
     * @param methodName
     *            The method name. Must not be {@literal null} or empty string
     * @return An array of {@link MethodDesc}
     */
    MethodDesc[] getMethodDescs(String methodName);

    /**
     * Returns an array of method names.
     *
     * @return An array of method names
     */
    String[] getMethodNames();

}
