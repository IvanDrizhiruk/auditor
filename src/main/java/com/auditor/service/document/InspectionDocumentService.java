package com.auditor.service.document;

import com.auditor.domain.InspectionDocument;

import java.util.List;

public interface InspectionDocumentService {
    void insert(InspectionDocument document);
    void insert(List<InspectionDocument> documents);
}
