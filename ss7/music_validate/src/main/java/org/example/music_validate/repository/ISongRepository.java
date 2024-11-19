package org.example.music_validate.repository;

import org.example.music_validate.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISongRepository extends JpaRepository<Song, Long> {

}
