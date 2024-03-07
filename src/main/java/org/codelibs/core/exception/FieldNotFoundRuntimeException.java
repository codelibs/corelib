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

import java.lang.reflect.Field;

/**
 * {@link Field}が見つからない場合にスローされる例外です。
 *
 * @author higa
 *
 */
public class FieldNotFoundRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -2715036865146285893L;

    private final Class<?> targetClass;

    private final String fieldName;

    /**
     * {@link FieldNotFoundRuntimeException}を作成します。
     *
     * @param targetClass
     *            ターゲットクラス
     * @param fieldName
     *            フィールド名
     */
    public FieldNotFoundRuntimeException(final Class<?> targetClass, final String fieldName) {
        super("ECL0070", asArray(targetClass.getName(), fieldName));
        this.targetClass = targetClass;
        this.fieldName = fieldName;
    }

    /**
     * ターゲットクラスを返します。
     *
     * @return ターゲットクラス
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * フィールド名を返します。
     *
     * @return フィールド名
     */
    public String getFieldName() {
        return fieldName;
    }

}
