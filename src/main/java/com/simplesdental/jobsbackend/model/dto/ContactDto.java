package com.simplesdental.jobsbackend.model.dto;

import javax.validation.constraints.NotNull;

public class ContactDto extends DynamicQueryFilter {

    @NotNull
    private String name;

    @NotNull
    private String contact;

    @NotNull
    private Long professionalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Long professionalId) {
        this.professionalId = professionalId;
    }
}
