package com.lambdaschool.safespace.repository;

import com.lambdaschool.safespace.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>
{
}
