package com.auditor.service;

import com.auditor.domain.Item;
import com.auditor.service.dto.ItemsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Items.
 */
public interface ItemsService {

    /**
     * Save a items.
     *
     * @param itemsDTO the entity to save
     * @return the persisted entity
     */
    ItemsDTO save(ItemsDTO itemsDTO);

    /**
     *  Get all the items.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ItemsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" items.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ItemsDTO findOne(String id);

    /**
     *  Delete the "id" items.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    Page<Item> findAllItem(Pageable pageable, String inspectionId);
}
