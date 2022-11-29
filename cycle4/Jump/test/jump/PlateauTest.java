package jump;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests sur la classe Plateau.
 */
public class PlateauTest {

    @Test
    public void testAleatoire() {
        for (int i = 0; i < 100; i++) {
            Plateau plateau = Plateau.aleatoire(80, 20);
            for (int colonne = 0; colonne < plateau.largeur; colonne++) {
                boolean auMoinsUnePlateformeParColonne = false;
                for (int ligne = 0; ligne < plateau.hauteur; ligne++) {
                    Case laCase = plateau.cases[ligne][colonne];
                    boolean estPlateforme = laCase.estPlateforme();
                    auMoinsUnePlateformeParColonne |= estPlateforme;
                    if (ligne == 0) {
                        // pas de plateforme sur la ligne du haut
                        assertFalse(estPlateforme);
                    } else {
                        // pas de plateformes l'une au-dessus de l'autre directement
                        assertFalse(estPlateforme
                                && plateau.cases[ligne - 1][colonne].estPlateforme());
                    }
                }
                //assertTrue(auMoinsUnePlateformeParColonne);
            }
        }
    }

    @Test
    public void testSerialiser() {
        Plateau plateau = Plateau.aleatoire(40, 20);
        String serialise = plateau.serialiser();
        assertEquals(serialise, Plateau.deserialiser(serialise).serialiser());
    }

    @Test
    public void testDeserialiser() {
        Plateau p1 = Plateau.deserialiser(PLATEAU1);
        assertEquals(20, p1.hauteur);
        assertEquals(50, p1.largeur);
        assertEquals('A', p1.cases[9][0].contenu);
        assertEquals('X', p1.cases[9][1].contenu);
        assertEquals(' ', p1.cases[9][2].contenu);
        assertEquals('B', p1.cases[9][3].contenu);
        assertEquals('=', p1.cases[10][3].contenu);
        Plateau p2 = Plateau.deserialiser(PLATEAU2);
        assertEquals(20, p2.hauteur);
        assertEquals(50, p2.largeur);
        assertEquals(' ', p2.cases[9][0].contenu);
        assertEquals('X', p2.cases[9][1].contenu);
        assertEquals('A', p2.cases[9][2].contenu);
        assertEquals(' ', p2.cases[9][3].contenu);
        assertEquals('=', p2.cases[10][3].contenu);
    }

    @Test
    public void testTrouverColonneLibre() {
        assertEquals(2, Plateau.deserialiser(PLATEAU1).trouverColonneLibre());
        assertEquals(0, Plateau.deserialiser(PLATEAU2).trouverColonneLibre());
    }

    @Test
    public void testLigneJoueur() {
        Joueur x = new Joueur("Xavier");
        x.colonne = 1;
        assertEquals(9, Plateau.deserialiser(PLATEAU1).ligneJoueur(x));
    }

    @Test
    public void testLignePlateformeAuDessus() {
        Partie partie = PartieTest.partie1();
        assertEquals(5, partie.plateau.plateformeAuDessus(partie.joueurs[0]));
        assertEquals(1, partie.plateau.plateformeAuDessus(partie.joueurs[1]));
        assertEquals(-1, partie.plateau.plateformeAuDessus(partie.joueurs[2]));
        assertEquals(8, partie.plateau.plateformeAuDessus(partie.joueurs[3]));
    }

    @Test
    public void testLignePlateformeEnDessous() {
        Partie partie = PartieTest.partie1();
        assertEquals(-1, partie.plateau.plateformeEnDessous(partie.joueurs[0]));
        assertEquals(12, partie.plateau.plateformeEnDessous(partie.joueurs[1]));
        assertEquals(19, partie.plateau.plateformeEnDessous(partie.joueurs[2]));
        assertEquals(15, partie.plateau.plateformeEnDessous(partie.joueurs[3]));
    }

    @Test
    public void testAppliquer() {
        Partie partie = PartieTest.partie1();
        assertTrue(partie.plateau.appliquer(partie.joueurs[0], Action.HAUT));
        assertTrue(partie.plateau.appliquer(partie.joueurs[1], Action.HAUT));
        assertTrue(partie.plateau.appliquer(partie.joueurs[2], Action.BAS));
        assertTrue(partie.plateau.appliquer(partie.joueurs[3], Action.BAS));
        assertEquals(4, partie.plateau.ligneJoueur(partie.joueurs[0]));
        assertEquals(0, partie.joueurs[0].colonne);
        assertEquals(0, partie.plateau.ligneJoueur(partie.joueurs[1]));
        assertEquals(1, partie.joueurs[1].colonne);
        assertEquals(18, partie.plateau.ligneJoueur(partie.joueurs[2]));
        assertEquals(2, partie.joueurs[2].colonne);
        assertEquals(14, partie.plateau.ligneJoueur(partie.joueurs[3]));
        assertEquals(3, partie.joueurs[3].colonne);
        assertTrue(partie.plateau.appliquer(partie.joueurs[3], Action.RESTER));
        assertEquals(14, partie.plateau.ligneJoueur(partie.joueurs[3]));
        assertEquals(3, partie.joueurs[3].colonne);
        assertFalse(partie.plateau.appliquer(partie.joueurs[0], Action.HAUT));
        assertTrue(partie.plateau.appliquer(partie.joueurs[0], Action.BAS));
    }

