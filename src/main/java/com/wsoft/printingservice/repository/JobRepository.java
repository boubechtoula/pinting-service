/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wsoft.printingservice.repository;

import com.wsoft.printingservice.model.entity.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author b.walid
 */
@Repository
public interface JobRepository extends CrudRepository<Job, Long> {

    @Query("SELECT coalesce(max(jb.id), 0) FROM Job jb")
    Long getMaxId();

    Job findById(long id);

    Job findByName(String name);
}
