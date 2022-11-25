package biosphere7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests unitaires de la classe JoueurBiosphere7.
 */
public class JoueurBiosphere7Test {

    /**
     * Test de la fonction actionsPossibles. Commentez les appels aux tests des
     * niveaux inférieurs, n'activez que le test du niveau à valider.
     */
    @Test
    public void testActionsPossibles() {
        //testActionsPossibles_niveau1();
        //testActionsPossibles_niveau2();
        //testActionsPossibles_niveau3();
        //testActionsPossibles_niveau4();
        testActionsPossibles_niveau5();
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 1.
     */
    public void testActionsPossibles_niveau1() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // un plateau sur lequel on veut tester actionsPossibles()
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        // on choisit la couleur du joueur
        char couleur = 'R';
        // on choisit le niveau
        int niveau = 1;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        // on peut aussi tester si une action est dans les actions possibles :
        assertTrue(actionsPossibles.contient("PaB,1,0"));
        // on peut aussi tester si une action n'est pas dans les actions 
        // possibles :
        assertFalse(actionsPossibles.contient("PaO,1,0"));
        assertFalse(actionsPossibles.contient("PaA,0,0"));
        // testons les 4 coins :
        assertTrue(actionsPossibles.contient("PaA,1,0"));
        assertTrue(actionsPossibles.contient("PnA,1,0"));
        assertTrue(actionsPossibles.contient("PaN,1,0"));
        assertTrue(actionsPossibles.contient("PnN,1,0"));
        // vérifions s'il y a le bon nombre d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 2.
     */
    public void testActionsPossibles_niveau2() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        char couleur = 'B';
        int niveau = 2;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // testons les 4 coins :
        assertTrue(actionsPossibles.contient("PaA,2,3"));
        assertTrue(actionsPossibles.contient("PnA,2,3"));
        assertFalse(actionsPossibles.contient("PaN,2,3"));
        assertTrue(actionsPossibles.contient("PnN,2,3"));
        // on peut poser sur une case quelconque vide :
        assertTrue(actionsPossibles.contient("PkD,2,3"));
        // on ne peut pas poser sur une case occupée :
        assertFalse(actionsPossibles.contient("PfA,2,3"));
        assertFalse(actionsPossibles.contient("PeI,2,3"));
        assertFalse(actionsPossibles.contient("PhJ,2,3"));
        // nombre correct d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES - 4,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 3.
     */
    public void testActionsPossibles_niveau3() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'R', 3);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        // on vérifie que toute les cases occuper on une action possible 
        assertTrue(actionsPossibles.contient("CaN,6,3"));
        assertTrue(actionsPossibles.contient("CdI,6,4"));
        assertTrue(actionsPossibles.contient("CfA,5,4"));
        assertTrue(actionsPossibles.contient("CeH,7,3"));
        assertTrue(actionsPossibles.contient("CeI,7,6"));
        assertTrue(actionsPossibles.contient("CeJ,6,4"));
        assertTrue(actionsPossibles.contient("CfI,7,3"));
        assertTrue(actionsPossibles.contient("ChJ,6,3"));
        assertTrue(actionsPossibles.contient("ChH,4,4"));
        assertTrue(actionsPossibles.contient("PhL,7,4"));
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 4
     */
    public void testActionsPossibles_niveau4() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'R', 4);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        // on vérifie que les cases voisines influe sur l'action possible 
        assertTrue(actionsPossibles.contient("PaM,8,4"));
        assertTrue(actionsPossibles.contient("PbN,8,4"));
        assertTrue(actionsPossibles.contient("PdH,9,4"));
        assertTrue(actionsPossibles.contient("PdJ,9,4"));
        assertTrue(actionsPossibles.contient("PfJ,9,4"));
        assertTrue(actionsPossibles.contient("PfH,9,4"));
        //la vitalité d'une case sans voisin
        assertTrue(actionsPossibles.contient("PhL,7,4"));
        String[] actionsPossiblesDepuisPlateau2
                = joueur.actionsPossibles(plateau, 'B', 4);
        ActionsPossibles actionsPossibles2
                = new ActionsPossibles(actionsPossiblesDepuisPlateau2);
        actionsPossibles2.afficher();
        assertTrue(actionsPossibles2.contient("PaM,6,6"));
        assertTrue(actionsPossibles2.contient("PbN,6,6"));
        assertTrue(actionsPossibles2.contient("PdH,6,7"));
        assertTrue(actionsPossibles2.contient("PdJ,6,7"));
        assertTrue(actionsPossibles2.contient("PfJ,6,7"));
        assertTrue(actionsPossibles2.contient("PfH,6,7"));
        assertTrue(actionsPossibles2.contient("PhL,6,5"));
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }
    /**
     * Test de la fonction actionsPossibles, au niveau 5
     */
    public void testActionsPossibles_niveau5() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'R', 5);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        // on vérifie que les cases voisines influe sur l'action possible 
        assertTrue(actionsPossibles.contient("PaM,7,4"));
        assertTrue(actionsPossibles.contient("PbN,7,4"));
        assertTrue(actionsPossibles.contient("PdH,8,4"));
        assertTrue(actionsPossibles.contient("PdJ,9,4"));
        assertTrue(actionsPossibles.contient("PfJ,8,4"));
        assertTrue(actionsPossibles.contient("PfH,7,4"));
        //la vitalité d'une case sans voisin
        assertTrue(actionsPossibles.contient("PhL,7,4"));
        //la vitalité de la plante rouge voisine est pris en compte
        assertTrue(actionsPossibles.contient("PhI,8,4"));
        String[] actionsPossiblesDepuisPlateau2
                = joueur.actionsPossibles(plateau, 'B', 5);
        ActionsPossibles actionsPossibles2
                = new ActionsPossibles(actionsPossiblesDepuisPlateau2);
        actionsPossibles2.afficher();
        assertTrue(actionsPossibles2.contient("PaM,6,6"));
        assertTrue(actionsPossibles2.contient("PbN,6,6"));
        assertTrue(actionsPossibles2.contient("PdH,6,6"));
        assertTrue(actionsPossibles2.contient("PdJ,6,5"));
        assertTrue(actionsPossibles2.contient("PfJ,6,6"));
        assertTrue(actionsPossibles2.contient("PfH,6,7"));
        //la vitalité d'une case sans voisin
        assertTrue(actionsPossibles2.contient("PhL,6,5"));
        //La plante rouge de vitalite 2 n'est pas pris en compte 
        assertFalse(actionsPossibles2.contient("PhI,8,4"));
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction ajoutActionPommier.
     */
    @Test
    public void testAjoutActionPommier() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(0, 0);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        // on crée le tableau d'actions et on en ajoute une
        joueur.ajoutActionPommier(Coordonnees.depuisCars('f', 'D'), actions,
                vitalites, Case.CAR_ROUGE);
        // l'action est devenue possible
        assertTrue(actions.contient("PfD,1,0"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        joueur.ajoutActionPommier(Coordonnees.depuisCars('b', 'H'), actions,
                vitalites, Case.CAR_ROUGE);
        // l'action a bien été ajoutée
        assertTrue(actions.contient("PbH,1,0"));
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }

    @Test
    public void testEstDansPlateau() {
        assertTrue(JoueurBiosphere7.estDansPlateau(new Coordonnees(0, 0), 14));
        assertTrue(JoueurBiosphere7.estDansPlateau(new Coordonnees(13, 13), 14));
        assertTrue(JoueurBiosphere7.estDansPlateau(new Coordonnees(0, 1), 14));
        assertFalse(JoueurBiosphere7.estDansPlateau(new Coordonnees(-1, 1), 14));
        assertTrue(JoueurBiosphere7.estDansPlateau(new Coordonnees(7, 13), 14));
        assertFalse(JoueurBiosphere7.estDansPlateau(new Coordonnees(7, 14), 14));
        assertTrue(JoueurBiosphere7.estDansPlateau(new Coordonnees(13, 0), 14));
        assertFalse(JoueurBiosphere7.estDansPlateau(new Coordonnees(14, 0), 14));
        assertTrue(JoueurBiosphere7.estDansPlateau(new Coordonnees(7, 0), 14));
        assertFalse(JoueurBiosphere7.estDansPlateau(new Coordonnees(7, -1), 14));
        assertTrue(JoueurBiosphere7.estDansPlateau(new Coordonnees(0, 1), 2));
        assertFalse(JoueurBiosphere7.estDansPlateau(new Coordonnees(0, 1), 1));
    }
    
    @Test
    public void testCasesVoisines() {
        Coordonnees coord = new Coordonnees(0, 0);
        assertEquals(2, JoueurBiosphere7.voisines(coord, 14).length);
        Coordonnees coord2 = new Coordonnees(6, 6);
        assertEquals(4, JoueurBiosphere7.voisines(coord2, 14).length);
        Coordonnees coord3 = new Coordonnees(0, 6);
        assertEquals(3, JoueurBiosphere7.voisines(coord3, 14).length);
    }

    @Test
    public void testSuivante() {
        assertEquals(new Coordonnees(5, 4),
                JoueurBiosphere7.suivante(new Coordonnees(4, 4), Direction.SUD));
        assertEquals(new Coordonnees(2, 3),
                JoueurBiosphere7.suivante(new Coordonnees(3, 3), Direction.NORD));
        assertEquals(new Coordonnees(0, -1),
                JoueurBiosphere7.suivante(new Coordonnees(0, 0), Direction.OUEST));
        assertEquals(new Coordonnees(200, 201),
                JoueurBiosphere7.suivante(new Coordonnees(200, 200), Direction.EST));
    }

    @Test
    public void testVitalitesPlateau() {
        // à décommenter le moment venu...
        // plateau : rouge 0, bleu 0
        Case[][] plateau1 = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        Vitalites vita1 = JoueurBiosphere7.vitalitesPlateau(plateau1);
        assertEquals(0, vita1.vitalitesRouge);
        assertEquals(0, vita1.vitalitesBleu);
        // plateau : rouge 2, bleu 2
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        Vitalites vita2 = JoueurBiosphere7.vitalitesPlateau(plateau2);
        assertEquals(2, vita2.vitalitesRouge);
        assertEquals(2, vita2.vitalitesBleu);
    }

    /**
     * Un plateau de base, sous forme de chaîne. Pour construire une telle
     * chaîne depuis votre sortie.log, déclarez simplement : final String
     * MON_PLATEAU = ""; puis copiez le plateau depuis votre sortie.log, et
     * collez-le entre les guillemets. Puis Alt+Shift+f pour mettre en forme.
     */
    final String PLATEAU_VIDE
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

    /**
     * Un plateau pour tester le niveau 2.
     */
    final String PLATEAU_NIVEAU2
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |PB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |PR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|PR1|   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |PB1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    /**
     * Un plateau pour tester le niveau 3-4-5.
     */
    final String PLATEAU_NIVEAU3
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |PB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |PR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |PB1|PR1|PR1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|PR1|   |   |   |   |   |   |   |PB1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |PR2|   |PB1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
}
