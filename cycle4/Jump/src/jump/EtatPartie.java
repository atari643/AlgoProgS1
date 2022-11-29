package jump;

/**
 * État d'une partie.
 */
enum EtatPartie {
    /**
     * Attente de connexion de tous les joueurs.
     */
    ATTENTE_CONNEXIONS,
    /**
     * Partie en cours.
     */
    EN_COURS,
    /**
     * Partie terminée.
     */
    TERMINEE;
}
