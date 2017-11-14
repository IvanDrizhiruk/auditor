package com.auditor.repository;

import com.auditor.domain.Inspection;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
@Repository
public interface InspectionRepository extends MongoRepository<Inspection, String> {}
