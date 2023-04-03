package com.simplesdental.jobsbackend.model.dto;

import com.simplesdental.jobsbackend.model.enums.Role;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ProfessionalDto {

    @NotNull
    private String name;

    @NotNull
    private Role role;

    @NotNull
    private LocalDate birthday;

    private final List<QueryContactDto> contacts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<QueryContactDto> getContacts() {
        return unmodifiableList(contacts);
    }

    public void addContact(QueryContactDto contact) {
        this.contacts.add(contact);
    }

    public void removeContact(QueryContactDto contact) {
        this.contacts.remove(contact);
    }
}
