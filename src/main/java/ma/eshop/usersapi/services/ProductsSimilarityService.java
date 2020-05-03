package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsSimilarityService {
    @Value("api.similarityCalculator.url")
    private String similarityAPIUrl;

    public List<Product> getSimilarProductsOf(Product product) {
        //TODO: connect to API and get similar products.
        return null;
    }
}
