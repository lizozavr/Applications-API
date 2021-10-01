package com.softserveinc.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationDTO {
    private Integer id;
    private String name;
    private String version;
    private Integer content_rate;

    public ApplicationDTO(){}

    public ApplicationDTO(Integer id, String name, String version, Integer content_rate) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.content_rate = content_rate;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getVersion() {
        return version;
    }

    @JsonProperty
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty
    public Integer getContent_rate() {
        return content_rate;
    }

    @JsonProperty
    public void setContent_rate(Integer content_rate) {
        this.content_rate = content_rate;
    }
}
