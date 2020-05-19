package ma.eshop.usersapi.services;

import ma.eshop.usersapi.errorHandlers.QuantityInStockExceededException;
import ma.eshop.usersapi.models.Order;
import ma.eshop.usersapi.models.OrderLine;
import ma.eshop.usersapi.models.Product;
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

    private final OrdersRepository ordersRepository;

    private final OrderLinesService orderLinesService;

    private final ProductsService productsService;

    @Inject
    public OrdersService(OrdersRepository ordersRepository, OrderLinesService orderLinesService, ProductsService productsService) {
        this.ordersRepository = ordersRepository;
        this.orderLinesService = orderLinesService;
        this.productsService = productsService;
    }

    public Order getUndoneOrderOfUser(int userId) {
        return ordersRepository.getUndoneOrderOfUser(userId);
    }

    public void save(Order order){
        ordersRepository.save(order);
    }

    public boolean userHasUndoneOrders(int userId) {
         return ordersRepository.userHasUndoneOrder(userId);
    }

    public void addQuantityToExistingOrderLine(@AuthenticationPrincipal User currentUser, @RequestBody OrderLine newOrderLine) throws QuantityInStockExceededException {
        Order order = getUndoneOrderOfUser(currentUser.getId());
        Optional<OrderLine> existingOrderLineOfSameProduct = order.findOrderLineOfProduct(newOrderLine.getProductId());
        if(existingOrderLineOfSameProduct.isPresent()){
            addNewQuantityInExistingOrderLine(newOrderLine, existingOrderLineOfSameProduct.get());
        }else {
            addNewOrderLineInExistingUndoneOrder(newOrderLine, order);
        }
    }

    private void addNewOrderLineInExistingUndoneOrder(@RequestBody OrderLine newOrderLine, Order order) {
        order.addOrderLine(newOrderLine);
        newOrderLine.setOrder(order);
        save(order);
    }

    private void addNewQuantityInExistingOrderLine(@RequestBody OrderLine newOrderLine, OrderLine existingOrderLineOfSameProduct) throws QuantityInStockExceededException {
        existingOrderLineOfSameProduct.addQuantity(newOrderLine.getQuantity());
        orderLinesService.save(existingOrderLineOfSameProduct);
    }

    public void makeSureThereIsOnlyOneUndoneOrder(User user) {
        if (userHasMoreThanOneUndoneOrder(user)) {
            keepOnlyOneUndoneOrder(user);
        }
    }

    public boolean userHasNoUndoneOrders(int userId){
        return ordersRepository.userHasNoUndoneOrders(userId);
    }

    private void keepOnlyOneUndoneOrder(User currentUser) {
        List<OrderLine> orderLinesOfUser = orderLinesService.findOderLinesOfUser(currentUser.getId());
        List<Order> undoneOrdersOfCurrentUser = findUndoneOrdersOfUserWithId(currentUser);
        Order orderToKeep;
        if(undoneOrdersOfCurrentUser.size()>0){
            orderToKeep = undoneOrdersOfCurrentUser.get(0);
            putAllOrderLinesOfCurrentUserInTheOrderToKeep(orderLinesOfUser, orderToKeep);
            ordersRepository.save(orderToKeep);
            deleteAllOtherUndoneOrders(undoneOrdersOfCurrentUser, orderToKeep);
        }
    }

    private void deleteAllOtherUndoneOrders(List<Order> undoneOrdersOfCurrentUser, Order orderToKeep) {
        undoneOrdersOfCurrentUser.stream().filter(order -> order.getId() != orderToKeep.getId()).forEach(order -> ordersRepository.delete(order));
    }

    private void putAllOrderLinesOfCurrentUserInTheOrderToKeep(List<OrderLine> orderLinesOfUser, Order orderToKeep) {
        orderLinesOfUser.forEach(orderLine -> orderLine.setOrder(orderToKeep));
        orderLinesOfUser.forEach(orderLine -> orderToKeep.addOrderLine(orderLine));
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
        updateQuantityInStockOfProductsInCheckoutOrder(orderId);
    }

    private void updateQuantityInStockOfProductsInCheckoutOrder(int orderId) {
        List<OrderLine> orderLinesOfCheckoutOrder = orderLinesService.findOrderLinesByOrderId(orderId);
        orderLinesOfCheckoutOrder.stream().forEach(
                orderLine -> {
                    updateQuantityInStockOfProductInOrderLine(orderLine);
                }
        );
    }

    private void updateQuantityInStockOfProductInOrderLine(OrderLine orderLine) {
        Product productInCurrentOrderLine = orderLine.getProduct();
        int newQuantityInStockValue = productInCurrentOrderLine.getQuantityInStock() - orderLine.getQuantity();
        productInCurrentOrderLine.setQuantityInStock(newQuantityInStockValue);
        productsService.save(productInCurrentOrderLine);
    }

    public Optional<Order> findOrderById(int orderId) {
        return ordersRepository.findById(orderId);
    }

    public List<Order> findCheckoutsOfConnectedUser(int userId) {
        return ordersRepository.findAllDoneByUser(userId);
    }

    public float getTurnover() {
        return ordersRepository.findAllDone().stream().map(order -> order.getTotalCost()).reduce(0F,(a, b) -> a+b);
    }


}
