package com.readthisstuff.rts.service;


import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import com.readthisstuff.rts.service.util.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class DocumentRTSService {


    private final Logger log = LoggerFactory.getLogger(DocumentRTSService.class);
    private final static int THUMB_WIDTH = 700;
    private final static int THUMB_HEIGHT = 400;

    @Inject
    private DocumentRTSRepository documentRTSRepository;

    @Inject
    private AuthorService authorService;

    @Inject
    private ImageService imageService;

    public DocumentRTS saveDocument(DocumentRTS doc) {
        Author author = authorService.createCurrentUserAsAuthor();
        doc.setAuthor(author);

        byte[] img = optimizeThumb(doc.getThump(), doc.getThumpContentType());
        doc.setThump(img);
        return documentRTSRepository.save(doc);
    }

    public DocumentRTS publishDocument(DocumentRTS doc, boolean isPublish) {
        doc.setPublished(isPublish);

        if (isPublish) {
            doc.setPublicationDate(LocalDate.now());
        } else {
            doc.setPublicationDate(null);
        }

        return documentRTSRepository.save(doc);
    }

    private byte[] optimizeThumb(final byte[] img, final String imgType) {
        try {
            return imageService.resizeThumb(img, THUMB_WIDTH, THUMB_HEIGHT, imgType);
        } catch (IOException e) {
            log.debug("Optimize Thumb something goes wrong: {}", e);
        }
        return img;
    }
}
