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

/**
 * 空の場合にスローされる例外です。
 *
 * @author higa
 */
public class EmptyArgumentException extends ClIllegalArgumentException {

    private static final long serialVersionUID = 4625805280526951642L;

    /**
     * {@link EmptyArgumentException}を作成します。
     *
     * @param argName
     *            引数の名前
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の配列
     */
    public EmptyArgumentException(final String argName, final String messageCode, final Object[] args) {
        this(argName, messageCode, args, null);
    }

    /**
     * {@link EmptyArgumentException}を作成します。
     *
     * @param argName
     *            引数の名前
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の配列
     * @param cause
     *            原因となった例外
     */
    public EmptyArgumentException(final String argName, final String messageCode, final Object[] args, final Throwable cause) {
        super(argName, messageCode, args, cause);
    }

}
