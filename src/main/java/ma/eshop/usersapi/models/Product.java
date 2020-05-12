package ma.eshop.usersapi.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String description;
    private float unitPrice;
    private String color;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<Image> images;
    private  String providerName;
    private float note;
    private int quantityInStock;
    private float promotionRatio=0;

    protected Product(){

    }

    public boolean hasIdEqualTo(int id) {
        return this.id==id;
    }

    public static class Builder {
       private Product product;

       public Builder(String name, float unitPrice, Image image){
           product.title =name;
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

    public String getTitle() {
        return title;
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

    public String getColor() {
        return color;
    }

    public void setColor(String colors) {
        this.color = color;
    }

    public int getQuantityInStock(){
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public float getPromotionRatio() {
        return promotionRatio;
    }

    public void setPromotionRatio(float promotionRation) {
        if(promotionRation>1 || promotionRation<0) { throw new IllegalArgumentException("Ratio must be between 0 and 1 included."); }
        this.promotionRatio = promotionRation;
    }
}
