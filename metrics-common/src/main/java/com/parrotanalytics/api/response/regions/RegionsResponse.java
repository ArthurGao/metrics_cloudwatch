package com.parrotanalytics.api.response.regions;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.TV360Region;
import com.parrotanalytics.apicore.model.response.APIResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionsResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 5521778963726133667L;

    @JsonProperty("custom_regions")
    private List<TV360Region> customRegions;

    @JsonProperty("preset_regions")
    private List<TV360Region> presetRegions;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private List<String> country;

    @JsonProperty("is_preset")
    private Integer isPreset;

    @JsonProperty("id_user")
    private Integer idUser;

    @JsonProperty("updated_on")
    private Date updatedOn;
    
    @JsonProperty("flag")
    private String flag;

}
