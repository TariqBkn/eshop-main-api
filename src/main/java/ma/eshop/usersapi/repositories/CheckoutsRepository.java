package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Checkout;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutsRepository extends PagingAndSortingRepository<Checkout, Integer> {

}
