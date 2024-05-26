package belajar.spring.jpa.repository;

import belajar.spring.jpa.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends JpaRepository<Category, Long> {
}
