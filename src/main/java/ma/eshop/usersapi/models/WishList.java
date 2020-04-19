package ma.eshop.usersapi.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Entity
public class WishList {
    @Id
    private int id;
    @OneToOne
    private User user;
    @OneToMany
    private List<Product> products;
    private LocalDateTime created;
    protected WishList() {
    }
    WishList(User user){
        this.user=user;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        created = LocalDateTime.now();
    }
    void addProduct(Product product){
        products.add(product);
    }
    void removeProduct(int id){
        if ( WishListContainsProductWithIdEqualTo(id) ){
            products.removeIf(product -> product.hasIdEqualTo(id));
        }
    }
    private boolean WishListContainsProductWithIdEqualTo(int id) {
        return products.stream().filter(currentProduct -> currentProduct.hasIdEqualTo(id)).count()==1L;
    }

}
