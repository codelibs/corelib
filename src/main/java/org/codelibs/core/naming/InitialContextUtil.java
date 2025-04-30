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
package org.codelibs.core.naming;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.codelibs.core.exception.NamingRuntimeException;

/**
 * Utility class for handling {@link javax.naming.InitialContext InitialContext}.
 *
 * @author higa
 */
public abstract class InitialContextUtil {

    /**
     * Creates and returns an initial context.
     *
     * @return the initial context
     */
    public static InitialContext create() {
        try {
            return new InitialContext();
        } catch (final NamingException ex) {
            throw new NamingRuntimeException(ex);
        }
    }

    /**
     * Creates and returns an initial context using the specified environment.
     *
     * @param env
     *            The environment used to create the initial context. <code>null</code> indicates an empty environment.
     * @return The initial context
     */
    public static InitialContext create(final Hashtable<?, ?> env) {
        try {
            return new InitialContext(env);
        } catch (final NamingException ex) {
            throw new NamingRuntimeException(ex);
        }
    }

    /**
     * Retrieves and returns the specified object from the given initial context.
     *
     * @param ctx
     *            The initial context. Must not be {@literal null}.
     * @param jndiName
     *            The name of the object to look up. Must not be {@literal null} or an empty string.
     * @return The object bound to <code>jndiName</code>.
     * @throws NamingRuntimeException
     *             Thrown if the initial context cannot be created.
     */
    public static Object lookup(final InitialContext ctx, final String jndiName) throws NamingRuntimeException {
        assertArgumentNotNull("ctx", ctx);
        assertArgumentNotEmpty("jndiName", jndiName);

        try {
            return ctx.lookup(jndiName);
        } catch (final NamingException ex) {
            throw new NamingRuntimeException(ex);
        }
    }

}
