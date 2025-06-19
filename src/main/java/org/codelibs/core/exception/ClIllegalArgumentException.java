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

import org.codelibs.core.message.MessageFormatter;

/**
 * Exception that wraps {@link IllegalArgumentException}.
 *
 * @author koichik
 */
public class ClIllegalArgumentException extends IllegalArgumentException {

    private static final long serialVersionUID = -3701473506893554853L;

    /** Name of the argument that is {@code null} */
    protected final String argName;

    /** Message code */
    protected final String messageCode;

    /** Arguments for the message */
    protected final Object[] args;

    /**
     * Creates a {@link ClIllegalArgumentException}.
     *
     * @param argName
     *            Name of the argument
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     */
    public ClIllegalArgumentException(final String argName, final String messageCode, final Object[] args) {
        this(argName, messageCode, args, null);
    }

    /**
     * Creates a {@link ClIllegalArgumentException}.
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
    public ClIllegalArgumentException(final String argName, final String messageCode, final Object[] args, final Throwable cause) {
        super(MessageFormatter.getMessage(messageCode, args), cause);
        this.argName = argName;
        this.messageCode = messageCode;
        this.args = args;
    }

    /**
     * Returns the name of the argument.
     *
     * @return Name of the argument
     */
    public String getArgName() {
        return argName;
    }

    /**
     * Returns the message code.
     *
     * @return Message code
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Returns the array of arguments.
     *
     * @return Array of arguments
     */
    public Object[] getArgs() {
        return args;
    }

}
