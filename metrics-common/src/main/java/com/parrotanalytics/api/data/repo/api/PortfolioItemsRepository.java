package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.parrotanalytics.api.apidb_model.PortfolioItem;
import com.parrotanalytics.api.data.repo.api.custom.PortfolioItemsRepositoryCustom;

/**
 * Extension of {@link PagingAndSortingRepository} & implementation of {@link PortfolioItemsRepositoryCustom} to provide
 * all the required data about portfolio items
 * 
 * @author Chris
 * @author Minh Vu
 * 
 */
@Repository
public interface PortfolioItemsRepository extends PagingAndSortingRepository<PortfolioItem, Integer>
{

    /**
     * 
     * @param contentId
     * @param portfolioId
     * @param accountId
     * @return
     */
    @Query("SELECT pi from PortfolioItem pi " + "WHERE pi.short_id = :shortId " + "AND pi.idPortfolio = :portfolioId")
    public PortfolioItem findByContentIdPortfolioAccount(@Param("shortId") Long shortId,
            @Param("portfolioId") int portfolioId);

    @Modifying
    @Transactional
    @Query(value = "DELETE from `portal`.`portfolioitems` WHERE idPortfolio = ?1", nativeQuery = true)
    public void removePortfolioItems(int portfolioId);

    @Query("SELECT pi from PortfolioItem pi WHERE  pi.idPortfolio IN :portfolioIds")
    public List<PortfolioItem> findByPortfolios(@Param("portfolioIds") List<Integer> portfolioIds);

}
