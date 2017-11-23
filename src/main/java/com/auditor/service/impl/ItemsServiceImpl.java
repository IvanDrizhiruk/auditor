package com.auditor.service.impl;

import com.auditor.domain.Item;
import com.auditor.domain.Items;
import com.auditor.repository.ItemsRepository;
import com.auditor.service.ItemsService;
import com.auditor.service.dto.ItemsDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service Implementation for managing Items.
 */
@Service
public class ItemsServiceImpl implements ItemsService{

    private final Logger log = LoggerFactory.getLogger(ItemsServiceImpl.class);

    private final ItemsRepository itemsRepository;

    private final ModelMapper modelMapper;

    private final MongoTemplate mongoTemplate;

    public ItemsServiceImpl(ItemsRepository itemsRepository, ModelMapper modelMapper, MongoTemplate mongoTemplate) {
        this.itemsRepository = itemsRepository;
        this.modelMapper = modelMapper;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Save a items.
     *
     * @param itemsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemsDTO save(ItemsDTO itemsDTO) {
        log.debug("Request to save Items : {}", itemsDTO);
        Items items = modelMapper.map(itemsDTO, Items.class);
        items = itemsRepository.save(items);
        return modelMapper.map(items, ItemsDTO.class);
    }

    /**
     *  Get all the items.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<ItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemsRepository.findAll(pageable)
            .map(items -> {
                return modelMapper.map(items, ItemsDTO.class);
            });
    }

    /**
     *  Get one items by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public ItemsDTO findOne(String id) {
        log.debug("Request to get Items : {}", id);
        Items items = itemsRepository.findOne(id);
        return null == items
            ? null
            : modelMapper.map(items, ItemsDTO.class);
    }

    /**
     *  Delete the  items by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Items : {}", id);
        itemsRepository.delete(id);
    }

    @Override
    public Page<Item> findAllItem(Pageable pageable, String inspectionId) {
        Items inspectionItems = itemsRepository.testFinding(
            inspectionId,
            pageable.getPageNumber() * pageable.getPageSize(),
            pageable.getPageSize());
        List<Item> items = inspectionItems.getList();

        int total = inspectionItems.getCount();

        PageImpl<Item> items1 = new PageImpl<>(items,
            new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()), total);

        return items1;
    }
}
