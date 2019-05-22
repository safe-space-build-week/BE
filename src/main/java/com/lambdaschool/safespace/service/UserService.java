package com.lambdaschool.safespace.service;

import com.lambdaschool.safespace.model.User;

import java.util.List;

public interface UserService
{
    List<User> findAll();

    User findUserById(long id);

    User findUserByUsername(String name);

    void delete(long id);

    User save(User user);

    User update(User user, long id);
}