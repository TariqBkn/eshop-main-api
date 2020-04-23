package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.Order;
import ma.eshop.usersapi.models.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RestController
@RequestMapping("/Carts")
public class ProductsController {

    void AddProductToWishList(Product product){

    }
    void removeProductFromWishList(){

    }

//    void List<Product> getProductRecommendations(){
//        return new ArrayList<Product>();
//    }
}
