package org.example.dictionary_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
@Controller
public class DictionaryController {

    private static final Map<String, String> dictionary = new HashMap<>();

    static {
        dictionary.put("hello", "xin chào");
        dictionary.put("world", "thế giới");
        dictionary.put("computer", "máy tính");
        dictionary.put("java", "java");
        dictionary.put("spring", "mùa xuân");
        dictionary.put("summer", "mùa hè");
        dictionary.put("autumn", "mùa thu");
        dictionary.put("winter", "mùa đông");
        dictionary.put("book", "quyển sách");
        dictionary.put("pen", "cây bút");
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @GetMapping("/lookup")
    public String lookup(@RequestParam("word") String word, Model model) {
        String meaning = dictionary.get(word.toLowerCase());
        if (meaning == null) {
            meaning = "Không tìm thấy";
        }
        model.addAttribute("word", word);
        model.addAttribute("meaning", meaning);
        return "result";
    }
}