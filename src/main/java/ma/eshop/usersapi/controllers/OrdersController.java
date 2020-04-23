package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/Orders")
public class OrdersController {

    @Transactional
    void Checkout(Order order){

    }

    void getOrdersHistory(){

    }
}
