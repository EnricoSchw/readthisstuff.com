package com.readthisstuff.rts.web.rest.dto.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.readthisstuff.rts.domain.DocumentRTS;

import javax.validation.constraints.NotNull;

/**
 * Created by enrico on 16.12.16.
 */
public class PublishDTO {


    @NotNull
    private Boolean istPublish;

    @NotNull
    private String id;

    public PublishDTO(DocumentRTS documentRTS) {
        this.istPublish = documentRTS.getIsPublic();
        this.id = documentRTS.getId();
    }

    public PublishDTO(String id, Boolean istPublish) {
        this.istPublish = istPublish;
        this.id = id;
    }

    @JsonCreator
    public PublishDTO() {
    }

    public Boolean getIstPublish() {
        return istPublish;
    }

    public void setIstPublish(Boolean istPublish) {
        this.istPublish = istPublish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PublishDTO{" +
            "istPublish='" + istPublish + '\'' +
            ", id='" + id + '\'' +
            '}';
    }
}
