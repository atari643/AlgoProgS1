package jump;

import static jump.PlateauTest.PLATEAU3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests de la classe Partie.
 */
public class PartieTest {

    @Test
    public void testAjouterJoueur() {
        Partie partie = new Partie();
        assertEquals(0, partie.nbJoueurs);
        // ajout normal
        assertEquals(ResultatAjout.ACCEPTE,
                partie.ajouterJoueur(new Joueur("Neo")));
        assertEquals(1, partie.nbJoueurs);
        assertEquals("Neo", partie.joueurs[0].nom);
        // ajout normal
        assertEquals(ResultatAjout.ACCEPTE,
                partie.ajouterJoueur(new Joueur("Trinity")));
        assertEquals(2, partie.nbJoueurs);
        assertEquals("Trinity", partie.joueurs[1].nom);
        // même initiale : refus
        assertEquals(ResultatAjout.REFUS_NOM_UTILISE,
                partie.ajouterJoueur(new Joueur("Test")));
        assertEquals(2, partie.nbJoueurs);
        // ne commence pas par une majuscule : refus
        assertEquals(ResultatAjout.REFUS_FORMAT_NOM_INCORRECT,
                partie.ajouterJoueur(new Joueur("test")));
        assertEquals(2, partie.nbJoueurs);
        // ajout normal
        assertEquals(ResultatAjout.ACCEPTE,
                partie.ajouterJoueur(new Joueur("Morpheus")));
        assertEquals(3, partie.nbJoueurs);
        assertEquals("Morpheus", partie.joueurs[2].nom);
        // ajout normal
        assertEquals(ResultatAjout.ACCEPTE,
                partie.ajouterJoueur(new Joueur("Smith")));
        assertEquals(4, partie.nbJoueurs);
        assertEquals("Smith", partie.joueurs[3].nom);
        // nombre maximal de joueurs atteint : refus
        assertEquals(ResultatAjout.REFUS_SERVEUR_PLEIN,
                partie.ajouterJoueur(new Joueur("Architect")));
        assertEquals(4, partie.nbJoueurs);
    }

    @Test
    public void testDejaConnecte() {
        Partie partie = new Partie();
        // aucune connexion
        assertFalse(partie.dejaConnecte("Neo"));
        assertFalse(partie.dejaConnecte("Trinity"));
        assertFalse(partie.dejaConnecte("Morpheus"));
        assertFalse(partie.dejaConnecte("Smith"));
        // 1ère connexion
        partie.ajouterJoueur(new Joueur("Neo"));
        assertTrue(partie.dejaConnecte("Neo"));
        assertFalse(partie.dejaConnecte("Trinity"));
        // d'autres connexions
        partie.ajouterJoueur(new Joueur("Trinity"));
        partie.ajouterJoueur(new Joueur("Morpheus"));
        assertEquals(3, partie.nbJoueurs);
        assertTrue(partie.dejaConnecte("Neo"));
        assertTrue(partie.dejaConnecte("Trinity"));
        assertTrue(partie.dejaConnecte("Morpheus"));
        assertFalse(partie.dejaConnecte("Smith"));
    }

    @Test
    public void testJoueurDeNom() {
        Partie partie = new Partie();
        Joueur neo = new Joueur("Neo");
        Joueur trinity = new Joueur("Trinity");
        Joueur morpheus = new Joueur("Morpheus");
        partie.ajouterJoueur(neo);
        partie.ajouterJoueur(trinity);
        partie.ajouterJoueur(morpheus);
        assertEquals("Neo", partie.joueurDeNom("Neo").nom);
        assertEquals("Trinity", partie.joueurDeNom("Trinity").nom);
        assertEquals("Morpheus", partie.joueurDeNom("Morpheus").nom);
    }

    @Test
    public void testRetirerJoueur() {
        Partie partie = new Partie();
        // deconnexion avant toute connexion
        partie.retirerJoueur("Neo");
        // connexion de 3 joueurs
        partie.ajouterJoueur(new Joueur("Neo"));
        partie.ajouterJoueur(new Joueur("Trinity"));
        partie.ajouterJoueur(new Joueur("Morpheus"));
        assertTrue(partie.dejaConnecte("Neo"));
        assertTrue(partie.dejaConnecte("Trinity"));
        assertTrue(partie.dejaConnecte("Morpheus"));
        assertEquals(3, partie.nbJoueurs);
        // deconnexion d'un joueur
        partie.retirerJoueur("Trinity");
        assertTrue(partie.dejaConnecte("Neo"));
        assertFalse(partie.dejaConnecte("Trinity"));
        assertTrue(partie.dejaConnecte("Morpheus"));
        assertEquals(2, partie.nbJoueurs);
        // deconnexion d'un second joueur
        partie.retirerJoueur("Neo");
        assertFalse(partie.dejaConnecte("Neo"));
        assertFalse(partie.dejaConnecte("Trinity"));
        assertTrue(partie.dejaConnecte("Morpheus"));
        assertEquals(1, partie.nbJoueurs);
        // connexion d'un nouveau joueur
        partie.ajouterJoueur(new Joueur("Smith"));
        assertFalse(partie.dejaConnecte("Neo"));
        assertFalse(partie.dejaConnecte("Trinity"));
        assertTrue(partie.dejaConnecte("Morpheus"));
        assertTrue(partie.dejaConnecte("Smith"));
        assertEquals(2, partie.nbJoueurs);
        // déconnexion d'un joueur inexistant
        partie.retirerJoueur("Test");
        // déconnexion de tous les joueurs
        partie.retirerJoueur("Smith");
        partie.retirerJoueur("Morpheus");
        assertFalse(partie.dejaConnecte("Neo"));
        assertFalse(partie.dejaConnecte("Trinity"));
        assertFalse(partie.dejaConnecte("Morpheus"));
        assertFalse(partie.dejaConnecte("Smith"));
        assertEquals(0, partie.nbJoueurs);
    }

