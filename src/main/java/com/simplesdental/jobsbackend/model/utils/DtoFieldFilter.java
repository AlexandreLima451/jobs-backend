package com.simplesdental.jobsbackend.model.utils;

import com.simplesdental.jobsbackend.model.dto.QueryFilterDto;
import com.simplesdental.jobsbackend.model.enums.CollectionInitializerEnum;

import java.lang.reflect.Field;
import java.util.*;

public abstract class DtoFieldFilter {

    private Map<String, Field> getAllFields(Map<String, Field> fields, Class<?> type) {

        var declaredFields = type.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            fields.put(declaredField.getName(), declaredField);
        }

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private void setNullOrEmpty(Field field) throws IllegalAccessException {
        var initializer = CollectionInitializerEnum.fromString(field.getType().getName());
        field.setAccessible(true);
        field.set(this, initializer.getEmptyCollection());
    }

    public void filterByFields(QueryFilterDto dto) throws IllegalAccessException {
        Map<String, Field> allFields = new HashMap<>();
        getAllFields(allFields, this.getClass());

        dto.getFields().forEach(allFields::remove);
        for (Field field : allFields.values()) {
            setNullOrEmpty(field);
        }
    }
}