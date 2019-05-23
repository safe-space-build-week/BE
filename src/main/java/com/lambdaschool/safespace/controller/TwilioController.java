package com.lambdaschool.safespace.controller;

import com.lambdaschool.safespace.model.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.lambdaschool.safespace.model.Note;
import com.lambdaschool.safespace.service.NoteService;
import com.lambdaschool.safespace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/twilio")
public class TwilioController
{
    public static final String ACCOUNT_SID =
            "ACc7137b08d56cd6633e621af07777ae5e";
    public static final String AUTH_TOKEN =
            "a90118bb06f653946cf34fca9a54b80d";

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/send", produces = "application/json")
    public ResponseEntity<?> sendUserNote() throws EntityNotFoundException
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(authentication.getName());

        if (currentUser.getNotes().size() > 0) {
            Random rand = new Random();
            Note randomNote = currentUser.getNotes().get(rand.nextInt(currentUser.getNotes().size()));

            Message message = Message
                    .creator(new PhoneNumber("+" + currentUser.getPhone()), // to
                            new PhoneNumber("+17622526232"), // from
                            randomNote.getText()
                    )
                    .create();

            return new ResponseEntity<>(randomNote, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("No notes found for user:" + currentUser.getUsername());
        }

    }
}
