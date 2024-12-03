import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

public class ControlGameTest {
    
    private ControlGame controlGame;

    @Before
    public void initialiseControlGame() {
        controlGame = new ControlGame();
    }

    @Test
    public void testInitialisationGameWonValue() {
        assertFalse(controlGame.isGameWon());
    }

    @Test
    public void testSetGameWon() {
        controlGame.setGameWon();
        
        assertTrue(controlGame.isGameWon());
    }

}
