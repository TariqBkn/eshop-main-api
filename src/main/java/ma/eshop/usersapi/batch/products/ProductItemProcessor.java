package ma.eshop.usersapi.batch.products;

import ma.eshop.usersapi.models.Product;
import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product product) throws Exception {
        //TODO: check quantity in stock && unit price && promotion ratio
        return product;
    }
}
