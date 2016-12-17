package com.readthisstuff.rts.domain;

import com.readthisstuff.rts.domain.enumeration.ContentType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * A DocumentRTS.
 */

@Document(collection = "document_rts")
public class DocumentRTS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 20)
    @Field("title")
    private String title;

    //NotNull//
    @Field("author")
    private Author author;

    @NotNull
    @Field("content")
    private Set<Content> content;

    //NotNull
    @Field("type")
    private ContentType type;

    //NotNull
    @Field("thump")
    private byte[] thump;

    @Field("thump_content_type")
    private String thumpContentType;

    @Field("publication_date")
    private LocalDate publicationDate;

    @Field("is_public")
    private Boolean isPublic;

    //Min(value = 0)
    @Field("clicks")
    private Integer clicks;


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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Content> getContent() {
        return content;
    }

    public void setContent(Set<Content> content) {
        this.content = content;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentRTS documentRTS = (DocumentRTS) o;
        if (documentRTS.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, documentRTS.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DocumentRTS{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", author='" + author + "'" +
                ", content='" + content + "'" +
                ", type='" + type + "'" +
                ", thump='" + thump + "'" +
                ", thumpContentType='" + thumpContentType + "'" +
                ", publicationDate='" + publicationDate + "'" +
                ", isPublic='" + isPublic + "'" +
                ", clicks='" + clicks + "'" +
                '}';
    }

    public Boolean getIsPublic() {
        return isPublic;
    }
}
