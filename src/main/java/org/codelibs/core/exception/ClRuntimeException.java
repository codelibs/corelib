/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
 * A base exception class for CoreLib.
 *
 * @author higa
 * @author shinsuke
 */
public class ClRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -4452607868694297329L;

    private final String messageCode;

    private final Object[] args;

    private final String message;

    private final String simpleMessage;

    /**
     * Creates {@link ClRuntimeException}.
     *
     * @param messageCode message code
     */
    public ClRuntimeException(final String messageCode) {
        this(messageCode, new Object[0], null);
    }

    /**
     * Creates {@link ClRuntimeException}.
     *
     * @param messageCode message code
     * @param args arguments for messages
     */
    public ClRuntimeException(final String messageCode, final Object[] args) {
        this(messageCode, args, null);
    }

    /**
     * Creates {@link ClRuntimeException}.
     *
     * @param messageCode message code
     * @param cause cause of exception
     */
    public ClRuntimeException(final String messageCode, final Throwable cause) {
        this(messageCode, new Object[0], cause);
    }

    /**
     * Creates {@link ClRuntimeException}.
     *
     * @param messageCode message code
     * @param args arguments for messages
     * @param cause cause of exception
     */
    public ClRuntimeException(final String messageCode, final Object[] args, final Throwable cause) {
        super(cause);
        this.messageCode = messageCode;
        this.args = args;
        simpleMessage = MessageFormatter.getSimpleMessage(messageCode, args);
        message = "[" + messageCode + "]" + simpleMessage;
    }

    /**
     * Returns a message code.
     *
     * @return message code
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Returns arguments of a message
     *
     * @return array of arguments
     */
    public Object[] getArgs() {
        return args;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Returns a simple message without a message code.
     *
     * @return simple message without message code
     */
    public final String getSimpleMessage() {
        return simpleMessage;
    }

}
