package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Resource;
import com.parrotanalytics.api.apidb_model.UserLog;

/**
 * 
 * @author Raja 
 *
 */
@Repository
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Integer>
{
	@Query("SELECT r FROM Resource r WHERE r.active = 1")
    public List<Resource> fetchResources();
	
	@Query("SELECT r FROM Resource r")
    public List<Resource> fetchAllResources();

    @Query("SELECT r FROM Resource r WHERE r.idResource = :idResource")
    public Resource fetchResource(@Param("idResource") Integer idResource);
}
