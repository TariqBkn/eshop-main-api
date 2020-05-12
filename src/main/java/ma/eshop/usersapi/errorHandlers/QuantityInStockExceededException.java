package ma.eshop.usersapi.errorHandlers;

public class QuantityInStockExceededException extends Exception{
    public QuantityInStockExceededException(String message){
        super(message);
    }
}
