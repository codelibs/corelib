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

import java.sql.SQLException;

import org.codelibs.core.message.MessageFormatter;

/**
 * Exception that wraps {@link SQLException}.
 *
 * @author higa
 * @author manhole
 */
public class SQLRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = 2533513110369526191L;

    /**
     * Creates a {@link SQLRuntimeException}.
     *
     * @param cause the underlying exception
     */
    public SQLRuntimeException(final SQLException cause) {
        super("ECL0072", asArray(getSql(cause), getRealMessage(cause), Integer.toString(cause.getErrorCode()), cause.getSQLState()), cause);
    }

    /**
     * Returns the <code>SQL</code>.
     *
     * @param cause the underlying exception
     * @return the <code>SQL</code>
     */
    protected static String getSql(final SQLException cause) {
        if (cause instanceof ClSQLException) {
            return ((ClSQLException) cause).getSql();
        }
        return "";
    }

    /**
     * Returns the real message.
     *
     * @param cause the underlying exception
     * @return the real message
     */
    protected static String getRealMessage(final SQLException cause) {
        final StringBuilder buf = new StringBuilder(256);
        buf.append(cause.getMessage()).append(" : [");
        SQLException next = cause.getNextException();
        while (next != null) {
            buf.append(MessageFormatter.getSimpleMessage("ECL0071", next.getMessage(), Integer.toString(next.getErrorCode()),
                    next.getSQLState())).append("], [");
            next = next.getNextException();
        }
        Throwable t = cause.getCause();
        while (t != null) {
            buf.append(t.getMessage()).append("], [");
            t = t.getCause();
        }
        buf.setLength(buf.length() - 4);
        return new String(buf);
    }

}
