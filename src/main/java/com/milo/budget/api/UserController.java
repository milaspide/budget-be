package com.milo.budget.api;

import java.util.Objects;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.milo.budget.dto.UserDto;
import com.milo.budget.entity.UserEntity;
import com.milo.budget.service.UserService;
import com.milo.budget.utils.ObjectMapperUtils;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserDto> getUser(@PathVariable Long id) {
		return ResponseEntity.ok(ObjectMapperUtils.map(userService.getUserById(id), UserDto.class));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserDto> newUser(@RequestBody UserDto newUserDto) {
		UserEntity newUser = ObjectMapperUtils.map(newUserDto, UserEntity.class);
		return ResponseEntity.ok(ObjectMapperUtils.map(userService.newUser(newUser), UserDto.class));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	void updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
		if (!Objects.equals(id, userDto.getUserId())) {
			throw new IllegalArgumentException("IDs don't match");
		}

		UserEntity user = ObjectMapperUtils.map(userDto, UserEntity.class);
		userService.updateUser(user);
	}

	@GetMapping(value = "/{id}/remainingDays")
	ResponseEntity<Long> getRemainingDays(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getRemainingDays(id));
	}
	
	@PutMapping(value = "/{id}/calculateRemainingBudget")
	@ResponseStatus(HttpStatus.OK)
	void calculateRemainingBudget(@PathVariable Long id) {
		userService.calculateRemainingBudget(id);
	}

}
