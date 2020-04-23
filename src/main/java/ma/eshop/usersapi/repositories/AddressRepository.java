package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends JpaRepository<Address, Integer> {
}
