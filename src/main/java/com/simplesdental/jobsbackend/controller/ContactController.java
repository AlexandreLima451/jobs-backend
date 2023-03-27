package com.simplesdental.jobsbackend.controller;

import com.simplesdental.jobsbackend.model.dto.ContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryContactDto;
import com.simplesdental.jobsbackend.model.dto.QueryFilterDto;
import com.simplesdental.jobsbackend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ContactController {

    @Autowired
    private ContactService service;

    private final String CONTACT_BASE_PATH = "/contacts";

    @GetMapping(value = CONTACT_BASE_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QueryContactDto>> getByParam(@RequestParam(required = false) String text,
                                             @RequestBody(required = false) QueryFilterDto queryFilterDto) {

        List<QueryContactDto> queryContactDtos = new ArrayList<>();
        Optional<String> optionalText = Optional.ofNullable(text);
        if (optionalText.isPresent()) {
            queryContactDtos = service.getByQuery(text, queryFilterDto);
        }

        if (queryContactDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(queryContactDtos);
        }
    }

    @GetMapping(value = CONTACT_BASE_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryContactDto> getById(@PathVariable Long id) {
        QueryContactDto queryContactDto = service.getById(id);
        if (queryContactDto.isPresent()) {
            return ResponseEntity.ok(queryContactDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = CONTACT_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryContactDto> createContact(@Valid @RequestBody ContactDto contactDto) {
        QueryContactDto queryContactDto = service.create(contactDto);
        if (queryContactDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(queryContactDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(value = CONTACT_BASE_PATH + "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryContactDto> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDto contactDto) {
        QueryContactDto queryContactDto = service.update(id, contactDto);
        if (queryContactDto.isPresent()) {
            return ResponseEntity.ok(queryContactDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = CONTACT_BASE_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryContactDto> deleteContact(@PathVariable Long id) {
        QueryContactDto queryContactDto = service.delete(id);
        if (queryContactDto.isPresent()) {
            return ResponseEntity.ok(queryContactDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
