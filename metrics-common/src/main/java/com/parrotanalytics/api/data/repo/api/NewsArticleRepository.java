package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.ExtendedNewsArticlePK;
import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedNewsArticle;
import com.parrotanalytics.api.commons.constants.APICacheConstants;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@Repository
public interface NewsArticleRepository extends JpaRepository<ExtendedNewsArticle, ExtendedNewsArticlePK>
{
    @Cacheable(cacheNames = APICacheConstants.CACHE_NEWS, key = "'news:' + #contentType + '-' + #searchDays + '-' + #page + '-' + #size")
    @Query(value = "SELECT publish_date, max(g.genevalue) AS platform,  max(m.content_type) AS content_type, max(m.display_name) AS title, m.parrot_id, publisher, max(author) AS author, news_title, max(news_content) AS news_content, news_url, image_url FROM externaldata.news_articles n JOIN portal.series_metadata m ON m.short_id = n.short_id JOIN portal.catalog_genes g ON g.parrot_id = m.parrot_id AND g.gene = 'network' WHERE publish_date >= DATE_SUB(CURRENT_DATE, INTERVAL ?2 DAY) AND m.content_type = ?1 GROUP BY publish_date, publisher, m.parrot_id, news_title, news_url,image_url ORDER BY publish_date DESC LIMIT ?3,?4", nativeQuery = true)
    public List<ExtendedNewsArticle> getLatestNews(String contentType, int searchDays, int page, int size);

    @Cacheable(cacheNames = APICacheConstants.CACHE_NEWS, key = "'news:' + #listParrotIDs + '-' + #searchDays + '-' + #page + '-' + #size")
    @Query(value = "SELECT publish_date, max(g.genevalue) AS platform, max(m.content_type) AS content_type, max(m.display_name) AS title, m.parrot_id, publisher, max(author) AS author, news_title, max(news_content) AS news_content, news_url, image_url FROM externaldata.news_articles n JOIN portal.series_metadata m ON m.short_id = n.short_id JOIN portal.catalog_genes g ON g.parrot_id = m.parrot_id AND g.gene = 'network' WHERE publish_date >= DATE_SUB(CURRENT_DATE, INTERVAL ?2 DAY) AND m.parrot_id IN ?1 GROUP BY publish_date, publisher, m.parrot_id, news_title, news_url,image_url ORDER BY publish_date DESC LIMIT ?3,?4", nativeQuery = true)
    public List<ExtendedNewsArticle> getNewsByShowID(List<String> listParrotIDs, int searchDays, int page, int size);

    @Cacheable(cacheNames = APICacheConstants.CACHE_NEWS, key = "'news:' + #contentType + '-' + #searchTag + '-' + #searchDays + '-' + #page + '-' + #size")
    @Query(value = "SELECT publish_date, max(g.genevalue) AS platform, max(m.content_type) AS content_type, max(m.display_name) AS title, m.parrot_id, publisher, max(author) AS author, news_title, max(news_content) AS news_content, news_url, image_url FROM externaldata.news_articles n JOIN portal.series_metadata m ON m.short_id = n.short_id JOIN portal.catalog_genes g ON g.parrot_id = m.parrot_id AND g.gene = 'network' WHERE publish_date >= DATE_SUB(CURRENT_DATE, INTERVAL ?3 DAY) AND m.content_type = ?1 AND (news_title LIKE CONCAT('%', ?2, '%') OR news_content LIKE CONCAT('%', ?2, '%')) GROUP BY publish_date, publisher, m.parrot_id, news_title, news_url,image_url ORDER BY publish_date DESC LIMIT ?4,?5", nativeQuery = true)
    public List<ExtendedNewsArticle> getNewsByTag(String contentType, String searchTag, int searchDays, int page,
            int size);

    @Cacheable(cacheNames = APICacheConstants.CACHE_NEWS, key = "'news:' + #contentType + '-' + #network + '-' + #searchDays + '-' + #page + '-' + #size")
    @Query(value = "SELECT publish_date, max(g.genevalue) AS platform, max(m.content_type) AS content_type, max(m.display_name) AS title, m.parrot_id, publisher, max(author) AS author, news_title, max(news_content) AS news_content, news_url, image_url FROM externaldata.news_articles n JOIN portal.series_metadata m ON n.short_id = m.short_id JOIN portal.catalog_genes g ON g.parrot_id = m.parrot_id WHERE publish_date >= DATE_SUB(CURRENT_DATE, INTERVAL ?3 DAY) AND m.content_type = ?1 AND g.gene = 'network' AND g.genevalue = ?2  GROUP BY publish_date, publisher, m.parrot_id, news_title, news_url,image_url ORDER BY publish_date DESC LIMIT ?4,?5", nativeQuery = true)
    public List<ExtendedNewsArticle> getNewsByPlatform(String contentType, String network, int searchDays, int page,
            int size);

    @Cacheable(cacheNames = APICacheConstants.CACHE_NEWS, key = "'news:' + #contentType + '-' + #network + '-like-' + #searchDays + '-' + #page + '-' + #size")
    @Query(value = "SELECT publish_date, max(g.genevalue) AS platform, max(m.content_type) AS content_type, max(m.display_name) AS title, m.parrot_id, publisher, max(author) AS author, news_title, max(news_content) AS news_content, news_url, image_url FROM externaldata.news_articles n JOIN portal.series_metadata m ON n.short_id = m.short_id JOIN portal.catalog_genes g ON g.parrot_id = m.parrot_id WHERE publish_date >= DATE_SUB(CURRENT_DATE, INTERVAL ?3 DAY) AND m.content_type = ?1 AND g.gene = 'network' AND g.genevalue LIKE CONCAT('%', ?2, '%')  GROUP BY publish_date, publisher, m.parrot_id, news_title, news_url,image_url ORDER BY publish_date DESC LIMIT ?4,?5", nativeQuery = true)
    public List<ExtendedNewsArticle> getNewsByPlatformFulltext(String contentType, String network, int searchDays,
            int page, int size);
}
