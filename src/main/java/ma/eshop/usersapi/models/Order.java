package ma.eshop.usersapi.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Order {
    @Id
    @GeneratedValue
    private int id;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "order", orphanRemoval = true)
     private List<OrderLine> orderLines;
    @ManyToOne
    private User user;

    private boolean checkedOut=false;

    public Order(){
        if(orderLines==null) orderLines = new ArrayList<>();
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
    public boolean isCheckedOut(){
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public void checkoutOrder(){
        checkedOut=true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addOrderLine(OrderLine orderLine){
        this.orderLines.add(orderLine);
    }
    public void removeOrderLine(int orderLineId){
        orderLines = this.orderLines.stream().filter(orderLine -> orderLine.getId()!=orderLineId).collect(Collectors.toList());
    }

    public Optional<OrderLine> findOrderLineOfProduct(int productId) {
        return orderLines.stream().filter(orderLine -> orderLine.getProductId()==productId).findFirst();
    }
}
