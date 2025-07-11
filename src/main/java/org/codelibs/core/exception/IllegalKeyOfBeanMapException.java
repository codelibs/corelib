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

import java.util.Map;

/**
 * Exception thrown when using a key not contained in a {@literal BeanMap}.
 *
 * @author koichik
 */
public class IllegalKeyOfBeanMapException extends ClIllegalArgumentException {

    private static final long serialVersionUID = 3456740832476626338L;

    /**
     * Constructs an instance.
     *
     * @param key
     *            Key of the map
     * @param map
     *            Map
     */
    public IllegalKeyOfBeanMapException(final Object key, final Map<?, ?> map) {
        super("key", "ECL0016", asArray(key, map));
    }

}
