package com.readthisstuff.rts.repository;

import com.readthisstuff.rts.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Author entity.
 */
@SuppressWarnings("unused")
public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findOneByUserName(String userName);

}
