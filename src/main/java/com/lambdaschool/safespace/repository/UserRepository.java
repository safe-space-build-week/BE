package com.lambdaschool.safespace.repository;

import com.lambdaschool.safespace.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}

