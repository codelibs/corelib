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

import javax.xml.parsers.ParserConfigurationException;

/**
 * Exception that wraps {@link ParserConfigurationException}.
 *
 * @author higa
 */
public class ParserConfigurationRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -4610465906028959083L;

    /**
     * Creates a {@link ParserConfigurationRuntimeException}.
     *
     * @param cause the underlying exception
     */
    public ParserConfigurationRuntimeException(final ParserConfigurationException cause) {
        super("ECL0053", asArray(cause), cause);
    }

}
