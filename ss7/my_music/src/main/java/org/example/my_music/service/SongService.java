package org.example.my_music.service;

import org.example.my_music.model.Song;
import org.example.my_music.repository.IArtistRepository;
import org.example.my_music.repository.IGenreRepository;
import org.example.my_music.repository.ISongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SongService implements ISongService {

    @Autowired
    private ISongRepository songRepository;

    @Autowired
    private IArtistRepository artistRepository;

    @Autowired
    private IGenreRepository genreRepository;

    private final Path root = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void saveSong(Song song) {
        songRepository.save(song);
    }

    public Song saveSong(Song song, MultipartFile file) {
////        try {
////            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
////            song.setFilePath(this.root.resolve(file.getOriginalFilename()).toString());
//        } catch (Exception e) {
//            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
//        }
        return songRepository.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song getSongById(long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found with id " + id));
    }

    @Override
    public void deleteSongById(long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found with id " + id));
//
//        try {
//            Files.delete(Paths.get(song.getFilePath()));
//        } catch (IOException e) {
//            throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
//        }

        songRepository.delete(song);
    }

    @Override
    public void updateSong(Song songDetails) {
        Song song = songRepository.findById(songDetails.getId())
                .orElseThrow(() -> new RuntimeException("Song not found with id " + songDetails.getId()));

        song.setTitle(songDetails.getTitle());
        song.setArtist(songDetails.getArtist());
        song.setGenre(songDetails.getGenre());

        songRepository.save(song);
    }

    @Override
    public List<Song> getSongByArtist(String artistName) {
        return songRepository.findByArtistName(artistName);
    }

    @Override
    public List<Song> getSongByGenre(String genreName) {
        return songRepository.findByGenreName(genreName);
    }
}