package ma.eshop.usersapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @JsonIgnore
    @ManyToOne
    private Product product;
    public Image(){

    }
    public Image(Product product, String name){
        this.name=name;
        this.product=product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
