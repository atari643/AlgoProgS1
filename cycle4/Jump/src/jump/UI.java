package jump;

import java.util.Scanner;

/**
 * Interface texte.
 */
class UI {

    /**
     * Caractère indiquant que le joueur a déjà joué.
     */
    final static char CAR_A_JOUE = ' ';

    /**
     * Caractère indiquant que le joueur doit encore envoyer son action.
     */
    final static char CAR_DOIT_JOUER = '*';

    /**
     * Caractère indiquant que le joueur a quitté la partie.
     */
    final static char CAR_A_QUITTE = '-';

    /**
     * Caractère saisi par l'utilisateur pour démarrer la partie.
     */
    final static char CAR_DEMARRER = 'd';

    /**
     * Caractère saisi par l'utilisateur pour démarrer l'action "haut".
     */
    final static char CAR_HAUT = 'z';

    /**
     * Caractère saisi par l'utilisateur pour démarrer l'action "rester".
     */
    final static char CAR_RESTER = 's';

    /**
     * Caractère saisi par l'utilisateur pour démarrer l'action "bas".
     */
    final static char CAR_BAS = 'x';

    /**
     * Caractère saisi par l'utilisateur pour démarrer l'action "sauvegarder".
     */
    final static char CAR_SAUVEGARDER = 'w';

    /**
     * Caractère saisi par l'utilisateur pour démarrer l'action "restaurer".
     */
    final static char CAR_RESTAURER = 'r';

    /**
     * Caractère saisi par l'utilisateur pour démarrer l'action "quitter".
     */
    final static char CAR_QUITTER = 'q';

    /**
     * La vue globale de l'état du jeu, affichée sur les clients.
     *
     * @param partie la partie en cours
     * @return une chaîne présentant l'état de la partie sur les clients
     */
    static String vueGlobale(Partie partie) {
        // on calcule les 2 parties : gauche (plateau) et droite (scores)
        String partieGauche = partie.plateau.toString();
        String partieDroite = partieDroite(partie);
        // on fusionne ces 2 parties
        StringBuilder vue = new StringBuilder();
        Scanner scanGauche = new Scanner(partieGauche);
        scanGauche.useDelimiter(System.lineSeparator());
        Scanner scanDroite = new Scanner(partieDroite);
        scanDroite.useDelimiter(System.lineSeparator());
        // on suppose que la partie gauche (plateau) a plus de lignes que
        // celle de droite (joueurs).
        while (scanGauche.hasNext()) {
            vue.append(scanGauche.next());
            if (scanDroite.hasNext()) {
                vue.append(" ");
                vue.append(scanDroite.next());
            }
            vue.append(System.lineSeparator());
        }
        return vue.toString();
    }

    /**
     * La partie droite, contenant la liste des joueurs et les actions.
     *
     * @param partie la partie en cours
     * @return partie droite de l'affichage
     */
    static String partieDroite(Partie partie) {
        return new StringBuilder()
                .append("Tour #").append(partie.numTour)
                .append(System.lineSeparator())
                .append(tableauJoueurs(partie))
                .append(System.lineSeparator())
                .append(listeActions(partie))
                .toString();
    }

    /**
     * Tableau des joueurs et de leur score, affiché sur la droite.
     *
     * @param partie la partie en cours
     * @return l'affichage du tableau des joueurs
     */
    static StringBuilder tableauJoueurs(Partie partie) {
        StringBuilder tabJoueurs = new StringBuilder();
        tabJoueurs
                .append(" ").append(System.lineSeparator())
                .append("    Joueurs    | Scores ").append(System.lineSeparator())
                .append("---------------+--------").append(System.lineSeparator());
        for (int i = 0; i < partie.nbJoueurs; i++) {
            Joueur joueur = partie.joueurs[i];
            tabJoueurs.append(ligneJoueur(joueur));
        }
        return tabJoueurs;
    }

    /**
     * Renvoie la ligne affichée pour un joueur.
     *
     * @param joueur le joueur à afficher
     * @return la ligne affichée pour ce joueur
     */
    static String ligneJoueur(Joueur joueur) {
        return " " + carStatutJoueur(joueur) + "  " + joueur.nom + " | " 
                + joueur.score + System.lineSeparator();
    }

    /**
     * Liste des actions possibles.
     *
     * @param partie la partie en cours
     * @return l'affichage de la liste des actions possibles
     */
    static StringBuilder listeActions(Partie partie) {
        StringBuilder actions = new StringBuilder();
        actions.append(System.lineSeparator());
        actions.append("Actions possibles :").append(System.lineSeparator());
        switch (partie.etat) {
            case ATTENTE_CONNEXIONS:
                actions.append(listeAction(Action.DEMARRER));
                break;
            case EN_COURS:
                actions.append(listeAction(Action.HAUT));
                actions.append(listeAction(Action.RESTER));
                actions.append(listeAction(Action.BAS));
                actions.append(listeAction(Action.SAUVEGARDER));
                actions.append(listeAction(Action.RESTAURER));
                break;
            case TERMINEE:
                break;
            default:
                System.out.println("Cas non géré : " + partie.etat);
                break;
        }
        actions.append(listeAction(Action.QUITTER));
        return actions;
    }

    /**
     * Chaîne représentant une action dans la liste des actions à droite.
     * 
     * @param action l'action à afficher
     * @return la chaîne représentant cette action
     */
    static String listeAction(Action action) {
        return "[" + action.caractere() + "] " + action.toString() 
                + System.lineSeparator();
    }

    /**
     * Caractère représentant le statut d'un joueur.
     *
     * @param joueur le joueur considéré
     * @return le caractère décrivant le statut du joueur
     */
    static char carStatutJoueur(Joueur joueur) {
        char carStatut = ' ';
        switch (joueur.statut) {
            case DOIT_JOUER:
                carStatut = CAR_DOIT_JOUER;
                break;
            case A_JOUE:
                carStatut = CAR_A_JOUE;
                break;
            case A_QUITTE:
                carStatut = CAR_A_QUITTE;
                break;
            default:
                System.out.println("Erreur, cas non géré : " + joueur.statut);
                break;
        }
        return carStatut;
    }
}
