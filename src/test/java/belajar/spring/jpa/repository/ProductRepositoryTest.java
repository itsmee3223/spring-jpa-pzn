package belajar.spring.jpa.repository;

import belajar.spring.jpa.entity.Category;
import belajar.spring.jpa.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    void findByCategoryNameSortAndPaging() {
//        page 0
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")));
        Page<Product> products = productRepository.findAllByCategory_Name("SHOES MURAH", pageRequest);
        assertEquals(1, products.getContent().size());
        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("Adidas", products.getContent().get(0).getName());
//        page 1
        pageRequest = PageRequest.of(1, 1, Sort.by(Sort.Order.desc("id")));
        products = productRepository.findAllByCategory_Name("SHOES MURAH", pageRequest);
        assertEquals(1, products.getContent().size());
        assertEquals(1, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("Nike", products.getContent().get(0).getName());
    }

    @Test
    void count() {
        Long count = productRepository.count();
        assertEquals(2L, count);

        Long products = productRepository.countByCategory_Name("SHOES MURAH");
        assertEquals(2L, products);
    }

    @Test
    void exists() {
        boolean exist = productRepository.existsByName("Adidas");
        assertTrue(exist);

        exist = productRepository.existsByName("SHOES MURAH");
        assertFalse(exist);
    }

    @Test
    void delete(){
//        delete harus dalam transaksi
//        jika tidak transaksi maka akan terjadi error
//        karena secara default turunan menggunakan @Transactional(readOnly=true) kecuali pada bebapara query method tertentu yang sudah diubah
//        https://docs.spring.io/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/support/SimpleJpaRepository.html
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Category category = categoryRepository.findById(1L).orElse(null);
            assertNotNull(category);

            Product product = new Product();
            product.setName("oke");
            product.setPrice(10_000_000L);
            product.setCategory(category);

            productRepository.save(product);

            int delete = productRepository.deleteByName("oke");
            assertEquals(1, delete);

            delete = productRepository.deleteByName("oke");
            assertEquals(0, delete);
        });
    }

    @Test
    void deleteWithSeperateTransaction(){
//        karena sudah ditambhkan annotation pada methodnya maka tidak perlu lagi dibungkus dalam transaction
//        namun mereka akan diekseuki pada transaksi yang berbeda
//        maka jika terjadi error tidak akan ada rollback
        Category category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);

        Product product = new Product();
        product.setName("oke");
        product.setPrice(10_000_000L);
        product.setCategory(category);
//      transaksi 1
        productRepository.save(product);

//      trsansaki 2
        int delete = productRepository.deleteByName("oke1");
        assertEquals(1, delete);
//      transaksi 3
        delete = productRepository.deleteByName("oke1");
        assertEquals(0, delete);
    }

    @Test
    void namedQuery() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Product> products = productRepository.searchProductUsingName("Nike", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals("Nike", products.getContent().get(0).getName());
    }

    @Test
    void queryAnnotation() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")));
        Page<Product> products = productRepository.searchProduct("%Nike%", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals("Nike", products.getContent().get(0).getName());

        products = productRepository.searchProduct("%SHOES%", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals("SHOES MURAH", products.getContent().get(0).getCategory().getName());
    }


}