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

import java.io.UnsupportedEncodingException;

/**
 * Signals that a character encoding is not supported.
 * @author shinsuke
 */
public class UnsupportedEncodingRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link UnsupportedEncodingRuntimeException} with the specified cause.
     *
     * @param cause
     *            the cause
     */
    public UnsupportedEncodingRuntimeException(final UnsupportedEncodingException cause) {
        super("ECL0105", new Object[] { cause.getMessage() }, cause);
    }

}
