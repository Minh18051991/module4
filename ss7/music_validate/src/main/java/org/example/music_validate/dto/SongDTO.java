package org.example.music_validate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDTO {

    @NotBlank(message = "Tên bài hát không được để trống")
    @Size(max = 800, message = "Tên bài hát không được vượt quá 800 ký tự")
    @Pattern(regexp = "^[^@;,.=+\\-]+$", message = "Tên bài hát không được chứa các ký tự đặc biệt như @ ; , . = - + , ….")
    private String name;

    @NotBlank(message = "Nghệ sĩ thể hiện không được để trống")
    @Size(max = 300, message = "Nghệ sĩ thể hiện không được vượt quá 300 ký tự")
    @Pattern(regexp = "^[^@;,.=+\\-]+$", message = "Nghệ sĩ thể hiện không được chứa các ký tự đặc biệt như @ ; , . = - + , ….")
    private String artist;

    @NotBlank(message = "Thể loại nhạc không được để trống")
    @Size(max = 1000, message = "Thể loại nhạc không được vượt quá 1000 ký tự")
    @Pattern(regexp = "^[^@;=.\\-+]+(,[^@;=.\\-+]+)*$", message = "Thể loại nhạc chỉ được chứa dấu phẩy “,” và không được chứa các ký tự đặc biệt khác")
    private String genre;
}