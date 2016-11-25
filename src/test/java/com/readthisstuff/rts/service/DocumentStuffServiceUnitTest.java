package com.readthisstuff.rts.service;


import com.google.common.collect.Sets;
import com.readthisstuff.rts.RtsApp;
import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.Content;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.domain.DocumentStuff;
import com.readthisstuff.rts.domain.enumeration.ContentType;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import com.readthisstuff.rts.web.rest.TestUtil;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RtsApp.class)
public class DocumentStuffServiceUnitTest {


    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String DEFAULT_AUTHOR_NAME = "DEFAULT_AUTHOR_NAME";
    private static final String DEFAULT_AUTHOR_ID = "DEFAULT_AUTHOR_ID";

    private static final String DEFAULT_CONTENT_ID = "DEFAULT_CONTENT_ID ";
    private static final String DEFAULT_CONTENT_CONTENT = "DEFAULT_CONTENT_CONTENT";

    private static final ContentType DEFAULT_TYPE = ContentType.ARTICLE;

    private static final byte[] DEFAULT_THUMP = TestUtil.createByteArray(1, "0");
    private static final String DEFAULT_THUMP_CONTENT_TYPE = "image/jpg";

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);

    private static final Boolean DEFAULT_IS_PUBLIC = false;

    private static final Integer DEFAULT_CLICKS = 0;


    private DocumentRTS documentRTS;
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


    private DocumentRTS createDocument(String number) {
        Author author = new Author();
        author.setUserName(DEFAULT_AUTHOR_NAME + number);
        author.setId(DEFAULT_AUTHOR_ID);


        Set<Content> content = createContent(DEFAULT_CONTENT_ID + number, DEFAULT_CONTENT_CONTENT + number);

        documentRTS = new DocumentRTS();
        documentRTS.setId(number);
        documentRTS.setTitle(DEFAULT_TITLE + number);
        documentRTS.setContent(content);
        documentRTS.setAuthor(author);
        documentRTS.setType(DEFAULT_TYPE);
        documentRTS.setThump(DEFAULT_THUMP);
        documentRTS.setThumpContentType(DEFAULT_THUMP_CONTENT_TYPE);

        documentRTS.setPublicationDate(DEFAULT_PUBLICATION_DATE);
        documentRTS.setIsPublic(DEFAULT_IS_PUBLIC);
        documentRTS.setClicks(DEFAULT_CLICKS);

        return documentRTS;
    }

    private Set<Content> createContent(String id, String content) {
        Content contentObj = new Content();
        contentObj.setId(id);
        contentObj.setContent(content);

        return Sets.newHashSet(contentObj);
    }

}
