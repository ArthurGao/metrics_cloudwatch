package com.parrotanalytics.api.data.repo.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.LiveMetricStatus;

@Repository
public interface LiveMetricStatusRepository extends JpaRepository<LiveMetricStatus, Long>
{

}
