package ma.eshop.usersapi.models;


public class SimilarProduct {

    private Product product;

    private float similarityPercentage;

    public SimilarProduct(Product product, float similarityPercentage){
        this.similarityPercentage = similarityPercentage;
        this.product = product;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getSimilarityPercentage() {
        return similarityPercentage;
    }

    public void setSimilarityPercentage(float similarityPercentage) {
        this.similarityPercentage = similarityPercentage;
    }
}
