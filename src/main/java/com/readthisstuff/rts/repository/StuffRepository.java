package com.readthisstuff.rts.repository;

import com.readthisstuff.rts.domain.Stuff;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Stuff entity.
 */
@SuppressWarnings("unused")
public interface StuffRepository extends MongoRepository<Stuff,String> {

}
