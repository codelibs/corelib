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
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.codelibs.core.exception.SQLRuntimeException;
import org.codelibs.core.log.Logger;

/**
 * Utility class for {@link ResultSet}.
 *
 * @author higa
 */
public abstract class ResultSetUtil {

    private static final Logger logger = Logger.getLogger(ResultSetUtil.class);

    /**
     * Closes the result set.
     * <p>
     * If {@link ResultSet#close()} throws an exception, an error message is logged. The exception is not rethrown.
     * </p>
     *
     * @param resultSet
     *            The result set
     */
    public static void close(final ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }
        try {
            resultSet.close();
        } catch (final SQLException e) {
            logger.log(format("ECL0017", e.getMessage()), e);
        }
    }

    /**
     * Moves the result set to the next position.
     *
     * @param resultSet
     *            The result set. Must not be {@literal null}.
     * @return Whether the result set successfully moved to the next position.
     */
    public static boolean next(final ResultSet resultSet) {
        assertArgumentNotNull("resultSet", resultSet);

        try {
            return resultSet.next();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Moves the cursor to the specified position.
     *
     * @param resultSet
     *            The result set. Must not be {@literal null}.
     * @param index
     *            The position.
     * @return Whether the cursor successfully moved to the specified position.
     * @throws SQLRuntimeException
     *             If an SQL exception occurs.
     */
    public static boolean absolute(final ResultSet resultSet, final int index) throws SQLRuntimeException {
        assertArgumentNotNull("resultSet", resultSet);

        try {
            return resultSet.absolute(index);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

}
