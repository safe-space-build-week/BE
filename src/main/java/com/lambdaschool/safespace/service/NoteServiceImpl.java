package com.lambdaschool.safespace.service;

import com.lambdaschool.safespace.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "noteService")
public class NoteServiceImpl implements NoteService
{
    @Override
    public List<Note> findAll()
    {
        return null;
    }

    @Override
    public Note findNoteById(long id)
    {
        return null;
    }

    @Override
    public void delete(long id)
    {

    }

    @Override
    public Note save(Note user)
    {
        return null;
    }

    @Override
    public Note update(Note user, long id)
    {
        return null;
    }
}
