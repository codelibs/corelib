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

import java.lang.reflect.Method;

import org.codelibs.core.lang.MethodUtil;

/**
 * Exception thrown when a {@link Method} cannot be found.
 *
 * @author higa
 *
 */
public class MethodNotFoundRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -3508955801981550317L;

    private final Class<?> targetClass;

    private final String methodName;

    private final Class<?>[] methodArgClasses;

    /**
     * Creates a {@link MethodNotFoundRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param methodName
     *            Method name
     * @param methodArgs
     *            Array of arguments
     */
    public MethodNotFoundRuntimeException(final Class<?> targetClass, final String methodName, final Object[] methodArgs) {
        this(targetClass, methodName, toClassArray(methodArgs));
    }

    /**
     * Creates a {@link MethodNotFoundRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param methodName
     *            Method name
     * @param methodArgClasses
     *            Array of parameter types
     */
    public MethodNotFoundRuntimeException(final Class<?> targetClass, final String methodName, final Class<?>[] methodArgClasses) {
        super("ECL0049", asArray(targetClass.getName(), MethodUtil.getSignature(methodName, methodArgClasses)));
        this.targetClass = targetClass;
        this.methodName = methodName;
        this.methodArgClasses = methodArgClasses;
    }

    /**
     * Returns the {@link Class} of the target.
     *
     * @return {@link Class} of the target
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns the method name.
     *
     * @return Method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Returns the array of {@link Class} for the method arguments.
     *
     * @return Array of {@link Class} for the method arguments
     */
    public Class<?>[] getMethodArgClasses() {
        return methodArgClasses;
    }

    private static Class<?>[] toClassArray(final Object... methodArgs) {
        if (methodArgs == null) {
            return null;
        }
        final Class<?>[] result = new Class[methodArgs.length];
        for (int i = 0; i < methodArgs.length; ++i) {
            if (methodArgs[i] != null) {
                result[i] = methodArgs[i].getClass();
            }
        }
        return result;
    }

}
