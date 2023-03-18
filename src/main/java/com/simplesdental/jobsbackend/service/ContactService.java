package com.simplesdental.jobsbackend.service;

import com.simplesdental.jobsbackend.model.dto.ContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryFilterDto;

import java.util.List;
import java.util.Map;

public interface ContactService {

    List<Map<String, Object>> getByQuery(String text, QueryFilterDto queryFilterDto);
    QueryContactDto getById(Long id);
    QueryContactDto create(ContactDto dto);
    QueryContactDto update(Long id, ContactDto contactDto);
    QueryContactDto delete(Long id);
}
