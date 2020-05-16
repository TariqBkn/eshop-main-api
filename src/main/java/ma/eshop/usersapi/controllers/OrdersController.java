package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.Order;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.services.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Inject
    private OrdersService ordersService;

    @PatchMapping("/{orderId}/checkout")
    ResponseEntity Checkout(@PathVariable int orderId, @AuthenticationPrincipal User user){
        if(ordersService.userHasNoUndoneOrders(user.getId())) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No order found");
        ordersService.checkoutOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}")
    ResponseEntity getOrderById(@PathVariable int orderId){
        Optional<Order> order = ordersService.findOrderById(orderId);
        if(order.isPresent()) return ResponseEntity.ok().body(order.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit introuvable");
    }
    @GetMapping("/checkouts")
    List<Order> getOrdersHistory(@AuthenticationPrincipal User connectedUser){
        return ordersService.findCheckoutsOfConnectedUser(connectedUser.getId());
    }

    @GetMapping("/turnover")
    float getTurnover(){
        return ordersService.getTurnover();
    }
}
