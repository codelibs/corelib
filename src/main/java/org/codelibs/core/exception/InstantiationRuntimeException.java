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
 * {@link InstantiationException}をラップする例外です。
 *
 * @author higa
 */
public class InstantiationRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 5220902071756706607L;

    private final Class<?> targetClass;

    /**
     * {@link InstantiationRuntimeException}を作成します。
     *
     * @param targetClass
     *            ターゲットクラス
     * @param cause
     *            原因となった例外
     */
    public InstantiationRuntimeException(final Class<?> targetClass,
            final InstantiationException cause) {
        super("ECL0041", asArray(targetClass.getName(), cause), cause);
        this.targetClass = targetClass;
    }

    @Override
    public InstantiationRuntimeException initCause(final Throwable cause) {
        return (InstantiationRuntimeException) super.initCause(cause);
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
