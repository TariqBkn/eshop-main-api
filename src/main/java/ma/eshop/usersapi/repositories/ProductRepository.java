package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
}
