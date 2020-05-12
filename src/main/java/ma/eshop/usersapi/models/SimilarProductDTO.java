package ma.eshop.usersapi.models;

public class SimilarProductDTO {
    private int productId;
    private float similarityPercentage;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getSimilarityPercentage() {
        return similarityPercentage;
    }

    public void setSimilarityPercentage(float similarityPercentage) {
        this.similarityPercentage = similarityPercentage;
    }
}
