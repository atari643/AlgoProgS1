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
                if (plateau[coord.ligne][coord.colonne].plantePresente() == false) {
                    for (int i = 0; i < v.length; i++) {
                        if (plateau[v[i].ligne][v[i].colonne].plantePresente()) {
                            compteur += 1;
                            boolean t = avoir3Voisines(v[i], 14, plateau);
                            if (plateau[v[i].ligne][v[i].colonne].couleur == 'B') {
                                if (couleurJoueur == 'B') {
                                    vitalites.vitalitesBleu += 1;
                                }
                                if (t) {
                                    vitalites.vitalitesBleu -= plateau[v[i].ligne][v[i].colonne].vitalite;
                                }
                            } else if (plateau[v[i].ligne][v[i].colonne].couleur == 'R') {
                                if (couleurJoueur == 'R') {
                                    vitalites.vitalitesRouge += 1;
                                }
                                if (t) {
                                    vitalites.vitalitesRouge -= plateau[v[i].ligne][v[i].colonne].vitalite;
                                }
                            }
                        }
                    }
                    if (compteur < 4) {
                        for (Plante p : Plante.values()) {
                            ajoutAction(coord, actions, vitalites, couleurJoueur, p);
                        }
                    }
                } else if (plateau[coord.ligne][coord.colonne].plantePresente()) {
                    ajoutActionCouper(coord, actions, vitalites, plateau[lig][col].couleur, plateau);
                    ajoutActionFertiliser(coord, actions, vitalites, plateau);
                    for (int i = 0; i < v.length; i++) {
                        if (plateau[v[i].ligne][v[i].colonne].plantePresente()) {
                            compteur += 1;
                        }
                    }
                        switch (plateau[coord.ligne][coord.colonne].espece) {
                            case 'H':
                            case 'T':
                                if (minimum1voisinDeMemeEspece(coord, plateau, v)) {
                                    ajoutActionDisséminer(coord, actions, vitalites, couleurJoueur, plateau, v, compteur);
                                }
                                break;
                            default:
                                ajoutActionDisséminer(coord, actions, vitalites, couleurJoueur, plateau, v, compteur);
                                break;
                        }

                    }
                    vitalites = vitalitesPlateau(plateau);
                }
            }
            System.out.println("actionsPossibles : fin");
            return actions.nettoyer();
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
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                if (plateau[coord.ligne][coord.colonne].plantePresente() == true) {
                    if (plateau[coord.ligne][coord.colonne].couleur == 'R') {
                        vitaliterR += plateau[coord.ligne][coord.colonne].vitalite;
                    } else {
                        vitaliterB += plateau[coord.ligne][coord.colonne].vitalite;
                    }
                }
            }
        }
        return new Vitalites(vitaliterR, vitaliterB);
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
        int vitaliterR = 0;
        int vitaliterB = 0;
        if (couleur == 'R') {
            vitaliterR += 1;
        } else if (couleur == 'B') {
            vitaliterB += 1;
        }
        String action = "" + initiale(p) + coord.carLigne() + coord.carColonne() + ","
                + (vitalites.vitalitesRouge + vitaliterR) + ","
                + (vitalites.vitalitesBleu + vitaliterB);
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
        if (plateau[coord.ligne][coord.colonne].vitalite + valeurAjouter >= 9) {
            valeurAjouter = 9 - plateau[coord.ligne][coord.colonne].vitalite;
        }
        if (plateau[coord.ligne][coord.colonne].couleur == 'R') {
            vitaliterR += valeurAjouter;
        } else {
            vitaliterB += valeurAjouter;
        }
        String action = "F" + coord.carLigne() + coord.carColonne() + ","
                + (vitaliterR) + ","
                + (vitaliterB);
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
        int compteur = 0;
        for (int i = 0; i < voisine.length; i++) {
            if (plateau[voisine[i].ligne][voisine[i].colonne].vitalite < minimum
                    && plateau[voisine[i].ligne][voisine[i].colonne].espece == espece) {
                minimum = plateau[voisine[i].ligne][voisine[i].colonne].vitalite;
                compteur += 1;
            }
        }
        return minimum;
    }
    /**
     * Fonction qui vérifie sur une plante à au moins 1 voisin de même espèce
     * @param coord la case 
     * @param plateau le jeu
     * @param voisine la liste des voisin
     * @return vrai ssi il y a au moins 1 voisin de même espèce sinon faux
     */
    static boolean minimum1voisinDeMemeEspece(Coordonnees coord, Case[][] plateau, Coordonnees[] voisine) {
        int compteur = 0;
        boolean statue = false;
        char espece = plateau[coord.ligne][coord.colonne].espece;
        while (compteur<voisine.length && !statue) {
            if (plateau[voisine[compteur].ligne][voisine[compteur].colonne].espece == espece) {
                statue = true;
            }
            compteur++;
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
        int vitaliterR = vitalites.vitalitesRouge;
        int vitaliterB = vitalites.vitalitesBleu;
        int valeurAjouter = 0;
        int voisinVide = voisine.length-nbVoisin;
        switch (plateau[coord.ligne][coord.colonne].espece) {
            case 'H':
            case 'T':
                valeurAjouter = minimumVitalite(plateau, coord, voisine);
                break;
            default:
                valeurAjouter = 1;
                break;
        }

        valeurAjouter *= voisinVide;
        if (couleurJ == 'R') {
            vitaliterR += valeurAjouter;
        } else if (couleurJ == 'B') {
            vitaliterB += valeurAjouter;
        }
        String action = "I" + coord.carLigne() + coord.carColonne() + ","
                + (vitaliterR) + ","
                + (vitaliterB);
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

        if (couleur == 'R') {
            vitaliterR -= plateau[coord.ligne][coord.colonne].vitalite;
        } else {
            vitaliterB -= plateau[coord.ligne][coord.colonne].vitalite;
        }

        Coordonnees[] v = voisines(coord, 14);

        for (int i = 0; i < v.length; i++) {
            if (plateau[v[i].ligne][v[i].colonne].plantePresente()) {
                if (plateau[v[i].ligne][v[i].colonne].vitalite < 9) {
                    if (plateau[v[i].ligne][v[i].colonne].couleur == 'R') {
                        vitaliterR += 1;
                    } else if (plateau[v[i].ligne][v[i].colonne].couleur == 'B') {
                        vitaliterB += 1;
                    }
                }
            }
        }
        String action = "C" + coord.carLigne() + coord.carColonne() + ","
                + (vitaliterR) + ","
                + (vitaliterB);
        actions.ajouterAction(action);

    }
}
