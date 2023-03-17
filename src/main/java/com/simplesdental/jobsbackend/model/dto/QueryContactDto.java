package com.simplesdental.jobsbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QueryContactDto extends ContactDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Boolean isPresent() {
        return getId() != null;
    }
}
