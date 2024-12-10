package org.example.case_study_4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Attendence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private LocalDate day;

    private String note;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Class classes;

}
