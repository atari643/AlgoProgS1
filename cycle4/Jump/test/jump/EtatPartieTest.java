package jump;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test de l'énumération EtatPartie.
 */
public class EtatPartieTest {
    
    @Test
    public void testNombre() {
        assertEquals(3, EtatPartie.values().length);
    }
}
