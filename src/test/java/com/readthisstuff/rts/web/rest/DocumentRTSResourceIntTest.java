package com.readthisstuff.rts.web.rest;

import com.readthisstuff.rts.RtsApp;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.repository.DocumentRTSRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.readthisstuff.rts.domain.enumeration.ContentType;

/**
 * Test class for the DocumentRTSResource REST controller.
 *
 * @see DocumentRTSResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RtsApp.class)
@WebAppConfiguration
@IntegrationTest
public class DocumentRTSResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_AUTHOR = "AAAAA";
    private static final String UPDATED_AUTHOR = "BBBBB";
    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";

    private static final ContentType DEFAULT_TYPE = ContentType.ARTICLE;
    private static final ContentType UPDATED_TYPE = ContentType.INTERVIEW;

    private static final byte[] DEFAULT_THUMP = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_THUMP = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_THUMP_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMP_CONTENT_TYPE = "image/png";

    @Inject
    private DocumentRTSRepository documentRTSRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDocumentRTSMockMvc;

    private DocumentRTS documentRTS;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DocumentRTSResource documentRTSResource = new DocumentRTSResource();
        ReflectionTestUtils.setField(documentRTSResource, "documentRTSRepository", documentRTSRepository);
        this.restDocumentRTSMockMvc = MockMvcBuilders.standaloneSetup(documentRTSResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        documentRTSRepository.deleteAll();
        documentRTS = new DocumentRTS();
        documentRTS.setTitle(DEFAULT_TITLE);
        documentRTS.setAuthor(DEFAULT_AUTHOR);
        documentRTS.setContent(DEFAULT_CONTENT);
        documentRTS.setType(DEFAULT_TYPE);
        documentRTS.setThump(DEFAULT_THUMP);
        documentRTS.setThumpContentType(DEFAULT_THUMP_CONTENT_TYPE);
    }

    @Test
    public void createDocumentRTS() throws Exception {
        int databaseSizeBeforeCreate = documentRTSRepository.findAll().size();

        // Create the DocumentRTS

        restDocumentRTSMockMvc.perform(post("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentRTS)))
                .andExpect(status().isCreated());

        // Validate the DocumentRTS in the database
        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeCreate + 1);
        DocumentRTS testDocumentRTS = documentRTS.get(documentRTS.size() - 1);
        assertThat(testDocumentRTS.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDocumentRTS.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testDocumentRTS.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDocumentRTS.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDocumentRTS.getThump()).isEqualTo(DEFAULT_THUMP);
        assertThat(testDocumentRTS.getThumpContentType()).isEqualTo(DEFAULT_THUMP_CONTENT_TYPE);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRTSRepository.findAll().size();
        // set the field null
        documentRTS.setTitle(null);

        // Create the DocumentRTS, which fails.

        restDocumentRTSMockMvc.perform(post("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentRTS)))
                .andExpect(status().isBadRequest());

        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRTSRepository.findAll().size();
        // set the field null
        documentRTS.setAuthor(null);

        // Create the DocumentRTS, which fails.

        restDocumentRTSMockMvc.perform(post("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentRTS)))
                .andExpect(status().isBadRequest());

        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRTSRepository.findAll().size();
        // set the field null
        documentRTS.setContent(null);

        // Create the DocumentRTS, which fails.

        restDocumentRTSMockMvc.perform(post("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentRTS)))
                .andExpect(status().isBadRequest());

        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRTSRepository.findAll().size();
        // set the field null
        documentRTS.setType(null);

        // Create the DocumentRTS, which fails.

        restDocumentRTSMockMvc.perform(post("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentRTS)))
                .andExpect(status().isBadRequest());

        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkThumpIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRTSRepository.findAll().size();
        // set the field null
        documentRTS.setThump(null);

        // Create the DocumentRTS, which fails.

        restDocumentRTSMockMvc.perform(post("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentRTS)))
                .andExpect(status().isBadRequest());

        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDocumentRTS() throws Exception {
        // Initialize the database
        documentRTSRepository.save(documentRTS);

        // Get all the documentRTS
        restDocumentRTSMockMvc.perform(get("/api/document-rts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(documentRTS.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].thumpContentType").value(hasItem(DEFAULT_THUMP_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].thump").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMP))));
    }

    @Test
    public void getDocumentRTS() throws Exception {
        // Initialize the database
        documentRTSRepository.save(documentRTS);

        // Get the documentRTS
        restDocumentRTSMockMvc.perform(get("/api/document-rts/{id}", documentRTS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(documentRTS.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.thumpContentType").value(DEFAULT_THUMP_CONTENT_TYPE))
            .andExpect(jsonPath("$.thump").value(Base64Utils.encodeToString(DEFAULT_THUMP)));
    }

    @Test
    public void getNonExistingDocumentRTS() throws Exception {
        // Get the documentRTS
        restDocumentRTSMockMvc.perform(get("/api/document-rts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDocumentRTS() throws Exception {
        // Initialize the database
        documentRTSRepository.save(documentRTS);
        int databaseSizeBeforeUpdate = documentRTSRepository.findAll().size();

        // Update the documentRTS
        DocumentRTS updatedDocumentRTS = new DocumentRTS();
        updatedDocumentRTS.setId(documentRTS.getId());
        updatedDocumentRTS.setTitle(UPDATED_TITLE);
        updatedDocumentRTS.setAuthor(UPDATED_AUTHOR);
        updatedDocumentRTS.setContent(UPDATED_CONTENT);
        updatedDocumentRTS.setType(UPDATED_TYPE);
        updatedDocumentRTS.setThump(UPDATED_THUMP);
        updatedDocumentRTS.setThumpContentType(UPDATED_THUMP_CONTENT_TYPE);

        restDocumentRTSMockMvc.perform(put("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDocumentRTS)))
                .andExpect(status().isOk());

        // Validate the DocumentRTS in the database
        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeUpdate);
        DocumentRTS testDocumentRTS = documentRTS.get(documentRTS.size() - 1);
        assertThat(testDocumentRTS.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDocumentRTS.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testDocumentRTS.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDocumentRTS.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDocumentRTS.getThump()).isEqualTo(UPDATED_THUMP);
        assertThat(testDocumentRTS.getThumpContentType()).isEqualTo(UPDATED_THUMP_CONTENT_TYPE);
    }

    @Test
    public void deleteDocumentRTS() throws Exception {
        // Initialize the database
        documentRTSRepository.save(documentRTS);
        int databaseSizeBeforeDelete = documentRTSRepository.findAll().size();

        // Get the documentRTS
        restDocumentRTSMockMvc.perform(delete("/api/document-rts/{id}", documentRTS.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeDelete - 1);
    }
}
