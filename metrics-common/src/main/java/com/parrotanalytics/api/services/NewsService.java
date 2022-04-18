package com.parrotanalytics.api.services;

import java.util.List;

import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedNewsArticle;
import com.parrotanalytics.api.request.news.NewsRequest;
import com.parrotanalytics.apicore.exceptions.APIException;

public interface NewsService
{
    List<ExtendedNewsArticle> findLatestNews(NewsRequest apiRequest) throws APIException;
}
