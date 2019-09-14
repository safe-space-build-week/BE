package com.lambdaschool.safespace.controller;

import com.lambdaschool.safespace.model.Note;
import com.lambdaschool.safespace.model.User;
import com.lambdaschool.safespace.service.NoteService;
import com.lambdaschool.safespace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController
{
    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/allnotes", produces = "application/json")
    public ResponseEntity<?> getAllNotes()
    {
        List<Note> noteList = noteService.findAll();
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }

    @GetMapping(value = "/mynotes", produces = "application/json")
    public ResponseEntity<?> getUserNotes()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(authentication.getName());

        return new ResponseEntity<>(currentUser.getNotes(), HttpStatus.OK);
    }

    @PostMapping(value = "/newnote", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewNote(@Valid @RequestBody Note note)
    {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User currentUser = userService.findUserByUsername(authentication.getName());

        note = noteService.save(note);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PutMapping(value = "/edit/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> editNote(@Valid @RequestBody Note note, @PathVariable long id)
    {
        note = noteService.update(note, id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable long id)
    {
        noteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
