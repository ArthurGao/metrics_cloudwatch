package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The persistent class for the Customer database table.
 * 
 */
@Entity
@Table(name = "resource", schema = "portal")
@JsonPropertyOrder(
{
        "resource_id", "title", "type", "content_link", "description", "thumbnail", "library_path", "module_path",
        "ok_button", "close_button", "remind", "active"
})
@JsonInclude(Include.NON_NULL)
public class Resource implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResource")
    @JsonProperty("resource_id")
    private Integer idResource;

    @JsonProperty("title")
    private String title;

    @JsonProperty("type")
    private String type;

    @Column(name = "content_link")
    @JsonProperty("content_link")
    private String contentLink;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "thumbnail")
    @JsonProperty("thumbnail")
    private String thumbnail;

    @Column(name = "library_path")
    @JsonProperty("library_path")
    private String library_path;

    @Column(name = "module_path")
    @JsonProperty("module_path")
    private String module_path;

    @Column(name = "ok_button")
    @JsonProperty("ok_button")
    private String okButton;

    @Column(name = "close_button")
    @JsonProperty("close_button")
    private String closeButton;

    @JsonProperty("remind")
    private Integer remind;

    @JsonProperty("active")
    private Integer active;  
    
    @JsonProperty("notified")
    private Integer notified;  

	/** The created on. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    @JsonProperty("created_time")
    private Date createdTime;

    /** The created on. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    @JsonProperty("updated_time")
    private Date updatedTime;

    public Integer getIdResource()
    {
        return idResource;
    }

    public void setIdResource(Integer idResource)
    {
        this.idResource = idResource;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getContentLink()
    {
        return contentLink;
    }

    public void setContentLink(String contentLink)
    {
        this.contentLink = contentLink;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getLibrary_path()
    {
        return library_path;
    }

    public void setLibrary_path(String library_path)
    {
        this.library_path = library_path;
    }

    public String getModule_path()
    {
        return module_path;
    }

    public void setModule_path(String module_path)
    {
        this.module_path = module_path;
    }

    public String getOk_button()
    {
        return okButton;
    }

    public void setOk_button(String ok_button)
    {
        this.okButton = ok_button;
    }

    public String getClose_button()
    {
        return closeButton;
    }

    public void setClose_button(String close_button)
    {
        this.closeButton = close_button;
    }

    public Integer getRemind()
    {
        return remind;
    }

    public void setRemind(Integer remind)
    {
        this.remind = remind;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }
    
   	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}
}