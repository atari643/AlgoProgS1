package jump;

/**
 * Statut d'un joueur en cours de partie.
 */
enum StatutJoueur {
    /**
     * Le joueur a déjà joué, pendant le tour de jeu courant.
     */
    A_JOUE,
    /**
     * Le joueur doit jouer, pendant le tour de jeu courant.
     */
    DOIT_JOUER,
    /**
     * Le joueur a quitté la partie (volontairement, ou a perdu).
     */
    A_QUITTE;
    
    /**
     * Sérialiser le statut du joueur.
     * 
     * @return une chaîne représentant le statut du joueur
     */
    String serialiser() {
        String s = null;
        switch (this) {
            case A_JOUE:
                s = "A";
                break;
            case DOIT_JOUER:
                s = "D";
                break;
            case A_QUITTE:
                s = "Q";
                break;
            default:
                System.out.println("Cas non géré : " + this);
                break;
        }
        return s;
    }
    
    /**
     * Désérialiser le statut du joueur.
     * 
     * @param serial la sérialisation
     * @return le statut correspondant
     */
    static StatutJoueur deserialiser(String serial) {
        StatutJoueur statut = null;
        char serialChar = serial.charAt(0);
        switch (serialChar) {
            case 'A':
                statut = A_JOUE;
                break;
            case 'D':
                statut = DOIT_JOUER;
                break;
            case 'Q':
                statut = A_QUITTE;
                break;
            default:
                System.out.println("Cas non géré : " + serialChar);
                break;
        }
        return statut;
    }
}
