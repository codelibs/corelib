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
package org.codelibs.core.lang;

import static org.codelibs.core.collection.ArrayUtil.asArray;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.codelibs.core.exception.ClIllegalArgumentException;
import org.codelibs.core.exception.IllegalAccessRuntimeException;

/**
 * {@link Field}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class FieldUtil {

    /**
     * {@link Field}によって表される{@code static}フィールドの値を返します。
     *
     * @param <T>
     *            フィールドの型
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return {@code static}フィールドで表現される値
     * @throws IllegalAccessRuntimeException
     *             基本となるフィールドにアクセスできない場合
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(final Field field) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        return (T) get(field, null);
    }

    /**
     * 指定されたオブジェクトについて、{@link Field}によって表されるフィールドの値を返します。
     *
     * @param <T>
     *            フィールドの型
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @param target
     *            表現されるフィールド値の抽出元オブジェクト。フィールドが{@literal static}の場合は
     *            {@literal null}
     * @return オブジェクト{@code obj}内で表現される値
     * @throws IllegalAccessRuntimeException
     *             基本となるフィールドにアクセスできない場合
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(final Field field, final Object target) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            return (T) field.get(target);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), ex);
        }
    }

    /**
     * {@literal static}な {@link Field}の値をintとして取得します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return フィールドの値
     * @throws IllegalAccessRuntimeException
     *             {@link IllegalAccessException}が発生した場合
     * @see #getInt(Field, Object)
     */
    public static int getInt(final Field field) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        return getInt(field, null);
    }

    /**
     * {@link Field}の値をintとして取得します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @param target
     *            ターゲットオブジェクト。フィールドが{@literal static}の場合は{@literal null}
     * @return フィールドの値
     * @throws IllegalAccessRuntimeException
     *             {@link IllegalAccessException}が発生した場合
     * @see Field#getInt(Object)
     */
    public static int getInt(final Field field, final Object target) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            return field.getInt(target);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), ex);
        }
    }

    /**
     * {@literal static}な {@link Field}の値を {@link String}として取得します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return フィールドの値
     * @throws IllegalAccessRuntimeException
     *             {@link IllegalAccessException}が発生した場合
     * @see #getString(Field, Object)
     */
    public static String getString(final Field field) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        return getString(field, null);
    }

    /**
     * {@link Field}の値を {@link String}として取得します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @param target
     *            ターゲットオブジェクト。フィールドが{@literal static}の場合は{@literal null}
     * @return フィールドの値
     * @throws IllegalAccessRuntimeException
     *             {@link IllegalAccessException}が発生した場合
     * @see Field#get(Object)
     */
    public static String getString(final Field field, final Object target) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            return (String) field.get(target);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), ex);
        }
    }

    /**
     * {@link Field}オブジェクトによって表される{@code static}フィールドを、指定された新しい値に設定します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @param value
     *            {@literal static}フィールドの新しい値
     * @throws IllegalAccessRuntimeException
     *             基本となるフィールドにアクセスできない場合
     * @see Field#set(Object, Object)
     */
    public static void set(final Field field, final Object value) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        set(field, null, value);
    }

    /**
     * {@link Field}オブジェクトによって表される指定されたオブジェクト引数のフィールドを、指定された新しい値に設定します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @param target
     *            フィールドを変更するオブジェクト。フィールドが{@literal static}の場合は{@literal null}
     * @param value
     *            変更中の{@code target}の新しいフィールド値
     * @throws IllegalAccessRuntimeException
     *             基本となるフィールドにアクセスできない場合
     * @see Field#set(Object, Object)
     */
    public static void set(final Field field, final Object target, final Object value) throws IllegalAccessRuntimeException {
        assertArgumentNotNull("field", field);

        try {
            field.set(target, value);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(field.getDeclaringClass(), e);
        } catch (final IllegalArgumentException e) {
            final Class<?> clazz = field.getDeclaringClass();
            final Class<?> fieldClass = field.getType();
            final Class<?> valueClass = value == null ? null : value.getClass();
            final Class<?> targetClass = target == null ? field.getDeclaringClass() : target.getClass();
            throw new ClIllegalArgumentException("field", "ECL0094",
                    asArray(clazz.getName(), clazz.getClassLoader(), fieldClass.getName(), fieldClass.getClassLoader(), field.getName(),
                            valueClass == null ? null : valueClass.getName(), valueClass == null ? null : valueClass.getClassLoader(),
                            value, targetClass == null ? null : targetClass.getName(),
                            targetClass == null ? null : targetClass.getClassLoader()),
                    e);
        }
    }

    /**
     * インスタンスフィールドかどうか返します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return インスタンスフィールドなら{@literal true}
     */
    public static boolean isInstanceField(final Field field) {
        assertArgumentNotNull("field", field);

        return !Modifier.isStatic(field.getModifiers());
    }

    /**
     * パブリックフィールドかどうか返します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return パブリックフィールドなら{@literal true}
     */
    public static boolean isPublicField(final Field field) {
        assertArgumentNotNull("field", field);

        return Modifier.isPublic(field.getModifiers());
    }

    /**
     * ファイナルフィールドかどうか返します。
     *
     * @param field
     *            フィールド。{@literal null}であってはいけません
     * @return ファイナルフィールドなら{@literal true}
     */
    public static boolean isFinalField(final Field field) {
        assertArgumentNotNull("field", field);

        return Modifier.isFinal(field.getModifiers());
    }

    /**
     * パラメタ化されたコレクション型のフィールドの要素型を返します。
     *
     * @param field
     *            パラメタ化されたコレクション型のフィールド。{@literal null}であってはいけません
     * @return コレクションの要素型
     */
    public static Class<?> getElementTypeOfCollection(final Field field) {
        assertArgumentNotNull("field", field);

        final Type type = field.getGenericType();
        return GenericsUtil.getRawClass(GenericsUtil.getElementTypeOfCollection(type));
    }

    /**
     * パラメタ化されたマップ型のフィールドのキー型を返します。
     *
     * @param field
     *            パラメタ化されたマップ型のフィールド。{@literal null}であってはいけません
     * @return マップのキー型
     */
    public static Class<?> getKeyTypeOfMap(final Field field) {
        assertArgumentNotNull("field", field);

        final Type type = field.getGenericType();
        return GenericsUtil.getRawClass(GenericsUtil.getKeyTypeOfMap(type));
    }

    /**
     * パラメタ化されたマップ型のフィールドの値型を返します。
     *
     * @param field
     *            パラメタ化されたマップ型のフィールド。{@literal null}であってはいけません
     * @return マップの値型
     */
    public static Class<?> getValueTypeOfMap(final Field field) {
        assertArgumentNotNull("field", field);

        final Type type = field.getGenericType();
        return GenericsUtil.getRawClass(GenericsUtil.getValueTypeOfMap(type));
    }

}
