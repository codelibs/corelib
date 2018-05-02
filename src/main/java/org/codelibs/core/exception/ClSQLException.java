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

import java.sql.SQLException;

import org.codelibs.core.message.MessageFormatter;

/**
 * S2Util用の{@link SQLException}です。
 *
 * @author higa
 */
public class ClSQLException extends SQLException {

    private static final long serialVersionUID = 4098267431221202677L;

    private final String messageCode;

    private final Object[] args;

    private final String sql;

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     */
    public ClSQLException(final String messageCode, final Object[] args) {
        this(messageCode, args, null, 0, null, null);
    }

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     * @param cause
     *            原因となった例外
     */
    public ClSQLException(final String messageCode, final Object[] args, final Throwable cause) {
        this(messageCode, args, null, 0, cause, null);
    }

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     * @param sqlState
     *            SQLステート
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState) {
        this(messageCode, args, sqlState, 0, null, null);
    }

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     * @param sqlState
     *            SQLステート
     * @param cause
     *            原因となった例外
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final Throwable cause) {
        this(messageCode, args, sqlState, 0, cause, null);
    }

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     * @param sqlState
     *            SQLステート
     * @param vendorCode
     *            ベンダーコード
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode) {
        this(messageCode, args, sqlState, vendorCode, null, null);
    }

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     * @param sqlState
     *            SQLステート
     * @param vendorCode
     *            ベンダーコード
     * @param cause
     *            原因となった例外
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode, final Throwable cause) {
        this(messageCode, args, sqlState, vendorCode, cause, null);
    }

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     * @param sqlState
     *            SQLステート
     * @param vendorCode
     *            ベンダーコード
     * @param sql
     *            SQL文字列
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode, final String sql) {
        this(messageCode, args, sqlState, vendorCode, null, sql);
    }

    /**
     * {@link ClSQLException}を作成します。
     *
     * @param messageCode
     *            メッセージコード
     * @param args
     *            引数の並び
     * @param sqlState
     *            SQLステート
     * @param vendorCode
     *            ベンダーコード
     * @param cause
     *            原因となった例外
     * @param sql
     *            SQL文字列
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode,
            final Throwable cause, final String sql) {
        super(MessageFormatter.getMessage(messageCode, args), sqlState, vendorCode, cause);
        this.messageCode = messageCode;
        this.args = args;
        this.sql = sql;
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

    /**
     * SQLを返します。
     *
     * @return SQL
     */
    public String getSql() {
        return sql;
    }

}
