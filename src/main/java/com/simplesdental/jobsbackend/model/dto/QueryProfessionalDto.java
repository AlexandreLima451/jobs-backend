package com.simplesdental.jobsbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplesdental.jobsbackend.model.entity.Contact;
import com.simplesdental.jobsbackend.model.enums.Role;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class QueryProfessionalDto extends ProfessionalDto {

    private Long id;

    private LocalDateTime createDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }
    @JsonIgnore
    public Boolean isPresent() {
        return getId() != null;
    }
}
