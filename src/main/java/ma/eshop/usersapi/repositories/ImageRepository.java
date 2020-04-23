package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
}
