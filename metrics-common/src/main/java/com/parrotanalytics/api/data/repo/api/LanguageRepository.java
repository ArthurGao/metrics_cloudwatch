package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.Customer;
import com.parrotanalytics.api.apidb_model.Language;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minh Vu
 * @since v1
 */
@Repository
public interface LanguageRepository extends PagingAndSortingRepository<Language, String>
{

    @Query("SELECT l FROM Language l")
    List<Language> findAllLanguages();
}
