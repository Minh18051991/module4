package org.example.ket_thuc_module_4.repository;

import org.example.ket_thuc_module_4.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
