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
package org.codelibs.core.beans.impl;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;
import static org.codelibs.core.misc.AssertionUtil.assertState;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.ParameterizedClassDesc;
import org.codelibs.core.beans.PropertyDesc;
import org.codelibs.core.beans.factory.ParameterizedClassDescFactory;
import org.codelibs.core.convert.BooleanConversionUtil;
import org.codelibs.core.convert.CalendarConversionUtil;
import org.codelibs.core.convert.DateConversionUtil;
import org.codelibs.core.convert.NumberConversionUtil;
import org.codelibs.core.convert.TimeConversionUtil;
import org.codelibs.core.convert.TimestampConversionUtil;
import org.codelibs.core.exception.ClIllegalArgumentException;
import org.codelibs.core.exception.IllegalPropertyRuntimeException;
import org.codelibs.core.exception.ParseRuntimeException;
import org.codelibs.core.lang.ConstructorUtil;
import org.codelibs.core.lang.FieldUtil;
import org.codelibs.core.lang.MethodUtil;
import org.codelibs.core.lang.ModifierUtil;

/**
 * {@link PropertyDesc}の実装クラスです。
 *
 * @author higa
 */
public class PropertyDescImpl implements PropertyDesc {

    private static final Object[] EMPTY_ARGS = new Object[0];

    private final String propertyName;

    private final Class<?> propertyType;

    private Method readMethod;

    private Method writeMethod;

    private Field field;

    private final BeanDesc beanDesc;

    private Constructor<?> stringConstructor;

    private Method valueOfMethod;

    private boolean readable = false;

    private boolean writable = false;

    private ParameterizedClassDesc parameterizedClassDesc;

    /**
     * {@link PropertyDescImpl}を作成します。
     *
     * @param propertyName
     *            プロパティ名。{@literal null}や空文字列であってはいけません
     * @param propertyType
     *            プロパティの型。{@literal null}であってはいけません
     * @param readMethod
     *            getterメソッド
     * @param writeMethod
     *            setterメソッド
     * @param beanDesc
     *            {@link BeanDesc}。{@literal null}であってはいけません
     */
    public PropertyDescImpl(final String propertyName, final Class<?> propertyType, final Method readMethod, final Method writeMethod,
            final BeanDesc beanDesc) {
        this(propertyName, propertyType, readMethod, writeMethod, null, beanDesc);
    }

    /**
     * {@link PropertyDescImpl}を作成します。
     *
     * @param propertyName
     *            プロパティ名。{@literal null}や空文字列であってはいけません
     * @param propertyType
     *            プロパティの型。{@literal null}であってはいけません
     * @param readMethod
     *            getterメソッド
     * @param writeMethod
     *            setterメソッド
     * @param field
     *            フィールド
     * @param beanDesc
     *            {@link BeanDesc}。{@literal null}であってはいけません
     */
    public PropertyDescImpl(final String propertyName, final Class<?> propertyType, final Method readMethod, final Method writeMethod,
            final Field field, final BeanDesc beanDesc) {
        assertArgumentNotEmpty("propertyName", propertyName);
        assertArgumentNotNull("propertyType", propertyType);
        assertArgumentNotNull("beanDesc", beanDesc);

        this.propertyName = propertyName;
        this.propertyType = propertyType;
        setReadMethod(readMethod);
        setWriteMethod(writeMethod);
        setField(field);
        this.beanDesc = beanDesc;
        setupStringConstructor();
        setupValueOfMethod();
        setUpParameterizedClassDesc();
    }

    private void setupStringConstructor() {
        for (final Constructor<?> con : propertyType.getConstructors()) {
            if (con.getParameterTypes().length == 1 && con.getParameterTypes()[0].equals(String.class)) {
                stringConstructor = con;
                break;
            }
        }
    }

