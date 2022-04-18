package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class ExtendedNewsArticlePK implements Serializable
{
    private static final long serialVersionUID = -6698532408802906200L;

    @Column(name = "publish_date")
    private Date publish_date;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "parrot_id")
    private String parrot_id;

    @Column(name = "news_title")
    private String news_title;

    @Column(name = "news_url")
    private String news_url;

    @Column(name = "image_url")
    private String image_url;

    public ExtendedNewsArticlePK()
    {

    }

    public ExtendedNewsArticlePK(Date publish_date, String publisher, String parrot_id, String news_title,
            String news_url, String image_url)
    {
        super();
        this.publish_date = publish_date;
        this.publisher = publisher;
        this.parrot_id = parrot_id;
        this.news_title = news_title;
        this.news_url = news_url;
        this.image_url = image_url;
    }

    public Date getPublish_date()
    {
        return publish_date;
    }

    public void setPublish_date(Date publish_date)
    {
        this.publish_date = publish_date;
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

    public String getNews_title()
    {
        return news_title;
    }

    public void setNews_title(String news_title)
    {
        this.news_title = news_title;
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

}
