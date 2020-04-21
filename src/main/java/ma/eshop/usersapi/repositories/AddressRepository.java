package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Address;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends PagingAndSortingRepository<Address, Integer> {
}
