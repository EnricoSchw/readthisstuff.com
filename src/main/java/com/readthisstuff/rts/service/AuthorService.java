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
        Optional<User> user = userRepository.findOneByLogin(userName);
        return userAsAuthor(user);
    }

    private Author userAsAuthor(Optional<User> userOptional) {
        if (userOptional.isPresent()) {
            Author author = new Author();
            User user = userOptional.get();
            author.setFirstName(user.getFirstName());
            author.setLastName(user.getLastName());
            author.setUserName(user.getLogin());
            return authorRepository.save(author);
        } else {
            throw new IllegalStateException("Can not find logged in User to create Author! ");
        }
    }
}