    @Test
    public void testNbPlateformesLigne() {
        Plateau plateau = Plateau.deserialiser(PLATEAU1);
        assertEquals(0, plateau.nbPlateformesLigne(0));
        assertEquals(4, plateau.nbPlateformesLigne(1));
        assertEquals(2, plateau.nbPlateformesLigne(2));
        assertEquals(2, plateau.nbPlateformesLigne(3));
        assertEquals(4, plateau.nbPlateformesLigne(9));
        assertEquals(3, plateau.nbPlateformesLigne(19));
    }
    
    @Test
    public void testToString() {
        Plateau plateau = Plateau.deserialiser(PLATEAU1);
        assertEquals(PLATEAU1_STRING, plateau.toString());
    }

    final static String PLATEAU1
            = "50!20!"
            + "                                                  !"
            + "                ==      ==  =========          ===!"
            + "         =======                        ====      !"
            + "      ===                                        =!"
            + "                   =========      ===      ==     !"
            + "====         ======                               !"
            + "    =====               =======  ===============  !"
            + "           ======  =   =                        ==!"
            + "                 =   ==                    =====  !"
            + "AX B   ==========           ======      ===      =!"
            + "=======            ===               = =    ===== !"
            + "          ======= =              ==== =          =!"
            + "                 =     =======              ===== !"
            + "           =         ==               ====        !"
            + "       =               ====     = =         ======!"
            + "====             ======                           !"
            + "           =           ==                         !"
            + "        =          ===        ==========   =      !"
            + "    =       ======        =                  =====!"
            + "                                 ==   === ===     !";

    final static String SEP = System.lineSeparator();
    
    final static String PLATEAU1_STRING
            = "+--------------------------------------------------+" + SEP
            + "|                                                  |" + SEP
            + "|                ==      ==  =========          ===|" + SEP
            + "|         =======                        ====      |" + SEP
            + "|      ===                                        =|" + SEP
            + "|                   =========      ===      ==     |" + SEP
            + "|====         ======                               |" + SEP
            + "|    =====               =======  ===============  |" + SEP
            + "|           ======  =   =                        ==|" + SEP
            + "|                 =   ==                    =====  |" + SEP
            + "|AX B   ==========           ======      ===      =|" + SEP
            + "|=======            ===               = =    ===== |" + SEP
            + "|          ======= =              ==== =          =|" + SEP
            + "|                 =     =======              ===== |" + SEP
            + "|           =         ==               ====        |" + SEP
            + "|       =               ====     = =         ======|" + SEP
            + "|====             ======                           |" + SEP
            + "|           =           ==                         |" + SEP
            + "|        =          ===        ==========   =      |" + SEP
            + "|    =       ======        =                  =====|" + SEP
            + "|                                 ==   === ===     |" + SEP
            + "+--------------------------------------------------+" + SEP;

    final static String PLATEAU2
            = "50!20!"
            + "                                                  !"
            + "                ==      ==  =========          ===!"
            + "         =======                        ====      !"
            + "      ===                                        =!"
            + "                   =========      ===      ==     !"
            + "====         ======                               !"
            + "    =====               =======  ===============  !"
            + "           ======  =   =                        ==!"
            + "                 =   ==                    =====  !"
            + " XA    ==========           ======      ===      =!"
            + "=======            ===               = =    ===== !"
            + "          ======= =              ==== =          =!"
            + "                 =     =======              ===== !"
            + "           =         ==               ====        !"
            + "       =               ====     = =         ======!"
            + "====             ======                           !"
            + "           =           ==                         !"
            + "        =          ===        ==========   =      !"
            + "    =       ======        =                  =====!"
            + "                                 ==   === ===     !";

    final static String PLATEAU3
            = "50!20!"
            + "                                                  !"
            + " =              ==      ==  =========          ===!"
            + "         =======                        ====      !"
            + "      ===                                        =!"
            + "                   =========      ===      ==     !"
            + "=  =         ======                               !"
            + "    =====               =======  ===============  !"
            + "           ======  =   =                        ==!"
            + "   =             =   ==                    =====  !"
            + "       ==========           ======      ===      =!"
            + "==== ==            ===               = =    ===== !"
            + "          ======= =              ==== =          =!"
            + " =               =     =======              ===== !"
            + "           =         ==               ====        !"
            + "       =               ====     = =         ======!"
            + "   =             ======                           !"
            + "           =           ==                         !"
            + "        =          ===        ==========   =      !"
            + "   ==       ======        =                  =====!"
            + "  =                              ==   === ===     !";
}
