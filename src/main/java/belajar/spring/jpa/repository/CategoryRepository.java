package belajar.spring.jpa.repository;

import belajar.spring.jpa.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//  Untuk operator query yang didukung, kita bisa lihat di halaman ini
//  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Optional<Category> findByNameEquals(String name);

    List<Category> findByNameLike(String name);
}
