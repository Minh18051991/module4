package org.example.ket_thuc_module_4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "khong de trong")
    @Column(nullable = false)
    private String name;

    @Positive(message = "gi phai la so duong")
    @NotNull(message = "khong de trong")
    @Min(value = 100000, message = "khong duoc nho hon 100000")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "khong de trong")
    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}