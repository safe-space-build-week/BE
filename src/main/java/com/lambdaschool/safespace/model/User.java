package com.lambdaschool.safespace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @Column(nullable = false,
            unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<UserRoles> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Note> notes = new ArrayList<>();

    public User()
    {
    }


    public User(String username, String password, List<UserRoles> userRoles)
    {
        setUsername(username);
        setPassword(password);
        for (UserRoles ur : userRoles) {
            ur.setUser(this);
        }
        this.userRoles = userRoles;
    }

    public User(String username, String password, String name, String email, List<UserRoles> userRoles, List<Note> notes)
    {
        setUsername(username);
        setPassword(password);
        this.name = name;
        this.email = email;
        for (UserRoles ur : userRoles) {
            ur.setUser(this);
        }
        this.userRoles = userRoles;

        for (Note n : notes) {
            n.setUser(this);
        }
        this.notes = notes;

    }

    // public User(String username, String password)
    // {
    //     setUsername(username);
    //     setPassword(password);
    //
    //     Role role = new Role("user");
    //
    //     ArrayList<UserRoles> userRoles = new ArrayList<>();
    //     userRoles.add(new UserRoles(new User(), role));
    //
    //     for (UserRoles ur : userRoles) {
    //         ur.setUser(this);
    //     }
    //     this.setUserRoles(userRoles);
    // }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public List<UserRoles> getUserRoles()
    {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles)
    {
        this.userRoles = userRoles;
    }

    public List<Note> getNotes()
    {
        return notes;
    }

    public void setNotes(List<Note> notes)
    {
        this.notes = notes;
    }

    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.userRoles) {
            String myRole = "ROLE_" + r.getRole().getName().toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }
        return rtnList;
    }
}