    @Test
    public void testFinDeTour() {
        Partie partie = new Partie();
        Joueur neo = new Joueur("Neo");
        Joueur trinity = new Joueur("Trinity");
        Joueur morpheus = new Joueur("Morpheus");
        partie.ajouterJoueur(neo);
        partie.ajouterJoueur(trinity);
        partie.ajouterJoueur(morpheus);
        partie.demarrer();
        assertFalse(partie.finDeTour());
        partie.bouger(trinity, Action.BAS);
        assertFalse(partie.finDeTour());
        partie.bouger(morpheus, Action.HAUT);
        assertFalse(partie.finDeTour());
        partie.bouger(neo, Action.RESTER);
        assertTrue(partie.finDeTour());
    }

    @Test
    public void testFinDePartie() {
        Partie partie = new Partie();
        Joueur neo = new Joueur("Neo");
        Joueur trinity = new Joueur("Trinity");
        Joueur morpheus = new Joueur("Morpheus");
        partie.ajouterJoueur(neo);
        partie.ajouterJoueur(trinity);
        partie.ajouterJoueur(morpheus);
        assertFalse(partie.finDePartie());
        neo.statut = StatutJoueur.A_QUITTE;
        assertFalse(partie.finDePartie());
        morpheus.statut = StatutJoueur.A_QUITTE;
        assertFalse(partie.finDePartie());
        trinity.statut = StatutJoueur.A_QUITTE;
        assertTrue(partie.finDePartie());
    }

