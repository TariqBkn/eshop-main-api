package ma.eshop.usersapi.models;

import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private float unitPrice;
    private String colors;
    @OneToMany
    private List<Image> images;
    private  String providerName;
    private float note;

    protected Product(){

    }

    public boolean hasIdEqualTo(int id) {
        return this.id==id;
    }

    public static class Builder {
       private Product product;

       public Builder(String name, float unitPrice, Image image){
           product.name=name;
           product.images.add(image);
           product.unitPrice=unitPrice;
       }
        public Builder setDescription(String description){
            product.description=description;
            return this;
        }
        public Builder setProviderName(String providerName){
            product.providerName=providerName;
            return this;
        }

        public Product build(){
           return product;
        }

    }

    public void setNote(float note){
        this.note=note;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getProviderName() {
        return providerName;
    }

    public float getNote() {
        return note;
    }

    public int getId(){return id;}

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }
}
