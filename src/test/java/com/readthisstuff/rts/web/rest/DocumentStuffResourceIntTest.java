package com.readthisstuff.rts.web.rest;

import com.google.common.collect.Sets;
import com.readthisstuff.rts.RtsApp;
import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.Content;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.domain.enumeration.ContentType;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import com.readthisstuff.rts.service.DocumentStuffService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DocumentStuffResource REST controller.
 *
 * @see DocumentStuffResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RtsApp.class)
@WebAppConfiguration
@IntegrationTest
public class DocumentStuffResourceIntTest {


    private static Set<Content> DEFAULT_CONTENT;
    private static final String DEFAULT_CONTENT_ID = "DEFAULT_CONTENT_ID ";
    private static final String DEFAULT_CONTENT_CONTENT = "DEFAULT_CONTENT_CONTENT";
    private static final ContentType DEFAULT_TYPE = ContentType.ARTICLE;

    private static final byte[] DEFAULT_THUMP = TestUtil.createByteArray(1, "0");
    private static final String DEFAULT_THUMP_CONTENT_TYPE = "image/jpg";

    private static final String DEFAULT_TITLE = "TITLE";
    private static final String DEFAULT_DESCRIPTION = "DEFAULT_CONTENT_CONTENT";
    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Integer DEFAULT_CLICKS = 0;

    private static final Author DEFAULT_AUTHOR = new Author();
    private static final String DEFAULT_AUTHOR_NAME = "AUTHOR_NAME";
    private static final String DEFAULT_AUTHOR_ID = "AUTHOR_ID";

    @Inject
    private DocumentRTSRepository documentRTSRepository;

    @Inject
    private DocumentStuffService documentStuffService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDocumentStuffMockMvc;

    private DocumentRTS documentRTS;

    @PostConstruct
    public void setup() {

        DEFAULT_AUTHOR.setUserName(DEFAULT_AUTHOR_NAME);
        DEFAULT_AUTHOR.setId(DEFAULT_AUTHOR_ID);
        DEFAULT_CONTENT = createContent(DEFAULT_CONTENT_ID, DEFAULT_CONTENT_CONTENT);

        MockitoAnnotations.initMocks(this);
        DocumentStuffResource documentStuffResource = new DocumentStuffResource();
        ReflectionTestUtils.setField(documentStuffResource, "documentStuffService", documentStuffService);
        this.restDocumentStuffMockMvc = MockMvcBuilders.standaloneSetup(documentStuffResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    private Set<Content> createContent(String id, String content) {
        Content contentObj = new Content();
        contentObj.setId(id);
        contentObj.setContent(content);

        return Sets.newHashSet(contentObj);
    }


    @Before
    public void initTest() {
        documentRTSRepository.deleteAll();
        documentRTS = new DocumentRTS();
        documentRTS.setTitle(DEFAULT_TITLE);
        documentRTS.setContent(DEFAULT_CONTENT);
        documentRTS.setAuthor(DEFAULT_AUTHOR);
        documentRTS.setType(DEFAULT_TYPE);
        documentRTS.setThump(DEFAULT_THUMP);
        documentRTS.setThumpContentType(DEFAULT_THUMP_CONTENT_TYPE);

        documentRTS.setPublicationDate(DEFAULT_PUBLICATION_DATE);
        documentRTS.setIsPublic(DEFAULT_IS_PUBLIC);
        documentRTS.setClicks(DEFAULT_CLICKS);
    }


    @Test
    public void getAllDocumentStuffs() throws Exception {
        // Initialize the database
        documentRTSRepository.save(documentRTS);

        // Get all the documentStuffs
        restDocumentStuffMockMvc.perform(get("/api/document-stuffs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(documentRTS.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())))
                .andExpect(jsonPath("$.[*].clicks").value(hasItem(DEFAULT_CLICKS)))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR_NAME)))
                .andExpect(jsonPath("$.[*].authorId").value(hasItem(DEFAULT_AUTHOR_ID)));
    }


    @Test
    public void getNonExistingDocumentStuff() throws Exception {
        // Get the documentStuff
        restDocumentStuffMockMvc.perform(get("/api/document-stuffs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

}
