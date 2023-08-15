package ru.springBoot.Playlist.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.springBoot.Playlist.dto.AuthorDTO;
import ru.springBoot.Playlist.models.Author;
import ru.springBoot.Playlist.repositories.AuthorRepository;

@Component
public class AuthorValidator implements Validator {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorDTO authorDTO = (AuthorDTO) target;
        if (authorRepository.findByName(authorDTO.getName()).isPresent())
            errors.rejectValue("name", "", "Группа с таким названием существует, введите другое название");
    }
}
