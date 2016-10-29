package com.readthisstuff.rts.domain;

import com.readthisstuff.rts.domain.enumeration.ContentType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
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

    //NotNull
    @Field("title")
    private String title;

    //NotNull
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
                '}';
    }
}