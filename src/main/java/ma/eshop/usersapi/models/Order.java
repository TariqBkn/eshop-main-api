package ma.eshop.usersapi.models;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "Order_table")
public class Order {
    @Id
    @GeneratedValue
    private int id;
    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    public Order(){

    }
    public float getTotalCost(){
        return orderLines.stream()
                .map(product->product.getCost())
                .reduce(0f, (a, b) ->Float.sum(a,b));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines.clear();
        this.orderLines.addAll(orderLines);
    }
}
