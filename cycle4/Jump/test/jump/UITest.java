package jump;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests sur la classe UI.
 */
public class UITest {

    @Test
    public void testPartieDroite() {
        Partie partie = new Partie();
        // 1er joueur
        Joueur george = new Joueur("George");
        george.score = 14;
        george.statut = StatutJoueur.DOIT_JOUER;
        partie.ajouterJoueur(george);
        // 2ème joueur
        Joueur steven = new Joueur("Steven");
        steven.score = 7;
        steven.statut = StatutJoueur.A_QUITTE;
        partie.ajouterJoueur(steven);
        // 3ème joueur
        Joueur christopher = new Joueur("Christopher");
        christopher.score = 104;
        christopher.statut = StatutJoueur.A_JOUE;
        partie.ajouterJoueur(christopher);
        // test
        System.out.println(UI.partieDroite(partie));
        final String SEP = System.lineSeparator();
        String attendu
                = "Tour #0" + SEP
                + " " + SEP
                + "    Joueurs    | Scores " + SEP
                + "---------------+--------" + SEP
                + " *  George     |   14" + SEP
                + " -  Steven     |    7" + SEP
                + "    Christophe |  104" + SEP
                + "" + SEP
                + "" + SEP
                + "Actions possibles :" + SEP
                + "[d] démarrer" + SEP
                + "[q] quitter" + SEP;
        assertEquals(attendu, UI.partieDroite(partie));
    }

    @Test
    public void testCarStatutJoueur() {
        Joueur j = new Joueur("John");
        j.statut = StatutJoueur.A_JOUE;
        assertEquals(UI.CAR_A_JOUE, UI.carStatutJoueur(j));
        j.statut = StatutJoueur.DOIT_JOUER;
        assertEquals(UI.CAR_DOIT_JOUER, UI.carStatutJoueur(j));
        j.statut = StatutJoueur.A_QUITTE;
        assertEquals(UI.CAR_A_QUITTE, UI.carStatutJoueur(j));
    }

    @Test
    public void testLigneJoueur() {
        final String SEP = System.lineSeparator();
        // 1er joueur
        Joueur george = new Joueur("George");
        george.score = 14;
        george.statut = StatutJoueur.DOIT_JOUER;
        assertEquals(" *  George     |   14" + SEP, UI.ligneJoueur(george));
        // 2ème joueur
        Joueur steven = new Joueur("Steven");
        steven.score = 7;
        steven.statut = StatutJoueur.A_QUITTE;
        assertEquals(" -  Steven     |    7" + SEP, UI.ligneJoueur(steven));
        // 3ème joueur
        Joueur christopher = new Joueur("Christopher");
        christopher.score = 104;
        christopher.statut = StatutJoueur.A_JOUE;
        assertEquals("    Christophe |  104" + SEP, UI.ligneJoueur(christopher));
    }

    @Test
    public void testVueGlobale() {
        Partie partie = new Partie();
        // 1er joueur
        Joueur george = new Joueur("George");
        george.score = 14;
        george.statut = StatutJoueur.DOIT_JOUER;
        partie.ajouterJoueur(george);
        // 2ème joueur
        Joueur steven = new Joueur("Steven");
        steven.score = 7;
        steven.statut = StatutJoueur.A_QUITTE;
        partie.ajouterJoueur(steven);
        // 3ème joueur
        Joueur christopher = new Joueur("Christopher");
        christopher.score = 104;
        christopher.statut = StatutJoueur.A_JOUE;
        partie.ajouterJoueur(christopher);
        // affichage
        System.out.println(UI.vueGlobale(partie));
    }
}
