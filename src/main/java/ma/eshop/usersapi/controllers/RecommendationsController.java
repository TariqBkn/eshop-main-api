package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.services.ProductsService;
import ma.eshop.usersapi.services.ProductsSimilarityService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommend")
public class RecommendationsController {
    @Inject
    private ProductsService productsService;
    @Inject
    private ProductsSimilarityService productsSimilarityService;

    //TODO: make unified response form
    //TODO: remove null
    @GetMapping("/products/{productId}")
    public List<Product> findSimilarProductsById(@PathVariable int productId)  {
        Optional<Product> product = productsService.findById(productId);
        if(product.isPresent()){
            return productsSimilarityService.getSimilarProductsOf(product.get());
        }
        return null;
    }
}
