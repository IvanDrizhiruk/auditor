package com.auditor.repository;

import com.auditor.domain.InspectionItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ItemsRepository extends MongoRepository<InspectionItem, String> {
    InspectionItem findOneByInspectionId(@Param("inspection_id")String inspectionId);

    Page<InspectionItem> findByInspectionId(@Param("inspection_id")String inspectionId, Pageable pageable);
}
