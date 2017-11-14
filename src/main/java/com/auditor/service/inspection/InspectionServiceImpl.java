package com.auditor.service.inspection;

import com.auditor.domain.Inspection;
import com.auditor.repository.InspectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Inspection.
 */
@Service
public class InspectionServiceImpl implements InspectionService{

    private final Logger log = LoggerFactory.getLogger(InspectionServiceImpl.class);

    private final InspectionRepository inspectionRepository;

    private final InspectionMapper inspectionMapper;

    public InspectionServiceImpl(InspectionRepository inspectionRepository, InspectionMapper inspectionMapper) {
        this.inspectionRepository = inspectionRepository;
        this.inspectionMapper = inspectionMapper;
    }

    /**
     * Save a inspection.
     *
     * @param inspectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InspectionDTO save(InspectionDTO inspectionDTO) {
        log.debug("Request to save Inspection : {}", inspectionDTO);
        Inspection inspection = inspectionMapper.toEntity(inspectionDTO);
        inspection = inspectionRepository.save(inspection);
        return inspectionMapper.toDto(inspection);
    }

    /**
     *  Get all the inspections.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<InspectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inspections");
        return inspectionRepository.findAll(pageable)
            .map(inspectionMapper::toDto);
    }

    /**
     *  Get one inspection by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public InspectionDTO findOne(String id) {
        log.debug("Request to get Inspection : {}", id);
        Inspection inspection = inspectionRepository.findOne(id);
        return inspectionMapper.toDto(inspection);
    }

    /**
     *  Delete the  inspection by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Inspection : {}", id);
        inspectionRepository.delete(id);
    }
}
