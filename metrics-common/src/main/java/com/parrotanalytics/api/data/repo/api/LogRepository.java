package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.UserLog;
import com.parrotanalytics.api.data.repo.api.custom.LogRepositoryCustom;

/**
 * 
 * @author Sanjeev Sharma, Minh Vu
 * @since v1
 *
 */
@Repository
public interface LogRepository extends PagingAndSortingRepository<UserLog, String>, LogRepositoryCustom
{
    @Query("SELECT l from UserLog l where l.userId=:userId")
    public List<UserLog> findUserLogs(@Param("userId") Integer userId, Pageable page);
}
