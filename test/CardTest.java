import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    private Card newCard;

    // initialise new Card instance before running the tests
    @Before
    public void initialiseCard() {
        newCard = new Card(0);
    }
    // check that card was initialised correctly
    @Test
    public void checkCardInitialisation() {
        assertNotNull(newCard);
    }
   // check set and get value
    @Test
    public void checkCardValueChange() {
        newCard.setValue(5);
        assertEquals(5, newCard.getValue());  

        newCard.setValue(2);
        assertEquals(2, newCard.getValue());  
    }
    // check that negative values cannot be set
    @Test
    public void checkCardValueNotNegative() {
        newCard.setValue(-1);
        assertEquals(0, newCard.getValue());
        assertNotEquals(-1, newCard.getValue());
    }
    // check large value can be set
    @Test
    public void checkLargeCardValue() {
        newCard.setValue(20000);
        assertEquals(20000, newCard.getValue());  
}


}
