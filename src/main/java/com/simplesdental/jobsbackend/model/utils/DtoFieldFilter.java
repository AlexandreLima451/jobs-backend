package com.simplesdental.jobsbackend.model.utils;

import com.simplesdental.jobsbackend.model.dto.QueryFilterDto;
import com.simplesdental.jobsbackend.model.enums.CollectionInitializerEnum;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public abstract class DtoFieldFilter {

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private void setNullOrEmpty(Field field) throws IllegalAccessException {
        CollectionInitializerEnum initializer = CollectionInitializerEnum.fromString(field.getType().getName());
        field.setAccessible(true);
        field.set(this, initializer.getEmptyCollection());
    }

    public void filterByFields(QueryFilterDto dto) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>();
        Map<String, Field> allFields = getAllFields(fields, this.getClass())
                .stream()
                .collect(Collectors.toMap(Field::getName, identity()));

        dto.getFields().forEach(allFields::remove);
        for (Field field : allFields.values()) {
            setNullOrEmpty(field);
        }
    }
}