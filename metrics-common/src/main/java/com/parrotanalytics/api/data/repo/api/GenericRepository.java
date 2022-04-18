package com.parrotanalytics.api.data.repo.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.parrotanalytics.api.apidb_model.Resource;

/**
 * {@link RestController} for Generic Data Queries
 * 
 * @author sanjeev
 *
 */
@Repository
@RepositoryRestResource(exported = false)
public interface GenericRepository extends PagingAndSortingRepository<Resource, Integer>
{
}
