/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.core.exception;

/**
 * Exception that wraps {@link UnsupportedOperationException}.
 *
 * @author wyukawa
 */
public class ClUnsupportedOperationException extends UnsupportedOperationException {

    private static final long serialVersionUID = -6732367317955522602L;

    /**
     * Creates a {@link ClUnsupportedOperationException}.
     */
    public ClUnsupportedOperationException() {
    }

    /**
     * Creates a {@link ClUnsupportedOperationException}.
     *
     * @param message the message
     */
    public ClUnsupportedOperationException(final String message) {
        super(message);
    }

    /**
     * Creates a {@link ClUnsupportedOperationException}.
     *
     * @param message the message
     * @param cause the underlying exception
     */
    public ClUnsupportedOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@link ClUnsupportedOperationException}.
     *
     * @param cause the underlying exception
     */
    public ClUnsupportedOperationException(final Throwable cause) {
        super(cause);
    }

}
