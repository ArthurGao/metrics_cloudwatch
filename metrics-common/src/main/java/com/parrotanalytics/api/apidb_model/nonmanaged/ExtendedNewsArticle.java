package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SqlResultSetMapping;

import com.parrotanalytics.api.apidb_model.ExtendedNewsArticlePK;
import com.parrotanalytics.datasourceint.dsdb.model.base.NonManagedEntity;

@Entity
@SqlResultSetMapping(name = "ExtendedNewsArticle", classes =
{
        @ConstructorResult(targetClass = ExtendedNewsArticle.class, columns =
        {
                @ColumnResult(name = "publish_date", type = Date.class),

                @ColumnResult(name = "content_type", type = String.class),

                @ColumnResult(name = "title", type = String.class),

                @ColumnResult(name = "parrot_id", type = String.class),

                @ColumnResult(name = "news_title", type = String.class),

                @ColumnResult(name = "author", type = String.class),

                @ColumnResult(name = "news_content", type = String.class),

                @ColumnResult(name = "news_url", type = String.class),

                @ColumnResult(name = "image_url", type = String.class),

                @ColumnResult(name = "publisher", type = String.class)
        })
})
@IdClass(value = ExtendedNewsArticlePK.class)
public class ExtendedNewsArticle implements NonManagedEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -7070705465317360149L;

    @Id
    @Column(name = "publish_date")
    private Date publish_date;

    @Id
    @Column(name = "publisher")
    private String publisher;

    @Column(name = "platform")
    private String platform;

    @Column(name = "content_type")
    private String content_type;

    @Column(name = "title")
    private String title;

    @Id
    @Column(name = "parrot_id")
    private String parrot_id;

    @Id
    @Column(name = "news_title")
    private String news_title;

    @Column(name = "author")
    private String author;

    @Column(name = "news_content")
    private String news_content;

    @Id
    @Column(name = "news_url")
    private String news_url;

    @Id
    @Column(name = "image_url")
    private String image_url;

    public ExtendedNewsArticle()
    {
        super();
    }

    public ExtendedNewsArticle(Date publish_date, String platform, String content_type, String title, String parrot_id,
            String news_title, String author, String news_content, String news_url, String image_url, String publisher)
    {
        super();
        this.publish_date = publish_date;
        this.platform = platform;
        this.content_type = content_type;
        this.title = title;
        this.parrot_id = parrot_id;
        this.news_title = news_title;
        this.author = author;
        this.news_content = news_content;
        this.news_url = news_url;
        this.image_url = image_url;
        this.publisher = publisher;
    }

    public Date getPublish_date()
    {
        return publish_date;
    }

    public String getPlatform()
    {
        return platform;
    }

    public String getContent_type()
    {
        return content_type;
    }

    public String getTitle()
    {
        return title;
    }

    public String getParrot_id()
    {
        return parrot_id;
    }

    public String getNews_title()
    {
        return news_title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getNews_content()
    {
        return news_content;
    }

    public String getNews_url()
    {
        return news_url;
    }

    public String getImage_url()
    {
        return image_url;
    }

    public String getPublisher()
    {
        return publisher;
    }

}
