package biosphere7;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Joueur implémentant les actions possibles à partir d'un plateau, pour un
 * niveau donné.
 */
public class JoueurBiosphere7 implements IJoueurBiosphere7 {

    /**
     * Maximum de voisin possible pour une case
     */
    final int MAXIMUMVOISINPOSSIBLE = 4;
    /**
     * Vérifie si une case à de l'eau
     */
    boolean presenceEau = false;
    /**
     * La vitalité maximum d'une plante
     */
    final int VITALEMAXIMAL = 9;

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, toutes
     * les actions possibles pour ce joueur.
     *
     * @param plateau le plateau considéré
     * @param couleurJoueur couleur du joueur
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    @Override
    public String[] actionsPossibles(Case[][] plateau, char couleurJoueur, int niveau) {
        // afficher l'heure de lancement
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("actionsPossibles : lancement le " + format.format(new Date()));
        // se préparer à stocker les actions possibles
        ActionsPossibles actions = new ActionsPossibles();
        // calculer les vitalités sur le plateau initial
        Vitalites vitalites = vitalitesPlateau(plateau);
        // ajout des actions "planter une plante"
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                Coordonnees[] v = voisines(coord, 14);
                int compteur = 0;
                presenceEau = false;
                AdditionSousCondition val2 = new AdditionSousCondition(vitalites.vitalitesRouge, vitalites.vitalitesBleu, 0);
                AdditionSousCondition val = new AdditionSousCondition(0, 0, 0);
                if (plateau[coord.ligne][coord.colonne].plantePresente() == false && plateau[coord.ligne][coord.colonne].nature == 'T') {
                    val.valeurAjouter = 1;
                    for (int i = 0; i < v.length; i++) {
                        if (plateau[v[i].ligne][v[i].colonne].nature == 'E') {
                            val.ConditionBleu(couleurJoueur);
                            val.ConditionRouge(couleurJoueur);
                            presenceEau = true;
                        }
                        if (plateau[v[i].ligne][v[i].colonne].plantePresente()) {
                            val.valeurAjouter=1;
                            val2.valeurAjouter=1;
                            char couleur = plateau[v[i].ligne][v[i].colonne].couleur;
                            boolean t = avoir3Voisines(v[i], 14, plateau);
                            if (plateau[v[i].ligne][v[i].colonne].couleur == 'B') {
                                val.ConditionBleu(couleur);
                                val.ConditionRouge(couleur);
                                val2.ConditionBleu(couleurJoueur);
                                if (t) {
                                    val2.VitaliteBleu -= plateau[v[i].ligne][v[i].colonne].vitalite;
                                }
                            } else if (plateau[v[i].ligne][v[i].colonne].couleur == 'R') {
                                val.ConditionBleu(couleur);
                                val.ConditionRouge(couleur);
                                val2.ConditionRouge(couleurJoueur);
                                if (t) {
                                    val2.VitaliteRouge -= plateau[v[i].ligne][v[i].colonne].vitalite;
                                }
                                
                                
                            }
                        }
                    }
                    if (presenceEau) {
                        val2.valeurAjouter = 1;
                        val2.Condition(couleurJoueur);
                    }
                    compteur = presencePlant(val, plateau, v);
                    if (compteur < MAXIMUMVOISINPOSSIBLE || compteur == MAXIMUMVOISINPOSSIBLE && presenceEau == true) {
                        for (Plante p : Plante.values()) {
                            Vitalites vitalites2 = new Vitalites(val2.VitaliteRouge, val2.VitaliteBleu);
                            ajoutAction(coord, actions, vitalites2, couleurJoueur, p);
                        }
                    }
                } else if (plateau[coord.ligne][coord.colonne].plantePresente()) {
                    ajoutActionCouper(coord, actions, vitalites, plateau[lig][col].couleur, plateau);
                    ajoutActionFertiliser(coord, actions, vitalites, plateau);
                    compteur = presencePlant(val, plateau, v);
                    faireDissémination(plateau, coord, v, actions, vitalites, couleurJoueur, compteur);
                    ajoutActionRotation(coord, actions, vitalites, plateau, couleurJoueur, val.VitaliteRouge, val.VitaliteBleu, presenceEau);
                }
                vitalites = vitalitesPlateau(plateau);
            }
        }
        ajouterOmbre(vitalites, plateau, actions);
        System.out.println("actionsPossibles : fin");
        return actions.nettoyer();
    }
    /**
     * Fonction qui vérifie toute les cases autour d'une coordonnée
     * @param val 
     * @param plateau plateau de jeux
     * @param v les coordonnées de ces voisins
     * @return retourne le nombre de case occuper
     */

    int presencePlant(AdditionSousCondition val, Case[][] plateau, Coordonnees[] v) {
        int compteur = 0;
        for (int i = 0; i < v.length; i++) {
            if (plateau[v[i].ligne][v[i].colonne].nature == 'E') {
                compteur += 1;
                presenceEau = true;
            }
            if (plateau[v[i].ligne][v[i].colonne].plantePresente()) {
                compteur += 1;
                if (plateau[v[i].ligne][v[i].colonne].couleur == 'B') {
                    val.VitaliteBleu += 1;
                } else if (plateau[v[i].ligne][v[i].colonne].couleur == 'R') {
                    val.VitaliteRouge += 1;
                }

            }

        }
        return compteur;
    }
    /**
     * Fonction Qui éffectue la dissémination
     * @param plateau le plateau de jeux
     * @param coord les coordonnées de la case
     * @param v l'ensemble de ces voisins
     * @param actions L'ensemble des actions (en construction)
     * @param vitalites la vitalité du plateau 
     * @param couleurJoueur la couleur du joueur Courant
     * @param compteur le nombre de voisin
     */
    void faireDissémination(Case[][] plateau, Coordonnees coord, Coordonnees[] v, ActionsPossibles actions, Vitalites vitalites,
            char couleurJoueur, int compteur) {
        if (checkEspece(plateau[coord.ligne][coord.colonne].espece)) {
            if (minimum1voisinDeMemeEspece(coord, plateau, v)) {
                ajoutActionDisséminer(coord, actions, vitalites, couleurJoueur, plateau, v, compteur);
            }
        } else {
            ajoutActionDisséminer(coord, actions, vitalites, couleurJoueur, plateau, v, compteur);
        }
    }

    /**
     * fonction qui vérifie l'espèce si la plante est autostérile ou pas
     *
     * @param espece l'espèce de la plante
     * @return vrai ssi la plante est autostérile sinon faux
     */
    static boolean checkEspece(char espece) {
        boolean status = false;
        switch (espece) {
            case 'H':
            case 'T':
                status = true;
                break;
            default:
                status = false;
                break;
        }
        return status;
    }

    /**
     * Fonction qui vérifie qu'un case est uniquement 3 voisins
     *
     * @param coord de la case
     * @param taille taille du plateau
     * @param plateau plateau de jeu
     * @return vrai ssi la case à exactement 3 voisin sinon faux
     */
    static boolean avoir3Voisines(Coordonnees coord, int taille, Case[][] plateau) {
        int compteur = 0;
        Coordonnees[] nCoord = voisines(coord, taille);
        if (nCoord.length == 4) {
            for (int n = 0; n < 4; n++) {
                if (plateau[nCoord[n].ligne][nCoord[n].colonne].plantePresente()) {
                    compteur += 1;
                }
            }
        }
        return compteur == 3;
    }

    /**
     * Retourne les coordonnées de toutes les cases voisines.
     *
     * @param coord coordonnées de la case considérée
     * @param taille taille du plateau (carré)
     * @return les coordonnées de toutes les cases voisines
     */
    static Coordonnees[] voisines(Coordonnees coord, int taille) {
        Coordonnees[] voisines = new Coordonnees[4];
        int nbVoisines = 0;
        for (Direction d : Direction.values()) {
            if (estDansPlateau(suivante(coord, d), taille)) {
                voisines[nbVoisines] = suivante(coord, d);
                nbVoisines += 1;
            }
        }
        return Arrays.copyOf(voisines, nbVoisines);
    }

    /**
     * Somme des vitalités des plantes de chaque joueur sur le plateau.
     *
     * @param plateau le plateau
     * @return la somme des vitalités des plantes de chaque joueur
     */
    static Vitalites vitalitesPlateau(Case[][] plateau) {
        // TODO il y en aura besoin à un moment !
        int vitaliterR = 0;
        int vitaliterB = 0;
        AdditionSousCondition val = new AdditionSousCondition(vitaliterR, vitaliterB, 0);
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                val.valeurAjouter = plateau[coord.ligne][coord.colonne].vitalite;
                if (plateau[coord.ligne][coord.colonne].plantePresente() == true) {
                    val.Condition(plateau, coord);
                }
            }
        }
        return new Vitalites(val.VitaliteRouge, val.VitaliteBleu);
    }

    /**
     * Ajout d'une action de plantation de Plante dans l'ensemble des actions
     * possibles.
     *
     * @param coord coordonnées de la case où planter les différent plante
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer
     * l'action
     * @param couleur la couleur de la plante à ajouter
     * @param p l'espèce de la plante
     */
    void ajoutAction(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, char couleur, Plante p) {
        int vitaliterR = vitalites.vitalitesRouge;
        int vitaliterB = vitalites.vitalitesBleu;
        AdditionSousCondition val = new AdditionSousCondition(vitaliterR, vitaliterB, 1);
        val.Condition(couleur == 'R');
        String action = "" + initiale(p) + coord.carLigne() + coord.carColonne() + ","
                + (val.VitaliteRouge) + ","
                + (val.VitaliteBleu);
        actions.ajouterAction(action);
    }

    /**
     * Donne les initiale de chaque plante
     *
     * @param plante l'espèce de la plante
     * @return l'initiale de la plante
     */
    char initiale(Plante plante) {
        char letter = ' ';
        switch (plante) {
            case POMMIER:
                letter = 'P';
                break;
            case SUREAU:
                letter = 'S';
                break;
            case FRANBOISIER:
                letter = 'B';
                break;
            case HARICOTS:
                letter = 'H';
                break;
            case POMMESDETERRE:
                letter = 'D';
                break;
            case TOMATES:
                letter = 'T';
                break;
            default:
                letter = '?';
                break;
        }
        return letter;
    }

    /**
     * Renvoie les coordonnées de la case suivante, en suivant une direction
     * donnée.
     *
     * @param d la direction à suivre
     * @return les coordonnées de la case suivante
     */
    static Coordonnees suivante(Coordonnees c, Direction d) {
        return new Coordonnees(c.ligne + Direction.mvtVertic(d),
                c.colonne + Direction.mvtHoriz(d));
    }

    /**
     * Indique si ces coordonnées sont dans le plateau.
     *
     * @param coord coordonnées à tester
     * @param taille taille du plateau (carré)
     * @return vrai ssi ces coordonnées sont dans le plateau
     */
    static boolean estDansPlateau(Coordonnees coord, int taille) {
        return coord.ligne < taille && coord.ligne >= 0 && coord.colonne < taille && coord.colonne >= 0; // TODO
    }

    /**
     * Fonction qui ajoute une action de fertiliter à une plante
     *
     * @param coord coordonnées de la case où fertiliser la plante
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer
     * l'action
     * @param plateau l'ensemble des cases du plateau de jeu
     */
    void ajoutActionFertiliser(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, Case[][] plateau) {
        int vitaliterR = vitalites.vitalitesRouge;
        int vitaliterB = vitalites.vitalitesBleu;
        int valeurAjouter = 0;

        switch (plateau[coord.ligne][coord.colonne].espece) {
            case 'P':
            case 'S':
                valeurAjouter = 1;
                break;
            case 'B':
                valeurAjouter = 2;
                break;
            default:
                valeurAjouter = 3;
                break;
        }
        if (plateau[coord.ligne][coord.colonne].vitalite + valeurAjouter >= VITALEMAXIMAL) {
            valeurAjouter = VITALEMAXIMAL - plateau[coord.ligne][coord.colonne].vitalite;
        }
        AdditionSousCondition val = new AdditionSousCondition(vitaliterR, vitaliterB, valeurAjouter);
        val.Condition(plateau, coord);
        String action = "F" + coord.carLigne() + coord.carColonne() + ","
                + (val.VitaliteRouge) + ","
                + (val.VitaliteBleu);
        actions.ajouterAction(action);

    }

    /**
     * Fonction qui permet de connaitre la plante avec la plus petite vitalitée
     * de ces voisines
     *
     * @param plateau plateau de jeux
     * @param coord coordonnée de la case
     * @param voisine L'ensemble de ces voisines
     * @return la vitalite de la plante la plus petite
     */
    static int minimumVitalite(Case[][] plateau, Coordonnees coord, Coordonnees[] voisine) {
        int minimum = plateau[coord.ligne][coord.colonne].vitalite;
        char espece = plateau[coord.ligne][coord.colonne].espece;
        for (Coordonnees voisine1 : voisine) {
            if (plateau[voisine1.ligne][voisine1.colonne].vitalite < minimum && plateau[voisine1.ligne][voisine1.colonne].espece == espece) {
                minimum = plateau[voisine1.ligne][voisine1.colonne].vitalite;
            }
        }
        return minimum;
    }

    /**
     * Fonction qui vérifie sur une plante à au moins 1 voisin de même espèce
     *
     * @param coord la case
     * @param plateau le jeu
     * @param voisine la liste des voisin
     * @return vrai ssi il y a au moins 1 voisin de même espèce sinon faux
     */
    static boolean minimum1voisinDeMemeEspece(Coordonnees coord, Case[][] plateau, Coordonnees[] voisine) {
        int compteur = 0;
        boolean statue = false;
        boolean checkPlante = plateau[coord.ligne][coord.colonne].plantePresente();
        if (checkPlante == true) {
            char espece = plateau[coord.ligne][coord.colonne].espece;
            while (compteur < voisine.length && !statue) {
                if (plateau[voisine[compteur].ligne][voisine[compteur].colonne].espece == espece) {
                    statue = true;
                }
                compteur++;
            }
        }
        return statue;
    }

    /**
     * Fonction qui ajoute une action de Disséminer à une plante
     *
     * @param coord coordonnées de la case où Disséminer la plante
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer
     * l'action
     * @param couleurJ la couleur du joueur actif
     * @param voisine le nombre de voisin de la plante
     * @param plateau l'ensemble des cases du plateau de jeu
     */
    void ajoutActionDisséminer(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, char couleurJ, Case[][] plateau, Coordonnees[] voisine, int nbVoisin) {
        AdditionSousCondition val = new AdditionSousCondition(vitalites.vitalitesRouge, vitalites.vitalitesBleu, 0);
        int voisinVide = voisine.length - nbVoisin;
        switch (plateau[coord.ligne][coord.colonne].espece) {
            case 'H':
            case 'T':
                val.valeurAjouter= minimumVitalite(plateau, coord, voisine);
                break;
            default:
                val.valeurAjouter = 1;
                break;
        }

        val.valeurAjouter *= voisinVide;
        val.Condition(couleurJ);
        String action = "I" + coord.carLigne() + coord.carColonne() + ","
                + (val.VitaliteRouge) + ","
                + (val.VitaliteBleu);
        actions.ajouterAction(action);

    }

    /**
     * Ajout d'une action de plantation de pommier dans l'ensemble des actions
     * possibles.
     *
     * @param coord coordonnées de la case où planter le pommier
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer
     * l'action
     * @param couleur la couleur du pommier à ajouter
     */
    void ajoutActionCouper(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, char couleur, Case[][] plateau) {
        int vitaliterR = vitalites.vitalitesRouge;
        int vitaliterB = vitalites.vitalitesBleu;
        AdditionSousCondition val = new AdditionSousCondition(vitaliterR, vitaliterB, -plateau[coord.ligne][coord.colonne].vitalite);
        val.Condition(couleur);
        Coordonnees[] v = voisines(coord, 14);
        for (Coordonnees v1 : v) {
            if (plateau[v1.ligne][v1.colonne].plantePresente()) {
                if (plateau[v1.ligne][v1.colonne].vitalite < 9) {
                    if (plateau[v1.ligne][v1.colonne].couleur == 'R') {
                        val.VitaliteRouge += 1;
                    } else if (plateau[v1.ligne][v1.colonne].couleur == 'B') {
                        val.VitaliteBleu += 1;
                    }
                }
            }
        }
        String action = "C" + coord.carLigne() + coord.carColonne() + ","
                + (val.VitaliteRouge) + ","
                + (val.VitaliteBleu);
        actions.ajouterAction(action);

    }

    /**
     * Fonction qui gère l'ombre de l'entièreté du plateau
     *
     * @param vitalites la vitalité du plateau actuel
     * @param plateau le plateau de jeu
     * @param actions l'ensemble des actions possibles (en construction)
     */
    void ajouterOmbre(Vitalites vitalites, Case[][] plateau, ActionsPossibles actions) {
        int vitaliterR = vitalites.vitalitesRouge;
        int vitaliterB = vitalites.vitalitesBleu;
        int planteSud = 0;
        for (int i = 0; i < plateau.length; ++i) {
            for (int y = 0; y < plateau[0].length; ++y) {
                if (plateau[i][y].plantePresente()) {
                    Coordonnees c = new Coordonnees(i, y);
                    int distance = 0;
                    double valeurEnlever = 0;
                    int vitalitePlant = plateau[i][y].vitalite;
                    while (c.ligne < 13 && vitalitePlant - (int) valeurEnlever > 0) {
                        c = suivante(c, Direction.SUD);
                        distance += 1;
                        if (plateau[c.ligne][c.colonne].plantePresente()) {
                            if (plateau[c.ligne][c.colonne].espece == 'P' || plateau[c.ligne][c.colonne].espece == 'S') {
                                planteSud = plateau[c.ligne][c.colonne].vitalite;
                                if (distance < planteSud) {
                                    valeurEnlever = ((planteSud - distance) / 2);
                                    if ((int) valeurEnlever > vitalitePlant) {
                                        valeurEnlever = vitalitePlant;
                                    }
                                    if (plateau[i][y].couleur == 'R') {
                                        vitaliterR -= (int) valeurEnlever;
                                    } else if (plateau[i][y].couleur == 'B') {
                                        vitaliterB -= (int) valeurEnlever;
                                    }
                                    vitalitePlant = plateau[i][y].vitalite - (int) valeurEnlever;
                                    valeurEnlever = 0;

                                }
                            }

                        }
                    }
                }
            }
        }
        if (vitaliterR != vitalites.vitalitesRouge || vitaliterB != vitalites.vitalitesBleu) {
            String action = "O" + ","
                    + (vitaliterR) + ","
                    + (vitaliterB);
            actions.ajouterAction(action);
        }
    }

    /**
     * Fonction qui donne un tableau de l'ensemble des plante pouvant être
     * remplacé
     *
     * @param p un espèce de plante
     * @param planteActuel l'espèce de la plante actuel
     * @return la liste des plantes pottentielles
     */
    static char[] listeDesPlantesPouvantRemplacer(char planteActuel) {
        char[] plante = new char[5];
        int compteur = 0;
        switch (planteActuel) {
            case 'P':
            case 'S':
                plante[0] = 'B';
                plante[1] = 'D';
                plante[2] = 'H';
                plante[3] = 'T';
                compteur = 4;
                break;
            case 'B':
                plante[0] = 'P';
                plante[1] = 'S';
                plante[2] = 'D';
                plante[3] = 'H';
                plante[4] = 'T';
                compteur = 5;
                break;
            default:
                plante[0] = 'P';
                plante[1] = 'S';
                plante[2] = 'B';
                compteur = 3;
                break;

        }
        return Arrays.copyOf(plante, compteur);
    }

    /**
     * fonction qui perrmet de faire la rotation entre une plante et une autre
     * plante du plateau
     *
     * @param coord coordonnes de la plante
     * @param vitalites la vitalité du plateau actuel
     * @param plateau le plateau de jeu
     * @param actions l'ensemble des actions possibles (en construction)
     * @param couleurJoueur La couleur du joueur actuel
     * @param compteurRouge le nombre de voisin rouge
     * @param compteurBleu le nombre de voisin bleu
     */
    void ajoutActionRotation(Coordonnees coord, ActionsPossibles actions, Vitalites vitalites, Case[][] plateau, char couleurJoueur, int compteurRouge, int compteurBleu, boolean eau) {
        int vitaliterR = vitalites.vitalitesRouge;
        int vitaliterB = vitalites.vitalitesBleu;
        int valeurAjouter = 0;
        if (eau) {
            valeurAjouter = 4;
        } else {
            valeurAjouter = 3;
        }
        char[] tab = listeDesPlantesPouvantRemplacer(plateau[coord.ligne][coord.colonne].espece);
        if (plateau[coord.ligne][coord.colonne].couleur == 'R' && couleurJoueur == 'R') {
            vitaliterR -= plateau[coord.ligne][coord.colonne].vitalite;
            vitaliterR += valeurAjouter + compteurRouge;
            for (int i = 0; i < tab.length; ++i) {
                String action = "R" + "" + tab[i] + coord.carLigne() + coord.carColonne() + ","
                        + (vitaliterR) + ","
                        + (vitaliterB);
                actions.ajouterAction(action);
            }
        } else if (plateau[coord.ligne][coord.colonne].couleur == 'B' && couleurJoueur == 'B') {
            vitaliterB -= plateau[coord.ligne][coord.colonne].vitalite;
            vitaliterB += valeurAjouter + compteurBleu;
            for (int i = 0; i < tab.length; ++i) {
                String action = "R" + "" + tab[i] + coord.carLigne() + coord.carColonne() + ","
                        + (vitaliterR) + ","
                        + (vitaliterB);
                actions.ajouterAction(action);
            }
        }

    }
}
