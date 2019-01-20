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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * (Jakarta) Commons Loggingのロガーを利用するアダプタです。
 *
 * @author koichik
 */
class JclLoggerAdapter implements LoggerAdapter {

    protected final String sourceClass;

    protected final Log logger;

    public JclLoggerAdapter(final Class<?> clazz) {
        sourceClass = clazz.getName();
        logger = LogFactory.getLog(clazz);
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isFatalEnabled();
    }

    @Override
    public void fatal(final String message) {
        logger.fatal(message);
    }

    @Override
    public void fatal(final String message, final Throwable t) {
        logger.fatal(message, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(final String message) {
        logger.error(message);
    }

    @Override
    public void error(final String message, final Throwable t) {
        logger.error(message, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(final String message) {
        logger.warn(message);
    }

    @Override
    public void warn(final String message, final Throwable t) {
        logger.warn(message, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(final String message) {
        logger.info(message);
    }

    @Override
    public void info(final String message, final Throwable t) {
        logger.info(message, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(final String message) {
        logger.debug(message);
    }

    @Override
    public void debug(final String message, final Throwable t) {
        logger.debug(message, t);
    }

}
