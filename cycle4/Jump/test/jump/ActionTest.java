package jump;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests de l'énumération Action.
 */
public class ActionTest {
    
    @Test
    public void testNombre() {
        assertEquals(7, Action.values().length);
    }
    
    @Test
    public void testDepuisCaractere() {
        assertEquals(Action.DEMARRER, Action.depuisCaractere('d'));
        assertEquals(Action.HAUT, Action.depuisCaractere('z'));
        assertEquals(Action.RESTER, Action.depuisCaractere('s'));
        assertEquals(Action.BAS, Action.depuisCaractere('x'));
        assertEquals(Action.SAUVEGARDER, Action.depuisCaractere('w'));
        assertEquals(Action.RESTAURER, Action.depuisCaractere('r'));
        assertEquals(Action.QUITTER, Action.depuisCaractere('q'));
    }
    
    @Test
    public void testCaractere() {
        assertEquals('d', Action.DEMARRER.caractere());
        assertEquals('z', Action.HAUT.caractere());
        assertEquals('s', Action.RESTER.caractere());
        assertEquals('x', Action.BAS.caractere());
        assertEquals('w', Action.SAUVEGARDER.caractere());
        assertEquals('r', Action.RESTAURER.caractere());
        assertEquals('q', Action.QUITTER.caractere());
    }
    
    @Test
    public void testDescription() {
        assertEquals("démarrer", Action.DEMARRER.toString());
        assertEquals("haut", Action.HAUT.toString());
        assertEquals("rester", Action.RESTER.toString());
        assertEquals("bas", Action.BAS.toString());
        assertEquals("sauvegarder", Action.SAUVEGARDER.toString());
        assertEquals("restaurer", Action.RESTAURER.toString());
        assertEquals("quitter", Action.QUITTER.toString());
    }
}
