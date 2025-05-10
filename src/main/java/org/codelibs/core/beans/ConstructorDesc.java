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
import java.util.Collection;
import java.util.Map;

/**
 * Interface for handling constructors.
 *
 * @author koichik
 */
public interface ConstructorDesc {

    /**
     * Returns the {@link BeanDesc} of the class that owns this constructor.
     *
     * @return {@link BeanDesc}
     */
    BeanDesc getBeanDesc();

    /**
     * Returns the constructor.
     *
     * @param <T>
     *            The type of the Bean
     * @return The constructor
     */
    <T> Constructor<T> getConstructor();

    /**
     * Returns an array of the parameter types of the constructor.
     *
     * @return An array of the parameter types of the constructor
     */
    Class<?>[] getParameterTypes();

    /**
     * Returns {@literal true} if the constructor is {@literal public}.
     *
     * @return {@literal true} if the constructor is {@literal public}
     */
    boolean isPublic();

    /**
     * Returns {@literal true} if the parameter type of the constructor is parameterized.
     *
     * @param index
     *            The index of the parameter
     * @return {@literal true} if the parameter type is parameterized
     */
    boolean isParameterized(int index);

    /**
     * Returns an array of {@link ParameterizedClassDesc} representing the parameter types of the method.
     *
     * @return An array of {@link ParameterizedClassDesc} representing the parameter types of the method
     */
    ParameterizedClassDesc[] getParameterizedClassDescs();

    /**
     * Returns the element type if the parameter type of the method is a parameterized {@link Collection}.
     *
     * @param index
     *            The index of the parameter
     * @return The element type if the parameter type is a parameterized {@link Collection}, otherwise {@literal null}
     */
    Class<?> getElementClassOfCollection(int index);

    /**
     * Returns the key type if the parameter type of the method is a parameterized {@link Map}.
     *
     * @param index
     *            The index of the parameter
     * @return The key type if the parameter type is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getKeyClassOfMap(int index);

    /**
     * Returns the value type if the parameter type of the method is a parameterized {@link Map}.
     *
     * @param index
     *            The index of the parameter
     * @return The value type if the parameter type is a parameterized {@link Map}, otherwise {@literal null}
     */
    Class<?> getValueClassOfMap(int index);

    /**
     * Returns an instance created by invoking the constructor.
     *
     * @param <T>
     *            The type of the Bean to be created
     * @param args
     *            The arguments for the constructor
     * @return An instance created by invoking the constructor
     */
    <T> T newInstance(Object... args);

}
