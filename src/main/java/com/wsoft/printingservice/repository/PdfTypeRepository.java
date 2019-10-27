package com.wsoft.printingservice.repository;

import com.wsoft.printingservice.model.entity.PdfType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author b.walid
 */
@Repository
public interface PdfTypeRepository extends CrudRepository<PdfType, Long> {

    @Query("SELECT coalesce(max(pt.id), 0) FROM PdfType pt")
    Long getMaxId();

    PdfType findById(long id);

    @Query("SELECT pt  FROM PdfType pt where pt.name = :name")
    PdfType findByName(@Param("name") String name);
}
