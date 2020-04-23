package ma.eshop.usersapi.models;

import javax.persistence.*;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private Product product;
    @ManyToOne
    private Product order;

    private int quantity;
    private float cost;

    protected OrderLine(){

    }

    public OrderLine(Product product, int quantity){
        this.product=product;
        this.quantity=quantity;
        updateCost();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateCost();
    }
    public void setProduct(Product product) {
        this.product = product;
        updateCost();
    }
    public float getCost(){
        return cost;
    }
    private void updateCost() {
        cost=product.getUnitPrice()*quantity;
    }

    public Product getOrder() {
        return order;
    }

    public void setOrder(Product order) {
        this.order = order;
    }
}
