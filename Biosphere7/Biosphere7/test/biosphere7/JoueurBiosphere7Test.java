package biosphere7;

import static org.junit.Assert.assertArrayEquals;
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
        //testActionsPossibles_niveau5();
        //testActionsPossibles_niveau6();
        //testActionsPossibles_niveau7();
        //testActionsPossibles_niveau8();
        //testActionsPossibles_niveau9();
        testActionsPossibles_niveau10();
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
     * Test de la fonction actionsPossibles, au niveau 6
     */
    public void testActionsPossibles_niveau6() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU6);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'R', 6);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        assertTrue(actionsPossibles.contient("PaA,13,12"));
        assertTrue(actionsPossibles.contient("PcE,12,10"));
        assertFalse(actionsPossibles.contient("PeI,15,12"));
        String[] actionsPossiblesDepuisPlateau2
                = joueur.actionsPossibles(plateau, 'B', 6);
        ActionsPossibles actionsPossibles2
                = new ActionsPossibles(actionsPossiblesDepuisPlateau2);
        actionsPossibles2.afficher();
        assertTrue(actionsPossibles2.contient("PaA,12,13"));
        assertTrue(actionsPossibles2.contient("PcE,10,12"));
        assertFalse(actionsPossibles2.contient("PeI,12,15"));
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES - 1,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 7
     */
    public void testActionsPossibles_niveau7() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU7);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'R', 7);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        assertTrue(actionsPossibles.contient("PaA,27,17"));
        assertTrue(actionsPossibles.contient("SaA,27,17"));
        assertTrue(actionsPossibles.contient("BaA,27,17"));
        assertTrue(actionsPossibles.contient("DaA,27,17"));
        assertTrue(actionsPossibles.contient("TaA,27,17"));
        assertTrue(actionsPossibles.contient("HaA,27,17"));
        assertTrue(actionsPossibles.contient("BmA,29,17"));
        assertTrue(actionsPossibles.contient("FjJ,28,17"));
        assertTrue(actionsPossibles.contient("FmH,26,20"));
        assertFalse(actionsPossibles.contient("ShE,27,17"));
        assertFalse(actionsPossibles.contient("FjJ,29,17"));
        String[] actionsPossiblesDepuisPlateau2
                = joueur.actionsPossibles(plateau, 'B', 7);
        ActionsPossibles actionsPossibles2
                = new ActionsPossibles(actionsPossiblesDepuisPlateau2);
        actionsPossibles2.afficher();
        assertTrue(actionsPossibles2.contient("PaA,26,18"));
        assertTrue(actionsPossibles2.contient("SaA,26,18"));
        assertTrue(actionsPossibles2.contient("BaA,26,18"));
        assertTrue(actionsPossibles2.contient("DaA,26,18"));
        assertTrue(actionsPossibles2.contient("TaA,26,18"));
        assertTrue(actionsPossibles2.contient("HaA,26,18"));
        assertTrue(actionsPossibles2.contient("BmA,26,18"));
        assertTrue(actionsPossibles2.contient("FjJ,28,17"));
        assertTrue(actionsPossibles2.contient("FmH,26,20"));
        assertFalse(actionsPossibles2.contient("ShE,27,17"));
        assertFalse(actionsPossibles2.contient("FjJ,29,17"));
        int nombreDifferentActionParCaseVide = 6;
        int nombreDePlanteSurPlateau = 31;
        int nombreDifferentActionParCaseRemplie = 2;
        int nombreDeCaseVide = 2;
        int nombreActionTotal = ((Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES - nombreDePlanteSurPlateau - nombreDeCaseVide) * nombreDifferentActionParCaseVide + nombreDePlanteSurPlateau * nombreDifferentActionParCaseRemplie);
        assertEquals(nombreActionTotal,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 8
     */
    public void testActionsPossibles_niveau8() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU8);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'R', 8);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        assertTrue(actionsPossibles.contient("PaA,41,23"));
        assertTrue(actionsPossibles.contient("IeF,44,23"));
        assertTrue(actionsPossibles.contient("IeE,49,23"));
        assertTrue(actionsPossibles.contient("IhH,44,23"));
        assertTrue(actionsPossibles.contient("IaN,42,23"));
        assertTrue(actionsPossibles.contient("IfJ,44,23"));
        assertTrue(actionsPossibles.contient("IfI,46,23"));
        assertFalse(actionsPossibles.contient("IbA,40,23"));
        assertFalse(actionsPossibles.contient("IiE,48,23"));
        String[] actionsPossiblesDepuisPlateau2
                = joueur.actionsPossibles(plateau, 'B', 8);
        ActionsPossibles actionsPossibles2
                = new ActionsPossibles(actionsPossiblesDepuisPlateau2);
        actionsPossibles2.afficher();
        assertTrue(actionsPossibles2.contient("PaA,40,24"));
        assertTrue(actionsPossibles2.contient("IeF,40,27"));
        assertTrue(actionsPossibles2.contient("IeE,40,32"));
        assertTrue(actionsPossibles2.contient("IhH,40,27"));
        assertTrue(actionsPossibles2.contient("IaN,40,25"));
        assertTrue(actionsPossibles2.contient("IfJ,40,27"));
        assertTrue(actionsPossibles2.contient("IfI,40,29"));
        assertFalse(actionsPossibles2.contient("IiE,40,32"));
        assertFalse(actionsPossibles2.contient("IbA,40,23"));
        int nombreDifferentActionParCaseVide = 6;
        int nombreDePlanteSurPlateau = 36;
        int nombreDifferentActionParCaseRemplie = 3;
        int nombreDeCaseVide = 2;
        int nombreDeLegumeIsole = 5;
        int nombreActionTotal = ((Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES - nombreDePlanteSurPlateau - nombreDeCaseVide) * nombreDifferentActionParCaseVide + nombreDePlanteSurPlateau * nombreDifferentActionParCaseRemplie - nombreDeLegumeIsole);
        assertEquals(nombreActionTotal,
                actionsPossiblesDepuisPlateau.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 9
     */
    public void testActionsPossibles_niveau9() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'R', 9);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        assertTrue(actionsPossibles.contient("O,16,22"));
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9_2);
        String[] actionsPossiblesDepuisPlateau2
                = joueur.actionsPossibles(plateau2, 'B', 9);
        ActionsPossibles actionsPossibles2
                = new ActionsPossibles(actionsPossiblesDepuisPlateau2);
        actionsPossibles2.afficher();
        assertTrue(actionsPossibles2.contient("ImL,15,28"));
        Case[][] plateau3 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9_3);
        String[] actionsPossiblesDepuisPlateau3
                = joueur.actionsPossibles(plateau3, 'B', 9);
        ActionsPossibles actionsPossibles3
                = new ActionsPossibles(actionsPossiblesDepuisPlateau3);
        actionsPossibles3.afficher();
        assertTrue(actionsPossibles3.contient("O,37,18"));
        Case[][] plateau4 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9_4);
        String[] actionsPossiblesDepuisPlateau4
                = joueur.actionsPossibles(plateau4, 'B', 9);
        ActionsPossibles actionsPossibles4
                = new ActionsPossibles(actionsPossiblesDepuisPlateau4);
        actionsPossibles4.afficher();
        assertTrue(actionsPossibles4.contient("O,60,60"));
        Case[][] plateau5 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9_5);
        String[] actionsPossiblesDepuisPlateau5
                = joueur.actionsPossibles(plateau5, 'B', 9);
        ActionsPossibles actionsPossibles5
                = new ActionsPossibles(actionsPossiblesDepuisPlateau5);
        actionsPossibles5.afficher();
        assertTrue(actionsPossibles5.contient("O,13,29"));
        int nombreDifferentActionParCaseVide = 6;
        int nombreDePlanteSurPlateau = 23;
        int nombreDifferentActionParCaseRemplie = 3;
        int nombreDeCaseVide = 0;
        int nombreDeLegumeIsole = 3;
        int actionOmbre = 1;
        int nombreActionTotal = ((Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES
                - nombreDePlanteSurPlateau - nombreDeCaseVide) * nombreDifferentActionParCaseVide
                + nombreDePlanteSurPlateau * nombreDifferentActionParCaseRemplie
                - nombreDeLegumeIsole + actionOmbre);
        assertEquals(nombreActionTotal,
                actionsPossiblesDepuisPlateau.length);

    }

    /**
     * Test de la fonction actionsPossibles, au niveau 9
     */
    public void testActionsPossibles_niveau10() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9);
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, 'B', 10);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        assertTrue(actionsPossibles.contient("PaA,23,28"));
        assertTrue(actionsPossibles.contient("RBaD,23,29"));
        assertTrue(actionsPossibles.contient("RDaD,23,29"));
        assertTrue(actionsPossibles.contient("RTaD,23,29"));
        assertFalse(actionsPossibles.contient("RSaD,23,29"));
        assertFalse(actionsPossibles.contient("RPaD,23,29"));
        assertTrue(actionsPossibles.contient("RHaD,23,29"));
        assertTrue(actionsPossibles.contient("RBnI,23,22"));
        assertTrue(actionsPossibles.contient("RDnI,23,22"));
        assertTrue(actionsPossibles.contient("RTnI,23,22"));
        assertTrue(actionsPossibles.contient("RHnI,23,22"));
        assertTrue(actionsPossibles.contient("RPnF,23,29"));
        assertTrue(actionsPossibles.contient("RSnF,23,29"));
        assertFalse(actionsPossibles.contient("RDnF,23,29"));
        String[] actionsPossiblesDepuisPlateau2
                = joueur.actionsPossibles(plateau, 'R', 10);
        ActionsPossibles actionsPossibles2
                = new ActionsPossibles(actionsPossiblesDepuisPlateau2);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles2.afficher();
        assertTrue(actionsPossibles2.contient("RSbD,25,27"));
        assertTrue(actionsPossibles2.contient("RPbD,25,27"));
        assertTrue(actionsPossibles2.contient("RBbD,25,27"));
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10);
        String[] actionsPossiblesDepuisPlateau3
                = joueur.actionsPossibles(plateau2, 'R', 10);
        ActionsPossibles actionsPossibles3
                = new ActionsPossibles(actionsPossiblesDepuisPlateau3);
        actionsPossibles3.afficher();
        assertTrue(actionsPossibles3.contient("PaB,19,37"));
        assertTrue(actionsPossibles3.contient("RSeE,20,37"));
        assertTrue(actionsPossibles3.contient("RPeE,20,37"));
        assertFalse(actionsPossibles3.contient("PaA,17,37"));
        assertFalse(actionsPossibles3.contient("Pef,17,37"));
        assertTrue(actionsPossibles3.contient("IdH,20,37"));
        String[] actionsPossiblesDepuisPlateau4
                = joueur.actionsPossibles(plateau2, 'B', 10);
        ActionsPossibles actionsPossibles4
                = new ActionsPossibles(actionsPossiblesDepuisPlateau4);
        actionsPossibles4.afficher();
        assertTrue(actionsPossibles4.contient("PaB,17,39"));
        Case[][] plateau3 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10_1);
        String[] actionsPossiblesDepuisPlateau5
                = joueur.actionsPossibles(plateau3, 'B', 10);
        ActionsPossibles actionsPossibles5
                = new ActionsPossibles(actionsPossiblesDepuisPlateau5);
        actionsPossibles5.afficher();
        assertTrue(actionsPossibles5.contient("SmD,16,20"));
        Case[][] plateau4 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10_2);
        String[] actionsPossiblesDepuisPlateau6
                = joueur.actionsPossibles(plateau4, 'B', 10);
        ActionsPossibles actionsPossibles6
                = new ActionsPossibles(actionsPossiblesDepuisPlateau6);
        actionsPossibles6.afficher();
        assertTrue(actionsPossibles6.contient("RSnF,11,13"));
                Case[][] plateau5 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10_3);
        String[] actionsPossiblesDepuisPlateau7
                = joueur.actionsPossibles(plateau5, 'B', 10);
        ActionsPossibles actionsPossibles7
                = new ActionsPossibles(actionsPossiblesDepuisPlateau7);
        actionsPossibles7.afficher();
        assertTrue(actionsPossibles7.contient("DeN,6,6"));
    }

    @Test
    public void testCheckEspece() {
        assertTrue(JoueurBiosphere7.checkEspece('H'));
        assertTrue(JoueurBiosphere7.checkEspece('T'));
        assertFalse(JoueurBiosphere7.checkEspece('P'));
        assertFalse(JoueurBiosphere7.checkEspece('S'));
        assertFalse(JoueurBiosphere7.checkEspece('D'));
        assertFalse(JoueurBiosphere7.checkEspece('B'));
    }

    @Test
    public void testajoutActionDisséminer() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU8);
        Coordonnees coord = new Coordonnees(4, 5);
        Coordonnees[] voisine = JoueurBiosphere7.voisines(coord, 14);
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(0, 0);
        assertEquals(0, actions.nbActions);
        joueur.ajoutActionDisséminer(Coordonnees.depuisCars('e', 'E'), actions,
                vitalites, 'R', plateau, voisine, 2);
        actions.afficher();
        assertTrue(actions.contient("IeE,4,0"));
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10);
        ActionsPossibles actions2 = new ActionsPossibles();
        Coordonnees coord2 = new Coordonnees(3, 7);
        Coordonnees[] voisine2 = JoueurBiosphere7.voisines(coord2, 14);
        joueur.ajoutActionDisséminer(Coordonnees.depuisCars('d', 'H'), actions2,
                vitalites, 'R', plateau2, voisine2, 1);
        actions2.afficher();
        assertTrue(actions2.contient("IdH,3,0"));

    }

    @Test
    public void testlisteDesPlantesPouvantRemplacer() {
        char planteActuel = 'P';
        char[] tab = new char[]{'B', 'D', 'H', 'T'};
        assertArrayEquals(tab, JoueurBiosphere7.listeDesPlantesPouvantRemplacer(planteActuel));
        char planteActuel2 = 'S';
        char[] tab2 = new char[]{'B', 'D', 'H', 'T'};
        assertArrayEquals(tab2, JoueurBiosphere7.listeDesPlantesPouvantRemplacer(planteActuel2));
        char planteActuel3 = 'B';
        char[] tab3 = new char[]{'P', 'S', 'D', 'H', 'T'};
        assertArrayEquals(tab3, JoueurBiosphere7.listeDesPlantesPouvantRemplacer(planteActuel3));
        char planteActuel4 = 'D';
        char[] tab4 = new char[]{'P', 'S', 'B'};
        assertArrayEquals(tab4, JoueurBiosphere7.listeDesPlantesPouvantRemplacer(planteActuel4));
        char planteActuel5 = 'H';
        char[] tab5 = new char[]{'P', 'S', 'B'};
        assertArrayEquals(tab5, JoueurBiosphere7.listeDesPlantesPouvantRemplacer(planteActuel5));
        char planteActuel6 = 'T';
        char[] tab6 = new char[]{'P', 'S', 'B'};
        assertArrayEquals(tab6, JoueurBiosphere7.listeDesPlantesPouvantRemplacer(planteActuel6));
    }

    @Test
    public void testMinimum1Espèce() {
        Coordonnees v = new Coordonnees(4, 5);
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU8);
        Coordonnees[] voisine = JoueurBiosphere7.voisines(v, 14);
        assertTrue(JoueurBiosphere7.minimum1voisinDeMemeEspece(v, plateau, voisine));
        Coordonnees v2 = new Coordonnees(0, 0);
        Coordonnees[] voisine2 = JoueurBiosphere7.voisines(v2, 14);
        assertFalse(JoueurBiosphere7.minimum1voisinDeMemeEspece(v2, plateau, voisine2));
        Coordonnees v3 = new Coordonnees(0, 13);
        Coordonnees[] voisine3 = JoueurBiosphere7.voisines(v3, 14);
        assertFalse(JoueurBiosphere7.minimum1voisinDeMemeEspece(v3, plateau, voisine3));
        Coordonnees v4 = new Coordonnees(2, 3);
        Coordonnees[] voisine4 = JoueurBiosphere7.voisines(v4, 14);
        assertTrue(JoueurBiosphere7.minimum1voisinDeMemeEspece(v4, plateau, voisine4));
        Coordonnees v5 = new Coordonnees(9, 9);
        Coordonnees[] voisine5 = JoueurBiosphere7.voisines(v5, 14);
        assertFalse(JoueurBiosphere7.minimum1voisinDeMemeEspece(v5, plateau, voisine5));
    }

    @Test
    public void testMinimumVitalite() {
        Coordonnees v = new Coordonnees(4, 5);
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU8);
        Coordonnees[] voisine = JoueurBiosphere7.voisines(v, 14);
        assertEquals(2, JoueurBiosphere7.minimumVitalite(plateau, v, voisine));
        Coordonnees v2 = new Coordonnees(0, 0);
        Coordonnees[] voisine2 = JoueurBiosphere7.voisines(v, 14);
        assertEquals(0, JoueurBiosphere7.minimumVitalite(plateau, v2, voisine2));
        Coordonnees v3 = new Coordonnees(0, 13);
        Coordonnees[] voisine3 = JoueurBiosphere7.voisines(v3, 14);
        assertEquals(1, JoueurBiosphere7.minimumVitalite(plateau, v3, voisine3));
        Coordonnees v4 = new Coordonnees(2, 3);
        Coordonnees[] voisine4 = JoueurBiosphere7.voisines(v4, 14);
        assertEquals(1, JoueurBiosphere7.minimumVitalite(plateau, v4, voisine4));
        Coordonnees v5 = new Coordonnees(9, 9);
        Coordonnees[] voisine5 = JoueurBiosphere7.voisines(v5, 14);
        assertEquals(7, JoueurBiosphere7.minimumVitalite(plateau, v5, voisine5));

    }

    @Test
    public void testAvoirUniquement3voisines() {
        Coordonnees v = new Coordonnees(2, 3);
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU6);
        assertTrue(JoueurBiosphere7.avoir3Voisines(v, 14, plateau));
        Coordonnees v2 = new Coordonnees(2, 5);
        assertTrue(JoueurBiosphere7.avoir3Voisines(v2, 14, plateau));
        Coordonnees v3 = new Coordonnees(5, 8);
        assertFalse(JoueurBiosphere7.avoir3Voisines(v3, 14, plateau));
        Coordonnees v4 = new Coordonnees(4, 8);
        assertFalse(JoueurBiosphere7.avoir3Voisines(v4, 14, plateau));
    }

    /**
     * Test de la fonction ajoutAction.
     */
    @Test
    public void testAjoutAction() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(0, 0);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        // on crée le tableau d'actions et on en ajoute une
        joueur.ajoutAction(Coordonnees.depuisCars('f', 'D'), actions,
                vitalites, Case.CAR_ROUGE, Plante.POMMIER);
        // l'action est devenue possible
        assertTrue(actions.contient("PfD,1,0"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        joueur.ajoutAction(Coordonnees.depuisCars('b', 'H'), actions,
                vitalites, Case.CAR_ROUGE, Plante.POMMIER);
        // l'action a bien été ajoutée
        assertTrue(actions.contient("PbH,1,0"));
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
        joueur.ajoutAction(Coordonnees.depuisCars('a', 'A'), actions,
                vitalites, Case.CAR_ROUGE, Plante.FRANBOISIER);
        // l'action a bien été ajoutée
        assertTrue(actions.contient("BaA,1,0"));
        // désormais, deux actions possibles
        assertEquals(3, actions.nbActions);
    }

    /**
     * Test de la fonction ajoutOmbre.
     */
    @Test
    public void testAjoutOmbre() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(0, 0);
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        joueur.ajouterOmbre(vitalites, plateau, actions);
        assertEquals(0, actions.nbActions);
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        vitalites = new Vitalites(2, 2);
        joueur.ajouterOmbre(vitalites, plateau2, actions);
        assertEquals(0, actions.nbActions);
        Case[][] plateau3 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9);
        vitalites = new Vitalites(23, 27);
        joueur.ajouterOmbre(vitalites, plateau3, actions);
        assertTrue(actions.contient("O,16,22"));
        assertEquals(1, actions.nbActions);
    }

    /**
     * Test de la fonction ajoutAction.
     */
    @Test
    public void testAjoutActionFertiliser() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(0, 0);
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU7);
        assertEquals(0, actions.nbActions);

        joueur.ajoutActionFertiliser(Coordonnees.depuisCars('a', 'N'), actions,
                vitalites, plateau);
        assertTrue(actions.contient("FaN,0,1"));
        assertFalse(actions.contient("FaA,1,0"));
        assertEquals(1, actions.nbActions);
        joueur.ajoutActionFertiliser(Coordonnees.depuisCars('l', 'E'), actions,
                vitalites, plateau);
        assertTrue(actions.contient("FlE,0,2"));
        assertEquals(2, actions.nbActions);
        joueur.ajoutActionFertiliser(Coordonnees.depuisCars('h', 'D'), actions,
                vitalites, plateau);
        assertTrue(actions.contient("FhD,0,3"));
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
        // plateau : rouge 6, bleu 4
        Case[][] plateau3 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        Vitalites vita3 = JoueurBiosphere7.vitalitesPlateau(plateau3);
        assertEquals(6, vita3.vitalitesRouge);
        assertEquals(4, vita3.vitalitesBleu);
        // plateau : rouge 12, bleu 12
        Case[][] plateau4 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU6);
        Vitalites vita4 = JoueurBiosphere7.vitalitesPlateau(plateau4);
        assertEquals(12, vita4.vitalitesRouge);
        assertEquals(12, vita4.vitalitesBleu);
        // plateau : rouge 26, bleu 17
        Case[][] plateau5 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU7);
        Vitalites vita5 = JoueurBiosphere7.vitalitesPlateau(plateau5);
        assertEquals(26, vita5.vitalitesRouge);
        assertEquals(17, vita5.vitalitesBleu);
        // plateau : rouge 40, bleu 23
        Case[][] plateau6 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU8);
        Vitalites vita6 = JoueurBiosphere7.vitalitesPlateau(plateau6);
        assertEquals(40, vita6.vitalitesRouge);
        assertEquals(23, vita6.vitalitesBleu);
        // plateau : rouge 18, bleu 24
        Case[][] plateau7 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9);
        Vitalites vita7 = JoueurBiosphere7.vitalitesPlateau(plateau7);
        assertEquals(23, vita7.vitalitesRouge);
        assertEquals(27, vita7.vitalitesBleu);
        Case[][] plateau8 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10);
        Vitalites vita8 = JoueurBiosphere7.vitalitesPlateau(plateau8);
        // plateau : rouge 17, bleu 37
        assertEquals(17, vita8.vitalitesRouge);
        assertEquals(37, vita8.vitalitesBleu);
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
    /**
     * Un plateau pour tester le niveau 6.
     */
    final String PLATEAU_NIVEAU6
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |PB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |PB2|   |PR1|   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |PB1|PR2|   |PB2|PB1|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |PR2|   |PR1|   |   |PR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |PB1|   |PR1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|PR1|   |   |   |   |   |   |   |PB1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|PB1|   |   |   |   |   |   |PR2|   |PB1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |PB1|   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|PR1|   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
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
     * Un plateau pour tester le niveau 7.
     */
    final String PLATEAU_NIVEAU7
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |PB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |PB2|   |PR1|   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |PB1|PR2|   |PB2|PB1|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |PR2|   |PR1|   |   |PR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |PB1|   |PR1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|PR1|   |   |   |   |   |   |   |PB1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |TR1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|PB1|   |   |DB1|   |SB1|   |PR2|   |PB1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |PB1|   |   |HR2|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|PR1|   |   |   |   |DB1|   |   |   |TR7|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |TR1|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|PR1|   |   |   |BB1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |HB1|   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|SR1|   |   |   |   |   |   |   |SR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

    /* Un plateau pour tester le niveau 8.
     */
    final String PLATEAU_NIVEAU8
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |PB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |PB2|   |PR1|   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |PB1|PR2|   |PB2|PB1|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |PR2|   |TR2|   |   |PR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |TR7|TR3|   |PB1|   |PR1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|PR1|   |   |   |   |   |   |   |HB3|HR2|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |TR1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|PB1|   |   |DB1|   |SB1|   |PR2|   |PB1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |PB1|   |   |HR2|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|PR1|   |   |   |   |DB1|   |   |   |TR7|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |TR1|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|PR1|   |   |   |BB1|   |   |HB4|   |   |HR1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |HB1|   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|SR1|   |   |   |   |   |   |   |SR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    /* Un plateau pour tester le niveau 9.
     */
    final String PLATEAU_NIVEAU9
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |SB1|   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |HR2|   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|PR1|   |   |SR1|   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |BR1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |PR2|HB1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |DB8|   |   |   |   |PB1|   |DR1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |TR1|   |   |   |TB1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |PR5|   |   |   |   |   |   |   |   |BR1|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |HB1|   |   |   |   |   |BB4|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |PR3|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |SR4|   |   |PR1|   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |HB1|   |   |SB8|   |   |   |   |DB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

    /* Un plateau pour tester le niveau 9 particulié.
     */
    final String PLATEAU_NIVEAU9_2
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |DR2|   |   |   |   |   |   |DR1|   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |SR1|   |   |DR1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |DB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |TR1|   |   |   |   |   |   |SR1|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |BR1|   |   |   |BR1|   |TB2|SB1|PB2|   |   |SB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |SB1|   |   |   |   |   |   |   |DR1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |SR1|   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |DR1|   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |HR2|   |   |   |   |   |BB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |HB1|   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |PR1|   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |SB2|   |   |   |   |   |   |   |   |HB2|HB4|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |TB1|   |   |PB3|   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

    /* Un plateau pour tester le niveau 9 particulié.
     */
    final String PLATEAU_NIVEAU9_3
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |PR1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |BB1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |DB1|   |   |   |   |   |DR1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |HB8|   |   |SB4|   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |SR1|   |   |   |   |HB1|   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |SR6|   |   |   |   |   |HB1|   |PR1|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |   |   |   |   |TB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |BR2|HB1|   |   |   |   |   |SR8|   |PB2|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |PR1|   |   |PR1|   |   |HR1|SR5|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |PR8|   |   |SB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |SR5|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |HB1|   |SB1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    /* Un plateau pour tester le niveau 9 particulié.
     */
    final String PLATEAU_NIVEAU9_4
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |HR3|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |DR3|   |   |   |HB5|HB4|   |   |DB2|   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |   |BB6|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |DR2|   |TR7|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |PB3|SB8|DB1|   |   |   |   |BR1|   |PR5|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |TB1|   |   |SB1|   |SB3|   |   |   |   |PR5|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |DR9|HR2|SB3|   |   |   |   |   |   |PR5|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |BB3|PB6|   |   |   |   |DR3|   |PR5|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |PR5|   |   |   |HR4|   |   |   |   |   |PR5|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |BB5|   |HR9|   |PB6|   |   |SB1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |SB6|   |   |   |   |   |   |PB3|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |PR6|   |   |   |   |   |   |PB4|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |PB9|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    /* Un plateau pour tester le niveau 9 particulié.
     */
    final String PLATEAU_NIVEAU9_5
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |PB1|   |   |   |TR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |SB2|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |SB1|   |   |   |   |   |TR1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |BR1|   |   |   |   |   |   |   |   |PR4|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |   |   |HR1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |DB1|   |   |   |DB5|   |   |BB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |PR1|   |   |   |PB9|   |HB1|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |PR5|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |BR1|   |   |   |   |   |   |SB5|   |   |SB8|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|BB1|   |   |   |   |   |   |PR1|   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |BB1|   |PR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |BB1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

    /* Un plateau pour tester le niveau 10 (eau)
     */
    final String PLATEAU_NIVEAU10
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +E--+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |PB1|   |   |   |TR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |SB2|   |   |   |   |   |   |   |   |   |\n"
            + " +---+E--+---+---+---+---+---+E--+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |SB1|   |   |   |   |   |TR1|\n"
            + " +---+---+---+---+---+E--+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |BR1|   |   |   |   |   |   |   |   |PR4|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |   |   |HR1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |DB1|   |   |   |DB5|   |   |BB1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |PR1|   |   |   |PB9|   |HB1|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |PR5|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |BR1|   |   |   |   |   |   |SB5|   |   |SB8|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|BB1|   |   |   |   |   |   |PR1|   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |BB1|   |PR1|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |BB1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    /* Un plateau pour tester le niveau 10_1 (eau)
     */
    final String PLATEAU_NIVEAU10_1
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |HR1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |BB1|   |   |HB1|   |BB1|DR1|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |TB4|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |DB2|   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |PB1|   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |HB1|   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |TB1|PR3|DR1|   |   |   |   |   |   |HB1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |DR1|   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +E--+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |PR1|\n"
            + " +E--+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |DR1|   |TR1|   |   |   |   |\n"
            + " +E--+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |HB2|   |   |   |   |   |   |   |   |HR1|   |BR1|\n"
            + " +E--+E--+E--+E--+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |TB2|   |   |   |TR1|   |   |   |   |   |\n"
            + " +E--+E--+E--+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |PR1|   |   |\n"
            + " +E--+E--+E--+E--+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |HR1|   |   |HB1|SR1|   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    /* Un plateau pour tester le niveau 10_2 (eau)
     */
    final String PLATEAU_NIVEAU10_2
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |TB1|   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+E--+---+---+---+---+---+\n"
            + "c|   |   |DB1|   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+E--+E--+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+E--+E--+E--+---+---+---+\n"
            + "e|DR1|   |   |   |   |   |   |   |   |   |   |HR2|   |DR1|\n"
            + " +---+---+---+---+---+---+---+---+---+E--+E--+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |DB2|   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |BR1|   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |BR2|PR1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |SB2|SB1|   |DR1|   |   |   |\n"
            + " +---+---+---+E--+E--+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +E--+E--+E--+E--+E--+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |SR1|   |SB1|   |   |   |   |   |\n"
            + " +E--+E--+E--+E--+E--+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +E--+E--+E--+E--+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |PB1|   |   |   |DR1|   |   |   |   |\n"
            + " +E--+E--+E--+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |DB2|   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    /* Un plateau pour tester le niveau 10_3 (eau)
     */
    final String PLATEAU_NIVEAU10_3
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+E--+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |TB1|   |   |\n"
            + " +E--+E--+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +E--+E--+E--+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +E--+E--+E--+E--+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |DR1|   |   |   |   |   |BR1|\n"
            + " +E--+E--+E--+E--+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |PB1|   |   |   |   |   |   |\n"
            + " +E--+E--+E--+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |HB1|   |   |   |   |   |   |   |   |   |\n"
            + " +---+E--+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |BR1|   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |BR1|   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |DR1|   |   |   |   |   |   |   |   |PB1|   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |DB1|   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+E--+E--+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+E--+E--+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |BR1|   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

}
