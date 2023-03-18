package com.simplesdental.jobsbackend.model.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class QueryFieldFilter {

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private List<String> getFieldsName() {

        List<String> fieldNameList = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        List<Field> allFields = getAllFields(fields, this.getClass());
        for (Field field : allFields) {
            fieldNameList.add(field.getName());
        }

        return fieldNameList;
    }

    public List<String> getUnmatchedFieldsName(List<String> fields) {
        List<String> allFields = getFieldsName();
        List<String> fieldsToRemove = new ArrayList<>();

        for (String fieldName : allFields) {
            if(fields.contains(fieldName)) {
                fieldsToRemove.add(fieldName);
            }
        }
        allFields.removeAll(fieldsToRemove);

        return allFields;
    }
}