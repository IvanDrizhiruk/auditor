package com.auditor.service;

import com.auditor.domain.InspectionItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InspectionItemsService {
    InspectionItem save(InspectionItem inspectionItems);

    Page<InspectionItem> findItemsByInspectionId(String inspectionId, Pageable pageable);

    InspectionItem findOne(String id);

    void delete(String id);
}
