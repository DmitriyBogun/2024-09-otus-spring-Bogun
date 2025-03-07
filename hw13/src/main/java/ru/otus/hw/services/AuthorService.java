package ru.otus.hw.services;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

import java.util.List;

public interface AuthorService {

    @PreAuthorize("hasRole('USER')")
    List<AuthorDto> findAll();

    @PreAuthorize("hasRole('USER')")
    Author findByFullName(String fullName);
}
