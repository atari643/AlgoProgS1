package jump;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests de la classe Joueur.
 */
public class JoueurTest {
    
    @Test
    public void testJoueur() {
        Joueur george = new Joueur("George");
        assertEquals("George", george.nom);
        assertEquals("Christophe", new Joueur("Christopher").nom);
        assertEquals("Christophe", new Joueur("Christophe").nom);
        assertEquals("Émilie-Ros", new Joueur("Émilie-Rose").nom);
    }
    
    @Test
    public void testCaracterePlateau() {
        assertEquals('S', new Joueur("Sarah Connor").caracterePlateau());
        assertEquals('A', new Joueur("A").caracterePlateau());
    }
    
    @Test
    public void testNomCorrect() {
        assertTrue(new Joueur("A").nomCorrect());
        assertTrue(new Joueur("B").nomCorrect());
        assertTrue(new Joueur("Z").nomCorrect());
        assertTrue(new Joueur("Anakin").nomCorrect());
        assertTrue(new Joueur("C3PO").nomCorrect());
        assertTrue(new Joueur("BB8?").nomCorrect());
        assertFalse(new Joueur("").nomCorrect());
        assertFalse(new Joueur(" ").nomCorrect());
        assertFalse(new Joueur(" Anakin").nomCorrect());
        assertFalse(new Joueur("a").nomCorrect());
        assertFalse(new Joueur("anakin").nomCorrect());
        assertFalse(new Joueur("À").nomCorrect());
        assertFalse(new Joueur("0").nomCorrect());
        assertFalse(new Joueur("").nomCorrect());
        assertFalse(new Joueur("#").nomCorrect());
        assertFalse(new Joueur("^").nomCorrect());
        assertFalse(new Joueur("").nomCorrect());
        assertFalse(new Joueur("A" + Partie.SEPARATEUR).nomCorrect());
        assertFalse(new Joueur("A" + Joueur.SEPARATEUR).nomCorrect());
        assertFalse(new Joueur("A" + Plateau.SEPARATEUR).nomCorrect());
    }

    @Test
    public void testSerialiser() {
        Joueur han = new Joueur("Han");
        han.colonne = 3;
        han.score = 14;
        han.statut = StatutJoueur.DOIT_JOUER;
        String serial = han.serialiser();
        assertEquals("Han!3!14!D!", serial);
    }
    
    @Test
    public void testDeserialiser() {
        Joueur han = Joueur.deserialiser("Han!3!14!D!");
        assertNotNull(han);
        assertEquals("Han", han.nom);
        assertEquals(3, han.colonne);
        assertEquals(14, han.score);
        assertEquals(StatutJoueur.DOIT_JOUER, han.statut);
    }

    @Test
    public void testLigneFichierScore() {
        Joueur boba = new Joueur("Boba Fett");
        boba.score = 14;
        assertEquals("Boba Fett!14", boba.ligneFichierScore());
    }
}
