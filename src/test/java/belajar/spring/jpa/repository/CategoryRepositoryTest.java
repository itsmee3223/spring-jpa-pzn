package belajar.spring.jpa.repository;

import belajar.spring.jpa.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void testAddCategory(){
        Category category = new Category();
        category.setName("SHOES");

        categoryRepository.save(category);
        Assertions.assertNotNull(category.getId());
    }

    @Test
    void testUpdateCategory(){
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        category.setName("SHOES MURAH");
        categoryRepository.save(category);
        Assertions.assertNotNull(category.getName(), "SHOES MURAH");
    }

    @Test
    void testQueryMethod(){
        Category category = categoryRepository.findByNameEquals("SHOES MURAH").orElse(null);
        Assertions.assertNotNull(category);
        Assertions.assertEquals(category.getName(), "SHOES MURAH");

        List<Category> categories = categoryRepository.findByNameLike("%SHOES%");
        Assertions.assertEquals(1, categories.size());
        Assertions.assertNotNull(categories.getFirst().getName(), "SHOES MURAH");
    }


}