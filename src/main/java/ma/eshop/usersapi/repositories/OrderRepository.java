package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository< Order, Integer> {
}
