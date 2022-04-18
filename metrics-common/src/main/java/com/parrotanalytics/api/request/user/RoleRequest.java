package com.parrotanalytics.api.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

public class RoleRequest extends APIRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -3838056358836066206L;

    @JsonProperty("role_name")
    protected String roleName;

    protected OperationType roleAction;

    public OperationType getRoleAction()
    {
        return roleAction;
    }

    public void setRoleAction(OperationType roleAction)
    {
        this.roleAction = roleAction;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

}
