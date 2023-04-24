package exception;

public class InexistentProductException extends RuntimeException {
    public InexistentProductException(String productName) {
        super("The product doesn't exist: " + productName);
    }
}
