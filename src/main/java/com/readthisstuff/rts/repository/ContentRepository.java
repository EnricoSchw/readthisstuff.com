package com.readthisstuff.rts.repository;

import com.readthisstuff.rts.domain.Content;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Content entity.
 */
@SuppressWarnings("unused")
public interface ContentRepository extends MongoRepository<Content,String> {

}
