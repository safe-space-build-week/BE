package com.lambdaschool.safespace.repository;

import com.lambdaschool.safespace.model.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long>
{
}
