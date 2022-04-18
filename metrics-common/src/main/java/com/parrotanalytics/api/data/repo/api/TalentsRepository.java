package com.parrotanalytics.api.data.repo.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.data.repo.api.custom.TalentsRepositoryCustom;

/**
 *
 * Extension of {@link PagingAndSortingRepository} & implementation of licensed
 * talents & their metadata to any of the REST API controllers.
 * 
 * 
 * @author Raja
 * @since v1
 *
 */
@Repository
@RepositoryRestResource(exported = false)
public interface TalentsRepository extends PagingAndSortingRepository<Country, String>, TalentsRepositoryCustom
{

}
