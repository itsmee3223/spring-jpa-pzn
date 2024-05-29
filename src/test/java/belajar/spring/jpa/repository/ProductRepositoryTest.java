package belajar.spring.jpa.repository;

import belajar.spring.jpa.entity.Category;
import belajar.spring.jpa.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.support.TransactionOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    @Test
    void createProduct(){
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);
        {
            Product product = new Product();
            product.setName("Nike");
            product.setPrice(25_000_000L);
            product.setCategory(category);
            productRepository.save(product);
        }

        {
            Product product = new Product();
            product.setName("Adidas");
            product.setPrice(18_000_000L);
            product.setCategory(category);
            productRepository.save(product);
        }
    }

    @Test
    void findByCategoryName() {
        List<Product> products = productRepository.findAllByCategory_Name("SHOES MURAH");
        assertEquals(2, products.size());
        assertEquals("Nike", products.get(0).getName());
        assertEquals("Adidas", products.get(1).getName());
    }

    @Test
    void findByCategoryNameSort() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findAllByCategory_Name("SHOES MURAH", sort);
        assertEquals(2, products.size());
        assertEquals("Adidas", products.get(0).getName());
        assertEquals("Nike", products.get(1).getName());
    }
}