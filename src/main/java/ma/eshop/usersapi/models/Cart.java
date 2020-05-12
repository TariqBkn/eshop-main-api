package ma.eshop.usersapi.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;
    private Date creationDate;

    public Cart(){
    }

    public Cart(Date creationDate){
        this.creationDate=creationDate;
    }

    public void addOrderLine(OrderLine orderLine){
        orderLines.add(orderLine);
    }

    public void addOrderLines(List<OrderLine> orderLines){
        orderLines.addAll(orderLines);
    }

    public void clearOrderLines(){
        orderLines.clear();
    }
}
