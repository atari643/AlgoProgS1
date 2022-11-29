package jump;

/**
 * Résultats possibles d'une connexion.
 */
public enum ResultatAjout {
    /**
     * Connexion acceptée
     */
    ACCEPTE,
    /**
     * Capacité maximale du serveur atteinte
     */
    REFUS_SERVEUR_PLEIN,
    /**
     * Nom de joueur déjà utilisé
     */
    REFUS_NOM_UTILISE,
    /**
     * Le format du nom du joueur est incorrect
     */
    REFUS_FORMAT_NOM_INCORRECT,
    /**
     * La partie a déjà commencé
     */
    REFUS_PARTIE_COMMENCEE;
    
    /**
     * Motif d'un refus de connexion.
     * 
     * @return le motif sous forme d'une chaîne de caractères
     */
    String motifRefus() {
        String motif = null;
        switch (this) {
            case REFUS_SERVEUR_PLEIN:
                motif = "capacité maximale du serveur atteinte";
                break;
            case REFUS_NOM_UTILISE:
                motif = "nom de joueur déjà utilisé";
                break;
            case REFUS_FORMAT_NOM_INCORRECT:
                motif = "le nom de joueur ne commence pas par une majuscule";
                break;
            case REFUS_PARTIE_COMMENCEE:
                motif = "la partie a déjà commencé";
                break;
            case ACCEPTE:
                motif = "";
                break;
            default:
                System.out.println("Cas non géré");
                break;
        }
        return motif;
    }

    /**
     * Affiche le résultat de la connexion, côté serveur.
     * 
     * @param nomJoueur le nom du joueur souhaitant se connecter
     */
    void afficherServeur(String nomJoueur) {
        switch (this) {
            case REFUS_SERVEUR_PLEIN:
            case REFUS_NOM_UTILISE:
            case REFUS_FORMAT_NOM_INCORRECT:
            case REFUS_PARTIE_COMMENCEE:
                Serveur.log(TypeLog.INFO, "connexion refusée : " + motifRefus());
                break;
            case ACCEPTE:
                Serveur.log(TypeLog.AJOUT, "connexion : " + nomJoueur);
                break;
            default:
                Serveur.log(TypeLog.ERREUR, "Cas non géré : " + this);
                break;
        }
    }

    /**
     * Affiche le résultat de la connexion, côté client.
     */
    void afficherClient() {
        switch (this) {
            case REFUS_SERVEUR_PLEIN:
            case REFUS_NOM_UTILISE:
            case REFUS_FORMAT_NOM_INCORRECT:
            case REFUS_PARTIE_COMMENCEE:
                System.out.println(". connexion refusée : " + motifRefus());
                break;
            case ACCEPTE:
                break;
            default:
                System.out.println(". cas non géré : " + this);
                break;
        }
    }
}
