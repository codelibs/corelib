/*
 * Copyright 2012 the CodeLibs Project and the Others.
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

import org.seasar.util.exception.SRuntimeException;

/**
 * Base exception class for CoreLib.
 * 
 * @author shinsuke
 * 
 */
public class CoreLibRuntimeException extends SRuntimeException {

    private static final long serialVersionUID = 1L;

    public CoreLibRuntimeException(final String messageCode,
            final Object[] args, final Throwable cause) {
        super(messageCode, args, cause);
    }

    public CoreLibRuntimeException(final String messageCode, final Object[] args) {
        super(messageCode, args);
    }

    public CoreLibRuntimeException(final String messageCode,
            final Throwable cause) {
        super(messageCode, new Object[] {}, cause);
    }

    public CoreLibRuntimeException(final String messageCode) {
        super(messageCode, new Object[0]);
    }
}
