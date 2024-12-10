package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String img;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "User_Id")
    private AppUser appUser;

@JsonBackReference
@ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}