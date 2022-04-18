package com.parrotanalytics.api.data.repo.api.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;

import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.PortfolioItem;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.data.repo.api.PortfolioItemsRepository;
import com.parrotanalytics.api.data.repo.api.PortfoliosRepository;
import com.parrotanalytics.api.data.repo.api.TitlesRepository;
import com.parrotanalytics.api.data.repo.api.custom.PortfoliosRepositoryCustom;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class PortfoliosRepositoryImpl implements PortfoliosRepositoryCustom
{
    @Autowired
    private PortfoliosRepository portfolioRepo;

    @Autowired
    private PortfolioItemsRepository portfolioItemsRepo;

    @Autowired
    TitlesRepository titlesRepository;

    
    @Override
    @CacheEvict(value = APICacheConstants.CACHE_PORTFOLIOS, key = "#p0.idPortfolio")
    public void deletePortfolio(Portfolio portfolio)
    {
        portfolioRepo.delete(portfolio);
        portfolioRepo.clearPortfoliosCache();
    }

    @Override
    public Portfolio savePortfolio(Portfolio portfolio)
    {
        return savePortfolio(portfolio, false);
    }

    @Override
    @CachePut(value = APICacheConstants.CACHE_PORTFOLIOS, key = "#p0.idPortfolio")
    public Portfolio savePortfolio(Portfolio portfolio, boolean isPortfolioItemsChanged)
    {
        Portfolio savedPortfolio = portfolioRepo.save(portfolio);
        return savedPortfolio;
    }

    /*
     * ####### PORTFOLIO ITEMS methods ######
     */

    @Override
    public Page<CatalogItem> findPortfoioTitles(Integer portfolioID) throws APIException
    {
        return titlesRepository.portfolioTitles(portfolioID);
    }

    @Override
    @CachePut(value = APICacheConstants.CACHE_PORTFOLIOS, key = "#p0.idPortfolio")
    public Portfolio deletePortfolioItem(Portfolio portfolio, Long shortId)
    {
        PortfolioItem pfItem = portfolioItemsRepo.findByContentIdPortfolioAccount(shortId, portfolio.getIdPortfolio());

        portfolioItemsRepo.delete(pfItem);

        return portfolio;
    }

    /*
     * ####### CACHE MANAGEMENT methods ######
     */

    @CacheEvict(value = APICacheConstants.CACHE_PORTFOLIOS, allEntries = true)
    public void clearPortfoliosCache()
    {
        // keep it balnk
    }
}
