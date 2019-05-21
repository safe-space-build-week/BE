package com.lambdaschool.safespace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "notes")
public class Note extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long noteid;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"notes", "hibernateLazyInitializer"})
    @JoinColumn(name = "userid")
    private User user;

    public Note()
    {
    }

    public Note(String text, User user)
    {
        this.text = text;
        this.user = user;
    }

    public long getNoteid()
    {
        return noteid;
    }

    public void setNoteid(long noteid)
    {
        this.noteid = noteid;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
