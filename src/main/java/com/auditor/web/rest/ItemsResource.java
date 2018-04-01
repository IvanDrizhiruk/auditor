package com.auditor.web.rest;

import com.auditor.domain.InspectionItem;
import com.auditor.service.InspectionItemsService;
import com.auditor.web.rest.util.HeaderUtil;
import com.auditor.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemsResource {

    private final Logger log = LoggerFactory.getLogger(ItemsResource.class);

    private static final String ENTITY_NAME = "items";

    private final InspectionItemsService itemsService;

    public ItemsResource(InspectionItemsService itemsService) {
        this.itemsService = itemsService;
    }


    @PutMapping("/items")
    @Timed
    public ResponseEntity<InspectionItem> updateItems(@Valid @RequestBody InspectionItem inspectionItem) throws URISyntaxException {
        log.debug("REST request to update InspectionItem : {}", inspectionItem);


        return null;
    }

    @GetMapping("/inspections/{inspectionId}/items")
    @Timed
    public ResponseEntity<List<InspectionItem>> getAllItems(@ApiParam Pageable pageable, @PathVariable String inspectionId) {
        log.debug("REST request to get a page of InspectionItem");
        Page<InspectionItem> page = itemsService.findItemsByInspectionId(inspectionId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    @Timed
    public ResponseEntity<Void> deleteItems(@PathVariable String id) {
        log.debug("REST request to delete InspectionItem : {}", id);
        itemsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
