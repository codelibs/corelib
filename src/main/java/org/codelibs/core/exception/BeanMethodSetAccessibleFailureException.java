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
package org.codelibs.core.exception;

import java.lang.reflect.Method;

public class BeanMethodSetAccessibleFailureException extends ClRuntimeException {

    private static final long serialVersionUID = 1L;

    protected final Class<?> targetClass;

    protected final Method targetMethod;

    public BeanMethodSetAccessibleFailureException(final Class<?> componentClass, final Method targetMethod, final Throwable cause) {
        super("ECL0116", new Object[] { targetMethod }, cause);
        this.targetClass = componentClass;
        this.targetMethod = targetMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }
}
