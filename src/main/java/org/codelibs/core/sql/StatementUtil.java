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
package org.codelibs.core.sql;

import static org.codelibs.core.log.Logger.format;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.codelibs.core.exception.SQLRuntimeException;
import org.codelibs.core.log.Logger;

/**
 * Utility class for {@link Statement}.
 *
 * @author higa
 */
public abstract class StatementUtil {

    private static final Logger logger = Logger.getLogger(StatementUtil.class);

    /**
     * Do not instantiate.
     */
    protected StatementUtil() {
    }

    /**
     * Executes the SQL.
     *
     * @param statement
     *            {@link Statement}. Must not be {@literal null}.
     * @param sql
     *            SQL string. Must not be {@literal null} or empty.
     * @return The result of the execution.
     * @see Statement#execute(String)
     */
    public static boolean execute(final Statement statement, final String sql) {
        assertArgumentNotNull("statement", statement);
        assertArgumentNotEmpty("sql", sql);

        try {
            return statement.execute(sql);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Sets the fetch size.
     *
     * @param statement
     *            {@link Statement}. Must not be {@literal null}.
     * @param fetchSize
     *            Fetch size.
     * @see Statement#setFetchSize(int)
     */
    public static void setFetchSize(final Statement statement, final int fetchSize) {
        assertArgumentNotNull("statement", statement);

        try {
            statement.setFetchSize(fetchSize);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Sets the maximum number of rows.
     *
     * @param statement
     *            {@link Statement}. Must not be {@literal null}.
     * @param maxRows
     *            Maximum number of rows.
     * @see Statement#setMaxRows(int)
     */
    public static void setMaxRows(final Statement statement, final int maxRows) {
        assertArgumentNotNull("statement", statement);

        try {
            statement.setMaxRows(maxRows);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Sets the query timeout.
     *
     * @param statement
     *            {@link Statement}. Must not be {@literal null}.
     * @param queryTimeout
     *            Query timeout.
     * @see Statement#setQueryTimeout(int)
     */
    public static void setQueryTimeout(final Statement statement, final int queryTimeout) {
        assertArgumentNotNull("statement", statement);

        try {
            statement.setQueryTimeout(queryTimeout);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Closes the {@link Statement}.
     * <p>
     * If {@link Statement#close()} throws an exception, an error message is logged. The exception is not rethrown.
     * </p>
     *
     * @param statement
     *            {@link Statement}
     * @see Statement#close()
     */
    public static void close(final Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            statement.close();
        } catch (final SQLException e) {
            logger.log(format("ECL0017", e.getMessage()), e);
        }
    }

    /**
     * Returns the result set.
     *
     * @param statement
     *            {@link Statement}. Must not be {@literal null}.
     * @return The result set.
     */
    public static ResultSet getResultSet(final Statement statement) {
        assertArgumentNotNull("statement", statement);

        try {
            return statement.getResultSet();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Returns the update count.
     *
     * @param statement
     *            {@link Statement}. Must not be {@literal null}.
     * @return The update count.
     */
    public static int getUpdateCount(final Statement statement) {
        assertArgumentNotNull("statement", statement);

        try {
            return statement.getUpdateCount();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

}
