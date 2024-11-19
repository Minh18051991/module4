package org.example.form_validate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 5, max = 45, message = "First name must be between 5 and 45 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 5, max = 45, message = "Last name must be between 5 and 45 characters")
    private String lastName;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
}