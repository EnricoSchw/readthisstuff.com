package com.readthisstuff.rts.service;

import com.readthisstuff.rts.domain.Author;
import com.readthisstuff.rts.domain.User;
import com.readthisstuff.rts.repository.AuthorRepository;
import com.readthisstuff.rts.repository.UserRepository;
import com.readthisstuff.rts.security.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class AuthorService {

    @Inject
    private UserRepository userRepository;

    @Inject
    AuthorRepository authorRepository;

    public Author createCurrentUserAsAuthor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> userOptional = userRepository.findOneByLogin(userName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userAsAuthor(user);
        } else {
            throw new IllegalStateException("Can not find logged in User to create Author! ");
        }
    }

    private Author userAsAuthor(User user) {
        Optional<Author> authorOptional = authorRepository.findOneByUserName(user.getLogin());
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        } else {
            return createAuthor(user);
        }
    }

    private Author createAuthor(User user) {
        Author author = new Author();
        author.setFirstName(user.getFirstName());
        author.setLastName(user.getLastName());
        author.setUserName(user.getLogin());
        return authorRepository.save(author);
    }


}
