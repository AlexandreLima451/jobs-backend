package com.simplesdental.jobsbackend.repository;

import com.simplesdental.jobsbackend.model.entity.Contact;
import com.simplesdental.jobsbackend.model.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    @Query("SELECT p FROM Professional p WHERE p.name like '%' || :text || '%' or c.role like '%' || :text || '%' " +
            "or c.birthday like '%' || :text || '%' or c.create_date_time like '%' || :text || '%'")
    List<Professional> findByQueryParam(@Param("text") String text);
}
