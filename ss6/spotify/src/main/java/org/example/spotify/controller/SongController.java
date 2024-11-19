package org.example.spotify.controller;

import jakarta.validation.Valid;
import org.example.spotify.dto.SongDto;
import org.example.spotify.model.Song;
import org.example.spotify.model.Artist;
import org.example.spotify.model.Genre;
import org.example.spotify.service.ISongService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private ISongService songService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("songDto", new SongDto());
        return "song/create";
    }

    @PostMapping("/create")
    public String createSong(@Valid @ModelAttribute("songDto") SongDto songDto,
                             BindingResult bindingResult,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "song/create";
        }
        Song song = new Song();
        BeanUtils.copyProperties(songDto, song);
        Artist artist = new Artist();
        artist.setName(songDto.getArtist());
        song.setArtist(artist);
        Genre genre = new Genre();
        genre.setName(songDto.getGenre());
        song.setGenre(genre);
        songService.saveSong(song);
        model.addAttribute("song", song);
        return "song/details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Song song = songService.getSongById(id);
        SongDto songDto = new SongDto();
        BeanUtils.copyProperties(song, songDto);
        songDto.setArtist(song.getArtist().getName());
        songDto.setGenre(song.getGenre().getName());
        model.addAttribute("songDto", songDto);
        model.addAttribute("songId", id);
        return "song/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateSong(@PathVariable Long id,
                             @Valid @ModelAttribute("songDto") SongDto songDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("songId", id);
            return "song/edit";
        }
        Song existingSong = songService.getSongById(id);
        BeanUtils.copyProperties(songDto, existingSong, "id");
        Artist artist = new Artist();
        artist.setName(songDto.getArtist());
        existingSong.setArtist(artist);
        Genre genre = new Genre();
        genre.setName(songDto.getGenre());
        existingSong.setGenre(genre);
        songService.updateSong(existingSong);
        model.addAttribute("song", existingSong);
        return "song/details";
    }

    @GetMapping("/list")
    public String listSongs(Model model) {
        List<Song> songs = songService.getAllSongs();
        model.addAttribute("songs", songs);
        return "song/list";
    }

    @GetMapping("/details/{id}")
    public String songDetails(@PathVariable Long id, Model model) {
        Song song = songService.getSongById(id);
        model.addAttribute("song", song);
        return "song/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.deleteSongById(id);
        return "redirect:/songs/list";
    }

    @GetMapping("/artist/{artistName}")
    public String listSongsByArtist(@PathVariable String artistName, Model model) {
        List<Song> songs = songService.getSongByArtist(artistName);
        model.addAttribute("songs", songs);
        return "song/list";
    }

    @GetMapping("/genre/{genreName}")
    public String listSongsByGenre(@PathVariable String genreName, Model model) {
        List<Song> songs = songService.getSongByGenre(genreName);
        model.addAttribute("songs", songs);
        return "song/list";
    }
}