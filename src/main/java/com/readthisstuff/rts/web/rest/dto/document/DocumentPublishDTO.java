package com.readthisstuff.rts.web.rest.dto.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.readthisstuff.rts.domain.DocumentRTS;

import javax.validation.constraints.NotNull;

public class DocumentPublishDTO {


    @NotNull
    private Boolean published;

    @NotNull
    private String id;

    public DocumentPublishDTO(DocumentRTS documentRTS) {
        this.published = documentRTS.getPublished();
        this.id = documentRTS.getId();
    }

    public DocumentPublishDTO(String id, Boolean published) {
        this.published = published;
        this.id = id;
    }

    @JsonCreator
    public DocumentPublishDTO() {
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DocumentPublishDTO{" +
            "published='" + published + '\'' +
            ", id='" + id + '\'' +
            '}';
    }
}
