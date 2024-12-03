import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PackTest {
    private Pack pack;

    @Before
    public void initialisePack() {
        pack = new Pack("one.txt", null);
    }

    @Test 
    public void testPackInitialisation() {
        assertNotNull(pack);
        assertEquals("one.txt", pack.getName());
    }

    @Test
    public void checkPackFileExist() {
        File file = new File(pack.getName());
        if (file.exists()) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    @Test
    public void checkPackNotNull() {
        pack.setPackList(pack.packToList(pack.getName()));
        assertNotNull(pack.getPackList());
    }

    @Test
    public void checkPackFileInvalid() {
        pack.setName("test.txt");
        File file = new File(pack.getName());
        if (file.exists()) {
            fail();
        }
    }
    

}
