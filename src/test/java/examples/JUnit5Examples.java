package examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnit5Examples {
    @Test
    void firstTest(){
        System.out.println("firstTest()!!!!!");
        assertTrue(3<2);
    }
}
