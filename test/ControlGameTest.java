import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

public class ControlGameTest {
    
    private ControlGame controlGame;
    // initialise new instance of ControlGame
    @Before
    public void initialiseControlGame() {
        controlGame = new ControlGame();
    }
    // check initial value of controlGame is false
    @Test
    public void testInitialisationGameWonValue() {
        assertFalse(controlGame.isGameWon());
    }
    // check is setting to true works
    @Test
    public void testSetGameWon() {
        controlGame.setGameWon();
        
        assertTrue(controlGame.isGameWon());
    }

}
