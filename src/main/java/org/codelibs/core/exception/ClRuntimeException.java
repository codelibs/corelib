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
 * S2Utilの例外のベースクラスです。
 *
 * @author higa
 */
public class ClRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -4452607868694297329L;

    private final String messageCode;

    private final Object[] args;

    private final String message;

    private final String simpleMessage;

    /**
     * {@link ClRuntimeException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数
     */
    public ClRuntimeException(final String messageCode, final Object[] args) {
        this(messageCode, args, null);
    }

    /**
     * {@link ClRuntimeException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数
     * @param cause
     *            原因となった例外
     */
    public ClRuntimeException(final String messageCode, final Object[] args,
            final Throwable cause) {
        super(cause);
        this.messageCode = messageCode;
        this.args = args;
        simpleMessage = MessageFormatter.getSimpleMessage(messageCode, args);
        message = "[" + messageCode + "]" + simpleMessage;
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

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * メッセージコードなしの単純なメッセージを返します。
     *
     * @return メッセージコードなしの単純なメッセージ
     */
    public final String getSimpleMessage() {
        return simpleMessage;
    }

}
