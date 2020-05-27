package ma.eshop.usersapi.services;

import ma.eshop.usersapi.errorHandlers.ProductHasAtLeastMaxNumberOfImagesException;
import ma.eshop.usersapi.models.Image;
import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private Logger logger = LoggerFactory.getLogger(ProductsService.class);
    @Inject
    private ProductRepository productRepository;
    @Inject
    private JobLauncher jobLauncher;
    @Inject
    private Job job;
    @Inject
    private UploadsService uploadsService;


    public Optional<Product> findById(int id){
        return productRepository.findById(id);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAllProducts(pageable);
    }

    public boolean existsById(int productId) {
        return productRepository.existsById(productId);
    }

    public void saveAll(List<? extends Product> products) {
        productRepository.saveAll(products);
    }

    public BatchStatus loadProductsFromCsvFileIntoDataBase() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        JobParameters jobParameters = getJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        while(jobExecution.isRunning()) {
            logger.debug("NEW PRODUCTS BATCH RUNNING...");
        }
        return jobExecution.getStatus();
    }

    private JobParameters getJobParameters() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        return new JobParameters(jobParameterMap);
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public ResponseEntity AddImageToProductWithId(@RequestParam("image") MultipartFile MultiPartImage, @PathVariable int id) throws IOException, ProductHasAtLeastMaxNumberOfImagesException {
        Optional<Product> product = findById(id);
        if(product.isPresent()) {
            Product foundProduct = product.get();
            if(foundProduct.canNotAddImages()) { throw new ProductHasAtLeastMaxNumberOfImagesException("Image can't be added"); }
            String name = makeUniqueNameOfImage(MultiPartImage, id);
            persistImage(MultiPartImage, foundProduct, name);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    private void persistImage(@RequestParam("image") MultipartFile MultiPartImage, Product foundProduct, String name) throws IOException {
        uploadsService.uploadImage(MultiPartImage, name);
        attachImageToProduct(foundProduct, name);
        save(foundProduct);
    }

    private void attachImageToProduct(Product foundProduct, String name) {
        Image image = new Image(foundProduct, name);
        foundProduct.addImage(image);
    }

    private String makeUniqueNameOfImage(@RequestParam("image") MultipartFile MultiPartImage, @PathVariable int id) {
        long currentMillis = System.currentTimeMillis();
        return currentMillis+id+MultiPartImage.getOriginalFilename();
    }

    public void removeImageOfProduct(String imageName, int id) {
         Optional<Product> product = findById(id);
         if(product.isPresent()){
             Product foundProduct = removeImageFromProduct(imageName, product);
             save(foundProduct);
         }
    }

    private Product removeImageFromProduct(String imageName, Optional<Product> product) {
        Product foundProduct = product.get();

        foundProduct.getImages()
                    .removeIf(image -> image.getName().equals(imageName));
        return foundProduct;
    }

    public void patchProductTextualData(int existingProductId, Product newProduct) {
        Optional<Product> product = findById(existingProductId);
        if(product.isPresent()){
            patchTextualDataOfNewProductIntoExistingProduct(newProduct, product.get());
        }

    }

    private void patchTextualDataOfNewProductIntoExistingProduct(Product newProduct, Product existingProduct) {
        existingProduct.patchTextualDataFrom(newProduct);
        save(existingProduct);
    }

    public List<Product> search(List<String> keywords) {
        List<Product> foundProducts = new ArrayList<>();
        for (String keyword: keywords) {
            foundProducts.addAll(productRepository.findByKeyword(keyword.toLowerCase()));
        }
        List<Product> uniqueFoundProducts = removeDuplicatesFromFoundProducts(foundProducts);
        return uniqueFoundProducts;
    }

    private List<Product> removeDuplicatesFromFoundProducts(List<Product> foundProducts) {
        return foundProducts.stream().filter(distinctByKey(Product::getId)).collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public long countProducts() {
        return productRepository.count();
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
