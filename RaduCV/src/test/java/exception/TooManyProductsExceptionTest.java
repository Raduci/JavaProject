package exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TooManyProductsExceptionTest {
    @Test
    public void TestTooManyProducts(){
        int productNumber = 15;
        int productMax = 10;
        try{
            if(productNumber > productMax){
                throw new TooManyProductsException();
            }
            }catch (TooManyProductsException e){
            String expcetedMessage = "The maximum number of products has been exceeded.";
            String actualMessage = e.getMessage();
            assertTrue(actualMessage.contains(expcetedMessage));
        }
    }
}