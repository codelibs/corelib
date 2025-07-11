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
 * Exception thrown when a resource cannot be found.
 *
 * @author higa
 */
public class ResourceNotFoundRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 9033370905740809950L;

    /**
     * The path to the resource.
     */
    private final String path;

    /**
     * Creates a {@link ResourceNotFoundRuntimeException}.
     *
     * @param path the resource path
     */
    public ResourceNotFoundRuntimeException(final String path) {
        super("ECL0055", asArray(path));
        this.path = path;
    }

    /**
     * Returns the path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

}
