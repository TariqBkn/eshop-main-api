package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.errorHandlers.ProductHasAtLeastMaxNumberOfImagesException;
import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.models.SimilarProduct;
import ma.eshop.usersapi.services.ImagesService;
import ma.eshop.usersapi.services.ProductsService;
import ma.eshop.usersapi.services.SimilarProductsService;
import ma.eshop.usersapi.services.UploadsService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
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

    @Inject
    private ImagesService imagesService;

    @GetMapping("/{id}")
    ResponseEntity<Product> findById(@PathVariable int id){
        Optional<Product> product = productsService.findById(id);
        if(product.isPresent()){
            return ResponseEntity.ok().body(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<List<SimilarProduct>> getSimilarProductsOf(@PathVariable int id) throws UnsupportedEncodingException {
        Optional<Product> product = productsService.findById(id);
        if(product.isPresent()){
               return ResponseEntity.ok().body(similarProductsService.getSimilarProductsOf(id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public Page<Product> getProducts(@RequestParam(defaultValue="0") int page){
        return productsService.findAll(PageRequest.of(page, 30));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/bulk-add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BatchStatus> bulkAddProducts(@RequestParam("file") MultipartFile productsFile) throws Exception{
        uploadsService.uploadCsvFile(productsFile);
        return ResponseEntity.ok(productsService.loadProductsFromCsvFileIntoDataBase());
    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String imageName){
        return ResponseEntity.ok().body(imagesService.findByName(imageName));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity uploadImage(@RequestParam("image") MultipartFile MultiPartImage, @PathVariable int id){
        try {
            return productsService.AddImageToProductWithId(MultiPartImage, id);
        } catch(ProductHasAtLeastMaxNumberOfImagesException e){
            return ResponseEntity.status(405).body("Product has at least maximum number of images.");
        }catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}/images/{imageName}")
    public ResponseEntity deleteImage(@PathVariable("id") int id, @PathVariable("imageName") String imageName){
        try {
            productsService.removeImageOfProduct(imageName, id);
            imagesService.deleteByName(imageName);
            return ResponseEntity.ok().body("deleted");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Could not delete image");
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity updateProduct(@RequestBody Product newProduct, @PathVariable int id){
        productsService.patchProductTextualData(id, newProduct);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/search")
    public List<Product> search(@RequestBody List<String> keywords){
        return productsService.search(keywords);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/count")
    public long count(){
        return productsService.countProducts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable int id){
        productsService.deleteById(id);
    }

}
