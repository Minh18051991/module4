package org.example.library.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private boolean rememberMe;

    @OneToMany(mappedBy = "user")
    private List<BorrowRecord> borrowRecords;
}
