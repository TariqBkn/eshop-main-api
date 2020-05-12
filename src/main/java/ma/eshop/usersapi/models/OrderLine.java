package ma.eshop.usersapi.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ma.eshop.usersapi.errorHandlers.QuantityInStockExceededException;

import javax.persistence.*;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue
        private int id;
        @ManyToOne
        private Product product;
        @ManyToOne
        private Order order;

        private int quantity;
        private float cost;

    protected OrderLine(){

    }

    public OrderLine(Product product, int quantity){
        this.product=product;
        this.quantity=quantity;
        updateCost();
    }
    public int getQuantity(){
        return quantity;
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
        cost=quantity*(product.getUnitPrice()*(1-product.getPromotionRatio()));
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public int getProductId(){
        return product.getId();
    }

    public int getOrderId() {
        return order.getId();
    }

    public OrderLine addQuantity(int quantity) throws QuantityInStockExceededException {
        int newQuantity = this.quantity + quantity;
        if(newQuantity<=product.getQuantityInStock()) {this.quantity += quantity;}
        else{
            throw new QuantityInStockExceededException("Can't exceed quantity in stock.");
        }
        updateCost();
        return this;
    }

    public Product getProduct() {
        return product;
    }
}

