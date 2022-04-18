package com.parrotanalytics.api.apidb_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "userevents", schema = "portal")
public class UserEvent implements Serializable
{

    private static final long serialVersionUID = -5727760449986173824L;

    @Id
    @Column(name = "event_id")
    @JsonProperty("event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    /**
     * The date time.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_time")
    @JsonProperty("event_time")
    private Date eventTime;

    /**
     * The user id.
     */
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userId;

    /**
     * The ip address.
     */
    @JsonProperty("ip_address")
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * The user agent.
     */
    @JsonProperty("user_agent")
    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "page")
    private String page;

    @JsonProperty("session_id")
    @Column(name = "session_id")
    private String sessionId;

    @JsonProperty("page_ref")
    @Column(name = "page_ref")
    private String pageRef;

    @JsonProperty("market")
    @Column(name = "market")
    private String market;

    @Column(name = "platform")
    private String platform;

    @Column(name = "section")
    private String section;

    @JsonProperty("rendering_state")
    @Column(name = "rendering_state")
    private String renderingState;

    @JsonProperty("ui_event")
    @Column(name = "ui_event")
    private String uiEvent;

    @JsonProperty("previous_page")
    @Column(name = "previous_page")
    private String previousPage;

    @Column(name = "domain")
    @JsonProperty("domain")
    private String domain;

    public UserEvent()
    {
    }

}