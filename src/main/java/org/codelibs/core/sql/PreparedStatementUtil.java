/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
 * {@link PreparedStatement}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class PreparedStatementUtil {

    /**
     * クエリを実行します。
     *
     * @param ps
     *            {@link PreparedStatement}。{@literal null}であってはいけません
     * @return {@link ResultSet}
     * @throws SQLRuntimeException
     *             {@link SQLException}が発生した場合
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
     * 更新を実行します。
     *
     * @param ps
     *            {@link PreparedStatement}。{@literal null}であってはいけません
     * @return 更新した結果の行数
     * @throws SQLRuntimeException
     *             {@link SQLException}が発生した場合
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
     * 実行します。
     *
     * @param ps
     *            {@link PreparedStatement}。{@literal null}であってはいけません
     * @return 結果セットを返すかどうか
     * @throws SQLRuntimeException
     *             {@link SQLException}が発生した場合
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
     * バッチ更新を行ないます。
     *
     * @param ps
     *            {@link PreparedStatement}。{@literal null}であってはいけません
     * @return 更新した結果の行数の配列
     * @throws SQLRuntimeException
     *             {@link SQLException}が発生した場合
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
     * バッチを追加します。
     *
     * @param ps
     *            {@link PreparedStatement}。{@literal null}であってはいけません
     * @throws SQLRuntimeException
     *             {@link SQLException}が発生した場合
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
