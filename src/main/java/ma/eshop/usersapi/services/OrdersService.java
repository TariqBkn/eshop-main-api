package ma.eshop.usersapi.services;

import ma.eshop.usersapi.errorHandlers.QuantityInStockExceededException;
import ma.eshop.usersapi.models.Order;
import ma.eshop.usersapi.models.OrderLine;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.repositories.OrdersRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    @Inject
    private OrdersRepository ordersRepository;
    @Inject
    private OrderLinesService orderLinesService;

    public Order getUndoneOrderOfUser(int userId) {
        return ordersRepository.getUndoneOrderOfUser(userId);
    }

    public void save(Order order){
        ordersRepository.save(order);
    }

    public boolean userHasUndoneOrders(int userId) {
         return ordersRepository.userHasUndoneOrder(userId);
    }

    public void addQuantityToExistingOrderLine(@AuthenticationPrincipal User currentUser, @RequestBody OrderLine orderLine) throws QuantityInStockExceededException {
        Order order = getUndoneOrderOfUser(currentUser.getId());
        Optional<OrderLine> existingOrderLineOfSameProduct = order.findOrderLineOfProduct(orderLine.getProductId());
        if(existingOrderLineOfSameProduct.isPresent()){
            OrderLine existingOrderLine = existingOrderLineOfSameProduct.get();
            existingOrderLine.addQuantity(orderLine.getQuantity());
            orderLinesService.save(existingOrderLine);
        }else {
            order.addOrderLine(orderLine);
            orderLine.setOrder(order);
            save(order);
        }
    }
    public void makeSureThereIsOnlyOneUndoneOrder(User user) {
        if (userHasMoreThanOneUndoneOrder(user)) {
            keepOnlyOneUndoneOrder(user);
        }
    }

    public boolean userHasNoUndoneOrders(int userId){
        System.out.println(" /****************************///////////////  user "+userId+" has no undone order : "+ordersRepository.userHasNoUndoneOrders(userId));
        return ordersRepository.userHasNoUndoneOrders(userId);
    }
    private void keepOnlyOneUndoneOrder(User currentUser) {
        List<OrderLine> orderLinesOfUser = orderLinesService.findOderLinesOfUser(currentUser.getId());
        List<Order> orders = findUndoneOrdersOfUserWithId(currentUser);
        Order orderToKeep;
        if(orders.size()>0){
            orderToKeep = orders.get(0);
            orderLinesOfUser.forEach(orderLine -> orderLine.setOrder(orderToKeep));
            orderLinesOfUser.forEach(orderLine -> orderToKeep.addOrderLine(orderLine));
            ordersRepository.save(orderToKeep);
            orders.stream().filter(order -> order.getId() != orderToKeep.getId()).forEach(order -> ordersRepository.delete(order));
        }
    }

    private List<Order> findUndoneOrdersOfUserWithId(User user) {
        return ordersRepository.findAllUndoneByUser(user.getId());
    }

    private boolean userHasMoreThanOneUndoneOrder(User currentUser) {
        return ordersRepository.userHasMoreThanOneUndoneOrder(currentUser.getId());
    }

    public void saveOrderLineInANewOrder(@AuthenticationPrincipal User currentUser, @RequestBody OrderLine orderLine) {
        Order order = createNewOrderWithOrderLineInIt(currentUser, orderLine);
        save(order);
    }

    private Order createNewOrderWithOrderLineInIt(@AuthenticationPrincipal User currentUser, @RequestBody OrderLine orderLine) {
        Order order = new Order();
        order.addOrderLine(orderLine);
        order.setUser(currentUser);
        orderLine.setOrder(order);
        return order;
    }
    @Transactional
    public void checkoutOrder(int orderId) {
        ordersRepository.checkoutOrder(orderId);
    }

    public Optional<Order> findOrderById(int orderId) {
        return ordersRepository.findById(orderId);
    }

    public List<Order> findCheckoutsOfConnectedUser(int userId) {
        return ordersRepository.findAllDoneByUser(userId);
    }
}
