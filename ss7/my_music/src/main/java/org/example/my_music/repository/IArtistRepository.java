package org.example.my_music.repository;

import org.example.my_music.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArtistRepository extends JpaRepository<Artist, Long> {

}
