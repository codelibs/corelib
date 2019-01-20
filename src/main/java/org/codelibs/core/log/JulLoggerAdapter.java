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
package org.codelibs.core.log;

import java.util.logging.Level;

/**
 * java.util.logのロガーを利用するアダプタです。
 *
 * @author koichik
 */
class JulLoggerAdapter implements LoggerAdapter {

    protected final String sourceClass;

    protected final java.util.logging.Logger logger;

    public JulLoggerAdapter(final Class<?> clazz) {
        sourceClass = clazz.getName();
        logger = java.util.logging.Logger.getLogger(clazz.getName());
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public void fatal(final String message) {
        logger.logp(Level.SEVERE, sourceClass, null, message);
    }

    @Override
    public void fatal(final String message, final Throwable t) {
        logger.logp(Level.SEVERE, sourceClass, null, message, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public void error(final String message) {
        logger.logp(Level.SEVERE, sourceClass, null, message);
    }

    @Override
    public void error(final String message, final Throwable t) {
        logger.logp(Level.SEVERE, sourceClass, null, message, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public void warn(final String message) {
        logger.logp(Level.WARNING, sourceClass, null, message);
    }

    @Override
    public void warn(final String message, final Throwable t) {
        logger.logp(Level.WARNING, sourceClass, null, message, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

    @Override
    public void info(final String message) {
        logger.logp(Level.INFO, sourceClass, null, message);
    }

    @Override
    public void info(final String message, final Throwable t) {
        logger.logp(Level.INFO, sourceClass, null, message, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }

    @Override
    public void debug(final String message) {
        logger.logp(Level.FINE, sourceClass, null, message);
    }

    @Override
    public void debug(final String message, final Throwable t) {
        logger.logp(Level.FINE, sourceClass, null, message, t);
    }

}
