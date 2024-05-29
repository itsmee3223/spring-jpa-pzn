package belajar.spring.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
// named query support pageable tapi tidak support sort
// jika ada sort maka akan dihiraukan
// jika tetap ingin menggunakan sort maka dibuat manual pada query bukan pada method
@NamedQueries({
        @NamedQuery(
                name = "Product.searchProductUsingName",
                query = "SELECT p FROM Product p WHERE p.name = :name"
        )
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
