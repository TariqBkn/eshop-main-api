package ma.eshop.usersapi.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ma.eshop.usersapi.models.User;

import java.util.Optional;


@Repository
public interface UsersRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
