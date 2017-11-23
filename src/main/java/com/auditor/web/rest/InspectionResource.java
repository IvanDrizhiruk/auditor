package com.auditor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.auditor.service.InspectionService;
import com.auditor.web.rest.errors.BadRequestAlertException;
import com.auditor.web.rest.util.HeaderUtil;
import com.auditor.web.rest.util.PaginationUtil;
import com.auditor.service.dto.InspectionDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Inspection.
 */
@RestController
@RequestMapping("/api")
public class InspectionResource {

    private final Logger log = LoggerFactory.getLogger(InspectionResource.class);

    private static final String ENTITY_NAME = "inspection";

    private final InspectionService inspectionService;

    public InspectionResource(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    /**
     * POST  /inspections : Create a new inspection.
     *
     * @param inspectionDTO the inspectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inspectionDTO, or with status 400 (Bad Request) if the inspection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inspections")
    @Timed
    public ResponseEntity<InspectionDTO> createInspection(@Valid @RequestBody InspectionDTO inspectionDTO) throws URISyntaxException {
        log.debug("REST request to save Inspection : {}", inspectionDTO);
        if (inspectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new inspection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspectionDTO result = inspectionService.save(inspectionDTO);
        return ResponseEntity.created(new URI("/api/inspections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inspections : Updates an existing inspection.
     *
     * @param inspectionDTO the inspectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inspectionDTO,
     * or with status 400 (Bad Request) if the inspectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the inspectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inspections")
    @Timed
    public ResponseEntity<InspectionDTO> updateInspection(@Valid @RequestBody InspectionDTO inspectionDTO) throws URISyntaxException {
        log.debug("REST request to update Inspection : {}", inspectionDTO);
        if (inspectionDTO.getId() == null) {
            return createInspection(inspectionDTO);
        }
        InspectionDTO result = inspectionService.save(inspectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inspectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inspections : get all the inspections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inspections in body
     */
    @GetMapping("/inspections")
    @Timed
    public ResponseEntity<List<InspectionDTO>> getAllInspections(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Inspections");
        Page<InspectionDTO> page = inspectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inspections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inspections/:id : get the "id" inspection.
     *
     * @param id the id of the inspectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inspectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inspections/{id}")
    @Timed
    public ResponseEntity<InspectionDTO> getInspection(@PathVariable String id) {
        log.debug("REST request to get Inspection : {}", id);
        InspectionDTO inspectionDTO = inspectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inspectionDTO));
    }

    /**
     * DELETE  /inspections/:id : delete the "id" inspection.
     *
     * @param id the id of the inspectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inspections/{id}")
    @Timed
    public ResponseEntity<Void> deleteInspection(@PathVariable String id) {
        log.debug("REST request to delete Inspection : {}", id);
        inspectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
