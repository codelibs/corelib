/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Interface for handling methods.
 *
 * @author koichik
 */
public interface MethodDesc {

    /**
     * Returns the {@link BeanDesc} of the class that owns this method.
     *
     * @return {@link BeanDesc}
     */
    BeanDesc getBeanDesc();

    /**
     * Returns the method.
     *
     * @return the method
     */
    Method getMethod();

    /**
     * Returns the method name.
     *
     * @return the method name
     */
    String getMethodName();

    /**
     * Returns an array of the method's parameter types.
     *
     * @return an array of the method's parameter types
     */
    Class<?>[] getParameterTypes();

    /**
     * Returns the return type of the method.
     *
     * @param <T>
     *            the return type of the method
     * @return the return type of the method
     */
    <T> Class<T> getReturnType();

    /**
     * Returns {@literal true} if the method is {@literal public}.
     *
     * @return {@literal true} if the method is {@literal public}
     */
    boolean isPublic();

    /**
     * Returns {@literal true} if the method is {@literal static}.
     *
     * @return {@literal true} if the method is {@literal static}
     */
    boolean isStatic();

    /**
     * Returns {@literal true} if the method is {@literal final}.
     *
     * @return {@literal true} if the method is {@literal final}
     */
    boolean isFinal();

    /**
     * Returns {@literal true} if the method is {@literal abstract}.
     *
     * @return {@literal true} if the method is {@literal abstract}
     */
    boolean isAbstract();

    /**
     * Returns {@literal true} if the method's parameter type at the specified index is parameterized.
     *
     * @param index
     *            the index of the parameter
     * @return {@literal true} if the parameter type at the specified index is parameterized
     */
    boolean isParameterized(int index);

    /**
     * Returns {@literal true} if the return type is parameterized.
     *
     * @return {@literal true} if the return type is parameterized
     */
    boolean isParameterized();

    /**
     * Returns an array of {@link ParameterizedClassDesc} representing the parameter types of the method.
     *
     * @return an array of {@link ParameterizedClassDesc} representing the parameter types of the method
     */
    ParameterizedClassDesc[] getParameterizedClassDescs();

    /**
     * Returns the {@link ParameterizedClassDesc} representing the return type of the method.
     *
     * @return the {@link ParameterizedClassDesc} representing the return type of the method
     */
    ParameterizedClassDesc getParameterizedClassDesc();

    /**
     * Returns the element type if the method's parameter type at the specified index is a parameterized {@link Collection}.
     *
     * @param index
     *            the index of the parameter
     * @return the element type if the parameter type at the specified index is a parameterized {@link Collection}, otherwise {@literal null}
     */
    Class<?> getElementClassOfCollection(int index);

    /**
     * Returns the key type if the method's parameter type at the specified index is a parameterized {@link Map}.
     *
     * @param index
     *            the index of the parameter
     * @return the key type if the parameter type at the specified index is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getKeyClassOfMap(int index);

    /**
     * Returns the value type if the method's parameter type at the specified index is a parameterized {@link Map}.
     *
     * @param index
     *            the index of the parameter
     * @return the value type if the parameter type at the specified index is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getValueClassOfMap(int index);

    /**
     * Returns the element type if the return type of the method is a parameterized {@link Collection}.
     *
     * @return the element type if the return type of the method is a parameterized {@link Collection}, otherwise {@literal null}
     */
    Class<?> getElementClassOfCollection();

    /**
     * Returns the key type if the return type of the method is a parameterized {@link Map}.
     *
     * @return the key type if the return type of the method is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getKeyClassOfMap();

    /**
     * Returns the value type if the return type of the method is a parameterized {@link Map}.
     *
     * @return the value type if the return type of the method is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getValueClassOfMap();

    /**
     * Invokes the method and returns its result.
     *
     * @param <T>
     *            the return type of the method
     * @param target
     *            the target object. Must not be {@literal null}
     * @param args
     *            the method arguments
     * @return the result of the method
     */
    <T> T invoke(Object target, Object... args);

    /**
     * Invokes a static method and returns its result.
     *
     * @param <T>
     *            the return type of the method
     * @param args
     *            the method arguments
     * @return the result of the method
     */
    <T> T invokeStatic(Object... args);

}
