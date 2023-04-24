package exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InexistentProductExceptionTest {
    @Test
    public void testInexistentProductException(){
        String proudctName = "Bread";
        InexistentProductException exception = new InexistentProductException(proudctName);
        assertEquals("The product doesn't exist: " + proudctName, exception.getMessage());
    }
}