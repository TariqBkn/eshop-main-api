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
        System.out.print("--------------------------- Product id "+orderLine.getProduct().getId()+"--------------------------");
        if(ordersService.userHasUndoneOrders(connectedUser.getId())){
            System.out.print("-------------------------- Here in 1 ---------------------------");
            ordersService.makeSureThereIsOnlyOneUndoneOrder(connectedUser);
            try {
                System.out.print("------------------------Quantity about to be added-----------------------------");

                ordersService.addQuantityToExistingOrderLine(connectedUser, orderLine);
                System.out.print("-------------------------Quantity added----------------------------");

            } catch (QuantityInStockExceededException e) {
                System.out.print("------------------------In exception-----------------------------");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        }else{
            System.out.print("---------------------------IN else--------------------------");
            ordersService.saveOrderLineInANewOrder(connectedUser, orderLine);
        }
        System.out.print("-----------------------DONE-----------------------------");
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
