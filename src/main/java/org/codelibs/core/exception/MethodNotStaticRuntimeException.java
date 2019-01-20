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
package org.codelibs.core.exception;

import static org.codelibs.core.collection.ArrayUtil.asArray;

import java.lang.reflect.Method;

/**
 * オブジェクトを指定せずに非{@literal static}な{@link Method}にアクセスした場合にスローされる例外です。
 *
 * @author koichik
 */
public class MethodNotStaticRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 7186052234464152208L;

    private final Class<?> targetClass;

    private final String methodName;

    /**
     * {@link MethodNotStaticRuntimeException}を作成します。
     *
     * @param targetClass
     *            ターゲットクラス
     * @param methodName
     *            メソッド名
     */
    public MethodNotStaticRuntimeException(final Class<?> targetClass, final String methodName) {
        super("ECL0100", asArray(targetClass.getName(), methodName));
        this.targetClass = targetClass;
        this.methodName = methodName;
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
     * メソッド名を返します。
     *
     * @return メソッド名
     */
    public String getMethodName() {
        return methodName;
    }

}
