package jump;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests de la classe Case.
 */
public class CaseTest {
        
   @Test
   public void testCase() {
       assertEquals(Case.CASE_VIDE, new Case().contenu);
   }
    
   @Test
   public void testCaseChar() {
       assertEquals('#', new Case('#').contenu);
   }
          
   @Test
   public void testEstVide() {
       Case uneCase = new Case();
       uneCase.contenu = Case.CASE_VIDE;
       assertTrue(uneCase.estVide());
       uneCase.contenu = Case.CASE_PLATEFORME;
       assertFalse(uneCase.estVide());
   }
          
   @Test
   public void testEstPlateforme() {
       Case uneCase = new Case();
       uneCase.contenu = Case.CASE_PLATEFORME;
       assertTrue(uneCase.estPlateforme());
       uneCase.contenu = Case.CASE_VIDE;
       assertFalse(uneCase.estPlateforme());
   }
   
   @Test
   public void testToString() {
       Case uneCase = new Case('E');
       assertEquals("E", uneCase.toString());
   }
}
