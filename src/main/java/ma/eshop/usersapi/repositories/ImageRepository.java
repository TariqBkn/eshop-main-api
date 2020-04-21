package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Image;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image,Integer> {
}
