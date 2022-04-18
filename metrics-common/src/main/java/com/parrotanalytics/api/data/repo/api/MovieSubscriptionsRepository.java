package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.apidb_model.MovieSubscription;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Minh Vu
 * @since v1
 */
@Repository
public interface MovieSubscriptionsRepository
    extends PagingAndSortingRepository<MovieSubscription, String> {

}
