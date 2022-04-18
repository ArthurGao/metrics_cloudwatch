package com.parrotanalytics.api.data.repo.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface MetricRankRepository extends PagingAndSortingRepository<Long, Long>
{

}
