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
package org.codelibs.core.exception;

import static org.codelibs.core.collection.ArrayUtil.asArray;

import java.lang.reflect.Constructor;

import org.codelibs.core.lang.MethodUtil;

/**
 * Exception that wraps a {@link NoSuchMethodException} thrown when a {@link Constructor} cannot be found.
 *
 * @author higa
 */
public class NoSuchConstructorRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 8688818589925114466L;

    /**
     * The target class.
     */
    private final Class<?> targetClass;

    /**
     * The parameter types.
     */
    private final Class<?>[] argTypes;

    /**
     * Creates a {@link NoSuchConstructorRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param argTypes
     *            Array of parameter types
     * @param cause
     *            The cause of the exception
     */
    public NoSuchConstructorRuntimeException(final Class<?> targetClass, final Class<?>[] argTypes, final Throwable cause) {
        super("ECL0064", asArray(targetClass.getName(), MethodUtil.getSignature(targetClass.getSimpleName(), argTypes)), cause);
        this.targetClass = targetClass;
        this.argTypes = argTypes;
    }

    /**
     * Returns the target class.
     *
     * @return Target class
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns the array of parameter types.
     *
     * @return Array of parameter types
     */
    public Class<?>[] getArgTypes() {
        return argTypes;
    }

}
