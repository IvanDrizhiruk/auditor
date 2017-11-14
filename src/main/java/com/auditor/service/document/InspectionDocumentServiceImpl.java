package com.auditor.service.document;

import com.auditor.domain.InspectionDocument;
import com.auditor.repository.document.InspectionDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionDocumentServiceImpl implements InspectionDocumentService {
    @Autowired
    InspectionDocumentRepository documentRepository;

    @Override
    public void insert(InspectionDocument document) {
        documentRepository.insert(document);
    }

    @Override
    public void insert(List<InspectionDocument> documents) {
        documentRepository.insert(documents);
    }
}
