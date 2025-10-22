package com.example.springboot.service.impl;

import com.example.springboot.dto.UserDto;
import com.example.springboot.mapper.UserMapper;
import lombok.AllArgsConstructor;
import com.example.springboot.entity.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //Convert UserDTO into User JPA Entity
        //User user = UserMapper.mapToUser(userDto);
        User user = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(user);

       //Convert User JPA entity to UserDTO
       //UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);
         UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
       return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        //return UserMapper.mapToUserDto(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
       //return users.stream().map(UserMapper::mapToUserDto).toList();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(updatedUser);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
