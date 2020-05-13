package ma.eshop.usersapi.services;

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
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    public BatchStatus loadProductsFromFlatFileIntoDataBase() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        if(jobExecution.isRunning()) {
            logger.debug("::::::> PRODUCTS BATCH RUNNING");
        }
        return jobExecution.getStatus();
    }
}
