package belajar.spring.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityManagerTest {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void testEntity(){
        Assertions.assertNotNull(entityManagerFactory);
        EntityManager managerFactory = entityManagerFactory.createEntityManager();
        Assertions.assertNotNull(managerFactory);
    }
}
