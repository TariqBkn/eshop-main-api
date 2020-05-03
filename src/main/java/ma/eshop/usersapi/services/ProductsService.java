package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class ProductsService {
    @Inject
    private ProductRepository productRepository;

    public Optional<Product> findById(int id){
        return productRepository.findById(id);
    }
}
