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

import org.codelibs.core.message.MessageFormatter;

/**
 * {@link IllegalArgumentException}をラップする例外です。
 *
 * @author koichik
 */
public class ClIllegalArgumentException extends IllegalArgumentException {

    private static final long serialVersionUID = -3701473506893554853L;

    /** {@code null} である引数の名前 */
    protected final String argName;

    /** メッセージコード */
    protected final String messageCode;

    /** メッセージの引数 */
    protected final Object[] args;

    /**
     * {@link ClIllegalArgumentException}を作成します。
     *
     * @param argName
     *            引数の名前
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の配列
     */
    public ClIllegalArgumentException(final String argName,
            final String messageCode, final Object[] args) {
        this(argName, messageCode, args, null);
    }

    /**
     * {@link ClIllegalArgumentException}を作成します。
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
    public ClIllegalArgumentException(final String argName,
            final String messageCode, final Object[] args, final Throwable cause) {
        super(MessageFormatter.getMessage(messageCode, args), cause);
        this.argName = argName;
        this.messageCode = messageCode;
        this.args = args;
    }

    /**
     * 引数の名前を返します。
     *
     * @return 引数の名前
     */
    public String getArgName() {
        return argName;
    }

    /**
     * メッセージコードを返します。
     *
     * @return メッセージコード
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * 引数の配列を返します。
     *
     * @return 引数の配列
     */
    public Object[] getArgs() {
        return args;
    }

}
