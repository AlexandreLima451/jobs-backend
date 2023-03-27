package com.simplesdental.jobsbackend.service;

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
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<QueryProfessionalDto> getByQuery(String text, QueryFilterDto queryFilterDto) {

        List<Professional> professionals = professionalRepository.findByQueryParam(text);
        if(professionals.isEmpty())
            return new ArrayList<>();

        List<QueryProfessionalDto> professionalDtoList = new ArrayList<>();
        professionals.forEach(professional -> {
            try {
                QueryProfessionalDto dto = convertToQueryProfessionalDto(professional);
                dto.filterByFields(queryFilterDto);
                professionalDtoList.add(dto);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return professionalDtoList;
    }


    @Override
    public QueryProfessionalDto getById(Long id) {

        Optional<Professional> optional = professionalRepository.findById(id);
        return optional.map(this::convertToQueryProfessionalDto).orElseGet(QueryProfessionalDto::new);
    }

    @Override
    public QueryProfessionalDto create(ProfessionalDto dto) {

        Professional professional = new Professional();
        professional.setName(dto.getName());
        professional.setRole(dto.getRole());
        professional.setBirthday(dto.getBirthday());
        professional.setCreateDateTime(LocalDateTime.now());

        Professional professionalCreated = professionalRepository.save(professional);

        dto.getContacts().forEach(queryContactDto -> {
            Contact contactForProfessional = createContactForProfessional(professional, queryContactDto);
            contactRepository.save(contactForProfessional);
            professionalCreated
                .addContact(contactForProfessional);});
        return convertToQueryProfessionalDto(professionalRepository.save(professionalCreated));
    }

    private Contact createContactForProfessional(Professional professional, QueryContactDto queryContactDto) {
        Contact contact = new Contact();
        contact.setName(queryContactDto.getName());
        contact.setContact(queryContactDto.getContact());
        contact.setProfessional(professional);
        return contact;
    }

    @Override
    public QueryProfessionalDto update(Long id, ProfessionalDto dto) {
        Optional<Professional> optional = professionalRepository.findById(id);
        if (optional.isEmpty()) {
            return new QueryProfessionalDto();
        }

        Professional professional = optional.get();
        professional.setName(dto.getName());
        professional.setRole(dto.getRole());
        professional.setBirthday(dto.getBirthday());
        for (QueryContactDto queryContactDto : dto.getContacts()) {
            Contact contact;
            if (queryContactDto.getId() != null) {
                contact = convertDtoToContact(professional.getId(), queryContactDto);
            } else {
                continue;
            }
            contactRepository.save(contact);
            professional.addContact(contact);
        }

        Professional professionalUpdated = professionalRepository.save(professional);

        return convertToQueryProfessionalDto(professionalUpdated);
    }

    @Override
    public QueryProfessionalDto delete(Long id) {

        Optional<Professional> optional = professionalRepository.findById(id);
        if (optional.isEmpty()) {
            return new QueryProfessionalDto();
        }

        Professional professional = optional.get();
        professionalRepository.delete(professional);
        return convertToQueryProfessionalDto(professional);
    }

    private Contact convertDtoToContact (Long professionalId, QueryContactDto dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        contact.setName(dto.getName());
        contact.setContact(dto.getContact());

        Optional<Professional> optional = professionalRepository.findById(professionalId);
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
