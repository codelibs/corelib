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
 * Exception while accessing a file.
 *
 * @author shinsuke
 *
 */
public class FileAccessException extends ClRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link FileAccessException} with the specified message code, arguments, and cause.
     *
     * @param messageCode
     *            the message code
     * @param args
     *            the message arguments
     * @param cause
     *            the cause
     */
    public FileAccessException(final String messageCode, final Object[] args, final Throwable cause) {
        super(messageCode, args, cause);
    }

    /**
     * Creates a new {@link FileAccessException} with the specified message code and arguments.
     *
     * @param messageCode
     *            the message code
     * @param args
     *            the message arguments
     */
    public FileAccessException(final String messageCode, final Object[] args) {
        super(messageCode, args);
    }

    /**
     * Creates a new {@link FileAccessException} with the specified message code and cause.
     *
     * @param messageCode
     *            the message code
     * @param cause
     *            the cause
     */
    public FileAccessException(final String messageCode, final Throwable cause) {
        super(messageCode, new Object[0], cause);
    }

    /**
     * Creates a new {@link FileAccessException} with the specified message code.
     *
     * @param messageCode
     *            the message code
     */
    public FileAccessException(final String messageCode) {
        super(messageCode, new Object[0]);
    }

}
