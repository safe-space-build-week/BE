package com.lambdaschool.safespace;


import com.lambdaschool.safespace.model.Note;
import com.lambdaschool.safespace.model.Role;
import com.lambdaschool.safespace.model.User;
import com.lambdaschool.safespace.model.UserRoles;
import com.lambdaschool.safespace.repository.RoleRepository;
import com.lambdaschool.safespace.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    RoleRepository rolerepos;
    UserRepository userrepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");


        r1 = rolerepos.save(r1);
        r2 = rolerepos.save(r2);


        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));

        ArrayList<Note> sampleNotes = new ArrayList<>();
        sampleNotes.add(new Note("Have a great day!"));
        sampleNotes.add(new Note("You're doing a great job!"));

        ArrayList<Note> sampleNotesTwo = new ArrayList<>();
        sampleNotesTwo.add(new Note("Have a great day!"));
        sampleNotesTwo.add(new Note("You're doing a great job!"));


        User u1 = new User("dano", "password", "Dan", "dan47573@gmail.com", "7742696689", users, sampleNotesTwo);
        User u2 = new User("admin", "password", "Admin", "danpatrickoneill@gmail.com", "5555555555", admins, sampleNotes);


        userrepos.save(u1);
        userrepos.save(u2);

    }
}
