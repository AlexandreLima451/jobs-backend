package com.simplesdental.jobsbackend.repository;

import com.simplesdental.jobsbackend.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.name like '%' || :text || '%' or c.contact like '%' || :text || '%' ")
    List<Contact> findByQueryParam(@Param("text") String text);
}
