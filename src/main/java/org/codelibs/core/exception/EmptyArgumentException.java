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

/**
 * Exception thrown when an argument is empty.
 *
 * @author higa
 */
public class EmptyArgumentException extends ClIllegalArgumentException {

    private static final long serialVersionUID = 4625805280526951642L;

    /**
     * Creates an {@link EmptyArgumentException}.
     *
     * @param argName
     *            Name of the argument
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     */
    public EmptyArgumentException(final String argName, final String messageCode, final Object[] args) {
        this(argName, messageCode, args, null);
    }

    /**
     * Creates an {@link EmptyArgumentException}.
     *
     * @param argName
     *            Name of the argument
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param cause
     *            The cause of the exception
     */
    public EmptyArgumentException(final String argName, final String messageCode, final Object[] args, final Throwable cause) {
        super(argName, messageCode, args, cause);
    }

}
