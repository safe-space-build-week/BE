package com.lambdaschool.safespace.controller;

import com.lambdaschool.safespace.model.User;
import com.lambdaschool.safespace.service.RoleService;
import com.lambdaschool.safespace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserService userService;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User newUser)
    {

        newUser = userService.save(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
