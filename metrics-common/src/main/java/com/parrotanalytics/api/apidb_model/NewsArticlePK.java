package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class NewsArticlePK implements Serializable
{
    private static final long serialVersionUID = -6698532408802906200L;

    @Column(name = "publish_date")
    private Date publish_date;

    @Column(name = "short_id")
    private Long short_id;

    @Column(name = "news_title")
    private String news_title;

    @Column(name = "publisher")
    private String publisher;

    public NewsArticlePK()
    {

    }

    public NewsArticlePK(Date publish_date, Long short_id, String news_title, String publisher)
    {
        this.publish_date = publish_date;
        this.short_id = short_id;
        this.news_title = news_title;
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

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

}
