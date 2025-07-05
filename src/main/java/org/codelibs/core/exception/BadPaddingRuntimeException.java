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

import javax.crypto.BadPaddingException;

/**
 * Signals that this exception has been thrown when a particular padding mechanism is expected for the input data but the data is not padded properly.
 * @author shinsuke
 */
public class BadPaddingRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link BadPaddingRuntimeException} with the specified cause.
     *
     * @param cause
     *            the cause
     */
    public BadPaddingRuntimeException(final BadPaddingException cause) {
        super("ECL0105", new Object[] { cause.getMessage() }, cause);
    }

}
