package jump;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test de l'énumération StatutJoueur.
 */
public class StatutJoueurTest {
    
    @Test
    public void testNombre() {
        assertEquals(3, StatutJoueur.values().length);
    }

    @Test
    public void testSerialiser() {
        assertEquals("A", StatutJoueur.A_JOUE.serialiser());
        assertEquals("D", StatutJoueur.DOIT_JOUER.serialiser());
        assertEquals("Q", StatutJoueur.A_QUITTE.serialiser());
    }
    
    @Test
    public void testDeserialiser() {
        assertEquals(StatutJoueur.A_JOUE, StatutJoueur.deserialiser("A"));
        assertEquals(StatutJoueur.DOIT_JOUER, StatutJoueur.deserialiser("D"));
        assertEquals(StatutJoueur.A_QUITTE, StatutJoueur.deserialiser("Q"));
    }
}
