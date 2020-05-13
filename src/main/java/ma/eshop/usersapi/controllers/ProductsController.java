package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.models.SimilarProduct;
import ma.eshop.usersapi.services.ProductsService;
import ma.eshop.usersapi.services.SimilarProductsService;
import ma.eshop.usersapi.services.UploadsService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Inject
    private ProductsService productsService;

    @Inject
    private SimilarProductsService similarProductsService;

    @Inject
    private UploadsService uploadsService;


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

    @PostMapping(value = "/bulk-add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BatchStatus> bulkAddProducts(@RequestParam("file") MultipartFile productsFile) throws Exception{
        uploadsService.uploadCsvFile(productsFile);
        return ResponseEntity.ok(productsService.loadProductsFromCsvFileIntoDataBase());
    }


}
