package ru.practicum.main.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.user.dto.UserDto;


import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    List<UserDto> getUsers(List<Long> ids, Pageable pageable);

    void deleteUser(int userId);
}
