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
 * Exception thrown when a class cannot be found.
 *
 * @author higa
 */
public class ClassNotFoundRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -9022468864937761059L;

    /**
     * The name of the class that could not be found.
     */
    private final String className;

    /**
     * Creates a {@link ClassNotFoundRuntimeException}.
     *
     * @param cause the underlying exception
     */
    public ClassNotFoundRuntimeException(final ClassNotFoundException cause) {
        this(null, cause);
    }

    /**
     * Creates a {@link ClassNotFoundRuntimeException}.
     *
     * @param className the class name
     * @param cause the underlying exception
     */
    public ClassNotFoundRuntimeException(final String className, final ClassNotFoundException cause) {
        super("ECL0044", asArray(cause), cause);
        this.className = className;
    }

    /**
     * Returns the class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

}
