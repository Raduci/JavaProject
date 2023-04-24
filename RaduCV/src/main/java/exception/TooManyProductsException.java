package exception;

public class TooManyProductsException extends RuntimeException{
    public TooManyProductsException(){
        super("The maximum number of products has been exceeded.");
    }
}
