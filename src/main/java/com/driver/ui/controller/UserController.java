package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;
	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto = userService.getUserByUserId(id);
		return dtoToResponse(userDto);
	}


	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = requestToDto(userDetails);
		UserDto respondedUserDto = userService.createUser(userDto);
		return dtoToResponse(respondedUserDto);
	}


	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = requestToDto(userDetails);
		UserDto respondedUserDto = userService.updateUser(id,userDto);

		return dtoToResponse(respondedUserDto);
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		userService.deleteUser(id);
		return null;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserDto> userDtoList = userService.getUsers();
		List<UserResponse> userResponseList = dtoListToResponse(userDtoList);
		return userResponseList;
	}

	private List<UserResponse> dtoListToResponse(List<UserDto> userDtoList) {
		List<UserResponse> userResponseList = new ArrayList<>();
		for(UserDto userDto:userDtoList){
			userResponseList.add(dtoToResponse(userDto));
		}
		return userResponseList;
	}

	private UserResponse dtoToResponse(UserDto userDto) {
		return UserResponse.builder().userId(userDto.getUserId()).
				                     email(userDto.getEmail()).
				                     firstName(userDto.getFirstName()).
				                     lastName(userDto.getLastName()).build();
	}

	private UserDto requestToDto(UserDetailsRequestModel userDetails) {
		return UserDto.builder().firstName(userDetails.getFirstName()).
				                 lastName(userDetails.getLastName()).
				                 email(userDetails.getEmail()).build();
	}
	
}
