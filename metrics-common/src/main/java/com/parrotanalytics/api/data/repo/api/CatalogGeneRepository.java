package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.data.repo.api.custom.CatalogGeneRepositoryCustom;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Resource;

/**
 *
 *
 * @author Minh Vu
 *
 */
@Repository
@RepositoryRestResource(exported = false)
public interface CatalogGeneRepository extends PagingAndSortingRepository<Resource, Integer>,
    CatalogGeneRepositoryCustom {

    @Query(value = "SELECT distinct cg.genevalue FROM `portal`.`catalog_genes` cg  WHERE cg.short_id =? and cg.gene = ?", nativeQuery = true)
    List<String> getGenesByTitle(Long shortID, String gene);

}
