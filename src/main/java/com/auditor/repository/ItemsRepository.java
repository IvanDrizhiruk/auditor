package com.auditor.repository;

import com.auditor.domain.Items;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Items entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemsRepository extends MongoRepository<Items, String> {
    @Query(value = "{inspection_id:?0}", fields = "{list: {$slice: [?1, ?2]}}")
    Items testFinding(String inspectionId, int skip, int limit);
}
