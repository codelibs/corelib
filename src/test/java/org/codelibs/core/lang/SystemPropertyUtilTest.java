/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
package org.codelibs.core.lang;

import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class SystemPropertyUtilTest {

    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        System.out.println(SystemPropertyUtil.FILE_ENCODING);
        System.out.println(SystemPropertyUtil.LINE_SEPARATOR);
        System.out.println(SystemPropertyUtil.PATH_SEPARATOR);
        System.out.println(SystemPropertyUtil.OS_NAME);
        System.out.println(SystemPropertyUtil.JAVA_IO_TMPDIR);
        System.out.println(SystemPropertyUtil.USER_DIR);
        System.out.println(SystemPropertyUtil.USER_HOME);
    }

}
