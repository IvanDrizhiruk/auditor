package com.auditor.service;

import com.auditor.service.dto.InspectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Inspection.
 */
public interface InspectionService {

    /**
     * Save a inspection.
     *
     * @param inspectionDTO the entity to save
     * @return the persisted entity
     */
    InspectionDTO save(InspectionDTO inspectionDTO);

    /**
     *  Get all the inspections.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<InspectionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" inspection.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InspectionDTO findOne(String id);

    /**
     *  Delete the "id" inspection.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
