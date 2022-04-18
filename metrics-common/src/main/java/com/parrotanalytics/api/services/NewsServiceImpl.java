package com.parrotanalytics.api.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedNewsArticle;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.NewsArticleRepository;
import com.parrotanalytics.api.data.repo.api.TitlesRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.request.demand.SortInfo;
import com.parrotanalytics.api.request.metricrank.MetricRankRequest;
import com.parrotanalytics.api.request.news.NewsRequest;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService
{
    @Autowired
    protected NewsArticleRepository newsArticlesRepo;

    @Autowired
    protected TitlesRepository titlesRepo;

    /* Data repository for ACCOUNT related operations */
    @Autowired
    protected AccountRepository accountRepo;

    @Autowired
    protected MetadataCache metadataCache;

    @Autowired
    private DemandService demandService;

    @Override
    public List<ExtendedNewsArticle> findLatestNews(NewsRequest newsRequest) throws APIException
    {
        final List<ExtendedNewsArticle> newsArticles = Collections
                .synchronizedList(new ArrayList<ExtendedNewsArticle>());

        /*
         * Search by Tag
         */
        if (StringUtils.isNotEmpty(newsRequest.getTag()))
        {
            newsArticles.addAll(newsArticlesRepo.getNewsByTag(newsRequest.getContent_type(), newsRequest.getTag(),
                    searchDays(newsRequest), (newsRequest.getPage() - 1) * newsRequest.getSize(),
                    newsRequest.getSize()));
        }
        /*
         * Search by Parrot ID
         */
        else if (StringUtils.isNotEmpty(newsRequest.getContent()))
        {
            String[] contentGUIDs = newsRequest.getContent().split(APIConstants.DELIM_COMMA);

            if (contentGUIDs != null && contentGUIDs.length >= 1)
            {
                List<String> parrotIDsList = Arrays.asList(contentGUIDs);
                Collections.sort(parrotIDsList);
                newsRequest.setListContentIDs(parrotIDsList);
            }

            newsRequest.getListContentIDs().parallelStream().forEach(new Consumer<String>()
            {
                @Override
                public void accept(String parrotID)
                {
                    try
                    {
                        newsArticles.addAll(
                                newsArticlesRepo.getNewsByShowID(Arrays.asList(parrotID), searchDays(newsRequest),
                                        (newsRequest.getPage() - 1) * newsRequest.getSize(), newsRequest.getSize()));
                    }
                    catch (Exception e)
                    {
                    }
                }
            });

        }
        /*
         * Search by Network
         */
        else if (StringUtils.isNotEmpty(newsRequest.getPlatform()))
        {

            if (newsRequest.isFulltext())
            {
                newsArticles.addAll(newsArticlesRepo.getNewsByPlatformFulltext(newsRequest.getContent_type(),
                        newsRequest.getPlatform(), searchDays(newsRequest),
                        (newsRequest.getPage() - 1) * newsRequest.getSize(), newsRequest.getSize()));

            }
            else
            {

                newsArticles.addAll(newsArticlesRepo.getNewsByPlatform(newsRequest.getContent_type(),
                        newsRequest.getPlatform(), searchDays(newsRequest),
                        (newsRequest.getPage() - 1) * newsRequest.getSize(), newsRequest.getSize()));
            }

        }
        else if (newsRequest.isDemand())
        {
            List<Long> contentShortIDs = null;

            MetricRankRequest rankRequest = MetricRankRequest.defaultRequest();
            rankRequest.setSize(20);

            demandService.parseContent(rankRequest.getDataQuery(), rankRequest, UserService.callUser());
            demandService.parseMarket(rankRequest.getDataQuery(), rankRequest, UserService.callUser());
            demandService.parseTime(rankRequest, rankRequest.getDataQuery(), UserService.callUser());

            rankRequest.setSort(SortInfo.createSort(DemandHelper.aggregateFunction(rankRequest),
                    DemandHelper.prepareSortBy(rankRequest), rankRequest.getOrder()));

            if (rankRequest.isAllShowsRequest())
            {
                Account account = accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT);
                contentShortIDs = metadataCache.accountTitles(account).stream().map(s -> s.getShortID())
                        .collect(Collectors.toList());
            }

            rankRequest.getDataQuery().setShortIDsList(contentShortIDs);

            Page<GroupedExpressions> topShows = demandService.fetchDemandData(rankRequest,
                    PageRequest.of(0, rankRequest.getSize(), rankRequest.getSort()));

            List<Long> randomShows = randomShows(topShows.getContent(), 4);

            randomShows.parallelStream().forEach(new Consumer<Long>()
            {
                @Override
                public void accept(Long shortID)
                {
                    try
                    {
                        newsArticles.addAll(newsArticlesRepo.getNewsByShowID(
                                titlesRepo.longUUIDs(Arrays.asList(shortID)), searchDays(newsRequest),
                                (newsRequest.getPage() - 1) * newsRequest.getSize(), newsRequest.getSize()));
                    }
                    catch (Exception e)
                    {
                    }
                }
            });

        }
        /*
         * Search by Most Recent
         */
        else
        {
            newsArticles.addAll(newsArticlesRepo.getLatestNews(newsRequest.getContent_type(), searchDays(newsRequest),
                    (newsRequest.getPage() - 1) * newsRequest.getSize(), newsRequest.getSize()));
        }

        return newsArticles;
    }

    private int searchDays(NewsRequest apiRequest)
    {
        return apiRequest.getSearchDays() != 0 ? apiRequest.getSearchDays() : 30;
    }

    public List<Long> randomShows(List<GroupedExpressions> topShows, int totalItems)
    {
        topShows = new ArrayList<>(topShows);
        Collections.shuffle(topShows);
        return topShows.subList(0, totalItems).stream().map(e -> e.getShort_id()).collect(Collectors.toList());
    }

}
