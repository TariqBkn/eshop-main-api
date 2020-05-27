package ma.eshop.usersapi.batch.products;

import ma.eshop.usersapi.models.Product;
import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product product) throws Exception {
        if(containsInvalidPropertyValues(product)) {
            throw new Exception("valeurs incorrecte saisies dans le fichier");
        }
        return product;
    }

    private boolean containsInvalidPropertyValues(Product product) {
        return isNegative(product.getUnitPrice()) || isPromotionRatioInvalid(product) || isNegative(product.getQuantityInStock());
    }

    private boolean isPromotionRatioInvalid(Product product) {
        return product.getPromotionRatio() > 1 || isNegative(product.getPromotionRatio());
    }

    private boolean isNegative(float unitPrice) {
        return unitPrice < 0;
    }
}
