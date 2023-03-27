package com.simplesdental.jobsbackend.service;

import com.simplesdental.jobsbackend.model.dto.*;

import java.util.List;
import java.util.Map;

public interface ProfessionalService {

    List<QueryProfessionalDto> getByQuery(String text, QueryFilterDto queryFilterDto);
    QueryProfessionalDto getById(Long id);
    QueryProfessionalDto create(ProfessionalDto dto);
    QueryProfessionalDto update(Long id, ProfessionalDto dto);
    QueryProfessionalDto delete(Long id);
}
