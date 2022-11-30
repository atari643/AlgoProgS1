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
        // ajout des actions "planter pommier"
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                if (plateau[coord.ligne][coord.colonne].plantePresente() == false) {
                    Coordonnees[] v = voisines(coord, 14);
                    int compteur = 0;
                    for (int i = 0; i < v.length; i++) {
                        if (plateau[v[i].ligne][v[i].colonne].plantePresente()) {
                            compteur += 1;
                            boolean t = avoir3Voisines(v[i], 14, plateau);
                            if (plateau[v[i].ligne][v[i].colonne].couleur == 'B') {
                                if (couleurJoueur == 'B'){
                                    vitalites.vitalitesBleu += 1;
                                }
                                if(t){
                                    vitalites.vitalitesBleu-=plateau[v[i].ligne][v[i].colonne].vitalite;
                                }
                            } else if (plateau[v[i].ligne][v[i].colonne].couleur == 'R') {
                                if (couleurJoueur == 'R'){
                                    vitalites.vitalitesRouge += 1;
                                }
                                if(t){
                                    vitalites.vitalitesRouge-=plateau[v[i].ligne][v[i].colonne].vitalite;
                                }
                            }
                        }
                    }
                    if (compteur < 4) {
                        for (Plante p : Plante.values()){
                            ajoutAction(coord, actions, vitalites, couleurJoueur, p);}
                    }
                    vitalites = vitalitesPlateau(plateau);
                } else if (plateau[coord.ligne][coord.colonne].plantePresente()) {
                    ajoutActionCouper(coord, actions, vitalites, plateau[lig][col].couleur, plateau);
                }
            }
        }
        System.out.println("actionsPossibles : fin");
        return actions.nettoyer();
    }
    /**
     * Fonction qui vérifie qu'un case est uniquement 3 voisins
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
     * Ajout d'une action de plantation de pommier dans l'ensemble des actions
     * possibles.
     *
     * @param coord coordonnées de la case où planter le pommier
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer
     * l'action
     * @param couleur la couleur du pommier à ajouter
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
        String action = initiale(p) + coord.carLigne() + coord.carColonne() + ","
                + (vitalites.vitalitesRouge + vitaliterR) + ","
                + (vitalites.vitalitesBleu + vitaliterB);
        actions.ajouterAction(action);
    }
    
    /**
     * Donne les initiale de chaque plante
     *
     * @return l'initiale de la plante
     */
    String initiale(Plante plante) {
        String letter = " ";
        switch (plante) {
            case POMMIER:
                letter = "P";
                break;
            case SUREAU:
                letter = "S";
                break;
            case FRANBOISIER:
                letter = "B";
                break;
            case HARICOTS:
                letter = "H";
                break;
            case POMMESDETERRE:
                letter = "D";
                break;
            case TOMATES:
                letter = "T";
                break;
            default:
                letter = "?";
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
