package com.parrotanalytics.api.request.customersuccess;

import java.util.List;
import java.util.Map;

public class AmityRequest
{
    public final static String WORKSPACE_ID = "5989b7c5f7b90322f49abab5";
    public final static String SOURCE_TYPE = "ctp.app";
    public final static String KEY = "Basic NTk5ZTAzMzZmZDQwZTE2ZGQ2N2Q0ODY1OkFXWUw3cGw4SnMyZFBleXFueTFYQ2dqWQ==";
    public final static String ACCOUNT_ENDPOINT = "https://app.getamity.com/rest/v1/activities/identify_account";
    public final static String PATCH_ENDPOINT = "https://app.getamity.com/rest/v1/activities/patch_participant";
    public final static String USER_ENDPOINT = "https://app.getamity.com/rest/v1/activities/identify_participant";

    public String workspace_id = WORKSPACE_ID;
    public String account_id_source = SOURCE_TYPE;

    private String entity_id;
    private String entity_id_source;
    private String participant_id;
    private String participant_id_source;
    private String account_id;
    private String name;
    private String created_at;
    private String email;
    private String first_name;
    private String last_name;
    private List<AmityProperty> patch;
    private Map<String, String> properties;

    public String getEntity_id()
    {
        return entity_id;
    }

    public void setEntity_id(String entity_id)
    {
        this.entity_id = entity_id;
    }

    public String getEntity_id_source()
    {
        return entity_id_source;
    }

    public void setEntity_id_source(String entity_id_source)
    {
        this.entity_id_source = entity_id_source;
    }

    public String getParticipant_id()
    {
        return participant_id;
    }

    public void setParticipant_id(String participant_id)
    {
        this.participant_id = participant_id;
    }

    public String getParticipant_id_source()
    {
        return participant_id_source;
    }

    public void setParticipant_id_source(String participant_id_source)
    {
        this.participant_id_source = participant_id_source;
    }

    public String getAccount_id()
    {
        return account_id;
    }

    public void setAccount_id(String account_id)
    {
        this.account_id = account_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public Map<String, String> getProperties()
    {
        return properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        this.properties = properties;
    }

    public List<AmityProperty> getPatch()
    {
        return patch;
    }

    public void setPatch(List<AmityProperty> patch)
    {
        this.patch = patch;
    }

}