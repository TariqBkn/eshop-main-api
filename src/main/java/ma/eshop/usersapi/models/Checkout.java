package ma.eshop.usersapi.models;

import javax.persistence.*;
import java.util.List;
@Entity
public class Checkout {
    @Id
    @GeneratedValue
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;
    private float totalPrice;
    public Checkout(){
        //TODO: Stopped here
    }
    public float getTotalPrice(){
        return totalPrice;
    }
    public void addOrderLine(OrderLine orderLine){
        orderLines.add(orderLine);
    }
    public void addOrderLines(List<OrderLine> orderLines){
        orderLines.addAll(orderLines);
    }
}
