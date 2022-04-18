package com.parrotanalytics.api.apidb_model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "news_articles", schema = "externaldata")
@IdClass(NewsArticlePK.class)
public class NewsArticle implements Serializable
{
    private static final long serialVersionUID = -1491455516304225137L;

    @Id
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_date")
    private Date publish_date;

    @Id
    @Column(name = "short_id")
    private Long short_id;

    @Transient
    private String parrot_id;

    @Id
    @Column(name = "news_title")
    private String news_title;

    @Column(name = "news_content")
    private String news_content;

    @Column(name = "news_url")
    private String news_url;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "author")
    private String author;

    @Id
    @Column(name = "publisher")
    private String publisher;

    public NewsArticle()
    {

    }

    public NewsArticle(Date publish_date, Long short_id, String news_title, String news_content, String news_url,
            String image_url, String author, String publisher)
    {
        this.publish_date = publish_date;
        this.short_id = short_id;
        this.news_title = news_title;
        this.news_content = news_content;
        this.news_url = news_url;
        this.image_url = image_url;
        this.author = author;
        this.publisher = publisher;
    }

    public Date getPublish_date()
    {
        return publish_date;
    }

    public void setPublish_date(Date publish_date)
    {
        this.publish_date = publish_date;
    }

    public Long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(Long short_id)
    {
        this.short_id = short_id;
    }

    public String getNews_title()
    {
        return news_title;
    }

    public void setNews_title(String news_title)
    {
        this.news_title = news_title;
    }

    public String getNews_content()
    {
        return news_content;
    }

    public void setNews_content(String news_content)
    {
        this.news_content = news_content;
    }

    public String getNews_url()
    {
        return news_url;
    }

    public void setNews_url(String news_url)
    {
        this.news_url = news_url;
    }

    public String getImage_url()
    {
        return image_url;
    }

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getParrot_id()
    {
        return parrot_id;
    }

    public void setParrot_id(String parrot_id)
    {
        this.parrot_id = parrot_id;
    }

}
