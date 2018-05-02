/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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

import java.lang.reflect.InvocationTargetException;

/**
 * {@link InvocationTargetException}をラップする例外です。
 *
 * @author higa
 */
public class InvocationTargetRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 7760491787158046906L;

    private final Class<?> targetClass;

    /**
     * {@link InvocationTargetRuntimeException}を作成します。
     *
     * @param targetClass
     *            ターゲットクラス
     * @param cause
     *            原因となった例外
     */
    public InvocationTargetRuntimeException(final Class<?> targetClass, final InvocationTargetException cause) {
        super("ECL0043", asArray(targetClass.getName(), cause.getTargetException()), cause.getTargetException());
        this.targetClass = targetClass;
    }

    /**
     * ターゲットクラスを返します。
     *
     * @return ターゲットクラス
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

}
