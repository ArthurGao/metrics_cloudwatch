package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.Project;
import com.parrotanalytics.api.data.repo.api.custom.ProjectRepositoryCustom;

/**
 * Data repository for user account information.
 * 
 * @author jackson
 * @since v1
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer>, ProjectRepositoryCustom
{

    @Query("SELECT c FROM Project c WHERE c.account = :account")
    List<Project> loadByAccount(@Param("account") Account account);
    
    @Query("SELECT c FROM Project c")
    List<Project> loadAllProjects();

    @Query("SELECT c FROM Project c WHERE c.account = :account AND c.status = 'completed'")
    List<Project> loadCompletedProjectsByAccount(@Param("account") Account account);

    // @Query("SELECT c FROM Project c WHERE c.account = :account AND
    // YEAR(c.startDate) = (YEAR(Now()) - :yearBack) AND MONTH(c.startDate) =
    // :requestedMonth")
    // List<Project> loadByAccountMonthYear(@Param("account") Account account,
    // @Param("yearBack") Integer yearBack,
    // @Param("requestedMonth") Integer requestedMonth);

    @Query("SELECT c FROM Project c WHERE c.account = :account AND c.projectName = :projectName")
    Project loadByProjectName(@Param("account") Account account, @Param("projectName") String projectName);

}
