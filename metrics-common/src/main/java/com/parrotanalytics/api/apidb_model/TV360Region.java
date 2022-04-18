package com.parrotanalytics.api.apidb_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "regions", schema = "subscription")
public class TV360Region extends RDSBaseEntity
{

    private static final long serialVersionUID = -6449618549889839039L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name = "country")
    private String country;

    @JsonProperty("id_user")
    @Column(name = "id_user")
    private Integer idUser;

    @JsonProperty("is_preset")
    @Column(name = "is_preset")
    private Integer isPreset;

    @JsonProperty("active")
    @Column(name = "active")
    private Integer active;

    @JsonProperty("updated_on")
    @Column(name = "updated_on")
    private Date updatedOn;
    
    @JsonProperty("flag")
    @Column(name="flag")
    private String flag;

    @Transient
    @JsonProperty("country")
    public List<String> getCountryList()
    {
        if (StringUtils.isNotEmpty(country))
        {
            return Arrays.asList(country.split(",")).stream().map(c -> c.trim()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Transient
    public void setCountryList(List<String> countryList)
    {
        if (CollectionUtils.isNotEmpty(countryList))
        {
            this.country = StringUtils.join(countryList, ",");
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
        result = prime * result + ((isPreset == null) ? 0 : isPreset.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TV360Region other = (TV360Region) obj;
        if (active == null)
        {
            if (other.active != null)
                return false;
        }
        else if (!active.equals(other.active))
            return false;
        if (country == null)
        {
            if (other.country != null)
                return false;
        }
        else if (!country.equals(other.country))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (idUser == null)
        {
            if (other.idUser != null)
                return false;
        }
        else if (!idUser.equals(other.idUser))
            return false;
        if (isPreset == null)
        {
            if (other.isPreset != null)
                return false;
        }
        else if (!isPreset.equals(other.isPreset))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

}
