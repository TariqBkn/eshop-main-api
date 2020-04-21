package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends PagingAndSortingRepository<Cart,Integer> {
}
