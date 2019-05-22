package com.lambdaschool.safespace.service;

import com.lambdaschool.safespace.model.Note;
import com.lambdaschool.safespace.model.User;
import com.lambdaschool.safespace.repository.NoteRepository;
import com.lambdaschool.safespace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "noteService")
public class NoteServiceImpl implements NoteService
{
    @Autowired
    NoteRepository noterepo;

    @Autowired
    UserRepository userrepo;

    @Override
    public List<Note> findAll()
    {
        List<Note> notes = new ArrayList<>();
        noterepo.findAll().iterator().forEachRemaining(notes::add);
        return notes;
    }

    @Override
    public Note findNoteById(long id) throws EntityNotFoundException
    {
        return noterepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find note with id: " + id));
    }

    @Override
    public void delete(long id) throws EntityNotFoundException
    {
        if (noterepo.findById(id).isPresent()) {
            noterepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Couldn't find note with id: " + id);
        }
    }

    @Transactional
    @Override
    public Note save(Note note)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepo.findByUsername(authentication.getName());

        Note newNote = new Note();
        newNote.setText(note.getText());
        newNote.setUser(currentUser);

        return noterepo.save(newNote);
    }

    @Transactional
    @Override
    public Note update(Note note, long id) throws EntityNotFoundException
    {
        if (noterepo.findById(id).isPresent()) {
            Note currentNote = noterepo.findById(id).get();
            currentNote.setText(note.getText());

            return noterepo.save(currentNote);
        } else {
            throw new EntityNotFoundException("Couldn't find note with id: " + id);
        }
    }
}
