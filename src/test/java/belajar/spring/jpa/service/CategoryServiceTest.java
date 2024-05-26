package belajar.spring.jpa.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

//    data akan dirollback karena dipanggil pada objek yang berbeda
//    konsep jpa
    @Test
    void success(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            categoryService.create();
        });
    }

//    data categori terbuat karena dipanggil didalam objek yang sama
    @Test
    void fail(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            categoryService.test();
        });
    }
}