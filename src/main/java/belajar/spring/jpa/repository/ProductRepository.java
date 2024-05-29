package belajar.spring.jpa.repository;

import belajar.spring.jpa.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

//    untuk melakukan modify yaitu mengubah dan menghapus data maka perlu  menambhakan annotation @Modifying
    @Modifying
    @Query(
            value = "delete from Product p where p.name = :name"
    )
    int deleteUsingName(@Param("name") String name);

    @Modifying
    @Query(
            value = "update Product p set p.price = 0 where p.name = :name"
    )
    int updatePriceToZeroByName(@Param("name") String name);

    List<Product> findAllByCategory_Name(String name);

    List<Product> findAllByCategory_Name(String name, Sort sort);

    Page<Product> findAllByCategory_Name(String name, Pageable pageable);
}
