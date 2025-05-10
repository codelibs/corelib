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
package org.codelibs.core.beans.impl;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.FieldDesc;
import org.codelibs.core.beans.ParameterizedClassDesc;
import org.codelibs.core.beans.factory.ParameterizedClassDescFactory;
import org.codelibs.core.exception.FieldNotStaticRuntimeException;
import org.codelibs.core.lang.FieldUtil;

/**
 * Implementation class of {@link FieldDesc}.
 *
 * @author koichik
 */
public class FieldDescImpl implements FieldDesc {

    /** The {@link BeanDesc} of the class that owns this field */
    protected final BeanDesc beanDesc;

    /** The field */
    protected final Field field;

    /** The field name */
    protected final String fieldName;

    /** The type of the field */
    protected final Class<?> fieldType;

    /** Information about the parameterized type */
    protected final ParameterizedClassDesc parameterizedClassDesc;

    /**
     * Constructs an instance.
     *
     * @param beanDesc
     *            The {@link BeanDesc} of the class that owns this field. Must not be {@literal null}.
     * @param field
     *            The field. Must not be {@literal null}.
     */
    public FieldDescImpl(final BeanDesc beanDesc, final Field field) {
        assertArgumentNotNull("beanDesc", beanDesc);
        assertArgumentNotNull("field", field);

        this.beanDesc = beanDesc;
        this.field = field;
        fieldName = field.getName();
        fieldType = field.getType();
        parameterizedClassDesc = ParameterizedClassDescFactory.createParameterizedClassDesc(field, beanDesc.getTypeVariables());
    }

    @Override
    public BeanDesc getBeanDesc() {
        return beanDesc;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<T> getFieldType() {
        return (Class<T>) fieldType;
    }

    @Override
    public boolean isPublic() {
        return FieldUtil.isPublicField(field);
    }

    @Override
    public boolean isStatic() {
        return !FieldUtil.isInstanceField(field);
    }

    @Override
    public boolean isFinal() {
        return FieldUtil.isFinalField(field);
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
        if (!Collection.class.isAssignableFrom(fieldType) || !isParameterized()) {
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
        if (!Map.class.isAssignableFrom(fieldType) || !isParameterized()) {
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
        if (!Map.class.isAssignableFrom(fieldType) || !isParameterized()) {
            return null;
        }
        final ParameterizedClassDesc pcd = parameterizedClassDesc.getArguments()[1];
        if (pcd == null) {
            return null;
        }
        return pcd.getRawClass();
    }

    @Override
    public <T> T getFieldValue(final Object target) {
        assertArgumentNotNull("target", target);

        return FieldUtil.get(field, target);
    }

    @Override
    public <T> T getStaticFieldValue() {
        if (!isStatic()) {
            throw new FieldNotStaticRuntimeException(beanDesc.getBeanClass(), fieldName);
        }
        return FieldUtil.get(field);
    }

    @Override
    public void setFieldValue(final Object target, final Object value) {
        assertArgumentNotNull("target", target);

        FieldUtil.set(field, target, value);
    }

    @Override
    public void setStaticFieldValue(final Object value) {
        if (!isStatic()) {
            throw new FieldNotStaticRuntimeException(beanDesc.getBeanClass(), fieldName);
        }
        FieldUtil.set(field, value);
    }

}
