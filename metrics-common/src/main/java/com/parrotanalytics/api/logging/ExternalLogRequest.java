package com.parrotanalytics.api.logging;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class AmityRequest.
 *
 * @author Jackson Lee
 */
public class ExternalLogRequest
{

    /** The Constant WORKSPACE_ID. */
    public final static String WORKSPACE_ID = "5989b7c5f7b90322f49abab5";

    /** The Constant SOURCE_TYPE. */
    public final static String SOURCE_TYPE = "ctp.app";

    /** The Constant KEY. */
    public final static String KEY = "NTk5ZTAzMzZmZDQwZTE2ZGQ2N2Q0ODY1OkFXWUw3cGw4SnMyZFBleXFueTFYQ2dqWQ==";

    /** The workspace id. */
    private String workspace_id;

    /** The participant id. */
    private String participant_id;

    /** The participant id source. */
    private String participant_id_source;

    /** The account id. */
    private String account_id;

    /** The account id source. */
    private String account_id_source;

    /** The created at. */
    private String created_at;

    /** The name. */
    private String name;

    /** The properties. */
    private Map<String, String> properties;

    private String userId;

    private String event;

    private String timestamp;

    /**
     * Gets the workspace id.
     *
     * @return the workspace id
     */
    public String getWorkspace_id()
    {
        return workspace_id;
    }

    /**
     * Sets the workspace id.
     *
     * @param workspace_id
     *            the new workspace id
     */
    public void setWorkspace_id(String workspace_id)
    {
        this.workspace_id = workspace_id;
    }

    /**
     * Gets the participant id.
     *
     * @return the participant id
     */
    public String getParticipant_id()
    {
        return participant_id;
    }

    /**
     * Sets the participant id.
     *
     * @param participant_id
     *            the new participant id
     */
    public void setParticipant_id(String participant_id)
    {
        this.participant_id = participant_id;
    }

    /**
     * Gets the participant id source.
     *
     * @return the participant id source
     */
    public String getParticipant_id_source()
    {
        return participant_id_source;
    }

    /**
     * Sets the participant id source.
     *
     * @param participant_id_source
     *            the new participant id source
     */
    public void setParticipant_id_source(String participant_id_source)
    {
        this.participant_id_source = participant_id_source;
    }

    /**
     * Gets the account id.
     *
     * @return the account id
     */
    public String getAccount_id()
    {
        return account_id;
    }

    /**
     * Sets the account id.
     *
     * @param account_id
     *            the new account id
     */
    public void setAccount_id(String account_id)
    {
        this.account_id = account_id;
    }

    /**
     * Gets the account id source.
     *
     * @return the account id source
     */
    public String getAccount_id_source()
    {
        return account_id_source;
    }

    /**
     * Sets the account id source.
     *
     * @param account_id_source
     *            the new account id source
     */
    public void setAccount_id_source(String account_id_source)
    {
        this.account_id_source = account_id_source;
    }

    /**
     * Gets the created at.
     *
     * @return the created at
     */
    public String getCreated_at()
    {
        return created_at;
    }

    /**
     * Sets the created at.
     *
     * @param created_at
     *            the new created at
     */
    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public Map<String, String> getProperties()
    {
        return properties;
    }

    /**
     * Sets the properties.
     *
     * @param properties
     *            the properties
     */
    public void setProperties(Map<String, String> properties)
    {
        this.properties = properties;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

}