    @Test
    public void testTourSuivant() {
        Partie partie = partie1();
        partie.demarrer();
        partie.bouger(partie.joueurs[0], Action.HAUT);
        partie.bouger(partie.joueurs[1], Action.HAUT);
        partie.bouger(partie.joueurs[2], Action.HAUT);
        partie.bouger(partie.joueurs[3], Action.BAS);
        assertEquals(0, partie.joueurs[0].score);
        assertEquals(0, partie.joueurs[1].score);
        assertEquals(0, partie.joueurs[2].score);
        assertEquals(0, partie.joueurs[3].score);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[0].statut);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[1].statut);
        assertEquals(StatutJoueur.A_QUITTE, partie.joueurs[2].statut);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[3].statut);
        assertTrue(partie.finDeTour());
        partie.tourSuivant();
        assertEquals(1, partie.joueurs[0].score);
        assertEquals(1, partie.joueurs[1].score);
        assertEquals(0, partie.joueurs[2].score);
        assertEquals(1, partie.joueurs[3].score);
        assertEquals(StatutJoueur.DOIT_JOUER, partie.joueurs[0].statut);
        assertEquals(StatutJoueur.DOIT_JOUER, partie.joueurs[1].statut);
        assertEquals(StatutJoueur.A_QUITTE, partie.joueurs[2].statut);
        assertEquals(StatutJoueur.DOIT_JOUER, partie.joueurs[3].statut);
        assertFalse(partie.finDeTour());
    }

    @Test
    public void testTrouverJoueur() {
        Partie partie = partie1();
        assertEquals(0, partie.trouverJoueur("Alex"));
        assertEquals(3, partie.trouverJoueur("Boris"));
        assertEquals(-1, partie.trouverJoueur("Mace"));
        assertEquals(0, partie.trouverJoueur("Anakin"));
        assertEquals(3, partie.trouverJoueur("Boba"));        
    }
    
    @Test
    public void testNouvelleLigneJoueur() {
        Partie partie = partie1();
        assertEquals(9, partie.nouvelleLigneJoueur(partie.joueurs[0]));
        assertEquals(9, partie.nouvelleLigneJoueur(partie.joueurs[1]));
        assertEquals(9, partie.nouvelleLigneJoueur(partie.joueurs[2]));
        assertEquals(17, partie.nouvelleLigneJoueur(partie.joueurs[3]));
    }

    @Test
    public void testScoreMax() {
        Partie partie = new Partie();
        partie.scoreMax(); // résultat non défini, mais ne doit pas planter
        Joueur j1 = new Joueur("Neo");
        partie.ajouterJoueur(j1);
        assertEquals(0, partie.scoreMax());
        j1.score = 12;
        assertEquals(12, partie.scoreMax());
        Joueur j2 = new Joueur("Trinity");
        partie.ajouterJoueur(j2);
        j2.score = 12;
        assertEquals(12, partie.scoreMax());
        j2.score = 13;
        assertEquals(13, partie.scoreMax());
        j2.score = 11;
        assertEquals(12, partie.scoreMax());
    }

    @Test
    public void testListeVainqueurs() {
        Partie partie = new Partie();
        Joueur neo = new Joueur("Neo");
        Joueur trinity = new Joueur("Trinity");
        Joueur morpheus = new Joueur("Morpheus");
        partie.ajouterJoueur(neo);
        partie.ajouterJoueur(trinity);
        partie.ajouterJoueur(morpheus);
        neo.score = 12;
        trinity.score = 14;
        morpheus.score = 14;
        assertEquals("Trinity Morpheus ", partie.listeVainqueurs());
        neo.score = 14;
        assertEquals("Neo Trinity Morpheus ", partie.listeVainqueurs());
        trinity.score = 1;
        morpheus.score = 3;
        assertEquals("Neo ", partie.listeVainqueurs());
    }

    @Test
    public void testSerialiser() {
        Partie partie = partie1();
        partie.demarrer();
        partie.bouger(partie.joueurs[0], Action.HAUT);
        partie.bouger(partie.joueurs[1], Action.HAUT);
        partie.bouger(partie.joueurs[2], Action.HAUT);
        partie.bouger(partie.joueurs[3], Action.BAS);
        partie.joueurs[0].score = 1;
        partie.joueurs[1].score = 1;
        partie.joueurs[2].score = 0;
        partie.joueurs[3].score = 1;
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[0].statut);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[1].statut);
        assertEquals(StatutJoueur.A_QUITTE, partie.joueurs[2].statut);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[3].statut);
        assertEquals(1, partie.joueurs[0].score);
        assertEquals(1, partie.joueurs[1].score);
        assertEquals(0, partie.joueurs[2].score);
        assertEquals(1, partie.joueurs[3].score);
        assertEquals(EtatPartie.EN_COURS, partie.etat);
        assertEquals(SERIAL1, partie.serialiser());
    }

    @Test
    public void testDeserialiser() {
        Partie partie = Partie.deserialiser(SERIAL1, partie1());
        assertEquals(4, partie.nbJoueurs);
        assertEquals(1, partie.numTour);
        assertEquals(EtatPartie.EN_COURS, partie.etat);
        assertEquals("Alex", partie.joueurs[0].nom);
        assertEquals("Xavier", partie.joueurs[1].nom);
        assertEquals("Igor", partie.joueurs[2].nom);
        assertEquals("Boris", partie.joueurs[3].nom);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[0].statut);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[1].statut);
        assertEquals(StatutJoueur.A_QUITTE, partie.joueurs[2].statut);
        assertEquals(StatutJoueur.A_JOUE, partie.joueurs[3].statut);
        assertEquals(1, partie.joueurs[0].score);
        assertEquals(1, partie.joueurs[1].score);
        assertEquals(0, partie.joueurs[2].score);
        assertEquals(1, partie.joueurs[3].score);
        assertEquals(0, partie.joueurs[0].colonne);
        assertEquals(1, partie.joueurs[1].colonne);
        assertEquals(2, partie.joueurs[2].colonne);
        assertEquals(3, partie.joueurs[3].colonne);
    }
    
    static Partie partie1() {
        Partie partie = new Partie();
        partie.plateau = Plateau.deserialiser(PLATEAU3);
        partie.ajouterJoueur(new Joueur("Alex"));
        partie.ajouterJoueur(new Joueur("Xavier"));
        partie.ajouterJoueur(new Joueur("Igor"));
        partie.ajouterJoueur(new Joueur("Boris"));
        return partie;
    }

    final static String SERIAL1 = "1:4:1:"
            + "Alex!0!1!A!:"
            + "Xavier!1!1!A!:"
            + "Igor!2!0!Q!:"
            + "Boris!3!1!A!:"
            + "50!20!"
            + " X                                                !"
            + " =              ==      ==  =========          ===!"
            + "         =======                        ====      !"
            + "      ===                                        =!"
            + "A                  =========      ===      ==     !"
            + "=  =         ======                               !"
            + "    =====               =======  ===============  !"
            + "           ======  =   =                        ==!"
            + "   =             =   ==                    =====  !"
            + "  I    ==========           ======      ===      =!"
            + "==== ==            ===               = =    ===== !"
            + "          ======= =              ==== =          =!"
            + " =               =     =======              ===== !"
            + "           =         ==               ====        !"
            + "   B   =               ====     = =         ======!"
            + "   =             ======                           !"
            + "           =           ==                         !"
            + "        =          ===        ==========   =      !"
            + "   ==       ======        =                  =====!"
            + "  =                              ==   === ===     !:";
}
