package com.parrotanalytics.api.data.repo.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.data.repo.api.custom.MarketsRepositoryCustom;
import com.parrotanalytics.api.data.repo.api.custom.TitlesRepositoryCustom;

/**
 *
 * Extension of {@link PagingAndSortingRepository} & implementation of
 * {@link MarketsRepositoryCustom} to provide all the required data about
 * licensed titles & their metadata to any of the REST API controllers.
 * 
 * 
 * @author Sanjeev Sharma
 * @since v1
 *
 */
@Repository
@RepositoryRestResource(exported = false)
public interface TitlesRepository extends PagingAndSortingRepository<Country, String>, TitlesRepositoryCustom
{

}
