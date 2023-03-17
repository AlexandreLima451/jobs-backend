package com.simplesdental.jobsbackend.model.dto;

import com.simplesdental.jobsbackend.model.entity.Contact;
import com.simplesdental.jobsbackend.model.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProfessionalDto {

    private Long id;

    private String name;

    private Role role;

    private LocalDate birthday;

    private LocalDateTime createDateTime;

    private List<Contact> contacts;
}
