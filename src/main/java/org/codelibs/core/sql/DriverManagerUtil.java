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
package org.codelibs.core.sql;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import org.codelibs.core.exception.SQLRuntimeException;
import org.codelibs.core.lang.ClassUtil;

/**
 * {@link java.sql.DriverManager}のためのユーティリティクラスです。
 *
 * @author koichik
 */
public abstract class DriverManagerUtil {

    /**
     * JDBCドライバを登録します。
     *
     * @param driverClassName
     *            登録するJDBCドライバのクラス名。{@literal null}や空文字列であってはいけません
     */
    public static void registerDriver(final String driverClassName) {
        assertArgumentNotEmpty("driverClassName", driverClassName);

        final Class<Driver> clazz = ClassUtil.forName(driverClassName);
        registerDriver(clazz);
    }

    /**
     * JDBCドライバを登録します。
     *
     * @param driverClass
     *            登録するJDBCドライバのクラス。{@literal null}であってはいけません
     */
    public static void registerDriver(final Class<Driver> driverClass) {
        assertArgumentNotNull("driverClass", driverClass);
        registerDriver(ClassUtil.newInstance(driverClass));
    }

    /**
     * JDBCドライバを登録します。
     *
     * @param driver
     *            登録するJDBCドライバ。{@literal null}であってはいけません
     */
    public static void registerDriver(final Driver driver) {
        assertArgumentNotNull("driver", driver);

        try {
            DriverManager.registerDriver(driver);
        } catch (final SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * JDBCドライバを登録解除します。
     *
     * @param driver
     *            登録解除するJDBCドライバ。{@literal null}であってはいけません
     */
    public static void deregisterDriver(final Driver driver) {
        assertArgumentNotNull("driver", driver);

        try {
            DriverManager.deregisterDriver(driver);
        } catch (final SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * 現在のクラスローダに結びつけられている全てのJDBCドライバを登録解除します。
     */
    public static synchronized void deregisterAllDrivers() {
        for (final Enumeration<Driver> e = DriverManager.getDrivers(); e.hasMoreElements();) {
            deregisterDriver(e.nextElement());
        }
    }

}
