package com.lambdaschool.safespace.controller;

import com.lambdaschool.safespace.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController
{
    @Autowired
    NoteService noteService;
}
