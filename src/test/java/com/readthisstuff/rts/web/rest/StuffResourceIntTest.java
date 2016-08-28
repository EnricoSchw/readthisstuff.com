package com.readthisstuff.rts.web.rest;

import com.readthisstuff.rts.RtsApp;
import com.readthisstuff.rts.domain.Stuff;
import com.readthisstuff.rts.repository.StuffRepository;
import com.readthisstuff.rts.web.rest.dto.StuffDTO;
import com.readthisstuff.rts.web.rest.mapper.StuffMapper;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StuffResource REST controller.
 *
 * @see StuffResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RtsApp.class)
@WebAppConfiguration
@IntegrationTest
public class StuffResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CLICKS = 1;
    private static final Integer UPDATED_CLICKS = 2;
    private static final String DEFAULT_AUTHOR = "AAAAA";
    private static final String UPDATED_AUTHOR = "BBBBB";

    @Inject
    private StuffRepository stuffRepository;

    @Inject
    private StuffMapper stuffMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStuffMockMvc;

    private Stuff stuff;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StuffResource stuffResource = new StuffResource();
        ReflectionTestUtils.setField(stuffResource, "stuffRepository", stuffRepository);
        ReflectionTestUtils.setField(stuffResource, "stuffMapper", stuffMapper);
        this.restStuffMockMvc = MockMvcBuilders.standaloneSetup(stuffResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stuffRepository.deleteAll();
        stuff = new Stuff();
        stuff.setTitle(DEFAULT_TITLE);
        stuff.setDescription(DEFAULT_DESCRIPTION);
        stuff.setPublicationDate(DEFAULT_PUBLICATION_DATE);
        stuff.setClicks(DEFAULT_CLICKS);
        stuff.setAuthor(DEFAULT_AUTHOR);
    }

    @Test
    public void createStuff() throws Exception {
        int databaseSizeBeforeCreate = stuffRepository.findAll().size();

        // Create the Stuff
        StuffDTO stuffDTO = stuffMapper.stuffToStuffDTO(stuff);

        restStuffMockMvc.perform(post("/api/stuffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stuffDTO)))
                .andExpect(status().isCreated());

        // Validate the Stuff in the database
        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeCreate + 1);
        Stuff testStuff = stuffs.get(stuffs.size() - 1);
        assertThat(testStuff.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStuff.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStuff.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testStuff.getClicks()).isEqualTo(DEFAULT_CLICKS);
        assertThat(testStuff.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = stuffRepository.findAll().size();
        // set the field null
        stuff.setTitle(null);

        // Create the Stuff, which fails.
        StuffDTO stuffDTO = stuffMapper.stuffToStuffDTO(stuff);

        restStuffMockMvc.perform(post("/api/stuffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stuffDTO)))
                .andExpect(status().isBadRequest());

        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = stuffRepository.findAll().size();
        // set the field null
        stuff.setDescription(null);

        // Create the Stuff, which fails.
        StuffDTO stuffDTO = stuffMapper.stuffToStuffDTO(stuff);

        restStuffMockMvc.perform(post("/api/stuffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stuffDTO)))
                .andExpect(status().isBadRequest());

        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPublicationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stuffRepository.findAll().size();
        // set the field null
        stuff.setPublicationDate(null);

        // Create the Stuff, which fails.
        StuffDTO stuffDTO = stuffMapper.stuffToStuffDTO(stuff);

        restStuffMockMvc.perform(post("/api/stuffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stuffDTO)))
                .andExpect(status().isBadRequest());

        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkClicksIsRequired() throws Exception {
        int databaseSizeBeforeTest = stuffRepository.findAll().size();
        // set the field null
        stuff.setClicks(null);

        // Create the Stuff, which fails.
        StuffDTO stuffDTO = stuffMapper.stuffToStuffDTO(stuff);

        restStuffMockMvc.perform(post("/api/stuffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stuffDTO)))
                .andExpect(status().isBadRequest());

        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = stuffRepository.findAll().size();
        // set the field null
        stuff.setAuthor(null);

        // Create the Stuff, which fails.
        StuffDTO stuffDTO = stuffMapper.stuffToStuffDTO(stuff);

        restStuffMockMvc.perform(post("/api/stuffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stuffDTO)))
                .andExpect(status().isBadRequest());

        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllStuffs() throws Exception {
        // Initialize the database
        stuffRepository.save(stuff);

        // Get all the stuffs
        restStuffMockMvc.perform(get("/api/stuffs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stuff.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].clicks").value(hasItem(DEFAULT_CLICKS)))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())));
    }

    @Test
    public void getStuff() throws Exception {
        // Initialize the database
        stuffRepository.save(stuff);

        // Get the stuff
        restStuffMockMvc.perform(get("/api/stuffs/{id}", stuff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stuff.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.clicks").value(DEFAULT_CLICKS))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()));
    }

    @Test
    public void getNonExistingStuff() throws Exception {
        // Get the stuff
        restStuffMockMvc.perform(get("/api/stuffs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStuff() throws Exception {
        // Initialize the database
        stuffRepository.save(stuff);
        int databaseSizeBeforeUpdate = stuffRepository.findAll().size();

        // Update the stuff
        Stuff updatedStuff = new Stuff();
        updatedStuff.setId(stuff.getId());
        updatedStuff.setTitle(UPDATED_TITLE);
        updatedStuff.setDescription(UPDATED_DESCRIPTION);
        updatedStuff.setPublicationDate(UPDATED_PUBLICATION_DATE);
        updatedStuff.setClicks(UPDATED_CLICKS);
        updatedStuff.setAuthor(UPDATED_AUTHOR);
        StuffDTO stuffDTO = stuffMapper.stuffToStuffDTO(updatedStuff);

        restStuffMockMvc.perform(put("/api/stuffs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stuffDTO)))
                .andExpect(status().isOk());

        // Validate the Stuff in the database
        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeUpdate);
        Stuff testStuff = stuffs.get(stuffs.size() - 1);
        assertThat(testStuff.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStuff.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStuff.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testStuff.getClicks()).isEqualTo(UPDATED_CLICKS);
        assertThat(testStuff.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    public void deleteStuff() throws Exception {
        // Initialize the database
        stuffRepository.save(stuff);
        int databaseSizeBeforeDelete = stuffRepository.findAll().size();

        // Get the stuff
        restStuffMockMvc.perform(delete("/api/stuffs/{id}", stuff.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Stuff> stuffs = stuffRepository.findAll();
        assertThat(stuffs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
