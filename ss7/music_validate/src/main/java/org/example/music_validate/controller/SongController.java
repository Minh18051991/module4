package org.example.music_validate.controller;

import org.example.music_validate.dto.SongDTO;
import org.example.music_validate.model.Song;
import org.example.music_validate.service.ISongService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private ISongService songService;

    @GetMapping
    public String listSongs(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "list";
    }

    @GetMapping("/new")
    public String showAddSongForm(Model model) {
        model.addAttribute("songDTO", new SongDTO());
        return "create";
    }

    @PostMapping("/create")
    public String addSong(@Valid @ModelAttribute("songDTO") SongDTO songDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create";
        }
        Song song = new Song();
        BeanUtils.copyProperties(songDTO, song);
        songService.save(song);
        return "redirect:/songs";
    }

    @GetMapping("/edit/{id}")
    public String showEditSongForm(@PathVariable Long id, Model model) {
        Optional<Song> songOptional = songService.findById(id);
        if (songOptional.isPresent()) {
            SongDTO songDTO = new SongDTO();
            BeanUtils.copyProperties(songOptional.get(), songDTO);
            model.addAttribute("songDTO", songDTO);
            model.addAttribute("songId", id);
            return "edit";
        }
        return "redirect:/songs";
    }

    @PostMapping("/update/{id}")
    public String updateSong(@PathVariable Long id, @Valid @ModelAttribute("songDTO") SongDTO songDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("songId", id);
            return "edit";
        }
        Optional<Song> songOptional = songService.findById(id);
        if (songOptional.isPresent()) {
            Song song = songOptional.get();
            BeanUtils.copyProperties(songDTO, song);
            songService.save(song);
            return "redirect:/songs";
        }
        return "redirect:/songs";
    }

    @GetMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.deleteById(id);
        return "redirect:/songs";
    }
}