package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p")
    Page<Product> findAllProducts(Pageable pageable);
    @Query(   "select p from Product p where "
            + "LOWER(p.title)  like CONCAT('%',?1,'%')"
            + "or LOWER(p.color) like CONCAT('%',?1,'%')"
            + "or LOWER(p.description) like CONCAT('%',?1,'%')"
            + "or LOWER(p.providerName) like CONCAT('%',?1,'%')"
             )
    List<Product> findByKeyword(String keyword);

}
