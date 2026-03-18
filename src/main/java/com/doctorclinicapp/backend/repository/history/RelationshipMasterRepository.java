package com.doctorclinicapp.backend.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorclinicapp.backend.model.history.RelationshipMaster;

@Repository
public interface RelationshipMasterRepository
        extends JpaRepository<RelationshipMaster, Long> {
}