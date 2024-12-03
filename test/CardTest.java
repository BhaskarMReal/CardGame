import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    private Card newCard;

    @Before
    public void initialiseCard() {
        newCard = new Card(0);
    }

    @Test
    public void checkCardInitialisation() {
        assertNotNull(newCard);
    }
   
    @Test
    public void checkCardValueChange() {
        newCard.setValue(5);
        assertEquals(5, newCard.getValue());  

        newCard.setValue(2);
        assertEquals(2, newCard.getValue());  
    }

    @Test
    public void checkCardValueNotNegative() {
        newCard.setValue(-1);
        assertEquals(0, newCard.getValue());
        assertNotEquals(-1, newCard.getValue());
    }

    @Test
    public void checkLargeCardValue() {
        newCard.setValue(20000);
        assertEquals(20000, newCard.getValue());  
}


}
