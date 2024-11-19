package org.example.spotify.service;

import org.example.spotify.model.Song;

import java.util.List;


public interface ISongService {
    void saveSong(Song song);
    List<Song> getAllSongs();
    Song getSongById(long id);
    void deleteSongById(long id);
    void updateSong(Song song);
    List<Song> getSongByArtist(String artist);
    List<Song> getSongByGenre(String genre);

}
