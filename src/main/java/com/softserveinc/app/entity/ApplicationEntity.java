package com.softserveinc.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.io.Serializable;

@Entity
@Table(name = "APPLICATIONS")
public class ApplicationEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "content_rate", nullable = false)
    private Integer content_rate;

    public ApplicationEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getContent_rate() {
        return content_rate;
    }

    public void setContent_rate(Integer content_rate) {
        this.content_rate = content_rate;
    }
}
