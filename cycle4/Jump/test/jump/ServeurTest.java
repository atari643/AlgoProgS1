package jump;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests unitaires du serveur.
 */
public class ServeurTest {

    @BeforeClass
    public static void pre() {
        try {
            Serveur.logfile = new PrintWriter(Serveur.LOG_FILENAME);
        } catch (FileNotFoundException ex) {
            System.out.println("Erreur à l'ouverture du fichier de logs : " + ex);
        }
    }

    @Test
    public void testConnecterJoueur() {
        try {
            Serveur serveur = new Serveur();
            assertEquals(0, serveur.partie.nbJoueurs);
            // ajout normal
            assertEquals(ResultatAjout.ACCEPTE,
                    serveur.connecterJoueur("Neo", null));
            assertEquals(1, serveur.partie.nbJoueurs);
            assertEquals("Neo", serveur.partie.joueurs[0].nom);
        } catch (RemoteException ex) {
            System.err.println(ex.getMessage());
            fail();
        }
    }

    @Test
    public void testDeconnecterJoueur() {
        try {
            Serveur serveur = new Serveur();
            assertEquals(0, serveur.partie.nbJoueurs);
            // deconnexion avant toute connexion
            serveur.deconnecterJoueur("Neo");
            // connexion d'un joueur
            assertFalse(serveur.partie.dejaConnecte("Neo"));
            serveur.connecterJoueur("Neo", null);
            assertTrue(serveur.partie.dejaConnecte("Neo"));
            assertEquals(1, serveur.partie.nbJoueurs);
            // deconnexion d'un joueur
            serveur.deconnecterJoueur("Neo");
            assertFalse(serveur.partie.dejaConnecte("Neo"));
            assertEquals(0, serveur.partie.nbJoueurs);
            // reconnexion d'un nouveau joueur
            serveur.connecterJoueur("Neo", null);
            assertEquals(1, serveur.partie.nbJoueurs);
            // déconnexion d'un joueur inexistant
            serveur.deconnecterJoueur("Test");
        } catch (RemoteException ex) {
            Logger.getLogger(ServeurTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
