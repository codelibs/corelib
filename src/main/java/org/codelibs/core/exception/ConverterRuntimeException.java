/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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

import org.codelibs.core.beans.Converter;

/**
 * {@link Converter}でエラーが起きた場合にスローされる例外です。
 *
 * @author higa
 */
public class ConverterRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 1L;

    private final String propertyName;

    private final Object value;

    /**
     * インスタンスを構築します。
     *
     * @param propertyName
     *            プロパティ名
     * @param value
     *            値
     * @param cause
     *            原因
     */
    public ConverterRuntimeException(final String propertyName, final Object value, final Throwable cause) {
        super("ECL0097", asArray(propertyName, value, cause), cause);
        this.propertyName = propertyName;
        this.value = value;
    }

    /**
     * プロパティ名を返します。
     *
     * @return プロパティ名
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 値を返します。
     *
     * @return 値
     */
    public Object getValue() {
        return value;
    }

}
