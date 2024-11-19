package org.example.spotify.repository;

import org.example.spotify.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISongRepository extends JpaRepository<Song, Long> {
    List<Song> findByArtistName(String artistName);
    List<Song> findByGenreName(String genreName);
}