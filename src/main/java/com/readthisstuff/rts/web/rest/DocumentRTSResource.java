package com.readthisstuff.rts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import com.readthisstuff.rts.service.AuthorService;
import com.readthisstuff.rts.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DocumentRTS.
 */
@RestController
@RequestMapping("/api")
public class DocumentRTSResource {

    private final Logger log = LoggerFactory.getLogger(DocumentRTSResource.class);

    @Inject
    private DocumentRTSRepository documentRTSRepository;

    @Inject
    private AuthorService authorService;


    /**
     * POST  /document-rts : Create a new documentRTS.
     *
     * @param documentRTS the documentRTS to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentRTS, or with status 400 (Bad Request) if the documentRTS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/document-rts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DocumentRTS> createDocumentRTS(@Valid @RequestBody DocumentRTS documentRTS) throws URISyntaxException {
        log.debug("REST request to save DocumentRTS : {}", documentRTS);
        if (documentRTS.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("documentRTS", "idexists", "A new documentRTS cannot already have an ID")).body(null);
        }

        DocumentRTS result = saveDocument(documentRTS);
        return ResponseEntity.created(new URI("/api/document-rts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("documentRTS", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /document-rts : Updates an existing documentRTS.
     *
     * @param documentRTS the documentRTS to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentRTS,
     * or with status 400 (Bad Request) if the documentRTS is not valid,
     * or with status 500 (Internal Server Error) if the documentRTS couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/document-rts",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DocumentRTS> updateDocumentRTS(@Valid @RequestBody DocumentRTS documentRTS) throws URISyntaxException {
        log.debug("REST request to update DocumentRTS : {}", documentRTS);
        if (documentRTS.getId() == null) {
            return createDocumentRTS(documentRTS);
        }
        DocumentRTS result = saveDocument(documentRTS);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("documentRTS", documentRTS.getId().toString()))
                .body(result);
    }


    private DocumentRTS saveDocument(DocumentRTS documentRTS) {
        Author author = authorService.createCurrentUserAsAuthor();
        documentRTS.setAuthor(author);
        return documentRTSRepository.save(documentRTS);
    }


    /**
     * GET  /document-rts : get all the documentRTS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of documentRTS in body
     */
    @RequestMapping(value = "/document-rts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DocumentRTS> getAllDocumentRTS() {
        log.debug("REST request to get all DocumentRTS");
        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        return documentRTS;
    }

    /**
     * GET  /document-rts/:id : get the "id" documentRTS.
     *
     * @param id the id of the documentRTS to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentRTS, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/document-rts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DocumentRTS> getDocumentRTS(@PathVariable String id) {
        log.debug("REST request to get DocumentRTS : {}", id);
        DocumentRTS documentRTS = documentRTSRepository.findOne(id);
        return Optional.ofNullable(documentRTS)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /document-rts/:id : delete the "id" documentRTS.
     *
     * @param id the id of the documentRTS to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/document-rts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDocumentRTS(@PathVariable String id) {
        log.debug("REST request to delete DocumentRTS : {}", id);
        documentRTSRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("documentRTS", id.toString())).build();
    }

}
