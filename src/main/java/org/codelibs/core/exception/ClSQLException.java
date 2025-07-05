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

import java.sql.SQLException;

import org.codelibs.core.message.MessageFormatter;

/**
 * {@link SQLException} for S2Util.
 *
 * @author higa
 */
public class ClSQLException extends SQLException {

    private static final long serialVersionUID = 4098267431221202677L;

    /**
     * The message code.
     */
    private final String messageCode;

    /**
     * The arguments for the message.
     */
    private final Object[] args;

    /**
     * The SQL string.
     */
    private final String sql;

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     */
    public ClSQLException(final String messageCode, final Object[] args) {
        this(messageCode, args, null, 0, null, null);
    }

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param cause
     *            The cause of the exception
     */
    public ClSQLException(final String messageCode, final Object[] args, final Throwable cause) {
        this(messageCode, args, null, 0, cause, null);
    }

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param sqlState
     *            SQL state
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState) {
        this(messageCode, args, sqlState, 0, null, null);
    }

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param sqlState
     *            SQL state
     * @param cause
     *            The cause of the exception
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final Throwable cause) {
        this(messageCode, args, sqlState, 0, cause, null);
    }

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param sqlState
     *            SQL state
     * @param vendorCode
     *            Vendor code
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode) {
        this(messageCode, args, sqlState, vendorCode, null, null);
    }

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param sqlState
     *            SQL state
     * @param vendorCode
     *            Vendor code
     * @param cause
     *            The cause of the exception
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode,
            final Throwable cause) {
        this(messageCode, args, sqlState, vendorCode, cause, null);
    }

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param sqlState
     *            SQL state
     * @param vendorCode
     *            Vendor code
     * @param sql
     *            SQL string
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode, final String sql) {
        this(messageCode, args, sqlState, vendorCode, null, sql);
    }

    /**
     * Creates a {@link ClSQLException}.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Array of arguments
     * @param sqlState
     *            SQL state
     * @param vendorCode
     *            Vendor code
     * @param cause
     *            The cause of the exception
     * @param sql
     *            SQL string
     */
    public ClSQLException(final String messageCode, final Object[] args, final String sqlState, final int vendorCode, final Throwable cause,
            final String sql) {
        super(MessageFormatter.getMessage(messageCode, args), sqlState, vendorCode, cause);
        this.messageCode = messageCode;
        this.args = args;
        this.sql = sql;
    }

    /**
     * Returns the message code.
     *
     * @return Message code
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Returns the array of arguments.
     *
     * @return Array of arguments
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Returns the SQL.
     *
     * @return SQL
     */
    public String getSql() {
        return sql;
    }

}
