package com.lambdaschool.safespace.service;

import com.lambdaschool.safespace.model.Note;

import java.util.List;

public interface NoteService
{
    List<Note> findAll();

    Note findNoteById(long id);

    void delete(long id);

    Note save(Note note);

    Note update(Note note, long id);
}
