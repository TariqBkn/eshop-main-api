package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.Order;
import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.models.SimilarProduct;
import ma.eshop.usersapi.repositories.ProductRepository;
import ma.eshop.usersapi.services.ProductsService;
import ma.eshop.usersapi.services.SimilarProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Inject
    private ProductsService productsService;

    @Inject
    private SimilarProductsService similarProductsService;



    void AddProductToWishList(Product product){

    }
    void removeProductFromWishList(){

    }
    @RequestMapping("/{productId}")
    ResponseEntity<Product> findById(@PathVariable int productId){
        Optional<Product> product = productsService.findById(productId);
        if(product.isPresent()){
            return ResponseEntity.ok().body(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<SimilarProduct>> getSimilarProductsOf(@PathVariable int productId) throws UnsupportedEncodingException {
        Optional<Product> product = productsService.findById(productId);
        if(product.isPresent()){
               return ResponseEntity.ok().body(similarProductsService.getSimilarProductsOf(productId));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public Page<Product> getProducts(@RequestParam(defaultValue="0") int page){
        return productsService.findAllProducts(PageRequest.of(page, 30));
    }
}
