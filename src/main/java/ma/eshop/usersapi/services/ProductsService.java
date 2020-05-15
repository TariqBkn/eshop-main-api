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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductsService {
    private final Logger logger = LoggerFactory.getLogger(ProductsService.class);

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

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAllProducts(pageable);
    }

    public boolean existsById(int productId) {
        return productRepository.existsById(productId);
    }

    public void saveAll(List<? extends Product> products) {
        productRepository.saveAll(products);
    }

    public BatchStatus loadProductsFromCsvFileIntoDataBase() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        if(jobExecution.isRunning()) {
            logger.debug("NEW PRODUCTS BATCH RUNNING");
        }
        return jobExecution.getStatus();
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public ResponseEntity AddImageToProductWithId(@RequestParam("image") MultipartFile MultiPartImage, @PathVariable int id) throws IOException, ProductHasAtLeastMaxNumberOfImagesException {
        Optional<Product> product = findById(id);
        if(product.isPresent()) {
            Product foundProduct = product.get();
            if(foundProduct.canNotAddImages()) { throw new ProductHasAtLeastMaxNumberOfImagesException("Image can't be added"); }
            long currentMillis = System.currentTimeMillis();
            String name = currentMillis+id+MultiPartImage.getOriginalFilename();
            uploadsService.uploadImage(MultiPartImage, name);
            Image image = new Image(foundProduct, name);
            foundProduct.addImage(image);
            save(foundProduct);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

}
