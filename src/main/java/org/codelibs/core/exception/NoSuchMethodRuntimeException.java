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

import org.codelibs.core.lang.MethodUtil;

/**
 * Exception that wraps {@link NoSuchMethodException}.
 *
 * @author higa
 */
public class NoSuchMethodRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -5673845060079098617L;

    /**
     * The target class.
     */
    private final Class<?> targetClass;

    /**
     * The name of the method.
     */
    private final String methodName;

    /**
     * The argument types.
     */
    private final Class<?>[] argTypes;

    /**
     * Creates a {@link NoSuchMethodRuntimeException}.
     *
     * @param targetClass the target class
     * @param methodName the method name
     * @param argTypes the argument types
     * @param cause the underlying exception
     */
    public NoSuchMethodRuntimeException(final Class<?> targetClass, final String methodName, final Class<?>[] argTypes,
            final Throwable cause) {
        super("ECL0057", asArray(targetClass.getName(), MethodUtil.getSignature(methodName, argTypes)), cause);
        this.targetClass = targetClass;
        this.methodName = methodName;
        this.argTypes = argTypes;
    }

    /**
     * Returns the target class.
     *
     * @return the target class
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns the method name.
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Returns the argument types.
     *
     * @return the argument types
     */
    public Class<?>[] getArgTypes() {
        return argTypes;
    }

}
