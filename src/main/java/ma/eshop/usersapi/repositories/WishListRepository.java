package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.WishList;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends PagingAndSortingRepository<WishList, Integer> {

}
