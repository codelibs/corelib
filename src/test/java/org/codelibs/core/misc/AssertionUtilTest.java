/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
package org.codelibs.core.misc;

import static org.codelibs.core.misc.AssertionUtil.assertArgument;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;
import static org.codelibs.core.misc.AssertionUtil.assertIndex;
import static org.codelibs.core.misc.AssertionUtil.assertState;
import static org.hamcrest.CoreMatchers.is;

import org.codelibs.core.exception.ClIllegalArgumentException;
import org.codelibs.core.exception.ClIllegalStateException;
import org.codelibs.core.exception.ClIndexOutOfBoundsException;
import org.codelibs.core.exception.NullArgumentException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author wyukawa
 *
 */
public class AssertionUtilTest {

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test method for
     * {@link org.codelibs.core.misc.AssertionUtil#assertArgumentNotNull(java.lang.String, java.lang.Object)}
     * .
     */
    @Test
    public void testAssertArgumentNotNull() {
        exception.expect(NullArgumentException.class);
        exception.expectMessage(is("[ECL0008]argument[hoge] is null."));
        assertArgumentNotNull("hoge", null);
    }

    /**
     * Test method for
     * {@link org.codelibs.core.misc.AssertionUtil#assertArgument(String, boolean, String)}
     * .
     */
    @Test
    public void testAssertArgument() {
        exception.expect(ClIllegalArgumentException.class);
        exception.expectMessage(is("[ECL0009]argument[hoge] is illegal. because hogeだからです。."));
        assertArgument("hoge", false, "hogeだからです。");
    }

    /**
     * Test method for
     * {@link org.codelibs.core.misc.AssertionUtil#assertState(boolean, String)} .
     */
    @Test
    public void testAssertState() {
        exception.expect(ClIllegalStateException.class);
        exception.expectMessage(is("hogeだからです。"));
        assertState(false, "hogeだからです。");
    }

    /**
     * Test method for
     * {@link org.codelibs.core.misc.AssertionUtil#assertIndex(boolean, String)} .
     */
    @Test
    public void testAssertIndex() {
        exception.expect(ClIndexOutOfBoundsException.class);
        exception.expectMessage(is("hogeだからです。"));
        assertIndex(false, "hogeだからです。");
    }

}
