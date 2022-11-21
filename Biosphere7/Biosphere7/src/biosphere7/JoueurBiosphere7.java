package biosphere7;

import java.text.SimpleDateFormat;
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
                    ajoutActionPommier(coord, actions, vitalites, couleurJoueur);
                } else if (plateau[coord.ligne][coord.colonne].plantePresente() == true) {
                    ajoutActionCouper(coord, actions, vitalites, plateau[lig][col].couleur, plateau);
                }
            }
        }
        System.out.println("actionsPossibles : fin");

        return actions.nettoyer();
    }

    void vitaliteAutour(Case[][] plateau, Vitalites vitalite, Coordonnees coord) {
        System.out.println(voisines(coord, 14)[0].ligne);
        System.out.println(voisines(coord, 14)[1].colonne);
        for (int i = 0; i<2; i++){
            if (plateau[voisines(coord, 14)[i].ligne][voisines(coord, 14)[i].colonne].couleur=='R'){
                vitalite.vitalitesRouge+=1;
            }else{
                vitalite.vitalitesBleu+=1;
            }
        }
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
        for(Direction d : Direction.values()){
              if (estDansPlateau(suivante(coord, d), taille)){
                  voisines[nbVoisines]=suivante(coord, d);
                  nbVoisines+=1;
              }
        }
        return voisines;
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
                        vitaliterR += 1;
                    } else {
                        vitaliterB += 1;
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
    void ajoutActionPommier(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, char couleur) {
        int vitaliterR = 0;
        int vitaliterB = 0;
        if (couleur == 'R') {
            vitaliterR = 1;
        } else {
            vitaliterB = 1;
        }
        String action = "P" + coord.carLigne() + coord.carColonne() + ","
                + (vitalites.vitalitesRouge + vitaliterR) + ","
                + (vitalites.vitalitesBleu + vitaliterB);
        actions.ajouterAction(action);
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
        if (couleur == 'R') {
            vitalites.vitalitesRouge -= 1;
        } else {
            vitalites.vitalitesBleu -= 1;
        }
        vitaliteAutour(plateau, vitalites, coord);
        String action = "C" + coord.carLigne() + coord.carColonne() + ","
                + (vitalites.vitalitesRouge) + ","
                + (vitalites.vitalitesBleu);
        actions.ajouterAction(action);
    }
}
