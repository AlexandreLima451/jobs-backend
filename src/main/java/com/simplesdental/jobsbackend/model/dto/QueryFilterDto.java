package com.simplesdental.jobsbackend.model.dto;

import java.util.List;

public class QueryFilterDto {

    private List<String> fields;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
