package com.readthisstuff.rts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.readthisstuff.rts.domain.DocumentStuff;
import com.readthisstuff.rts.service.DocumentStuffService;
import com.readthisstuff.rts.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing DocumentStuff.
 */
@RestController
@RequestMapping("/api")
public class DocumentStuffResource {

    private final Logger log = LoggerFactory.getLogger(DocumentStuffResource.class);

    @Inject
    private DocumentStuffService documentStuffService;


    /**
     * GET  /document-stuffs : get all the documentStuffs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentStuffs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/document-stuffs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DocumentStuff>> getAllDocumentStuffs(Pageable pageable)
            throws URISyntaxException {
        log.debug("REST request to get a page of DocumentStuffs");
        Page<DocumentStuff> page = documentStuffService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-stuffs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
