package ma.eshop.usersapi.config;



import ma.eshop.usersapi.batch.products.ProductItemProcessor;
import ma.eshop.usersapi.batch.products.ProductItemWriter;
import ma.eshop.usersapi.models.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.inject.Inject;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Inject
    private JobBuilderFactory jobBuilderFactory;

    @Inject
    private StepBuilderFactory stepBuilderFactory;

    @Inject
    private ItemReader<Product> productItemReader;


    @Bean
    public Job productJob() {
        return jobBuilderFactory.get("productsJob")
                .start(loadProductsStep()).build();
    }

    @Bean
    public Step loadProductsStep() {
        return stepBuilderFactory.get("load-products-step")
                .<Product, Product> chunk(100)
                .reader(productItemReader)
                .processor(productItemProcessor())
                .writer(productItemWriter())
                .build();
    }

    @Bean
    BeanValidatingItemProcessor<Product> beanValidatingItemProcessor() {
        BeanValidatingItemProcessor<Product> beanValidatingItemProcessor = new BeanValidatingItemProcessor<>();
        beanValidatingItemProcessor.setFilter(true);
        return beanValidatingItemProcessor;
    }
    @Bean
    public FlatFileItemReader<Product> flatFileItemReader(@Value("${productsBatchInputFile}") Resource productsBatchInputFile){
        FlatFileItemReader<Product> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("CSV-READER");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(productsBatchInputFile);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Product> lineMapper(){
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(true);
        // properties names in class
        lineTokenizer.setNames("title", "description", "unitPrice", "color", "providerName", "quantityInStock", "promotionRatio");
        lineMapper.setLineTokenizer(lineTokenizer);

        BeanWrapperFieldSetMapper<Product> fieldSetMapper =  new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Product.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    ProductItemProcessor productItemProcessor(){
        return new ProductItemProcessor();
    }

    @Bean
    ProductItemWriter productItemWriter(){
        return new ProductItemWriter();
    }

}
