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

/**
 * Exception thrown when accessing a non-{@literal static} {@link Method} without specifying an object.
 *
 * @author koichik
 */
public class MethodNotStaticRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 7186052234464152208L;

    /**
     * The target class.
     */
    private final Class<?> targetClass;

    /**
     * The name of the method.
     */
    private final String methodName;

    /**
     * Creates a {@link MethodNotStaticRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param methodName
     *            Method name
     */
    public MethodNotStaticRuntimeException(final Class<?> targetClass, final String methodName) {
        super("ECL0100", asArray(targetClass.getName(), methodName));
        this.targetClass = targetClass;
        this.methodName = methodName;
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
     * Returns the method name.
     *
     * @return Method name
     */
    public String getMethodName() {
        return methodName;
    }

}
