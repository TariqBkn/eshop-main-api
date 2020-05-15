package ma.eshop.usersapi.errorHandlers;

public class ProductHasAtLeastMaxNumberOfImagesException extends Exception{
    public ProductHasAtLeastMaxNumberOfImagesException(String message){
        super(message);
    }
}
