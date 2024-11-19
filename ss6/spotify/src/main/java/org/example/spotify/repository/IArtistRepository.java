package org.example.spotify.repository;

import org.example.spotify.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArtistRepository extends JpaRepository<Artist, Long> {

}
