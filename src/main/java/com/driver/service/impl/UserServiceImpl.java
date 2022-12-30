package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity = dtoToEntity(user);
        userRepository.save(userEntity);
        return entityToDto(userEntity);
    }


    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity= userRepository.findByEmail(email);
        return entityToDto(userEntity);
    }


    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        return entityToDto(userEntity);
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userRepository.save(userEntity);
        return entityToDto(userEntity);
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> userEntities  = new ArrayList<>();
        userRepository.findAll().forEach(user->userEntities.add(user));
        List<UserDto> userDtos = entityListToDto(userEntities);
        return userDtos;
    }

    private List<UserDto> entityListToDto(List<UserEntity> userEntities) {
        List<UserDto> userDtos = new ArrayList<>();
        for(UserEntity userEntity:userEntities){
            userDtos.add(entityToDto(userEntity));
        }
        return userDtos;
    }

    private UserEntity dtoToEntity(UserDto user) {
        return UserEntity.builder().firstName(user.getFirstName()).
                                   lastName(user.getLastName()).
                                   email(user.getEmail()).
                                    userId(user.getUserId()).build();
    }

    private UserDto entityToDto(UserEntity userEntity) {
        return UserDto.builder().id(userEntity.getId()).
                userId(userEntity.getUserId()).
                firstName(userEntity.getFirstName()).
                lastName(userEntity.getLastName()).
                email(userEntity.getEmail()).build();
    }

}