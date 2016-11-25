package com.readthisstuff.rts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DocumentStuff.
 */

@Document(collection = "document_stuff")
public class DocumentStuff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 20)
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("publication_date")
    private LocalDate publicationDate;

    @Field("is_public")
    private Boolean isPublic;

    @Min(value = 0)
    @Field("clicks")
    private Integer clicks;

    @Field("author")
    private String author;

    @Field("author_id")
    private String authorId;

    //NotNull
    @Field("thump")
    private byte[] thump;

    @Field("thump_content_type")
    private String thumpContentType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }


    public byte[] getThump() {
        return thump;
    }

    public void setThump(byte[] thump) {
        this.thump = thump;
    }

    public String getThumpContentType() {
        return thumpContentType;
    }

    public void setThumpContentType(String thumpContentType) {
        this.thumpContentType = thumpContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentStuff documentStuff = (DocumentStuff) o;
        if (documentStuff.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, documentStuff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DocumentStuff{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", description='" + description + "'" +
                ", publicationDate='" + publicationDate + "'" +
                ", isPublic='" + isPublic + "'" +
                ", clicks='" + clicks + "'" +
                ", author='" + author + "'" +
                ", authorId='" + authorId + "'" +
                '}';
    }
}
