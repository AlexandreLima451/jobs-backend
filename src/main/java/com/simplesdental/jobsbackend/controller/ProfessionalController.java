package com.simplesdental.jobsbackend.controller;

import com.simplesdental.jobsbackend.model.dto.*;
import com.simplesdental.jobsbackend.service.ProfessionalService;
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
public class ProfessionalController {

    @Autowired
    private ProfessionalService service;

    private final String PROFESSIONAL_BASE_PATH = "/professionals";

    @GetMapping(value = PROFESSIONAL_BASE_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QueryProfessionalDto>> getByParam(@RequestParam(required = false) String text,
                                             @RequestBody(required = false) QueryFilterDto queryFilterDto) {

        List<QueryProfessionalDto> queryProfessionalsDto = new ArrayList<>();
        Optional<String> optionalText = Optional.ofNullable(text);
        if (optionalText.isPresent()) {
            queryProfessionalsDto = service.getByQuery(text, queryFilterDto);
        }

        if (queryProfessionalsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(queryProfessionalsDto);
        }
    }

    @GetMapping(value = PROFESSIONAL_BASE_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryProfessionalDto> getById(@PathVariable Long id) {
        QueryProfessionalDto queryProfessionalDto = service.getById(id);
        if (queryProfessionalDto.isPresent()) {
            return ResponseEntity.ok(queryProfessionalDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = PROFESSIONAL_BASE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryProfessionalDto> createProfessional(@Valid @RequestBody ProfessionalDto professionalDto) {
        QueryProfessionalDto queryProfessionalDto = service.create(professionalDto);
        if (queryProfessionalDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(queryProfessionalDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(value = PROFESSIONAL_BASE_PATH + "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryProfessionalDto> updateProfessional(@PathVariable Long id, @Valid @RequestBody ProfessionalDto professionalDto) {
        QueryProfessionalDto queryProfessionalDto = service.update(id, professionalDto);
        if (queryProfessionalDto.isPresent()) {
            return ResponseEntity.ok(queryProfessionalDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = PROFESSIONAL_BASE_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryProfessionalDto> deleteProfessional(@PathVariable Long id) {
        QueryProfessionalDto queryProfessionalDto = service.delete(id);
        if (queryProfessionalDto.isPresent()) {
            return ResponseEntity.ok(queryProfessionalDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
