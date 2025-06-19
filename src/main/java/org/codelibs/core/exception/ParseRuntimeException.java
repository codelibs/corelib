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

import java.text.ParseException;

/**
 * Exception thrown when parsing fails.
 *
 * @author higa
 */
public class ParseRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -5237329676597387063L;

    /**
     * Creates a {@link ParseRuntimeException}.
     *
     * @param cause the underlying exception
     */
    public ParseRuntimeException(final ParseException cause) {
        super("ECL0050", asArray(cause), cause);
    }

    /**
     * Creates a {@link ParseRuntimeException}.
     *
     * @param s the string that could not be parsed
     */
    public ParseRuntimeException(final String s) {
        super("ECL0051", asArray(s));
    }

}
