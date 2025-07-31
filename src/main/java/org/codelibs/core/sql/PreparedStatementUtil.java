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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codelibs.core.exception.SQLRuntimeException;

/**
 * Utility class for {@link PreparedStatement}.
 *
 * @author higa
 */
public abstract class PreparedStatementUtil {

    /**
     * Do not instantiate.
     */
    protected PreparedStatementUtil() {
    }

    /**
     * Executes the query.
     *
     * @param ps
     *            {@link PreparedStatement}. Must not be {@literal null}.
     * @return {@link ResultSet}
     * @throws SQLRuntimeException
     *             If a {@link SQLException} occurs.
     */
    public static ResultSet executeQuery(final PreparedStatement ps) throws SQLRuntimeException {
        assertArgumentNotNull("ps", ps);

        try {
            return ps.executeQuery();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Executes the update.
     *
     * @param ps
     *            {@link PreparedStatement}. Must not be {@literal null}.
     * @return The number of rows affected by the update.
     * @throws SQLRuntimeException
     *             If a {@link SQLException} occurs.
     */
    public static int executeUpdate(final PreparedStatement ps) throws SQLRuntimeException {
        assertArgumentNotNull("ps", ps);

        try {
            return ps.executeUpdate();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Executes the statement.
     *
     * @param ps
     *            {@link PreparedStatement}. Must not be {@literal null}.
     * @return Whether the result is a {@link ResultSet}.
     * @throws SQLRuntimeException
     *             If a {@link SQLException} occurs.
     * @see PreparedStatement#execute()
     */
    public static boolean execute(final PreparedStatement ps) throws SQLRuntimeException {
        assertArgumentNotNull("ps", ps);

        try {
            return ps.execute();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Executes a batch update.
     *
     * @param ps
     *            {@link PreparedStatement}. Must not be {@literal null}.
     * @return An array of the number of rows affected by each command in the batch.
     * @throws SQLRuntimeException
     *             If a {@link SQLException} occurs.
     */
    public static int[] executeBatch(final PreparedStatement ps) throws SQLRuntimeException {
        assertArgumentNotNull("ps", ps);

        try {
            return ps.executeBatch();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Adds a batch.
     *
     * @param ps
     *            {@link PreparedStatement}. Must not be {@literal null}.
     * @throws SQLRuntimeException
     *             If a {@link SQLException} occurs.
     */
    public static void addBatch(final PreparedStatement ps) throws SQLRuntimeException {
        assertArgumentNotNull("ps", ps);

        try {
            ps.addBatch();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

}
