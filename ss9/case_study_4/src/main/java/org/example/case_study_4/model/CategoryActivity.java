package org.example.case_study_4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CategoryActivity {
    @Id
    private Long id;
}
