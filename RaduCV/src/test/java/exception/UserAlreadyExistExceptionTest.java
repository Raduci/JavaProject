package exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistExceptionTest {

    @Test
    public void testUser(){
        model.User user = new model.User("Radu","123A");
        UserAlreadyExistException exception = new UserAlreadyExistException(user);
        assertEquals("The user already exists." + user,exception.getMessage());
    }
}