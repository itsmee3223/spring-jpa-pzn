package belajar.spring.jpa.service;

import belajar.spring.jpa.entity.Category;
import belajar.spring.jpa.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    void create(){
        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setName("Category " + i);
            categoryRepository.save(category);
        }

        throw new RuntimeException("Ups rollback");
    }

//    AOP tidak bisa dipanggil didalam object yang sama
    void test(){
        create();
    }
}
