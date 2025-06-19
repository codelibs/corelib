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

/**
 * Exception that wraps {@link IllegalAccessException}.
 *
 * @author higa
 */
public class IllegalAccessRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -3649900343028907465L;

    private final Class<?> targetClass;

    /**
     * Creates a {@link IllegalAccessRuntimeException}.
     *
     * @param targetClass
     *            Target class
     * @param cause
     *            The cause of the exception
     */
    public IllegalAccessRuntimeException(final Class<?> targetClass, final IllegalAccessException cause) {
        super("ECL0042", asArray(targetClass.getName(), cause), cause);
        this.targetClass = targetClass;
    }

    /**
     * Returns the target class.
     *
     * @return Target class
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

}
