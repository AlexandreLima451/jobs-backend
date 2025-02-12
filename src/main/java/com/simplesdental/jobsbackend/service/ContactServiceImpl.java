package com.simplesdental.jobsbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simplesdental.jobsbackend.model.dto.ContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryFilterDto;
import com.simplesdental.jobsbackend.model.entity.Contact;
import com.simplesdental.jobsbackend.model.entity.Professional;
import com.simplesdental.jobsbackend.repository.ContactRepository;
import com.simplesdental.jobsbackend.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Override
    public List<Map<String, Object>> getByQuery(String text, QueryFilterDto queryFilterDto) {
        List<Contact> contacts = contactRepository.findByQueryParam(text);
        if(contacts.isEmpty())
            return new ArrayList<>();

        List<String> unmatchedFieldsName = contacts.get(0).getUnmatchedFieldsName(queryFilterDto.getFields());

        return readJsonMapping(contacts, unmatchedFieldsName);
    }

    private List<Map<String, Object>> readJsonMapping(List<Contact> contacts, List<String> unmatchedFieldsName) {
        List<Map<String, Object>> jsonContacts = new ArrayList<>();
        String[] ignorableFieldNames = unmatchedFieldsName.toArray(new String[0]);

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("QueryFilter", SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));

        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter writer = objectMapper.writer(filters);
        try {
            for (Contact contact : contacts) {
                String json = writer.writeValueAsString(contact);

                Map<String, Object> jsonMapping = new ObjectMapper().readValue(json, HashMap.class);

                jsonContacts.add(jsonMapping);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonContacts;
    }

    @Override
    public QueryContactDto getById(Long id) {
        Optional<Contact> optional = contactRepository.findById(id);
        return optional.map(this::convertToQueryContactDto).orElseGet(QueryContactDto::new);
    }

    @Override
    public QueryContactDto create(ContactDto dto) {

        Optional<Professional> optional = professionalRepository.findById(dto.getProfessionalId());
        if (optional.isEmpty()) {
            return new QueryContactDto();
        }

        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setContact(dto.getContact());
        contact.setProfessional(optional.get());
        return convertToQueryContactDto(contactRepository.save(contact));
    }

    @Override
    public QueryContactDto update(Long id, ContactDto contactDto) {
        Optional<Contact> optional = contactRepository.findById(id);
        if (optional.isEmpty()) {
            return new QueryContactDto();
        }

        Contact contact = optional.get();
        Optional<Professional> optionalProfessional = Optional.empty();

        if (!contactDto.getProfessionalId().equals(contact.getProfessional().getId())) {
            optionalProfessional = professionalRepository.findById(contactDto.getProfessionalId());
            if (optionalProfessional.isEmpty()) {
                return new QueryContactDto();
            }
        }

        contact.setName(contactDto.getName());
        contact.setContact(contactDto.getContact());
        optionalProfessional.ifPresent(contact::setProfessional);

        contactRepository.save(contact);
        return convertToQueryContactDto(contact);
    }

    @Override
    public QueryContactDto delete(Long id) {
        Optional<Contact> optional = contactRepository.findById(id);
        if (optional.isEmpty()) {
            return new QueryContactDto();
        }

        Contact contact = optional.get();
        contactRepository.delete(contact);
        return convertToQueryContactDto(contact);
    }

    private QueryContactDto convertToQueryContactDto(Contact contact) {
        QueryContactDto queryContactDto = new QueryContactDto();
        queryContactDto.setId(contact.getId());
        queryContactDto.setName(contact.getName());
        queryContactDto.setContact(contact.getContact());
        queryContactDto.setProfessionalId(contact.getProfessional().getId());
        return queryContactDto;
    }

    private Contact convertToContact(ContactDto dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setContact(dto.getContact());
        if (dto.getProfessionalId() != null) {
            Optional<Professional> optional = professionalRepository.findById(dto.getProfessionalId());
            optional.ifPresent(contact::setProfessional);
        }
        return contact;
    }
}
