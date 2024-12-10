package org.example.case_study_4.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String code;
    private String name;
    private Date startDate;
    @ManyToOne
    private Employee teacher_;
    @ManyToOne
    private Employee teacherAssistant;




}
