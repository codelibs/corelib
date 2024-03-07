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
package org.codelibs.core;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

/**
 * @author koichik
 */
public abstract class TestUtil {

    /**
     * 実際の値が期待する{@literal clazz}と等しいかを検証する{@link Matcher}を返します。
     *
     * <pre>assertThat(clazz, is(Xxx.class));</pre>
     * <p>
     * と書くと{@link CoreMatchers#is(Class)}が適用されて{@literal clazz}が {@literal Xxx}
     * のインスタンスかを検証されてしまうのでこれが必要。
     *
     * @param clazz
     *            期待するクラス
     * @return クラスをチェックする{@link Matcher}
     */
    public static Matcher<Object> sameClass(final Class<?> clazz) {
        return is((Object) clazz);
    }

}
