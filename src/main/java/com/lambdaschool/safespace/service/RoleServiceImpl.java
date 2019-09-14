package com.lambdaschool.safespace.service;

import com.lambdaschool.safespace.model.Role;
import com.lambdaschool.safespace.model.UserRoles;
import com.lambdaschool.safespace.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleRepository rolerepos;

    @Override
    public List<Role> findAll()
    {
        List<Role> list = new ArrayList<>();
        rolerepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }


    @Override
    public Role findRoleById(long id)
    {
        return rolerepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }


    @Override
    public void delete(long id)
    {
        rolerepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
        rolerepos.deleteById(id);
    }


    @Transactional
    @Override
    public Role save(Role role)
    {
        return rolerepos.save(role);
    }

    // @Transactional
    // @Override
    // public Role update(Role role, long id)
    // {
    //     Role currentRole = rolerepos.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    //
    //     if (role.getName() != null)
    //     {
    //         currentRole.setName((role.getName()));
    //     }
    //
    //     if (role.getUserRoles().size() > 0)
    //     {
    //         // with so many relationships happening, I decided to go
    //         // with old school queries
    //         // delete the old ones
    //         rolerepos.deleteUserRolesByRoleId(currentRole.getRoleid());
    //
    //         // add the new ones
    //         for (UserRoles ur : role.getUserRoles())
    //         {
    //             rolerepos.insertUserRoles(ur.getUser().getUserid(), id);
    //         }
    //     }
    //
    //     return rolerepos.save(currentRole);
    // }
    //
    // @Transactional
    // @Override
    // public void saveUserRole(long userid, long roleid)
    // {
    //     rolerepos.insertUserRoles(userid, roleid);
    // }
    //
    // @Transactional
    // @Override
    // public void deleteUserRole(long userid, long roleid)
    // {
    //     rolerepos.deleteUserRoles(userid, roleid);
    // }

}
