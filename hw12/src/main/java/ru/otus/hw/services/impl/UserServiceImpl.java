package ru.otus.hw.services.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.UserMapper;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder encoder;

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtos = repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto findByUsername(String username) {
        UserDto userDto = mapper.toDto(repository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with username = %s is not found"
                        .formatted(username))));
        return userDto;
    }

    @Override
    public UserDto createUser(UserCreateDto createDto) {
        User user = mapper.toModel(createDto, encoder);
        UserDto userDto = mapper.toDto(repository.save(user));
        return userDto;
    }

    @Override
    public UserDto updateUser(UserUpdateDto updateDto) {
        User user = mapper.toModel(updateDto, encoder);
        UserDto userDto = mapper.toDto(repository.save(user));
        return userDto;
    }

    @Override
    public void deleteUser(@NotNull UserDto dto) {
        repository.delete(mapper.toModel(dto, encoder));
    }
}
