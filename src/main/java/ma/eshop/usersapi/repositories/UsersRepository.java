package ma.eshop.usersapi.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ma.eshop.usersapi.models.User;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
    boolean existsByEmail(String email);
    @Query("select u from User u where u.role='USER'")
    Page<User> findAllNonAdminUsers(Pageable pageable);

    @Query("select u from User u where u.role='USER' and" +
            "(" +
            "lower(u.email) like CONCAT('%', ?1,'%')" +
            "or lower(u.address.city) like CONCAT('%', ?1,'%') "+
            "or lower(u.address.number) like CONCAT('%', ?1,'%') "+
            "or lower(u.address.streetName) like CONCAT('%', ?1,'%')"+
            "or lower(u.firstName) like CONCAT('%', ?1,'%')"+
            "or lower(u.lastName) like CONCAT('%', ?1,'%')" +
            ")"
           )
    Page<User> search( Pageable pageable, String keyword);

    @Modifying
    @Transactional
    @Query("update User u SET u.accountNonLocked= CASE u.accountNonLocked WHEN TRUE THEN FALSE ELSE TRUE END where u.id = ?1")
    void alterAccountStatusOfUserWithId(int userId);
}
