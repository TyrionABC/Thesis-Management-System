package com.service;

import com.domain.Note_and_extra_file;

public interface NoteAndFileService {
    void insert(Note_and_extra_file note_and_extra_file);
    void delete(String id);
    void update(Note_and_extra_file note_and_extra_file);
    Note_and_extra_file select(String id);
}
