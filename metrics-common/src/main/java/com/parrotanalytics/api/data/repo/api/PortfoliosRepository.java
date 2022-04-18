package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.commons.constants.Entity;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.nonmanaged.PortfolioType;
import com.parrotanalytics.api.apidb_model.nonmanaged.PortfolioWithCount;
import com.parrotanalytics.api.data.repo.api.custom.PortfoliosRepositoryCustom;

/**
 * Extension of {@link PagingAndSortingRepository} & implementation of {@link PortfoliosRepositoryCustom} to provide all
 * the required {@link ExpressionsDaily} & {@link Portfolio} data.
 * 
 * @author Chris
 * @author minhvu
 * @since v1
 */
@Repository
public interface PortfoliosRepository extends PagingAndSortingRepository<Portfolio, Integer>, PortfoliosRepositoryCustom
{

    @Query("SELECT p FROM Portfolio p WHERE p.idPortfolio IN :portfolioIds")
    List<Portfolio> findMultiplePortfoliosById(@Param("portfolioIds") List<Integer> portfolioIds);

    @Query("SELECT p FROM Portfolio p WHERE p.idAccount = :idAccount")
    public List<Portfolio> findAccountPortfolios(@Param("idAccount") Integer idAccount);

    @Query("SELECT p FROM Portfolio p WHERE p.idPortfolio = :portfolioId")
    public Portfolio findByPortfolioId(@Param("portfolioId") Integer portfolioId);

    @Query("SELECT p FROM Portfolio p WHERE p.idPortfolio IN :portfolioIds AND p.idAccount = :idAccount")
    public List<Portfolio> findAccountPortfoliosByIDs(@Param("portfolioIds") List<Integer> portfolioIds,
            @Param("idAccount") Integer idAccount);

    @Query("SELECT p FROM Portfolio p WHERE p.type = :type AND p.idAccount = :idAccount")
    public List<Portfolio> findPortfoliosByAccountNType(@Param("idAccount") Integer idAccount,
            @Param("type") PortfolioType type);

    /**
     * Find all content IDs for given portfolio
     * 
     * @param portfolioId
     * @param accountId
     * @return
     */
    @Query(value = "SELECT short_id from `portal`.`portfolioitems` WHERE idPortfolio = ?1", nativeQuery = true)
    public List<Long> findContentIDsByPortfolio(Integer portfolioId);

    /**
     * Non Managed query to fetch portfolios by account with Portfolio Items Count
     * 
     * @return
     */
    @Query("SELECT NEW com.parrotanalytics.api.apidb_model.nonmanaged.PortfolioWithCount"
            + "(p.idPortfolio, p.name, p.description,p.type,p.filterValue,p.createdOn, p.updatedOn, p.managed, p.shared, count(pi), p.entity) "
            + "FROM Portfolio p LEFT JOIN PortfolioItem pi ON p.idPortfolio = pi.idPortfolio WHERE p.entity=:entity AND (((p.idAccount = :idAccount OR p.idAccount = 99) and p.shared = true) OR p.idUser = :idUser) "
            + "GROUP BY p.idPortfolio")
    public List<PortfolioWithCount> findPortfolioWithItemsCount(@Param("idAccount") Integer idAccount,
            @Param("idUser") Integer idUser, @Param("entity") Entity entity);

    @Query(value = "SELECT MAX(idPortfolio) from portal.portfolio", nativeQuery = true)
    public int getPortfolioIdMax();

}
