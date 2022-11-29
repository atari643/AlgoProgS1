package jump;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests sur l'énumération ResultatAjout.
 */
public class ResultatAjoutTest {
    
    @Test
    public void testNombre() {
        assertEquals(5, ResultatAjout.values().length);
    }
    
    @Test
    public void testMotifRefus() {
        assertEquals("capacité maximale du serveur atteinte",
                ResultatAjout.REFUS_SERVEUR_PLEIN.motifRefus());
        assertEquals("nom de joueur déjà utilisé",
                ResultatAjout.REFUS_NOM_UTILISE.motifRefus());
        assertEquals("le nom de joueur ne commence pas par une majuscule",
                ResultatAjout.REFUS_FORMAT_NOM_INCORRECT.motifRefus());
        assertEquals("la partie a déjà commencé",
                ResultatAjout.REFUS_PARTIE_COMMENCEE.motifRefus());
        assertEquals("",
                ResultatAjout.ACCEPTE.motifRefus());
    }
}
