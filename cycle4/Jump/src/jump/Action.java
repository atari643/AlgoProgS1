package jump;

/**
 * Action d'un joueur.
 */
public enum Action {

    /**
     * Demander le début de la partie.
     */
    DEMARRER,
    /**
     * Sauter sur la plateforme du dessus.
     */
    HAUT,
    /**
     * Sauter sur la plateforme du dessous.
     */
    BAS,
    /**
     * Rester sur la plateforme courante.
     */
    RESTER,
    /**
     * Sauvegarder la partie en cours.
     */
    SAUVEGARDER,
    /**
     * Restaurer une partie sauvegardée.
     */
    RESTAURER,
    /**
     * Quitter la partie.
     */
    QUITTER;

    /**
     * Action correspondant à un caractère saisi au clavier.
     *
     * @param reponse le caractère saisi par le joueur
     * @return l'action correspondante
     */
    static Action depuisCaractere(char reponse) {
        Action action = null;
        switch (reponse) {
            case UI.CAR_DEMARRER:
                action = DEMARRER;
                break;
            case UI.CAR_HAUT:
                action = HAUT;
                break;
            case UI.CAR_RESTER:
                action = RESTER;
                break;
            case UI.CAR_BAS:
                action = BAS;
                break;
            case UI.CAR_SAUVEGARDER:
                action = SAUVEGARDER;
                break;
            case UI.CAR_RESTAURER:
                action = RESTAURER;
                break;
            case UI.CAR_QUITTER:
                action = QUITTER;
                break;
            default:
                break;
        }
        return action;
    }

    /**
     * Retourner le caractère (saisi par l'utilisateur) associé à une action.
     *
     * @return le caractère associé à l'action
     */
    char caractere() {
        char c = ' ';
        switch (this) {
            case DEMARRER:
                c = UI.CAR_DEMARRER;
                break;
            case HAUT:
                c = UI.CAR_HAUT;
                break;
            case RESTER:
                c = UI.CAR_RESTER;
                break;
            case BAS:
                c = UI.CAR_BAS;
                break;
            case SAUVEGARDER:
                c = UI.CAR_SAUVEGARDER;
                break;
            case RESTAURER:
                c = UI.CAR_RESTAURER;
                break;
            case QUITTER:
                c = UI.CAR_QUITTER;
                break;
            default:
                System.out.println("Cas non géré : " + this);
                break;
        }
        return c;
    }

    /**
     * Une description textuelle de l'action.
     *
     * @return la description de l'action
     */
    @Override
    public String toString() {
        String desc = "";
        switch (this) {
            case DEMARRER:
                desc = "démarrer";
                break;
            case HAUT:
                desc = "haut";
                break;
            case RESTER:
                desc = "rester";
                break;
            case BAS:
                desc = "bas";
                break;
            case SAUVEGARDER:
                desc = "sauvegarder";
                break;
            case RESTAURER:
                desc = "restaurer";
                break;
            case QUITTER:
                desc = "quitter";
                break;
            default:
                System.out.println("Cas non géré : " + this);
                break;
        }
        return desc;
    }
}
