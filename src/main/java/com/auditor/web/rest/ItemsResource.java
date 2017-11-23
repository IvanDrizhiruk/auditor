package com.auditor.web.rest;

import com.auditor.domain.Item;
import com.auditor.service.ItemsService;
import com.auditor.service.dto.ItemsDTO;
import com.auditor.web.rest.errors.BadRequestAlertException;
import com.auditor.web.rest.util.HeaderUtil;
import com.auditor.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing Items.
 */
@RestController
@RequestMapping("/api")
public class ItemsResource {

    private final Logger log = LoggerFactory.getLogger(ItemsResource.class);

    private static final String ENTITY_NAME = "items";

    private final ItemsService itemsService;

    public ItemsResource(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    /**
     * POST  /items : Create a new items.
     *
     * @param itemsDTO the itemsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemsDTO, or with status 400 (Bad Request) if the items has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/items")
    @Timed
    public ResponseEntity<ItemsDTO> createItems(@Valid @RequestBody ItemsDTO itemsDTO) throws URISyntaxException {
        log.debug("REST request to save Items : {}", itemsDTO);
        if (itemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new items cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemsDTO result = itemsService.save(itemsDTO);
        return ResponseEntity.created(new URI("/api/items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /items : Updates an existing items.
     *
     * @param itemsDTO the itemsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemsDTO,
     * or with status 400 (Bad Request) if the itemsDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/items")
    @Timed
    public ResponseEntity<ItemsDTO> updateItems(@Valid @RequestBody ItemsDTO itemsDTO) throws URISyntaxException {
        log.debug("REST request to update Items : {}", itemsDTO);
        if (itemsDTO.getId() == null) {
            return createItems(itemsDTO);
        }
        ItemsDTO result = itemsService.save(itemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /items : get all the items.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of items in body
     */
    @GetMapping("/items")
    @Timed
    public ResponseEntity<List<Item>> getAllItems(@ApiParam Pageable pageable, @ApiParam String inspectionId) {
        log.debug("REST request to get a page of Items");
        Page<Item> page = itemsService.findAllItem(pageable, "5a14a276d12fa5223c205d10"/*inspectionId*/);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /items/:id : get the "id" items.
     *
     * @param id the id of the itemsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/items/{id}")
    @Timed
    public ResponseEntity<ItemsDTO> getItems(@PathVariable String id) {
        log.debug("REST request to get Items : {}", id);
        ItemsDTO itemsDTO = itemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(itemsDTO));
    }

    /**
     * DELETE  /items/:id : delete the "id" items.
     *
     * @param id the id of the itemsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/items/{id}")
    @Timed
    public ResponseEntity<Void> deleteItems(@PathVariable String id) {
        log.debug("REST request to delete Items : {}", id);
        itemsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
