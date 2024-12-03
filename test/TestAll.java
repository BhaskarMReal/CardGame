
import org.junit.runners.Suite;
import org.junit.runner.RunWith;


@RunWith(Suite.class)
@Suite.SuiteClasses({CardTest.class, 
                    ControlGameTest.class,
                    DeckTest.class,
                    PackTest.class, 
                    PlayerTest.class})
public class TestAll {
    }