    private void setupValueOfMethod() {
        for (final Method method : propertyType.getMethods()) {
            if (method.isBridge() || method.isSynthetic()) {
                continue;
            }
            if (ModifierUtil.isStatic(method.getModifiers()) && method.getName().equals("valueOf")
                    && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].equals(String.class)) {
                valueOfMethod = method;
                break;
            }
        }
    }

    private void setUpParameterizedClassDesc() {
        final Map<TypeVariable<?>, Type> typeVariables = ((BeanDescImpl) beanDesc).getTypeVariables();
        if (field != null) {
            parameterizedClassDesc = ParameterizedClassDescFactory.createParameterizedClassDesc(field, typeVariables);
        } else if (readMethod != null) {
            parameterizedClassDesc = ParameterizedClassDescFactory.createParameterizedClassDesc(readMethod, typeVariables);
        } else if (writeMethod != null) {
            parameterizedClassDesc = ParameterizedClassDescFactory.createParameterizedClassDesc(writeMethod, 0, typeVariables);
        }
    }

    @Override
    public final String getPropertyName() {
        return propertyName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> Class<T> getPropertyType() {
        return (Class<T>) propertyType;
    }

    @Override
    public final Method getReadMethod() {
        return readMethod;
    }

    /**
     * getterメソッドを設定します。
     *
     * @param readMethod
     *            getterメソッド
     */
    protected final void setReadMethod(final Method readMethod) {
        this.readMethod = readMethod;
        if (readMethod != null) {
            readable = true;
            readMethod.setAccessible(true);
        }
    }

    @Override
    public final boolean hasReadMethod() {
        return readMethod != null;
    }

    @Override
    public final Method getWriteMethod() {
        return writeMethod;
    }

    /**
     * setterメソッドを設定します。
     *
     * @param writeMethod
     *            setterメソッド
     */
    protected final void setWriteMethod(final Method writeMethod) {
        this.writeMethod = writeMethod;
        if (writeMethod != null) {
            writable = true;
            writeMethod.setAccessible(true);
        }
    }

    @Override
    public final boolean hasWriteMethod() {
        return writeMethod != null;
    }

    @Override
    public Field getField() {
        return field;
    }

    /**
     * プロパティとして認識しているpublicフィールドを設定します。
     *
     * @param field
     *            プロパティとして認識するpublicフィールド
     */
    public void setField(final Field field) {
        this.field = field;
        if (field != null && ModifierUtil.isPublic(field)) {
            readable = true;
            writable = true;
        }
    }

    @Override
    public boolean isReadable() {
        return readable;
    }

    @Override
    public boolean isWritable() {
        return writable;
    }

    @Override
    public <T> T getValue(final Object target) {
        assertArgumentNotNull("target", target);

        try {
            assertState(readable, propertyName + " is not readable.");
            if (hasReadMethod()) {
                return MethodUtil.invoke(readMethod, target, EMPTY_ARGS);
            }
            return FieldUtil.get(field, target);
        } catch (final Throwable t) {
            throw new IllegalPropertyRuntimeException(beanDesc.getBeanClass(), propertyName, t);
        }
    }

    @Override
    public void setValue(final Object target, final Object value) {
        assertArgumentNotNull("target", target);

        try {
            final Object convertedValue = convertIfNeed(value);
            assertState(writable, propertyName + " is not writable.");
            if (hasWriteMethod()) {
                try {
                    MethodUtil.invoke(writeMethod, target, convertedValue);
                } catch (final Throwable t) {
                    final Class<?> clazz = writeMethod.getDeclaringClass();
                    final Class<?> valueClass = convertedValue == null ? null : convertedValue.getClass();
                    final Class<?> targetClass = target == null ? null : target.getClass();
                    throw new ClIllegalArgumentException("target", "ECL0098", new Object[] { clazz.getName(), clazz.getClassLoader(),
                            propertyType.getName(), propertyType.getClassLoader(), propertyName,
                            valueClass == null ? null : valueClass.getName(), valueClass == null ? null : valueClass.getClassLoader(),
                            convertedValue, targetClass == null ? null : targetClass.getName(),
                            targetClass == null ? null : targetClass.getClassLoader() }).initCause(t);
                }
            } else {
                FieldUtil.set(field, target, convertedValue);
            }
        } catch (final Throwable t) {
            throw new IllegalPropertyRuntimeException(beanDesc.getBeanClass(), propertyName, t);
        }
    }

    @Override
    public BeanDesc getBeanDesc() {
        return beanDesc;
    }

    @Override
    public final String toString() {
        final StringBuilder buf = new StringBuilder(256);
        buf.append("propertyName=").append(propertyName).append(",propertyType=").append(propertyType.getName()).append(",readMethod=")
                .append(readMethod != null ? readMethod.getName() : "null").append(",writeMethod=")
                .append(writeMethod != null ? writeMethod.getName() : "null");
        return new String(buf);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convertIfNeed(final Object arg) {
        if (propertyType.isPrimitive()) {
            return (T) convertPrimitiveWrapper(arg);
        } else if (Number.class.isAssignableFrom(propertyType)) {
            return (T) convertNumber(arg);
        } else if (java.util.Date.class.isAssignableFrom(propertyType)) {
            return (T) convertDate(arg);
        } else if (Boolean.class.isAssignableFrom(propertyType)) {
            return (T) BooleanConversionUtil.toBoolean(arg);
        } else if (arg != null && arg.getClass() != String.class && String.class == propertyType) {
            return (T) arg.toString();
        } else if (arg instanceof String && !String.class.equals(propertyType)) {
            return (T) convertWithString(arg);
        } else if (java.util.Calendar.class.isAssignableFrom(propertyType)) {
            return (T) CalendarConversionUtil.toCalendar(arg);
        }
        return (T) arg;
    }

    private Object convertPrimitiveWrapper(final Object arg) {
        return NumberConversionUtil.convertPrimitiveWrapper(propertyType, arg);
    }

    private Object convertNumber(final Object arg) {
        return NumberConversionUtil.convertNumber(propertyType, arg);
    }

    private Object convertDate(final Object arg) {
        if (propertyType == java.util.Date.class) {
            try {
                return TimestampConversionUtil.toDate(arg);
            } catch (final ParseRuntimeException ex) {
                try {
                    return DateConversionUtil.toDate(arg);
                } catch (final ParseRuntimeException ex2) {
                    return TimeConversionUtil.toDate(arg);
                }
            }
        } else if (propertyType == Timestamp.class) {
            return TimestampConversionUtil.toSqlTimestamp(arg);
        } else if (propertyType == java.sql.Date.class) {
            return DateConversionUtil.toSqlDate(arg);
        } else if (propertyType == Time.class) {
            return TimeConversionUtil.toSqlTime(arg);
        }
        return arg;
    }

    private Object convertWithString(final Object arg) {
        if (stringConstructor != null) {
            return ConstructorUtil.newInstance(stringConstructor, arg);
        }
        if (valueOfMethod != null) {
            return MethodUtil.invoke(valueOfMethod, null, arg);
        }
        return arg;
    }

    @Override
    public boolean isParameterized() {
        return parameterizedClassDesc != null && parameterizedClassDesc.isParameterizedClass();
    }

    @Override
    public ParameterizedClassDesc getParameterizedClassDesc() {
        return parameterizedClassDesc;
    }

    @Override
    public Class<?> getElementClassOfCollection() {
        if (!Collection.class.isAssignableFrom(propertyType) || !isParameterized()) {
            return null;
        }
        final ParameterizedClassDesc pcd = parameterizedClassDesc.getArguments()[0];
        if (pcd == null) {
            return null;
        }
        return pcd.getRawClass();
    }

    @Override
    public Class<?> getKeyClassOfMap() {
        if (!Map.class.isAssignableFrom(propertyType) || !isParameterized()) {
            return null;
        }
        final ParameterizedClassDesc pcd = parameterizedClassDesc.getArguments()[0];
        if (pcd == null) {
            return null;
        }
        return pcd.getRawClass();
    }

    @Override
    public Class<?> getValueClassOfMap() {
        if (!Map.class.isAssignableFrom(propertyType) || !isParameterized()) {
            return null;
        }
        final ParameterizedClassDesc pcd = parameterizedClassDesc.getArguments()[1];
        if (pcd == null) {
            return null;
        }
        return pcd.getRawClass();
    }

}
