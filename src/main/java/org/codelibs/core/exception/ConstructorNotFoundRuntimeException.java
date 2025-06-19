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

/**
 * Exception thrown when a {@link Constructor} cannot be found.
 *
 * @author higa
 */
public class ConstructorNotFoundRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 8584662068396978822L;

    private final Class<?> targetClass;

    private final Object[] methodArgs;

    private final Class<?>[] paramTypes;

    /**
     * Creates a {@link ConstructorNotFoundRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param methodArgs
     *            Array of arguments
     */
    public ConstructorNotFoundRuntimeException(final Class<?> targetClass, final Object[] methodArgs) {
        super("ECL0048", asArray(targetClass.getName(), getSignature(methodArgs)));
        this.targetClass = targetClass;
        this.methodArgs = methodArgs;
        paramTypes = null;
    }

    /**
     * Creates a {@link ConstructorNotFoundRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param paramTypes
     *            Array of parameter types
     */
    public ConstructorNotFoundRuntimeException(final Class<?> targetClass, final Class<?>[] paramTypes) {
        super("ECL0048", asArray(targetClass.getName(), getSignature(paramTypes)));
        this.targetClass = targetClass;
        this.paramTypes = paramTypes;
        methodArgs = null;
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
     * Returns the array of arguments.
     *
     * @return Array of arguments
     */
    public Object[] getMethodArgs() {
        return methodArgs;
    }

    /**
     * Returns the array of parameter types.
     *
     * @return Array of parameter types
     */
    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    private static String getSignature(final Object... methodArgs) {
        if (methodArgs == null || methodArgs.length == 0) {
            return "";
        }
        final StringBuilder buf = new StringBuilder(100);
        for (final Object arg : methodArgs) {
            if (arg != null) {
                buf.append(arg.getClass().getName());
            } else {
                buf.append("null");
            }
            buf.append(", ");
        }
        buf.setLength(buf.length() - 2);
        return new String(buf);
    }

    private static String getSignature(final Class<?>... paramTypes) {
        if (paramTypes == null || paramTypes.length == 0) {
            return "";
        }
        final StringBuilder buf = new StringBuilder(100);
        for (final Class<?> type : paramTypes) {
            if (type != null) {
                buf.append(type.getName());
            } else {
                buf.append("null");
            }
            buf.append(", ");
        }
        buf.setLength(buf.length() - 2);
        return buf.toString();
    }

}
