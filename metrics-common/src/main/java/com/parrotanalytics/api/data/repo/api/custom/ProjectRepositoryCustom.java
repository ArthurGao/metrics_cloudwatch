package com.parrotanalytics.api.data.repo.api.custom;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.data.repo.api.MarketsRepository;
import com.parrotanalytics.api.response.project.ProjectResponse;
import com.parrotanalytics.apicore.exceptions.APIException;

/**
 * Interface for custom methods in {@link MarketsRepository}
 * 
 * @author Jackson
 * 
 */
public interface ProjectRepositoryCustom
{

    /**
     * 
     * @param accountID
     * @return
     * @throws ExecutionException
     */
    public List<ProjectResponse> injectProjectToResponseDemandPortal(Account account) throws APIException;

}
