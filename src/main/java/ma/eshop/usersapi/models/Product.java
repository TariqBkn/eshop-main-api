package ma.eshop.usersapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private float unitPrice;
    private String color;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<Image> images;
    @NotEmpty
    private  String providerName;
    private float note=-1;
    private int quantityInStock=0;
    private float promotionRatio=0;

    public Product(){

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


    public void setPromotionRatio(float promotionRation) {
        if(promotionRation>1 || promotionRation<0) { throw new IllegalArgumentException("Ratio must be between 0 and 1 included."); }
        this.promotionRatio = promotionRation;
    }


    public int getId() {
        return id;
    }
    public boolean hasIdEqualTo(int id) {
        return this.id==id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public float getPromotionRatio() {
        return promotionRatio;
    }
}
