package com.auditor.service.impl;

import com.auditor.domain.InspectionItem;
import com.auditor.repository.InspectionRepository;
import com.auditor.repository.ItemsRepository;
import com.auditor.service.InspectionItemsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class InspectionItemsServiceImpl implements InspectionItemsService {

    private final Logger log = LoggerFactory.getLogger(InspectionItemsServiceImpl.class);

    private final ItemsRepository itemsRepository;
    private final MongoOperations mongoOperations;

    private final ModelMapper modelMapper;

    private final InspectionRepository inspectionRepository;


    public InspectionItemsServiceImpl(ItemsRepository itemsRepository,
                                      ModelMapper modelMapper,
                                      MongoOperations mongoOperations,
                                      InspectionRepository inspectionRepository) {
        this.itemsRepository = itemsRepository;
        this.modelMapper = modelMapper;
        this.mongoOperations = mongoOperations;
        this.inspectionRepository = inspectionRepository;
    }

    @Override
    public InspectionItem save(InspectionItem inspectionItems) {
        log.debug("Request to save InspectionItem : {}", inspectionItems);
        return itemsRepository.save(inspectionItems);
    }

    @Override
    public Page<InspectionItem> findItemsByInspectionId(String inspectionId, Pageable pageable) {
        log.debug("Request to get all InspectionItem");
        return itemsRepository.findByInspectionId(inspectionId, pageable);
    }

    @Override
    public InspectionItem findOne(String id) {
        log.debug("Request to get InspectionItem : {}", id);
        InspectionItem inspectionId = itemsRepository.findOne(id);
        return null == inspectionId
            ? null
            : modelMapper.map(inspectionId, InspectionItem.class);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete InspectionItem : {}", id);
        itemsRepository.delete(id);
    }
}
