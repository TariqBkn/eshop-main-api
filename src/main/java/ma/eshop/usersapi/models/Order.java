package ma.eshop.usersapi.models;

import javax.persistence.OneToMany;
import java.util.List;

public class Order {
    @OneToMany
    private List<OrderLine> orderLines;

    float getTotalCost(){
        return orderLines.stream()
                .map(product->product.getCost())
                .reduce(0f, (a, b) ->Float.sum(a,b));
    }
}
