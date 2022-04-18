package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.Customer;
import com.parrotanalytics.api.commons.constants.APICacheConstants;

/**
 * Extension of {@link PagingAndSortingRepository} to provide all the required
 * user account related data to any of the REST API controllers.
 *
 * @author Sanjeev
 * @author Minh Vu
 * @since v1
 */
@Repository("accountRepo")
public interface AccountRepository extends CrudRepository<Account, Integer>
{

    @Query("SELECT a FROM Account a")
    public List<Account> loadAllAccounts();

    @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNTS, keyGenerator = "cacheKeyGenerator", unless = "#result == null")
    @Query("SELECT a FROM Account a " + "WHERE a.accountName = :accountName ")
    public Account loadAccountByAccountName(@Param("accountName") String accountName);

    @Query("SELECT a.accountName FROM Account a")
    public List<String> loadAllAccountNames();

    @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNTS, keyGenerator = "cacheKeyGenerator", unless = "#result == null")
    @Query("SELECT a from Account a WHERE a.idAccount = :idAccount")
    Account findByIdAccount(@Param("idAccount") Integer idAccount);

    @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNTS, keyGenerator = "cacheKeyGenerator", unless = "#result == null")
    @Query("SELECT a FROM Account a " + "WHERE a.customer = :customer")
    public List<Account> loadAllAccountsByCompany(@Param("customer") Customer customer);

    @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNTS, keyGenerator = "cacheKeyGenerator", unless = "#result == null")
    @Query("SELECT a FROM Account a " + "WHERE a.accountType = :accountType")
    public List<Account> loadAllAccountsByType(@Param("accountType") String accountType);

    @Cacheable(cacheNames = APICacheConstants.CACHE_ACCOUNTS, keyGenerator = "cacheKeyGenerator", unless = "#result == null")
    @Query("SELECT a FROM Account a " + "WHERE a.accountLevel = :accountLevel")
    public List<Account> loadAllAccountsByAccountLevel(@Param("accountLevel") String accountLevel);

    @Query("SELECT a FROM Account a " + "WHERE a.accountLevel = 'allaccess_root'")
    public Account loadAllAccessRootAccount();

    @Query("SELECT a from Account a WHERE a.accountName = :accountName and a.customer = :customer")
    Account findByAccountNameAndCustomer(@Param("accountName") String accountName,
            @Param("customer") Customer customer);

}
