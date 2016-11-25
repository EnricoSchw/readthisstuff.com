package com.readthisstuff.rts.service;

import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.Content;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.domain.DocumentStuff;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * Service Implementation for managing DocumentStuff.
 */
@Service
public class DocumentStuffService {

    private final Logger log = LoggerFactory.getLogger(DocumentStuffService.class);


    @Inject
    private DocumentRTSRepository documentRTSRepository;


    /**
     * Get all the documentStuffs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<DocumentStuff> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStuffs");
        Page<DocumentRTS> documents = documentRTSRepository.findAll(pageable);

        Page<DocumentStuff> stuffPage = documents.map(doc -> {

            //Mapping document tu stuff
            DocumentStuff stuff = new DocumentStuff();
            stuff.setId(doc.getId());
            stuff.setClicks(doc.getClicks());
            stuff.setPublicationDate(doc.getPublicationDate());
            stuff.setIsPublic(doc.isIsPublic());
            stuff.setThump(doc.getThump());
            stuff.setThumpContentType(doc.getThumpContentType());
            stuff.setTitle(doc.getTitle());

            setAuthorData(stuff, doc);
            setContent(stuff, doc);

            return stuff;
        });
        return stuffPage;
    }


    private void setAuthorData(DocumentStuff stuff, DocumentRTS doc) {
        Author author = doc.getAuthor();
        if (author != null) {
            stuff.setAuthor(author.getUserName());
            stuff.setAuthorId(author.getId());
        } else {
            stuff.setAuthor("unknown");
            stuff.setAuthorId("-1");
        }
    }

    private void setContent(DocumentStuff stuff, DocumentRTS doc) {
        Set<Content> contents = doc.getContent();
        if (!contents.isEmpty()) {
            Content con = contents.iterator().next();
            String description = con.getContent();
            stuff.setDescription(description);
        } else {
            stuff.setDescription("No Content");
        }
    }
}
