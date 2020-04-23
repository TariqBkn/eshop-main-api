package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutsRepository extends JpaRepository<Checkout, Integer> {

}
