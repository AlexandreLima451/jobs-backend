package com.simplesdental.jobsbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class QueryProfessionalDto extends ProfessionalDto {

    @JsonInclude(NON_NULL)
    private Long id;

    @JsonInclude(NON_NULL)
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
