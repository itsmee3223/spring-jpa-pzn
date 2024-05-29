package belajar.spring.jpa.repository;

import belajar.spring.jpa.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Long countByCategory_Name(String name);
//  tambah annotation transactional
    @Transactional
    int deleteByName(String name);

    boolean existsByName(String name);

//    support pageable tapi tidak dapat melakukan sort
//    sort dilakukan secara manual pada query
    Page<Product> searchProductUsingName(@Param("name") String name, Pageable pageable);

    @Query(
            value = "select p from Product p where p.name like :name or p.category.name like :name",
//          jika ingin menggunakan pageable maka perlu tentukan bagaimana cara melkukan count querynya
            countQuery = "select count(p) from Product p where p.name like :name or p.category.name like :name"
    )
//    untuk nama method bebas
    Page<Product>  searchProduct(@Param("name") String name, Pageable pageable);

    List<Product> findAllByCategory_Name(String name);

    List<Product> findAllByCategory_Name(String name, Sort sort);

    Page<Product> findAllByCategory_Name(String name, Pageable pageable);
}
