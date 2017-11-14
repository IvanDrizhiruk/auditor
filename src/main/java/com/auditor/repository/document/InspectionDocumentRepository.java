package com.auditor.repository.document;

import com.auditor.domain.InspectionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InspectionDocumentRepository extends MongoRepository<InspectionDocument, String> {}
