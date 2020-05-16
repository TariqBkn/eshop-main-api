package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.Order;
import ma.eshop.usersapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository< Order, Integer> {
    @Query("select count(o)>0 from Order o where o.user.id=?1 and o.checkedOut=false ")
    boolean userHasUndoneOrder(int userId);

    @Query("select o from Order o where o.user.id=?1 and o.checkedOut=false")
    Order getUndoneOrderOfUser(int userId);

    @Query("select count(o)>1 from Order o where o.user.id=?1 and o.checkedOut=false")
    boolean userHasMoreThanOneUndoneOrder(int id);

    @Query("select o from Order o where o.user.id=?1 and o.checkedOut=false")
    List<Order> findAllUndoneByUser(int userId);
    @Query("select count(o)=0 from Order o where o.user.id=?1 and o.checkedOut=false")
    boolean userHasNoUndoneOrders(int userId);

    @Modifying
    @Transactional
    @Query("update Order o set o.checkedOut=true where o.id=?1")
    void checkoutOrder(int orderId);

    @Query("select o from Order o where o.user.id=?1 and o.checkedOut=true")
    List<Order> findAllDoneByUser(int id);
    @Query("select o from Order o where o.checkedOut=true")
    List<Order> findAllDone();
}
