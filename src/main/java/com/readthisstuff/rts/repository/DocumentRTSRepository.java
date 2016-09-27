package com.readthisstuff.rts.repository;

import com.readthisstuff.rts.domain.DocumentRTS;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the DocumentRTS entity.
 */
@SuppressWarnings("unused")
public interface DocumentRTSRepository extends MongoRepository<DocumentRTS,String> {

}
