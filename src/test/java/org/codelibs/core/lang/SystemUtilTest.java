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
package org.codelibs.core.lang;

import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class SystemUtilTest {

    /**
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        System.out.println(SystemUtil.FILE_ENCODING);
        System.out.println(SystemUtil.LINE_SEPARATOR);
        System.out.println(SystemUtil.PATH_SEPARATOR);
        System.out.println(SystemUtil.OS_NAME);
        System.out.println(SystemUtil.JAVA_IO_TMPDIR);
        System.out.println(SystemUtil.USER_DIR);
        System.out.println(SystemUtil.USER_HOME);
    }

}
