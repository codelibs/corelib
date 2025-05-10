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

import org.codelibs.core.lang.MethodUtil;

/**
 * {@link NoSuchMethodException}をラップする例外です。
 *
 * @author higa
 */
public class NoSuchMethodRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -5673845060079098617L;

    private final Class<?> targetClass;

    private final String methodName;

    private final Class<?>[] argTypes;

    /**
     * {@link NoSuchMethodRuntimeException}を作成します。
     *
     * @param targetClass
     *            ターゲットクラス
     * @param methodName
     *            メソッド名
     * @param argTypes
     *            引数型の並び
     * @param cause
     *            原因となった例外
     */
    public NoSuchMethodRuntimeException(final Class<?> targetClass, final String methodName, final Class<?>[] argTypes,
            final Throwable cause) {
        super("ECL0057", asArray(targetClass.getName(), MethodUtil.getSignature(methodName, argTypes)), cause);
        this.targetClass = targetClass;
        this.methodName = methodName;
        this.argTypes = argTypes;
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

    /**
     * 引数型の並びを返します。
     *
     * @return 引数型の並び
     */
    public Class<?>[] getArgTypes() {
        return argTypes;
    }

}
