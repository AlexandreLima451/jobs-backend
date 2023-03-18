package com.simplesdental.jobsbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simplesdental.jobsbackend.model.dto.ProfessionalDto;
import com.simplesdental.jobsbackend.model.dto.QueryContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryFilterDto;
import com.simplesdental.jobsbackend.model.dto.QueryProfessionalDto;
import com.simplesdental.jobsbackend.model.entity.Contact;
import com.simplesdental.jobsbackend.model.entity.Professional;
import com.simplesdental.jobsbackend.repository.ContactRepository;
import com.simplesdental.jobsbackend.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    @Autowired
    private ProfessionalRepository repository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Map<String, Object>> getByQuery(String text, QueryFilterDto queryFilterDto) {

        List<Professional> professionals = repository.findByQueryParam(text);
        if(professionals.isEmpty())
            return new ArrayList<>();

        List<String> unmatchedFieldsName = professionals.get(0).getUnmatchedFieldsName(queryFilterDto.getFields());

        return readJsonMapping(professionals, unmatchedFieldsName);
    }

    private List<Map<String, Object>> readJsonMapping(List<Professional> professionals, List<String> unmatchedFieldsName) {
        List<Map<String, Object>> jsonProfessionals = new ArrayList<>();
        String[] ignorableFieldNames = unmatchedFieldsName.toArray(new String[0]);

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("QueryFilter", SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));

        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter writer = objectMapper.writer(filters);
        try {
            for (Professional professional : professionals) {
                String json = writer.writeValueAsString(professional);

                Map<String, Object> jsonMapping = new ObjectMapper().readValue(json, HashMap.class);

                jsonProfessionals.add(jsonMapping);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonProfessionals;
    }


    @Override
    public QueryProfessionalDto getById(Long id) {

        Optional<Professional> optional = repository.findById(id);
        return optional.map(this::convertToQueryProfessionalDto).orElseGet(QueryProfessionalDto::new);
    }

    @Override
    public QueryProfessionalDto create(ProfessionalDto dto) {

        Professional professional = new Professional();
        professional.setName(dto.getName());
        professional.setRole(dto.getRole());
        professional.setBirthday(dto.getBirthday());
        professional.setCreateDateTime(LocalDateTime.now());

        Professional professionalCreated = repository.save(professional);

        dto.getContacts().forEach(queryContactDto -> {
            Contact contactForProfessional = createContactForProfessional(professional, queryContactDto);
            professionalCreated
                .addContact(contactForProfessional);});
        return convertToQueryProfessionalDto(repository.save(professionalCreated));
    }

    private Contact createContactForProfessional(Professional professional, QueryContactDto queryContactDto) {
        Contact contact = new Contact();
        contact.setName(queryContactDto.getName());
        contact.setContact(queryContactDto.getContact());
        contact.setProfessional(professional);
        return contactRepository.save(contact);
    }

    @Override
    public QueryProfessionalDto update(Long id, ProfessionalDto dto) {
        Optional<Professional> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return new QueryProfessionalDto();
        }

        Professional professional = optional.get();
        professional.setName(dto.getName());
        professional.setRole(dto.getRole());
        professional.setBirthday(dto.getBirthday());
        dto.getContacts().forEach(queryContactDto -> {
            Contact contact;
            if(queryContactDto.getId() != null) {
                contact = convertDtoToContact(professional.getId(), queryContactDto);
            } else {
                contact = createContactForProfessional(professional, queryContactDto);
            }
            professional.addContact(contact);
        });

        repository.save(professional);
        return convertToQueryProfessionalDto(professional);
    }

    @Override
    public QueryProfessionalDto delete(Long id) {

        Optional<Professional> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return new QueryProfessionalDto();
        }

        Professional professional = optional.get();
        repository.delete(professional);
        return convertToQueryProfessionalDto(professional);
    }

    private Contact convertDtoToContact (Long professionalId, QueryContactDto dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setContact(dto.getContact());

        Optional<Professional> optional = repository.findById(professionalId);
        optional.ifPresent(contact::setProfessional);
        return contact;
    }

    private QueryProfessionalDto convertToQueryProfessionalDto(Professional professional) {
        QueryProfessionalDto dto = new QueryProfessionalDto();
        dto.setId(professional.getId());
        dto.setName(professional.getName());
        dto.setRole(professional.getRole());
        dto.setBirthday(professional.getBirthday());
        dto.setCreateDateTime(professional.getCreateDateTime());
        for (Contact contact : professional.getContacts()) {
            dto.addContact(convertToQueryContactDto(contact));
        }
        return dto;
    }

    private QueryContactDto convertToQueryContactDto(Contact contact) {
        QueryContactDto queryContactDto = new QueryContactDto();
        queryContactDto.setId(contact.getId());
        queryContactDto.setName(contact.getName());
        queryContactDto.setContact(contact.getContact());
        queryContactDto.setProfessionalId(contact.getProfessional().getId());
        return queryContactDto;
    }


}
