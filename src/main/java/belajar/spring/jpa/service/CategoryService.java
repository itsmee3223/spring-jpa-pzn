package belajar.spring.jpa.service;

import belajar.spring.jpa.entity.Category;
import belajar.spring.jpa.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionOperations;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    void error(){
        throw new RuntimeException("Ups");
    }

//    pada annotion ini terdapat beberapa behaviour
//    secara defaul behaviornya adalah required
//    Kita bisa memilih nilai apa yang ingin kita gunakan
//    https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Propagation.html
    @Transactional
    void create(){
        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setName("Category " + i);
            categoryRepository.save(category);
        }

        throw new RuntimeException("Ups rollback");
    }

    void createCategories(){
        transactionOperations.executeWithoutResult(transactionStatus -> {
            for (int i = 0; i < 5; i++) {
                Category category = new Category();
                category.setName("Category " + i);
                categoryRepository.save(category);
            }
            error();
        });
    }

    void manual(){
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setTimeout(10);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(definition);
        try {
            for (int i = 0; i < 5; i++) {
                Category category = new Category();
                category.setName("Category " + i);
                categoryRepository.save(category);
            }
            error();
            platformTransactionManager.commit(transactionStatus);
        } catch (Throwable throwable){
            platformTransactionManager.rollback(transactionStatus);
            throw throwable;
        }
    }

    void test(){
//    AOP tidak bisa dipanggil didalam object yang sama
//    hal ini terjadi jika menggunakan annotation @Transaction
        create();
//     jika dipanggil maka akan ke execute karena tidak menggunakan annotation @Transaction
//        createCategories();
//        manual();
    }
}
