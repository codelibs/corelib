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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import org.codelibs.core.exception.SQLRuntimeException;
import org.codelibs.core.lang.ClassUtil;

/**
 * Utility class for {@link java.sql.DriverManager}.
 *
 * @author koichik
 */
public abstract class DriverManagerUtil {

    /**
     * Do not instantiate.
     */
    protected DriverManagerUtil() {
    }

    /**
     * Registers a JDBC driver.
     *
     * @param driverClassName
     *            The class name of the JDBC driver to register. Must not be {@literal null} or an empty string.
     */
    public static void registerDriver(final String driverClassName) {
        assertArgumentNotEmpty("driverClassName", driverClassName);

        final Class<Driver> clazz = ClassUtil.forName(driverClassName);
        registerDriver(clazz);
    }

    /**
     * Registers a JDBC driver.
     *
     * @param driverClass
     *            The class of the JDBC driver to register. Must not be {@literal null}.
     */
    public static void registerDriver(final Class<Driver> driverClass) {
        assertArgumentNotNull("driverClass", driverClass);
        registerDriver(ClassUtil.newInstance(driverClass));
    }

    /**
     * Registers a JDBC driver.
     *
     * @param driver
     *            The JDBC driver to register. Must not be {@literal null}.
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
     * Deregisters a JDBC driver.
     *
     * @param driver
     *            The JDBC driver to deregister. Must not be {@literal null}.
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
     * Deregisters all JDBC drivers associated with the current class loader.
     */
    public static synchronized void deregisterAllDrivers() {
        for (final Enumeration<Driver> e = DriverManager.getDrivers(); e.hasMoreElements();) {
            deregisterDriver(e.nextElement());
        }
    }

}
