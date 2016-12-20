package com.readthisstuff.rts.web.rest;

import com.google.common.collect.Sets;
import com.readthisstuff.rts.RtsApp;
import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.Content;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.domain.enumeration.ContentType;
import com.readthisstuff.rts.repository.AuthorRepository;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import com.readthisstuff.rts.service.AuthorService;
import com.readthisstuff.rts.service.DocumentRTSService;
import com.readthisstuff.rts.service.util.ImageService;
import com.readthisstuff.rts.web.rest.dto.document.DocumentPublishDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private static final String DEFAULT_AUTHOR_NAME = "DEFAULT_AUTHOR_NAME";

    private static final Author DEFAULT_AUTHOR = new Author();
    private static final Author UPDATED_AUTHOR = new Author();
    private static Set<Content> DEFAULT_CONTENT;
    private static final String DEFAULT_CONTENT_ID = "DEFAULT_CONTENT_ID ";
    private static final String DEFAULT_CONTENT_CONTENT = "DEFAULT_CONTENT_CONTENT";
    private static Set<Content> UPDATED_CONTENT;
    private static final String UPDATED_CONTENT_ID = "UPDATED_CONTENT_ID ";
    private static final String UPDATED_CONTENT_CONTENT = "UPDATED_CONTENT_CONTENT";

    private static final ContentType DEFAULT_TYPE = ContentType.ARTICLE;
    private static final ContentType UPDATED_TYPE = ContentType.INTERVIEW;

    private static final byte[] DEFAULT_THUMP = TestUtil.createByteArray(3, "010101");
    private static final byte[] UPDATED_THUMP = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_THUMP_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMP_CONTENT_TYPE = "image/png";


    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    private static final Integer DEFAULT_CLICKS = 0;
    private static final Integer UPDATED_CLICKS = 1;

    @Inject
    private DocumentRTSRepository documentRTSRepository;

    @Inject
    private AuthorRepository authorRepository;

    @Mock
    private ImageService mockImageService;

    @Mock
    private AuthorService mockAuthorService;


    @Inject
    private DocumentRTSService documentRTSService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDocumentRTSMockMvc;

    private DocumentRTS documentRTS;

    @PostConstruct
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        DEFAULT_AUTHOR.setUserName(DEFAULT_AUTHOR_NAME);
        when(mockAuthorService.createCurrentUserAsAuthor()).thenReturn(DEFAULT_AUTHOR);

        when(mockImageService.resizeThumb(any(byte[].class),anyInt(),anyInt(),anyString())).then(returnsFirstArg());

        DEFAULT_CONTENT = createContent(DEFAULT_CONTENT_ID, DEFAULT_CONTENT_CONTENT);
        UPDATED_CONTENT = createContent(UPDATED_CONTENT_ID, UPDATED_CONTENT_CONTENT);


        DocumentRTSResource documentRTSResource = new DocumentRTSResource();

        ReflectionTestUtils.setField(documentRTSService, "authorService", mockAuthorService);
        ReflectionTestUtils.setField(documentRTSService, "imageService", mockImageService);

        ReflectionTestUtils.setField(documentRTSResource, "documentRTSRepository", documentRTSRepository);
        ReflectionTestUtils.setField(documentRTSResource, "documentRTSService", documentRTSService);


        this.restDocumentRTSMockMvc = MockMvcBuilders.standaloneSetup(documentRTSResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    private Set<Content> createContent(String id, String content) {
        Content contentObj = new Content();
        contentObj.setId(id);
        contentObj.setContent(content);

        return Sets.newHashSet(contentObj);
    }

    private Content getContent(Set<Content> contentSet) {
        return contentSet.iterator().next();
    }


    @Before
    public void initTest() {
        authorRepository.deleteAll();
        documentRTSRepository.deleteAll();
        documentRTS = new DocumentRTS();
        documentRTS.setTitle(DEFAULT_TITLE);
        documentRTS.setContent(DEFAULT_CONTENT);
        documentRTS.setType(DEFAULT_TYPE);
        documentRTS.setThump(DEFAULT_THUMP);
        documentRTS.setThumpContentType(DEFAULT_THUMP_CONTENT_TYPE);

        documentRTS.setPublicationDate(DEFAULT_PUBLICATION_DATE);
        documentRTS.setPublished(DEFAULT_IS_PUBLIC);
        documentRTS.setClicks(DEFAULT_CLICKS);
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
        assertThat(testDocumentRTS.getAuthor().getUserName()).isEqualTo(DEFAULT_AUTHOR_NAME);

        Content content = getContent(testDocumentRTS.getContent());
        assertThat(content.getId()).isEqualTo(DEFAULT_CONTENT_ID);
        assertThat(content.getContent()).isEqualTo(DEFAULT_CONTENT_CONTENT);

        assertThat(testDocumentRTS.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDocumentRTS.getThump()).isEqualTo(DEFAULT_THUMP);
        assertThat(testDocumentRTS.getThumpContentType()).isEqualTo(DEFAULT_THUMP_CONTENT_TYPE);


        assertThat(testDocumentRTS.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testDocumentRTS.isIsPublic()).isEqualTo(DEFAULT_IS_PUBLIC);
        assertThat(testDocumentRTS.getClicks()).isEqualTo(DEFAULT_CLICKS);


    }

    /*not yet*/
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

    /*Test*/
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

    /*not yet*/
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

    /*not yet*/
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
        documentRTS.setAuthor(DEFAULT_AUTHOR);
        documentRTSRepository.save(documentRTS);

        // Get all the documentRTS
        restDocumentRTSMockMvc.perform(get("/api/document-rts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(documentRTS.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].author.userName").value(hasItem(DEFAULT_AUTHOR_NAME)))
                .andExpect(jsonPath("$.[*].content.[*].id").value(hasItem(DEFAULT_CONTENT_ID)))
                .andExpect(jsonPath("$.[*].content.[*].content").value(hasItem(DEFAULT_CONTENT_CONTENT)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].thumpContentType").value(hasItem(DEFAULT_THUMP_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].thump").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMP))))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())))
                .andExpect(jsonPath("$.[*].clicks").value(hasItem(DEFAULT_CLICKS)));

    }

    @Test
    public void getDocumentRTS() throws Exception {
        // Initialize the database
        documentRTS.setAuthor(DEFAULT_AUTHOR);
        documentRTSRepository.save(documentRTS);

        // Get the documentRTS
        restDocumentRTSMockMvc.perform(get("/api/document-rts/{id}", documentRTS.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(documentRTS.getId()))
                .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
                .andExpect(jsonPath("$.author.userName").value(DEFAULT_AUTHOR_NAME))
                .andExpect(jsonPath("$.content.[*].id").value(DEFAULT_CONTENT_ID))
                .andExpect(jsonPath("$.content.[*].content").value(DEFAULT_CONTENT_CONTENT))
                .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
                .andExpect(jsonPath("$.thumpContentType").value(DEFAULT_THUMP_CONTENT_TYPE))
                .andExpect(jsonPath("$.thump").value(Base64Utils.encodeToString(DEFAULT_THUMP)))
                .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
                .andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()))
                .andExpect(jsonPath("$.clicks").value(DEFAULT_CLICKS));
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
        updatedDocumentRTS.setPublicationDate(UPDATED_PUBLICATION_DATE);
        updatedDocumentRTS.setPublished(UPDATED_IS_PUBLIC);
        updatedDocumentRTS.setClicks(UPDATED_CLICKS);

        restDocumentRTSMockMvc.perform(put("/api/document-rts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDocumentRTS)))
                .andExpect(status().isOk());

        // Validate the DocumentRTS in the database
        List<DocumentRTS> documentRTS = documentRTSRepository.findAll();
        assertThat(documentRTS).hasSize(databaseSizeBeforeUpdate);
        DocumentRTS testDocumentRTS = documentRTS.get(documentRTS.size() - 1);
        assertThat(testDocumentRTS.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDocumentRTS.getAuthor().getUserName()).isEqualTo(DEFAULT_AUTHOR_NAME);

        Content content = getContent(testDocumentRTS.getContent());
        assertThat(content.getId()).isEqualTo(UPDATED_CONTENT_ID);
        assertThat(content.getContent()).isEqualTo(UPDATED_CONTENT_CONTENT);

        assertThat(testDocumentRTS.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDocumentRTS.getThump()).isEqualTo(UPDATED_THUMP);
        assertThat(testDocumentRTS.getThumpContentType()).isEqualTo(UPDATED_THUMP_CONTENT_TYPE);

        assertThat(testDocumentRTS.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testDocumentRTS.isIsPublic()).isEqualTo(UPDATED_IS_PUBLIC);
        assertThat(testDocumentRTS.getClicks()).isEqualTo(UPDATED_CLICKS);

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

    @Test
    public void publishDocumentRTS() throws Exception {
        DocumentRTS savedDoc = documentRTSRepository.save(documentRTS);
        int databaseSizeBeforeUpdate = documentRTSRepository.findAll().size();

        DocumentPublishDTO docPublish = new DocumentPublishDTO(savedDoc.getId(), true);


        // publish the documentRTS
        restDocumentRTSMockMvc.perform(put("/api/document-rts/publish")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docPublish)))
            .andExpect(status().isOk());

        // Validate the database size not change
        List<DocumentRTS> documentRTSList = documentRTSRepository.findAll();
        assertThat(documentRTSList).hasSize(databaseSizeBeforeUpdate);

        DocumentRTS testDocumentRTS = documentRTSList.get(documentRTSList.size() - 1);
        assertThat(testDocumentRTS.getPublished()).isTrue();
    }
}
