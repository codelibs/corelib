/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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
package org.codelibs.core.beans.util;

import java.util.LinkedHashMap;

import org.codelibs.core.exception.IllegalKeyOfBeanMapException;

/**
 * Stringがキーで、存在しないキーにアクセスする(get)と例外を投げるマップです。
 *
 * @author higa
 */
public class BeanMap extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1;

    @Override
    public Object get(final Object key) {
        if (!containsKey(key)) {
            throw new IllegalKeyOfBeanMapException(key, this);
        }
        return super.get(key);
    }

}
