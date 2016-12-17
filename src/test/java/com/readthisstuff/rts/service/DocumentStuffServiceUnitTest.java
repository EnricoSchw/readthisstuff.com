package com.readthisstuff.rts.service;


import com.google.common.collect.Sets;
import com.readthisstuff.rts.RtsApp;
import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.Content;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.domain.DocumentStuff;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.readthisstuff.rts.helper.creator.DocumentRTSCreator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RtsApp.class)
public class DocumentStuffServiceUnitTest {

    private PageImpl<DocumentRTS> documentPage;

    @Mock
    private DocumentRTSRepository documentRTSRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private DocumentStuffService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        DocumentRTS doc1 = createDocument("1");
        DocumentRTS doc2 = createDocument("2");
        List<DocumentRTS> docList = Arrays.asList(doc1, doc2);
        documentPage = new PageImpl<>(docList);
        when(documentRTSRepository.findAll(any(Pageable.class))).thenReturn(documentPage);
    }


    @Test
    public void findAll() {

        //when
        Page<DocumentStuff> suffPage = service.findAll(pageable);

        //then
        Iterator<DocumentStuff> iterator = suffPage.iterator();
        DocumentStuff stuff1 = iterator.next();
        DocumentStuff stuff2 = iterator.next();

        assertThat(stuff1).isNotNull();
        assertThat(stuff2).isNotNull();
        assertThat(stuff1.getId()).isEqualTo("1");
        assertThat(stuff1.getAuthor()).isEqualTo(DEFAULT_AUTHOR_NAME + "1");
        assertThat(stuff1.getAuthorId()).isEqualTo(DEFAULT_AUTHOR_ID);
        assertThat(stuff1.getClicks()).isEqualTo(DEFAULT_CLICKS);
        assertThat(stuff1.getDescription()).isEqualTo(DEFAULT_CONTENT_CONTENT + "1");
        assertThat(stuff1.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(stuff1.getThump()).isEqualTo(DEFAULT_THUMP);
        assertThat(stuff1.getThumpContentType()).isEqualTo(DEFAULT_THUMP_CONTENT_TYPE);
        assertThat(stuff1.getTitle()).isEqualTo(DEFAULT_TITLE + "1");

        assertThat(stuff2.getId()).isEqualTo("2");
    }

}
