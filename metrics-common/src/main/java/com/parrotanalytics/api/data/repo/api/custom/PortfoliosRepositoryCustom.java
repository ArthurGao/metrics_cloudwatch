package com.parrotanalytics.api.data.repo.api.custom;

import org.springframework.data.domain.Page;

import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public interface PortfoliosRepositoryCustom
{

    /**
     * Deletes Portfolio
     * 
     * @param portfolio
     */
    void deletePortfolio(Portfolio portfolio);

    /**
     * 
     * @param portfolio
     * @return
     */
    Portfolio savePortfolio(Portfolio portfolio);

    /**
     * 
     * @param portfolio
     * @param isPortfolioItemsChanged
     * @return
     */
    Portfolio savePortfolio(Portfolio portfolio, boolean isPortfolioItemsChanged);

    /*
     * ####### PORTFOLIO ITEMS methods ######
     */

    /**
     * Find portfolio items as titles for given portfolio
     * 
     * @param portfolioID
     * @return
     * @throws APIException
     */
    Page<CatalogItem> findPortfoioTitles(Integer portfolioID) throws APIException;

    /**
     * Deletes portfolio item and invalidates titles cache for given portfolio @see {@link TitlesRepositoryCustom}
     * 
     * @param item
     * @return
     */
    Portfolio deletePortfolioItem(Portfolio portfolio, Long shortId);

    /*
     * ####### CACHE MANAGEMENT methods ######
     */

    public void clearPortfoliosCache();
}
