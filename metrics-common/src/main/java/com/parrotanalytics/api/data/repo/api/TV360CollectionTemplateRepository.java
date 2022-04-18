package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.parrotanalytics.api.apidb_model.TV360CollectionTemplate;

/**
 * 
 * @author minhvu
 *
 */
public interface TV360CollectionTemplateRepository extends CrudRepository<TV360CollectionTemplate, Integer>
{
    public List<TV360CollectionTemplate> findByName(String name);
}
