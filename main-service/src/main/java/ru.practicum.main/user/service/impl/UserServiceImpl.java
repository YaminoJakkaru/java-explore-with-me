package ru.practicum.main.user.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.UserRepository;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.service.UserService;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        log.info("Add user " + userDto.getName());
        return userRepository.save(userDto.toUser()).toUserDto();
    }


    @Override
    public List<UserDto> getUsers(List<Long> ids,  Pageable pageable) {
        return ids == null ?
                    userRepository.findAll(pageable).map(User::toUserDto).toList() :
            userRepository.findUserByIdIn(ids, pageable).map(User::toUserDto).toList();
    }



    @Override
    @Transactional
    public void deleteUser(int userId) {
        int response = userRepository.removeUserById(userId);
        if(response == 0) {
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        log.info("Remove user " + userId);
    }
}
