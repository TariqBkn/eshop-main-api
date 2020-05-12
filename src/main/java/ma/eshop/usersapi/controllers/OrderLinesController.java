package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.errorHandlers.QuantityInStockExceededException;
import ma.eshop.usersapi.models.OrderLine;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.services.OrderLinesService;
import ma.eshop.usersapi.services.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/orderlines")
public class OrderLinesController {

    @Inject
    private OrderLinesService orderLinesService;

    @Inject
    private OrdersService ordersService;

    @PostMapping("")
    @Transactional
    ResponseEntity add(@AuthenticationPrincipal User connectedUser, @RequestBody OrderLine orderLine){
        // there is an undone order of currentUser
        if(ordersService.userHasUndoneOrders(connectedUser.getId())){
            ordersService.makeSureThereIsOnlyOneUndoneOrder(connectedUser);
            try {
                ordersService.addQuantityToExistingOrderLine(connectedUser, orderLine);
            } catch (QuantityInStockExceededException e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        }else{
            ordersService.saveOrderLineInANewOrder(connectedUser, orderLine);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    ResponseEntity getAllNonCheckOutOrderLinesOfConnectedUser(@AuthenticationPrincipal User connectedUser){
        return ResponseEntity.ok()
                             .body(orderLinesService.findAllUndoneOrderLinesOf(connectedUser.getId()));
    }

    @DeleteMapping("/{orderLineId}")
    void deleteOrderLine(@PathVariable int orderLineId){
        this.orderLinesService.delete(orderLineId);
    }

}
