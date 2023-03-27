package com.simplesdental.jobsbackend.service;

import com.simplesdental.jobsbackend.model.dto.ContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryFilterDto;

import java.util.List;

public interface ContactService {

    List<QueryContactDto> getByQuery(String text, QueryFilterDto queryFilterDto);
    QueryContactDto getById(Long id);
    QueryContactDto create(ContactDto dto);
    QueryContactDto update(Long id, ContactDto contactDto);
    QueryContactDto delete(Long id);
}
