package ma.eshop.usersapi.batch.products;

import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.services.ProductsService;
import org.springframework.batch.item.ItemWriter;

import javax.inject.Inject;
import java.util.List;

public class ProductItemWriter implements ItemWriter<Product> {
    @Inject
    private ProductsService productsService;
    @Override
    public void write(List<? extends Product> products) throws Exception {
        productsService.saveAll(products);
    }
}
