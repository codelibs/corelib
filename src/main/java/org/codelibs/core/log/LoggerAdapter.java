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
package org.codelibs.core.log;

/**
 * Adapter for using any logging framework.
 *
 * @author koichik
 */
interface LoggerAdapter {

    boolean isFatalEnabled();

    void fatal(String message);

    void fatal(String message, Throwable t);

    boolean isErrorEnabled();

    void error(String message);

    void error(String message, Throwable t);

    boolean isWarnEnabled();

    void warn(String message);

    void warn(String message, Throwable t);

    boolean isInfoEnabled();

    void info(String message);

    void info(String message, Throwable t);

    boolean isDebugEnabled();

    void debug(String message);

    void debug(String message, Throwable t);

}
