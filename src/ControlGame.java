import java.util.concurrent.atomic.AtomicBoolean;

public class ControlGame {
    // creates gameWon using type AtomicBoolean with default value false
    private AtomicBoolean gameWon = new AtomicBoolean(false);
    
    
    // retrieves current atomic boolean value 
    public boolean isGameWon() {
        return gameWon.get();
    }
    // sets atomic bool to true
    public void setGameWon() {
        gameWon.set(true);
    }

}
