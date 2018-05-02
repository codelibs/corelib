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

/**
 * {@link IllegalAccessException}をラップする例外です。
 *
 * @author higa
 */
public class IllegalAccessRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -3649900343028907465L;

    private final Class<?> targetClass;

    /**
     * {@link IllegalAccessRuntimeException}を作成します。
     *
     * @param targetClass
     *            ターゲットクラス
     * @param cause
     *            原因となった例外
     */
    public IllegalAccessRuntimeException(final Class<?> targetClass, final IllegalAccessException cause) {
        super("ECL0042", asArray(targetClass.getName(), cause), cause);
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
