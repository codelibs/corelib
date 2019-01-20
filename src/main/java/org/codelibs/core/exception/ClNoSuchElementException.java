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
package org.codelibs.core.exception;

import java.util.NoSuchElementException;

/**
 * {@link NoSuchElementException}をラップする例外です。
 *
 * @author wyukawa
 */
@Deprecated
public class ClNoSuchElementException extends NoSuchElementException {

    private static final long serialVersionUID = 1632854460852262479L;

    /**
     * {@link ClNoSuchElementException}を作成します。
     */
    public ClNoSuchElementException() {
        super();
    }

    /**
     * {@link ClNoSuchElementException}を作成します。
     *
     * @param message
     *            メッセージ
     */
    public ClNoSuchElementException(final String message) {
        super(message);
    }

}
