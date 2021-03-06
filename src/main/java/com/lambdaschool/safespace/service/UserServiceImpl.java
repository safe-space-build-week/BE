package com.lambdaschool.safespace.service;

import com.lambdaschool.safespace.model.Note;
import com.lambdaschool.safespace.model.User;
import com.lambdaschool.safespace.model.UserRoles;
import com.lambdaschool.safespace.repository.RoleRepository;
import com.lambdaschool.safespace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException
    {
        User user = userrepos.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }

    public User findUserById(long id) throws EntityNotFoundException
    {
        return userrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public User findUserByUsername(String name) throws EntityNotFoundException
    {
        return userrepos.findByUsername(name);
    }

    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id)
    {
        if (userrepos.findById(id).isPresent()) {
            userrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles()) {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);

        ArrayList<Note> newNotes = new ArrayList<>();
        for (Note n : user.getNotes()) {
            newNotes.add(new Note(n.getText(), newUser));
        }
        newUser.setNotes(newNotes);

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());

        if (currentUser != null) {
            if (id == currentUser.getUserid()) {
                if (user.getUsername() != null) {
                    currentUser.setUsername(user.getUsername());
                }

                if (user.getPassword() != null) {
                    currentUser.setPasswordNoEncrypt(user.getPassword());
                }

                // if (user.getUserRoles().size() > 0)
                // {
                //     // with so many relationships happening, I decided to go
                //     // with old school queries
                //     // delete the old ones
                //     rolerepos.deleteUserRolesByUserId(currentUser.getUserid());
                //
                //     // add the new ones
                //     for (UserRoles ur : user.getUserRoles())
                //     {
                //         rolerepos.insertUserRoles(id, ur.getRole().getRoleid());
                //     }
                // }
                return userrepos.save(currentUser);
            } else {
                throw new EntityNotFoundException(Long.toString(id) + " Not current user");
            }
        } else {
            throw new EntityNotFoundException(authentication.getName());
        }

    }
}
