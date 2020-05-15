package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.OrderLine;
import ma.eshop.usersapi.repositories.OrderLinesRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class OrderLinesService {

    @Inject
    private OrderLinesRepository orderLinesRepository;

    public boolean existByProductId(int id){
        return orderLinesRepository.existsByProductId(id);
    }

    public void updateQuantityByProductId(int productId, int quantity) {
        orderLinesRepository.updateQuantityByProductId(productId, quantity);
    }

    public void save(OrderLine newOrderLine){
        orderLinesRepository.save(newOrderLine);
    }

    public List<OrderLine> findAllUndoneOrderLinesOf(int userId) {
        return orderLinesRepository.findAllNoneCheckedOrderLinesByOrderUserId(userId);
    }

    public void delete(int orderLineId) {
        this.orderLinesRepository.deleteById(orderLineId);
    }

    public List<OrderLine> findOderLinesOfUser(int userId) {
        return this.orderLinesRepository.findAllOrderLinesOfUserWithId(userId);
    }

    public List<OrderLine> findOrderLinesByOrderId(int orderId) {
        return this.orderLinesRepository.findOrderLinesByOrderId(orderId);
    }
}
