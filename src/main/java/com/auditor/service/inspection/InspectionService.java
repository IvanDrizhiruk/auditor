package com.auditor.service.inspection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InspectionService {
    InspectionDTO save(InspectionDTO inspectionDTO);
    Page<InspectionDTO> findAll(Pageable pageable);
    InspectionDTO findOne(String id);
    void delete(String id);
}
