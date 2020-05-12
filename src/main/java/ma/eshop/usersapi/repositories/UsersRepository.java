package ma.eshop.usersapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
import ma.eshop.usersapi.models.User;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
    boolean existsByEmail(String email);
}
