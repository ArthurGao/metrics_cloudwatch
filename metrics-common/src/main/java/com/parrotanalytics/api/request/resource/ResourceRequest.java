package com.parrotanalytics.api.request.resource;

import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * Request body for resource
 * 
 * @author Raja
 *
 */
public class ResourceRequest extends APIRequest {

	private OperationType operationType;

	@JsonProperty("resource_id")
    protected Integer idResource;
	
	@JsonProperty("title")
    protected String title;
	
	@JsonProperty("type")
    protected String type;
	
	@JsonProperty("content_link")
    protected String content_link;
	
	@JsonProperty("description")
    protected String description;
	
	@JsonProperty("thumbnail")
    protected String thumbnail;
	
	@JsonProperty("library_path")
    protected String library_path;
	
	@JsonProperty("module_path")
    protected String module_path;
	
	@JsonProperty("ok_button")
    protected String ok_button;
	
	@JsonProperty("close_button")
    protected String close_button;
	
	@JsonProperty("remind")
    protected Integer remind;
	
	@JsonProperty("active")
    protected Integer active;
	
	@JsonProperty("notified")
    protected Integer notified;
	
	@JsonProperty("created_time")
    protected Date created_time;
	
	@JsonProperty("updated_time")
    protected Date updated_time;
	
	
	public ResourceRequest() {
		super();
	}

	public ResourceRequest(OperationType operationType) {
		super();
		this.operationType = operationType;
	}
	
	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	
	public Integer getIdResource() {
		return idResource;
	}

	public void setIdResource(Integer idResource) {
		this.idResource = idResource;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent_link() {
		return content_link;
	}

	public void setContent_link(String content_link) {
		this.content_link = content_link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getLibrary_path() {
		return library_path;
	}

	public void setLibrary_path(String library_path) {
		this.library_path = library_path;
	}

	public String getModule_path() {
		return module_path;
	}

	public void setModule_path(String module_path) {
		this.module_path = module_path;
	}

	public String getOk_button() {
		return ok_button;
	}

	public void setOk_button(String ok_button) {
		this.ok_button = ok_button;
	}

	public String getClose_button() {
		return close_button;
	}

	public void setClose_button(String close_button) {
		this.close_button = close_button;
	}

	public Integer getRemind() {
		return remind;
	}

	public void setRemind(Integer remind) {
		this.remind = remind;
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

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public Date getUpdated_time() {
		return updated_time;
	}

	public void Date(Date updated_time) {
		this.updated_time = updated_time;
	}
}
