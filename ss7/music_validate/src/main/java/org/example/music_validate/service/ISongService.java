package org.example.music_validate.service;

import org.example.music_validate.model.Song;

import java.util.List;
import java.util.Optional;

public interface ISongService {
    List<Song> findAll();
    Optional<Song> findById(Long id);
    Song save(Song song);
    void deleteById(Long id);
}