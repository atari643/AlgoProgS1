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
                } else {
                    couperArbre(plateau, vitalites);
                    for (Direction d : Direction.values()) {
                        couperArbre(plateau, vitalites);
                    }
                }
            }
        }
            System.out.println("actionsPossibles : fin");

            return actions.nettoyer();
        }
        
 

    void couperArbre(Case[][] plateau, Vitalites vitalite) {
        
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
            Vitalites vitalites, char couleur) {
        int vitaliterR = 0;
        int vitaliterB = 0;
        if (couleur == 'R') {
            vitaliterR -= 1;
        } else {
            vitaliterB -= 1;
        }
        String action = "C" + coord.carLigne() + coord.carColonne() + ","
                + (vitalites.vitalitesRouge + vitaliterR) + ","
                + (vitalites.vitalitesBleu + vitaliterB);
        actions.ajouterAction(action);
    }
}
