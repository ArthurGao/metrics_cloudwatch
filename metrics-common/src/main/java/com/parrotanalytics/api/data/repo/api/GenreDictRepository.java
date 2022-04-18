package com.parrotanalytics.api.data.repo.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.GenreDict;

@Repository
@RepositoryRestResource(exported = false)
public interface GenreDictRepository extends CrudRepository<GenreDict, Integer>
{

}
