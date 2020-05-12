package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAllProducts(pageable);
    }

    public boolean existsById(int productId) {
        return productRepository.existsById(productId);
    }
}
