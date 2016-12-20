package com.readthisstuff.rts.service;


import com.google.common.collect.Sets;
import com.readthisstuff.rts.RtsApp;
import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.Content;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.domain.DocumentStuff;
import com.readthisstuff.rts.domain.enumeration.ContentType;
import com.readthisstuff.rts.repository.DocumentRTSRepository;
import com.readthisstuff.rts.service.util.ImageService;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.readthisstuff.rts.helper.creator.DocumentRTSCreator.createDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RtsApp.class)
public class DocumentRTSServiceUnitTest {


    private static final String AUTHOR_ID = "author_id";
    private static final byte[] DEFAULT_THUMP = TestUtil.createByteArray(1, "0");
    private static final String DEFAULT_THUMP_CONTENT_TYPE = "image/jpg";
    private final static int THUMB_WIDTH = 700;
    private final static int THUMB_HEIGHT = 400;

    @Mock
    private DocumentRTSRepository documentRTSRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private DocumentRTSService service;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(authorService.createCurrentUserAsAuthor()).thenReturn(createMockAuthor());
        when(imageService.resizeThumb(any(), anyInt(),anyInt(), anyString())).thenReturn(DEFAULT_THUMP);

    }

    @Test
    public void saveDocument() throws IOException {
        DocumentRTS documentRTS = createDocument("1");

        when(documentRTSRepository.save(any(DocumentRTS.class))).thenReturn(documentRTS);
        DocumentRTS actualDocumentRTS = service.saveDocument(documentRTS);
        assertThat(actualDocumentRTS.getAuthor().getId()).isEqualTo(AUTHOR_ID);

        verify(authorService,times(1)).createCurrentUserAsAuthor();
        verify(imageService,times(1)).resizeThumb(eq(DEFAULT_THUMP), eq(THUMB_WIDTH), eq(THUMB_HEIGHT), eq(DEFAULT_THUMP_CONTENT_TYPE));
    }

    @Test
    public void publishDocument() {
        DocumentRTS documentRTS = createDocument("1");
        when(documentRTSRepository.save(any(DocumentRTS.class))).then(returnsFirstArg());

        // make document publish
        DocumentRTS actualDocumentRTS = service.publishDocument(documentRTS, true);

        assertThat(actualDocumentRTS.getPublished()).isTrue();
        assertThat(actualDocumentRTS.getPublicationDate()).isEqualTo(LocalDate.now());

        // make document private
        actualDocumentRTS = service.publishDocument(documentRTS, false);
        assertThat(actualDocumentRTS.getPublished()).isFalse();
        assertThat(actualDocumentRTS.getPublicationDate()).isNull();
    }

    // #################### private helper
    private Author createMockAuthor() {
        Author author = new Author();
        author.setId(AUTHOR_ID);
        return author;
    }
}
