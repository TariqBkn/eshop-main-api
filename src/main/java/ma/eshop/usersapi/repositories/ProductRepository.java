package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p")
    Page<Product> findAllProducts(Pageable pageable);
}
