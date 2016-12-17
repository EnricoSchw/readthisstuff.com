package com.readthisstuff.rts.helper.creator;


import com.google.common.collect.Sets;
import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.Content;
import com.readthisstuff.rts.domain.DocumentRTS;
import com.readthisstuff.rts.domain.enumeration.ContentType;
import com.readthisstuff.rts.web.rest.TestUtil;

import java.time.LocalDate;
import java.util.Set;

public class DocumentRTSCreator {

    public static final String DEFAULT_TITLE = "AAAAA";
    public static final String DEFAULT_AUTHOR_NAME = "DEFAULT_AUTHOR_NAME";
    public static final String DEFAULT_AUTHOR_ID = "DEFAULT_AUTHOR_ID";

    public static final String DEFAULT_CONTENT_ID = "DEFAULT_CONTENT_ID ";
    public static final String DEFAULT_CONTENT_CONTENT = "DEFAULT_CONTENT_CONTENT";

    public static final ContentType DEFAULT_TYPE = ContentType.ARTICLE;

    public static final byte[] DEFAULT_THUMP = TestUtil.createByteArray(1, "0");
    public static final String DEFAULT_THUMP_CONTENT_TYPE = "image/jpg";

    public static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);

    public static final Boolean DEFAULT_IS_PUBLIC = false;

    public static final Integer DEFAULT_CLICKS = 0;



    public static DocumentRTS createDocument(String number) {

        Author author = new Author();
        author.setUserName(DEFAULT_AUTHOR_NAME + number);
        author.setId(DEFAULT_AUTHOR_ID);


        Set<Content> content = createContent(DEFAULT_CONTENT_ID + number, DEFAULT_CONTENT_CONTENT + number);

        DocumentRTS documentRTS = new DocumentRTS();
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

    private static Set<Content> createContent(String id, String content) {
        Content contentObj = new Content();
        contentObj.setId(id);
        contentObj.setContent(content);

        return Sets.newHashSet(contentObj);
    }
}
