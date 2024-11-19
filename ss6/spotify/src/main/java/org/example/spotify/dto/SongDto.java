package org.example.spotify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@Pattern(regexp = "^[^@;,.=\\-+]+$", message = "Tên bài hát, tên nghệ s��, thể loại nhạc không được chứa các ký tự đặc biệt như @ ; ,. = - +")
public class SongDto {

    @NotBlank(message = "Tên bài hát không được để trống")
    @Size(max = 800, message = "Tên bài hát không được vượt quá 800 ký tự")
    @Pattern(regexp = "^[^@;,.=\\-+]+$", message = "Tên bài hát không được chứa các ký tự đặc biệt như @ ; , . = - +")
    private String title;

    @NotBlank(message = "Tên nghệ sĩ không được để trống")
    @Size(max = 300, message = "Tên nghệ sĩ không được vượt quá 300 ký tự")
    @Pattern(regexp = "^[^@;,.=\\-+]+$", message = "Tên nghệ sĩ không được chứa các ký tự đặc biệt như @ ; , . = - +")
    private String artist;

    @NotBlank(message = "Thể loại nhạc không được để trống")
    @Size(max = 1000, message = "Thể loại nhạc không được vượt quá 1000 ký tự")
    @Pattern(regexp = "^[^@;.=\\-+]+$", message = "Thể loại nhạc chỉ được chứa dấu phẩy ',' là ký tự đặc biệt")
    private String genre;

    // Getters and setters
}