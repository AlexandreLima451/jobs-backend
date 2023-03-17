package com.simplesdental.jobsbackend.repository;

import com.simplesdental.jobsbackend.model.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
