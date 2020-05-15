package ma.eshop.usersapi.repositories;

import ma.eshop.usersapi.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderLinesRepository extends JpaRepository<OrderLine,Integer> {
    @Query("select o from OrderLine o where o.product.id = ?1")
    OrderLine orderLineByProductId(int id);

    @Modifying
    @Transactional
    @Query("update OrderLine o SET o.quantity=?1 where o.product.id = ?2")
    void updateQuantityByProductId(int productId, int newQuantity);
    @Query("select count(o)=1 from OrderLine o where o.product.id = ?1")
    boolean existsByProductId(int id);

    @Query("select o from OrderLine o where o.order.checkedOut=false and o.order.user.id=?1")
    List<OrderLine> findAllNoneCheckedOrderLinesByOrderUserId(int userId);

    @Query("select o from OrderLine o where o.order.user.id = ?1")
    List<OrderLine> findAllOrderLinesOfUserWithId(int userId);
    @Query("select o from OrderLine o where o.order.id = ?1")
    List<OrderLine> findOrderLinesByOrderId(int orderId);
}
